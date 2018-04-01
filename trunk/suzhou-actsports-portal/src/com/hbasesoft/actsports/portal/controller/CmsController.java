package com.hbasesoft.actsports.portal.controller;


import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hbasesoft.actsports.portal.LoginOff;
import com.hbasesoft.actsports.portal.RestfulException;
import com.hbasesoft.actsports.portal.biz.vo.Ad;
import com.hbasesoft.actsports.portal.biz.vo.AjaxResp;
import com.hbasesoft.actsports.portal.biz.vo.Article;
import com.hbasesoft.actsports.portal.biz.vo.Menu;
import com.hbasesoft.actsports.portal.biz.vo.NewsItem;
import com.hbasesoft.actsports.portal.biz.vo.PageTemplate;
import com.hbasesoft.actsports.portal.biz.vo.WeixinReleasePojo;
import com.hbasesoft.actsports.portal.constant.CommonContant;
import com.hbasesoft.actsports.portal.constant.ErrorCodeDef;
import com.hbasesoft.actsports.portal.service.api.FaqService;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.PropertyHolder;
import com.hbasesoft.framework.common.utils.logger.LoggerUtil;




/**
 * <Description> 常见问题<br>
 * 
 * @author liuxianan<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月29日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.wx.controller <br>
 */
@Controller
public class CmsController extends BaseController {

    @Resource(name = "FaqRemoteService")
    private FaqService faqService;
    

    /**
     * Description: 查询文章列表<br>
     * 
     * @author liuxianan<br>
     * @taskId <br>
     * @return
     * @throws RestfulException <br>
     */
    @RequestMapping(value = "/faq/queryAritcle", method = RequestMethod.POST)
    @LoginOff
    @ResponseBody
    public AjaxResp queryAritcles() throws RestfulException {
        logger.info("<========== 查询文章列表");
        String orgId = getParameter("orgId");
        String categoryCode = getParameter("code", ErrorCodeDef.PARAM_IS_EMPTY);
        int pageSize = getPageSize();
        int pageIndex = getPageIndex();
        try {
            String serverPath = PropertyHolder.getProperty("server.wx.url");
            String imagePath = PropertyHolder.getProperty("server.image.url");

            List<NewsItem> aritcleList = faqService.queryAritcleList(orgId, categoryCode, pageIndex, pageSize);

            for (NewsItem newsItem : aritcleList) {
                if ("true".equals(newsItem.getUrl())) {
                    newsItem.setUrl(serverPath + "queryAritycleContent/" + newsItem.getId());
                }
                if (CommonUtil.isNotEmpty(newsItem.getImagepath())) {
                    newsItem.setImagepath(imagePath + newsItem.getImagepath());
                }
            }
            AjaxResp ajaxResp = new AjaxResp();
            ajaxResp.put("aritcleList", aritcleList);
            return ajaxResp;
        }
        catch (Exception e) {
            throw new RestfulException(e);
        }
    }

    /**
     * Description: 查看文章内容<br>
     * 
     * @author liuxianan<br>
     * @taskId <br>
     * @param orgId
     * @return
     * @throws Exception
     * @throws TException <br>
     */
    @LoginOff
    @RequestMapping("/queryAritycleContent/{id}")
    public String queryAritycleContent(@PathVariable("id") String id, ModelMap modelMap) throws Exception {
        
        NewsItem newsItem = faqService.queryArticleById(id);
        modelMap.put("newsItem", newsItem);
        
//        Menu menu = faqService.queryMenuById(article.getParentId());
//        modelMap.put("name", menu.getName());
        return "/act/activityDetail";
    }
    
    /**
     * Description: 查看文章内容<br>
     * 
     * @author liuxianan<br>
     * @taskId <br>
     * @param orgId
     * @return
     * @throws Exception
     * @throws TException <br>
     */
    @LoginOff
    @RequestMapping("/queryArticleContent/{id}")
    public String queryArticleContent(@PathVariable("id") String id, ModelMap modelMap) throws Exception {
        
        Article article = faqService.queryCmsArticleById(id);
        modelMap.put("newsItem", article);
        
//        Menu menu = faqService.queryMenuById(article.getParentId());
//        modelMap.put("name", menu.getName());
        return "/act/activityDetail";
    }

