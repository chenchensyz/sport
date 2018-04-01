/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service.remote;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbasesoft.actsports.portal.bean.BeanTransformUtil;
import com.hbasesoft.actsports.portal.biz.vo.Ad;
import com.hbasesoft.actsports.portal.biz.vo.Article;
import com.hbasesoft.actsports.portal.biz.vo.CmsPageTemplatePojo;
import com.hbasesoft.actsports.portal.biz.vo.CmsWeixinAdPojo;
import com.hbasesoft.actsports.portal.biz.vo.CmsWeixinArticlePojo;
import com.hbasesoft.actsports.portal.biz.vo.CmsWeixinMenuPojo;
import com.hbasesoft.actsports.portal.biz.vo.Faq;
import com.hbasesoft.actsports.portal.biz.vo.Menu;
import com.hbasesoft.actsports.portal.biz.vo.NewsItem;
import com.hbasesoft.actsports.portal.biz.vo.PageTemplate;
import com.hbasesoft.actsports.portal.biz.vo.WeixinRelease;
import com.hbasesoft.actsports.portal.biz.vo.WeixinReleasePojo;
import com.hbasesoft.actsports.portal.constant.CacheCodeDef;
import com.hbasesoft.actsports.portal.constant.CommonContant;
import com.hbasesoft.actsports.portal.constant.ErrorCodeDef;
import com.hbasesoft.actsports.portal.service.CommonService;
import com.hbasesoft.actsports.portal.service.VoteInfoService;
import com.hbasesoft.actsports.portal.service.api.ConfigService;
import com.hbasesoft.actsports.portal.service.api.FaqService;
import com.hbasesoft.framework.cache.core.annotation.Cache;
import com.hbasesoft.framework.cache.core.annotation.CacheMethodConfig;
import com.hbasesoft.framework.cache.core.annotation.CacheProxy;
import com.hbasesoft.framework.cache.core.annotation.Key;
import com.hbasesoft.framework.common.RemoteServiceException;
import com.hbasesoft.framework.common.ServiceException;
import com.hbasesoft.framework.common.utils.Assert;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.PropertyHolder;
import com.hbasesoft.framework.common.utils.logger.Logger;
import com.hbasesoft.vcc.wechat.bean.NewsitemPojo;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月27日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.cbs.rmi <br>
 */
@Transactional(readOnly = true)
@Service("FaqRemoteService")
public class FaqRemoteService implements FaqService {

    private static Logger logger = new Logger(FaqRemoteService.class);

    private ConfigService configService;

    @Resource(name = "commonService")
    private CommonService commonService;

    @Resource
    private VoteInfoService voteInfoService;
    
    @Resource
    private com.hbasesoft.actsports.portal.service.FaqInfoService faqService;

    /**
     * Description: <br>
     * 
     * @author <br>
     * @taskId <br>
     * @param orgId
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws TException <br>
     */
    @Override
    @Cache(node = CacheCodeDef.FAQ_INFO, expireTime = CacheCodeDef.CONFIG_SAVE_TIME)
    public List<Faq> queryFaqByCode(String orgId, String code, int pageIndex, int pageSize)
        throws RemoteServiceException {
        try {

            String orgCode = configService.getOrgCodeByOrgId(orgId);
            CmsWeixinMenuPojo menu = faqService.getMenuByOrgCodeAndMenuCode(orgCode, code);
            List<Faq> faqList = new ArrayList<Faq>();
            if (CommonUtil.isNull(menu)) {
                menu = new CmsWeixinMenuPojo();
            }
            DetachedCriteria criteria = DetachedCriteria.forClass(CmsWeixinArticlePojo.class);
            criteria.add(Restrictions.eq(CmsWeixinArticlePojo.COLUMN_ID, menu.getId()));
            criteria.addOrder(Order.desc(CmsWeixinArticlePojo.CREATE_DATE));

            List<CmsWeixinArticlePojo> pojoList = commonService.getPageList(criteria, pageIndex, pageSize);
            if (CommonUtil.isNotEmpty(pojoList)) {
                for (CmsWeixinArticlePojo pojo : pojoList) {
                    if (CommonUtil.isNotEmpty(pojo.getContent()) || CommonUtil.isEmpty(pojo.getUrl())) {
                        pojo.setUrl("true");
                    }
                    faqList.add(BeanTransformUtil.cmsArticlePojo2Faq(pojo, null));
                }
            }
            return faqList;
        }
        catch (Exception e) {
            logger.error(e);
            throw new RemoteServiceException(e);
        }
    }

