/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.controller;

import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hbasesoft.actsports.portal.OAuthOff;
import com.hbasesoft.actsports.portal.bean.ActResp;
import com.hbasesoft.actsports.portal.bean.ActUserPojo;
import com.hbasesoft.actsports.portal.biz.vo.AjaxResp;
import com.hbasesoft.actsports.portal.biz.vo.CmsWeixinArticlePojo;
import com.hbasesoft.actsports.portal.biz.vo.WeixinAccount;
import com.hbasesoft.actsports.portal.constant.CacheCodeDef;
import com.hbasesoft.actsports.portal.constant.SessionKeyDef;
import com.hbasesoft.actsports.portal.constant.WeChatConstants;
import com.hbasesoft.actsports.portal.constant.WebConstant;
import com.hbasesoft.actsports.portal.service.CommonService;
import com.hbasesoft.actsports.portal.service.LoginService;
import com.hbasesoft.actsports.portal.service.WechatDbService;
import com.hbasesoft.actsports.portal.util.SignUtil;
import com.hbasesoft.actsports.portal.util.WeChatUtil;
import com.hbasesoft.framework.cache.core.CacheHelper;
import com.hbasesoft.framework.common.ServiceException;
import com.hbasesoft.framework.common.utils.Assert;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.io.HttpUtil;
import com.hbasesoft.framework.common.utils.io.IOUtil;
import com.hbasesoft.framework.common.utils.logger.Logger;
import com.hbasesoft.vcc.wechat.ErrorCodeDef;
import com.hbasesoft.vcc.wechat.bean.AccountPojo;
import com.hbasesoft.vcc.wechat.bean.NewsitemPojo;
import com.hbasesoft.vcc.wechat.service.WechatService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年6月4日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.wx.controller <br>
 */
@Api(value = "微信控制器", description = "处理微信")
@Controller
public class WechatController extends BaseController {

    private static Logger logger = new Logger(WechatController.class);

    @Resource
    private WechatDbService wechatDbService;

    @Resource
    private WechatService wechatService;

    @Resource
    private LoginService loginService;
    
    @Resource
    private CommonService commonService;
    @Value("${server.image.url}")
    private String imagePath;

    @Value("${server.wx.url}")
    private String serverPath;
    
    @OAuthOff
    @RequestMapping(value = "/{id}", params = "wechat", method = RequestMethod.GET)
    @ResponseBody
    @Transactional(readOnly = true)
    public String wechatGet(@PathVariable("id") String id, @RequestParam(value = "signature") String signature,
        @RequestParam(value = "timestamp") String timestamp, @RequestParam(value = "nonce") String nonce,
        @RequestParam(value = "echostr") String echostr) throws ServiceException {

        logger.info(CommonUtil.messageFormat(
            "==========> access wechat validate info [signature={0},timestamp={1},nonce={2},echostr={3}]", signature,
            timestamp, nonce, echostr));

        String result = null;
        AccountPojo account=wechatDbService.getEntity(AccountPojo.class, id);
//        List<AccountPojo> weixinAccountEntities = wechatDbService.loadAll(AccountPojo.class);
//        for (AccountPojo account : weixinAccountEntities) {
//            if (SignUtil.checkSignature(account.getAccounttoken(), signature, timestamp, nonce)) {
//                result = echostr;
//                break;
//            }
//        }
        if (SignUtil.checkSignature(account.getAccounttoken(), signature, timestamp, nonce)) {
            result = echostr;
        }
        logger.info("<========== access wechat validate info [echostr={0}]", result);
        return result;
    }

    @OAuthOff
    @RequestMapping(value = "/{id}", params = "wechat", method = RequestMethod.POST)
    @ResponseBody
    public String wechatPost(@PathVariable("id") String id,HttpServletRequest request) throws Exception {
    	return wechatService.coreService(id, IOUtil.readString(request.getInputStream()), imagePath, serverPath);
    }

    @OAuthOff
    @RequestMapping(value = WebConstant.OATH_CALLBACK_URI, method = RequestMethod.GET)
    public void oath2Callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = getParameter("code", ErrorCodeDef.CODE_NULL);
        // String state = getParameter("state", "state不能为空");
        String sessionId = request.getSession().getId();
        // String openId = (String) getAttribute(SessionKeyDef.SESSION_OPEN_ID);
        String openId = CacheHelper.getCache().get(CacheCodeDef.WX_AUTH_NODE, sessionId);
        logger.info("第3次get openId = [{0}],sessionId = [{1}]", openId, request.getSession().getId());
        
        HttpSession session = request.getSession();
        
