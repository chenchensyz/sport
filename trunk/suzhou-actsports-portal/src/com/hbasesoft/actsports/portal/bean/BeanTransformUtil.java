/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.bean;

import java.util.Date;

import com.hbasesoft.actsports.portal.biz.vo.Ad;
import com.hbasesoft.actsports.portal.biz.vo.Article;
import com.hbasesoft.actsports.portal.biz.vo.CmsWeixinAdPojo;
import com.hbasesoft.actsports.portal.biz.vo.CmsWeixinArticle;
import com.hbasesoft.actsports.portal.biz.vo.CmsWeixinArticlePojo;
import com.hbasesoft.actsports.portal.biz.vo.CmsWeixinMenuPojo;
import com.hbasesoft.actsports.portal.biz.vo.Faq;
import com.hbasesoft.actsports.portal.biz.vo.Menu;
import com.hbasesoft.actsports.portal.biz.vo.NewsItem;
import com.hbasesoft.actsports.portal.biz.vo.UserInfo;
import com.hbasesoft.actsports.portal.constant.CommonContant;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.date.DateConstants;
import com.hbasesoft.framework.common.utils.date.DateUtil;
import com.hbasesoft.vcc.wechat.bean.NewsitemPojo;


/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月14日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.cbs.bean <br>
 */
public class BeanTransformUtil {
    public static ActUserAccountPojo getUserAccount(String userId, String account, String accType, ActUserAccountPojo pojo) {
        if (pojo == null) {
            pojo = new ActUserAccountPojo();
            pojo.setCreateTime(new Date());
            pojo.setState(CommonContant.AVALIABLE);
            pojo.setBlackList("Y");
        }
        if (CommonUtil.isNotEmpty(userId)) {
            pojo.setUserId(userId);
        }
        pojo.setAccount(account);
        pojo.setType(accType);
        return pojo;
    }

    public static ActUserPojo userInfo2User(UserInfo userInfo, ActUserPojo pojo) {
        if (pojo == null) {
            pojo = new ActUserPojo();
            pojo.setCount(0L);
            Date currentDate = DateUtil.getCurrentDate();
            pojo.setCreateTime(currentDate);
            pojo.setState(CommonContant.USER_STATE_INIT);
            pojo.setLastLoginTime(currentDate);
        }
        pojo.setBrowser(userInfo.getBrowser());
        pojo.setIp(userInfo.getLoginIp());
        if (CommonUtil.isNotEmpty(userInfo.getUserId())) {
            pojo.setId(userInfo.getUserId());
        }
        if (CommonUtil.isNotEmpty(userInfo.getDefaultOrgId())) {
            pojo.setDefaultOrgId(pojo.getDefaultOrgId());
        }
        if (CommonUtil.isNotEmpty(userInfo.getState())) {
            pojo.setState(userInfo.getState());
        }
        return pojo;
    }

    public static UserInfo user2UserInfo(ActUserPojo pojo, UserInfo userInfo) {
        if (userInfo == null) {
            userInfo = new UserInfo();
        }
        userInfo.setState(pojo.getState());
        userInfo.setUserId(pojo.getId());
        userInfo.setBrowser(pojo.getBrowser());
        userInfo.setLoginIp(pojo.getIp());
        userInfo.setDefaultOrgId(pojo.getDefaultOrgId());
        return userInfo;
    }
    
    public static Faq cmsArticlePojo2Faq(CmsWeixinArticlePojo pojo, Faq faq) {
        if (faq == null) {
            faq = new Faq();
        }
        faq.setFaqId(pojo.getId());
        faq.setQuestion(pojo.getTitle());
        faq.setUrl(pojo.getUrl());
        return faq;
    }
    
    public static Article cmsArticlePojo2Aricle(CmsWeixinArticlePojo pojo, Article article) {
        if (article == null) {
            article = new Article();
        }
        article.setAccountId(pojo.getAccountid());
        article.setArticleSort(pojo.getArticleSort());
        article.setColumnId(pojo.getColumnId());
        article.setContent(pojo.getContent());
        article.setCreateBy(pojo.getCreateBy());
        article.setCreateDate(DateUtil.date2String(pojo.getCreateDate(), DateConstants.DATETIME_FORMAT_14));
        article.setCreateName(pojo.getCreateName());
        article.setId(pojo.getId());
        article.setImageName(pojo.getImageName());
        article.setOrgCode(pojo.getOrgCode());
        article.setPriceRange(pojo.getPriceRange());
        article.setSummary(pojo.getSummary());
        article.setTitle(pojo.getTitle());
        return article;
    }
    
    public static Menu cmsMenuPojo2Menu(CmsWeixinMenuPojo menuPojo, Menu menu) {
        if (menu == null) {
            menu = new Menu();
        }
        menu.setId(menuPojo.getId());
        menu.setCode(menuPojo.getMenuCode());
        menu.setName(menuPojo.getName());
        return menu;
    }
    
    public static CmsWeixinArticle cmsArticlePojo2CmsWeixinArticle(CmsWeixinArticlePojo pojo,
        CmsWeixinArticle article) {
        String time = DateUtil.date2String(pojo.getCreateDate(), DateConstants.DATE_FORMAT_11);
        if (article == null) {
            article = new CmsWeixinArticle();
        }
        article.setImageHref(pojo.getImageHref());
        article.setImageName(pojo.getImageName());
        article.setCreateName(pojo.getCreateName());
        article.setSummary(pojo.getSummary());
        article.setParentId(pojo.getParentId());
        article.setTitle(pojo.getTitle());
        article.setCreateDate(time);
        article.setContent(pojo.getContent());
        return article;
    }
    
    public static NewsItem newsItemPojo2NewsItem(NewsitemPojo pojo,
        NewsItem newsItem) {
        String time = DateUtil.date2String(pojo.getCreateDate(), DateConstants.DATE_FORMAT_11);
        if (newsItem == null) {
            newsItem = new NewsItem();
        }
        newsItem.setAuthor(pojo.getAuthor());
        newsItem.setContent(pojo.getContent());
        newsItem.setCreateDate(time);
        newsItem.setId(pojo.getId());
        newsItem.setDescription(pojo.getDescription());
        newsItem.setImagepath(pojo.getImagepath());
        newsItem.setNewType(pojo.getNewType());
        newsItem.setOrders(pojo.getOrders());
        newsItem.setTemplateid(pojo.getTemplateid());
        newsItem.setTitle(pojo.getTitle());
        newsItem.setUrl(pojo.getUrl());
        
        return newsItem;
    }
    
    public static Ad cmsAdPojo2Ad(CmsWeixinAdPojo adPojo, Ad ad) {
        if (ad == null) {
            ad = new Ad();
        }
        ad.setAccountId(adPojo.getAccountid());
        ad.setCreateBy(adPojo.getCreateBy());
        ad.setCreateDate(DateUtil.date2String(adPojo.getCreateDate()));
        ad.setCreateName(adPojo.getCreateName());
        ad.setId(adPojo.getId());
        ad.setImageHref(adPojo.getImageHref());
        ad.setImageName(adPojo.getImageName());
        ad.setOrgCode(adPojo.getOrgCode());
        ad.setTitle(adPojo.getTitle());
        return ad;
    }

}