    /**
     * Description: 查询文章列表<br>
     * 
     * @author liuxianan<br>
     * @taskId <br>
     * @param orgId
     * @param categoryCode
     * @param pageIndex
     * @param pageSize
     * @return <br>
     */
    public List<NewsItem> queryAritcleList(String orgId, String categoryCode, int pageIndex, int pageSize)
        throws RemoteServiceException {
        try {

            DetachedCriteria criteria = DetachedCriteria.forClass(CmsWeixinMenuPojo.class);
            criteria.add(Restrictions.eq(CmsWeixinMenuPojo.MENU_CODE, categoryCode));
            CmsWeixinMenuPojo menuPojo = faqService.getCriteriaQuery(criteria);

            List<NewsItem> newsItemList = new ArrayList<NewsItem>();

            if (menuPojo != null) {
                criteria = DetachedCriteria.forClass(WeixinReleasePojo.class);
                criteria.add(Restrictions.eq(WeixinReleasePojo.FATHER_ID, menuPojo.getId()));

                criteria.addOrder(Order.desc(WeixinReleasePojo.ORDERS));
                List<WeixinReleasePojo> pojoList = commonService.getPageList(criteria, pageIndex, pageSize);
                if (CommonUtil.isNotEmpty(pojoList)) {
                    for (WeixinReleasePojo pojo : pojoList) {
                        NewsitemPojo newsItemPojo = commonService.get(NewsitemPojo.class, pojo.getNewsId());
                        NewsItem newsItem = new NewsItem();
                        if (CommonUtil.isNotEmpty(newsItemPojo.getContent()) || CommonUtil.isEmpty(newsItemPojo.getUrl())) {
                            newsItem.setUrl("true");
                        }
                        newsItemList.add(newsItem);
                    }
                }
            }

            return newsItemList;
        }
        catch (Exception e) {
            logger.error(e);
            throw new RemoteServiceException(e);
        }

    }
    
    /**
     * 
     * Description: 根据code查询栏目<br> 
     *  
     * @author liuxianan<br>
     * @taskId <br>
     * @param orgId
     * @param categoryCode
     * @return
     * @throws RemoteServiceException <br>
     */
    public Menu queryMenuByCode(String categoryCode) throws RemoteServiceException {
    	 try {
             DetachedCriteria criteria = DetachedCriteria.forClass(CmsWeixinMenuPojo.class);
             criteria.add(Restrictions.eq(CmsWeixinMenuPojo.MENU_CODE, categoryCode));
             CmsWeixinMenuPojo menuPojo = faqService.getCriteriaQuery(criteria);
             Assert.notNull(menuPojo, ErrorCodeDef.WEIXIN_MENU_NOT_EMPTY, categoryCode);
             Menu menu = BeanTransformUtil.cmsMenuPojo2Menu(menuPojo, null);
             return menu;
         }
         catch (Exception e) {
             logger.error(e);
             throw new RemoteServiceException(e);
         }
    }
    
    /**
     * 
     * Description: 根据id查询栏目<br> 
     *  
     * @author liuxianan<br>
     * @taskId <br>
     * @param id
     * @return
     * @throws Exception <br>
     */
    @Override
	public Menu queryMenuById(String id) throws Exception {
    	CmsWeixinMenuPojo menuPojo = commonService.get(CmsWeixinMenuPojo.class, id);
    	Assert.notNull(menuPojo, ErrorCodeDef.WEIXIN_MENU_NOT_EMPTY, id);
    	Menu menu = BeanTransformUtil.cmsMenuPojo2Menu(menuPojo, null);
        return menu;
	}

    @Override
    public NewsItem queryArticleById(String id) throws Exception {
        NewsitemPojo pojo = commonService.get(NewsitemPojo.class, id);
        NewsItem newsItem = BeanTransformUtil.newsItemPojo2NewsItem(pojo, null);
        
        return newsItem;
    }
    