        if (CommonUtil.isEmpty(openId)) {
            String appid = (String)session.getAttribute(SessionKeyDef.SESSION_APPID);
            
            WeixinAccount weixinAccount = commonService.findUniqueByProperty(WeixinAccount.class,"accountappid",appid);
            openId = WeChatUtil.getOpenId4Oauth2(code, appid);

            logger.info("openId = [{0}]", openId);

            if (CommonUtil.isEmpty(openId)) {
                String url = WeChatUtil.getOauth2Url(WebConstant.OATH_CALLBACK_URI, "",
                    WeChatConstants.scope_snsapi_userinfo);
                logger.info(">>> 登录失败重新认证 [微信OAuth2认证授权跳转] location = [{0}]", url);
                response.sendRedirect(url);
                return;
            }
            // setAttribute(SessionKeyDef.SESSION_OPEN_ID, openId);

            CacheHelper.getCache().put(CacheCodeDef.WX_AUTH_NODE, sessionId, openId);
        }
        ActUserPojo userInfo = loginService.loginOrRegist(openId, HttpUtil.getClientInfo(request),
            HttpUtil.getRequestIp(request));
        Assert.notNull(userInfo, com.hbasesoft.framework.common.ErrorCodeDef.SYSTEM_ERROR_10001);

        

        String callbackUrl = (String) session.getAttribute(SessionKeyDef.CALLBACK_URL);
        /*
         * if(callbackUrl.contains("?")) callbackUrl = (String) session.getAttribute(SessionKeyDef.CALLBACK_URL)
         * +"&sessionId="+sessionId; else callbackUrl = (String) session.getAttribute(SessionKeyDef.CALLBACK_URL)
         * +"?sessionId="+sessionId;
         */
        callbackUrl = (String) session.getAttribute(SessionKeyDef.CALLBACK_URL) + "&?sessionId=" + sessionId;

        logger.info("callbackUrl = [{0}]", callbackUrl);

        logger.info("登录成功 跳转地址[{0}]", callbackUrl);
        logger.info("callbackUrl decode =[{0}]", URLDecoder.decode(callbackUrl, "UTF-8"));

        response.sendRedirect(callbackUrl);
    }

    @OAuthOff
    @RequestMapping("/article/{id}")
    public String article(@PathVariable("id") String id, ModelMap modelMap) throws ServiceException {
        modelMap.put("imagePath", imagePath);
        modelMap.put("newsItem", wechatDbService.get(NewsitemPojo.class, id));
        return "/article/article";
    }
    
    @OAuthOff
    @RequestMapping("/page/{id}")
    public String page(@PathVariable("id") String id, ModelMap modelMap) throws ServiceException {
        modelMap.put("cmsArticle", wechatDbService.get(CmsWeixinArticlePojo.class, id));
        return "/article/page";
    }

    // 访问微信后台,获得授权
    @ApiOperation(value = "访问微信后台,获得授权", notes = "传入callbackURl", response = ActResp.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "验证成功", response = ActResp.class),
        @ApiResponse(code = 500, message = "验证失败，出现异常", response = ActResp.class)
    })
    @OAuthOff
    @RequestMapping(value = "/wechatAuth", method = RequestMethod.GET)
    public void wechatAuth(HttpServletRequest request, HttpServletResponse response,
        @ApiParam(value = "授权成功访问的url") String callbackUrl) throws Exception {

        logger.info(" 第2次  callbackUrl =[{0}]", callbackUrl);

        HttpSession session = request.getSession();
        logger.info("session...{}",session);
        session.setAttribute(SessionKeyDef.CALLBACK_URL, callbackUrl);
        //
        // CacheHelper.getCache().put(CacheCodeDef.WX_AUTH_NODE, CacheCodeDef.CALLBACK_URL, callbackUrl);
        //
        // String dd = CacheHelper.getCache().get(CacheCodeDef.WX_AUTH_NODE, CacheCodeDef.CALLBACK_URL);
        //
        // logger.info(" 第2次 cache callbackUrl =[{0}]", dd);
        // String sourceUri = HttpUtil.getRequestURI(request, true);
        Integer first = callbackUrl.indexOf("=");
        Integer last = callbackUrl.indexOf("#");
        String appid = callbackUrl.substring(first+1, last);
        logger.info("appid...{}",appid);
        session.setAttribute(SessionKeyDef.SESSION_APPID, appid);
        String authUrl = WeChatUtil.getOauth2Url(appid, WebConstant.OATH_CALLBACK_URI, "", WeChatConstants.scope_snsapi_base);
        
        logger.info(">>> [微信OAuth2认证授权跳转] location = [{0}]", authUrl);
        response.sendRedirect(authUrl);
    }
    
    public static void main(String[] args) {
        String url = "http://wechat.szsports.gov.cn/actsports-web/index.html?qppid=12#/sign/4ae6ee4b5d822cb4015d822cb4600000?_k=3h7mgh";
        Integer first = url.indexOf("=");
        Integer last = url.indexOf("#");
        String appid = url.substring(first+1, last);
        System.out.println(appid);
        
    }
    
    @OAuthOff
    @RequestMapping(value = "/{id}", params = "checkSignature", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResp checkSignature(HttpServletResponse response, @PathVariable("id") String id)
        throws ServiceException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Max-Age", "1000");

        AjaxResp resp = new AjaxResp();
        resp.setCode(wechatService.checkSignature(id));
        return resp;
    }
}