    /**
     * Description: 查看文章内容<br>
     * 
     * @author liuxianan<br>
     * @taskId <br>
     * @param orgId
     * @return
     * @throws Exception
     * @throws TException <br>
     */
    @LoginOff
    @RequestMapping("/queryAritycle/{id}")
    @ResponseBody
    public AjaxResp queryArityclet(@PathVariable("id") String id) throws Exception {
        NewsItem newsItem = faqService.queryArticleByReleaseId(id);
        AjaxResp ajaxResp = new AjaxResp();
        String imagePath = PropertyHolder.getProperty("server.image.url");
        newsItem.setImagepath(imagePath + newsItem.getImagepath());
        ajaxResp.put("newsItem", newsItem);
        return ajaxResp;
    }
    
    
    @RequestMapping(value = "/cms/getPageTemplate", method = RequestMethod.GET)
    @LoginOff
    public ModelAndView getPageTemplate(HttpServletRequest req, HttpServletResponse res) throws RestfulException, IOException {
        String transactionID = CommonUtil.getTransactionID();
        String orgCode = getParameter("orgCode");
        String templateCode = getParameter("code", ErrorCodeDef.PARAM_IS_EMPTY);
        String cdnServer = PropertyHolder.getProperty("server.wx.cdn_server");
        logger.info("<========== 查询页面模板,{0}|{1}|{2}", transactionID, templateCode, orgCode);
        PageTemplate pageTemplate;
        try {
            pageTemplate = faqService.getPageTemplate(templateCode);
        }
        catch (Exception e) {
            throw new RestfulException(e);
        }
        logger.info("<========== 页面模板跳转,{0}|{1}", transactionID, pageTemplate);
        ModelAndView mv = new ModelAndView("/act/newsList");
        mv.addObject("pageTemplate", pageTemplate);
        mv.addObject("orgCode", orgCode);
        mv.addObject("cdn_server", cdnServer);
        //logger.info("url,{0}|{1}", pageTemplate.getLocation()+"?wx_appid="+appId+"&orgCode="+orgCode+"&templateCode="+templateCode);
        //res.sendRedirect(pageTemplate.getLocation()+"?wx_appid="+appId+"&orgCode="+orgCode+"&templateCode="+templateCode);
        return mv;
    }
    

    
    @RequestMapping(value = "/cms/queryMenu", method = RequestMethod.GET)
    @LoginOff
    @ResponseBody
    public AjaxResp queryMenu() throws RestfulException {
    	String transactionID = CommonUtil.getTransactionID();
        String orgCode = getParameter("orgCode");
        String contentId = getParameter("contentId", ErrorCodeDef.PARAM_IS_EMPTY);
        int pageSize = getPageSize();
        int pageIndex = getPageIndex();
        String serverPath = PropertyHolder.getProperty("server.wx.url");
        String imagePath = PropertyHolder.getProperty("server.image.url");
        logger.info("<==========request 根据contentId查询内容,{0}|{1}|{2}|{3}|{4}|{5}", transactionID, orgCode, contentId, pageIndex, pageSize);
        Menu menu = new Menu();
        AjaxResp ajaxResp = new AjaxResp();
        try {
        	menu = faqService.queryMenu(contentId, orgCode, pageIndex, pageSize, serverPath, imagePath);
        }
        catch (Exception e) {
        	ajaxResp.setCode(CommonContant.FAIL_CODE);
        	ajaxResp.setMessage("获取栏目失败！");
        }
        
        ajaxResp.put("menu", menu);
        logger.info("<==========response 页面模板跳转,{0}|{1}", transactionID, menu);
        return ajaxResp;
    }
    
    @RequestMapping(value = "/cms/queryAd", method = RequestMethod.GET)
    @LoginOff
    @ResponseBody
    public AjaxResp queryAd() throws RestfulException {
    	String transactionID = CommonUtil.getTransactionID();
        String orgCode = getParameter("orgCode");
        String contentId = getParameter("contentId", ErrorCodeDef.PARAM_IS_EMPTY);
        logger.info("<==========request 根据contentId查询Ad内容,{0}|{1}|{2}|{3}", transactionID, orgCode, contentId);
        Ad ad = new Ad();
        AjaxResp ajaxResp = new AjaxResp();
        try {
        	ad = faqService.queryAd(contentId, orgCode);
        }
        catch (Exception e) {
        	ajaxResp.setCode(CommonContant.FAIL_CODE);
        	ajaxResp.setMessage("获取广告失败！");
        }
        
        ajaxResp.put("ad", ad);
        logger.info("<==========response 根据contentId查询出Ad内容,{0}|{1}", transactionID, ad);
        return ajaxResp;
    }
    
    @RequestMapping(value = "/cms/changeMethod", method = RequestMethod.GET)
    @LoginOff
    public void gete(HttpServletRequest req, HttpServletResponse res) throws RestfulException, IOException {
        //ShowPojo showPojo = commonService.findUniqueByProperty(ShowPojo.class, ShowPojo.NAME,  ShowPojo.NAME_VALUE);
        //if(!CommonUtil.isNull(showPojo)){
            //logger.info("getPrizePage redirect url = [{0}]",showPojo.getValue());
            LoggerUtil.info("change come in");
            res.sendRedirect("http://www.baidu.com");
        //}
        
    }
}