    @Override
    public Article queryCmsArticleById(String id) throws Exception {
        CmsWeixinArticlePojo pojo = commonService.get(CmsWeixinArticlePojo.class, id);
        Article article = BeanTransformUtil.cmsArticlePojo2Aricle(pojo, null);
        
        return article;
    }
    
    @Override
    public NewsItem queryArticleByReleaseId(String id) throws Exception {
        String serverPath = PropertyHolder.getProperty("server.wx.url");
        NewsitemPojo pojo = commonService.get(NewsitemPojo.class, id);
        NewsItem newsItem = BeanTransformUtil.newsItemPojo2NewsItem(pojo, null);
        if (CommonUtil.isNotEmpty(pojo.getContent()) || CommonUtil.isEmpty(pojo.getUrl())) {
            newsItem.setUrl(serverPath + "queryAritycleContent/" + pojo.getId());
        }

        return newsItem;
    }

    @CacheProxy(name = "ConfigRemoteService", expireTime = CacheCodeDef.CLIENT_CACHE_TIME, value = {
        @CacheMethodConfig("getDictNameByData"), @CacheMethodConfig("queryDictsByCode"),
        @CacheMethodConfig("queryOrgList"), @CacheMethodConfig("queryOrgConfigById"),
        @CacheMethodConfig("queryOrgConfigByCode"), @CacheMethodConfig("getConfigValueByCode"),
        @CacheMethodConfig("getOrgIdByOrgCode"), @CacheMethodConfig("getWechatAccessToken"),
        @CacheMethodConfig("getWechatJsApiTickit"), @CacheMethodConfig("getDeviceByCode"),
        @CacheMethodConfig("getApiChannel"),
        @CacheMethodConfig(value = "resetWechatAccessToken", cacheAble = false, removeMethods = {
            "getWechatAccessToken"
        }), @CacheMethodConfig(value = "resetWechatJsApiTickit", cacheAble = false, removeMethods = {
            "getWechatJsApiTickit"
        }), @CacheMethodConfig(value = "resetDeviceSessionKey", cacheAble = false, removeMethods = {
            "getDeviceByCode"
        })
    })

    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

	/**
	 * Description: <br> 
	 *  
	 * @author 查思玮<br>
	 * @taskId <br>
	 * @param templateCode
	 * @param orgCode
	 * @return
	 * @throws Exception <br>
	 */ 
	@Override
	public PageTemplate getPageTemplate(@Key("templateCode")String templateCode) throws Exception {
		String transactionID = CommonUtil.getTransactionID();
		logger.info("根据页面模板CODE查询信息,{0}|{1}", transactionID, templateCode);
		PageTemplate pageTemplate = new PageTemplate();
		
		// 获得页面模板
		DetachedCriteria criteria = DetachedCriteria.forClass(CmsWeixinMenuPojo.class);
        criteria.add(Restrictions.eq(CmsWeixinMenuPojo.MENU_CODE, templateCode));
        CmsWeixinMenuPojo pageTemplatePojo = faqService.getCriteriaQuery(criteria);
        if(null != pageTemplatePojo){
            pageTemplate.setCode(templateCode);
            pageTemplate.setId(pageTemplatePojo.getId());
            pageTemplate.setName(pageTemplatePojo.getName());
//            DetachedCriteria releaseCriteria = DetachedCriteria.forClass(WeixinReleasePojo.class);
//            releaseCriteria.addOrder(Order.asc(WeixinReleasePojo.ORDERS));
//            List<WeixinReleasePojo> weixinReleasePojoList = faqService.getListByCriteriaQuery(releaseCriteria);
            
            List<WeixinReleasePojo> weixinReleasePojoList = voteInfoService.getWexinReleaseList(templateCode);
            List<WeixinRelease> wrList = new ArrayList<>();
            if(CommonUtil.isNotEmpty(weixinReleasePojoList)){
            	WeixinRelease wr;
            	for(WeixinReleasePojo weixinRelease:weixinReleasePojoList){
            	    wr = new WeixinRelease();
            	    wr.setFatherId(weixinRelease.getFatherId());
            	    wr.setId(weixinRelease.getId());
            	    wr.setNewsId(weixinRelease.getNewsId());
            	    wr.setOrders(weixinRelease.getOrders());
            	    wr.setPosition(weixinRelease.getPosition());
            	    wrList.add(wr);
            	}
            }
            pageTemplate.setWeixinReleaseList(wrList);
        }
        
        logger.info("返回查询的模板信息,{0}|{1}", transactionID, templateCode, pageTemplate);
		return pageTemplate;
	}

	/**
	 * Description: <br> 
	 *  
	 * @author 查思玮<br>
	 * @taskId <br>
	 * @param contentId
	 * @param orgCode
	 * @param type
	 * @return
	 * @throws Exception <br>
	 */ 
	@Override
	public Menu queryMenu(String contentId, String orgCode, int pageIndex,
			int pageSize, String serverPath, String imagePath) throws Exception {
		
		String transactionID = CommonUtil.getTransactionID();
		logger.info("根据contentId与ORGCODE查询信息,{0}|{1}|{2}|{3}|{4}", transactionID, contentId, orgCode, pageIndex, pageSize);
		Menu menu;
		try {
			menu = queryMenuContent(contentId, orgCode, pageIndex, pageSize, serverPath, imagePath);
			
		} catch (Exception e) {
			throw new RemoteServiceException(e);
		}
		logger.info("根据contentId与ORGCODE查询信息出menu,{0}|{1}", transactionID, menu);
		return menu;
	}

	
	private Menu queryMenuContent(String contentId, String orgCode,
			int pageIndex, int pageSize, String serverPath, String imagePath){
	     CmsWeixinMenuPojo menuPojo = faqService.get(CmsWeixinMenuPojo.class, contentId);
         Menu menu = BeanTransformUtil.cmsMenuPojo2Menu(menuPojo, null);
         
         List<NewsItem> newsItemList = new ArrayList<NewsItem>();

         if (menu != null) {
        	 DetachedCriteria criteria = DetachedCriteria.forClass(WeixinReleasePojo.class);
             criteria.add(Restrictions.eq(WeixinReleasePojo.FATHER_ID, menuPojo.getId()));


             criteria.addOrder(Order.desc(WeixinReleasePojo.ORDERS));
             List<WeixinReleasePojo> pojoList = commonService.getPageList(criteria, pageIndex, pageSize);
             if (CommonUtil.isNotEmpty(pojoList)) {
            	 NewsItem newsItem;
                 for (WeixinReleasePojo pojo : pojoList) {
                     NewsitemPojo newsItemPojo = commonService.get(NewsitemPojo.class, pojo.getNewsId());
                     newsItem = new NewsItem();
                     newsItem.setId(newsItemPojo.getId());
                     newsItem.setTitle(newsItemPojo.getTitle());
                     
                     if (CommonUtil.isNotEmpty(newsItemPojo.getContent()) || CommonUtil.isEmpty(newsItemPojo.getUrl())) {
                         newsItem.setUrl(serverPath + "queryAritycleContent/" + pojo.getNewsId());
                     }
                     else{
                         newsItem.setUrl(newsItemPojo.getUrl()); 
                     }
                     if(CommonUtil.isNotEmpty(newsItemPojo.getImagepath())){
                         newsItem.setImagepath(imagePath+newsItemPojo.getImagepath());
                     }
                     newsItemList.add(newsItem);
                 }
             }
         }
         menu.setNewsItemList(newsItemList);
 		 return menu;
	}

	/**
	 * Description: <br> 
	 *  
	 * @author 查思玮<br>
	 * @taskId <br>
	 * @param contentId
	 * @param orgCode
	 * @return
	 * @throws Exception <br>
	 */ 
	@Override
	public Ad queryAd(String contentId, String orgCode) throws Exception {
		Ad ad = new Ad();
		
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(CmsWeixinAdPojo.class);
			criteria.add(Restrictions.eq(CmsWeixinAdPojo.ID, contentId));
			criteria.add(Restrictions.eq(CmsWeixinAdPojo.ORG_CODE, orgCode));
			CmsWeixinAdPojo adPojo = faqService.getCriteriaQuery(criteria);
			BeanTransformUtil.cmsAdPojo2Ad(adPojo, ad);
		} catch (Exception e) {
			throw new RemoteServiceException(e);
		}
		return ad;
	}

    

}
