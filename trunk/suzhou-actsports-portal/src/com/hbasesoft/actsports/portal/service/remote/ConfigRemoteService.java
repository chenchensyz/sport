/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service.remote;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.hbasesoft.actsports.portal.biz.vo.OrgPojo;
import com.hbasesoft.actsports.portal.biz.vo.WechatAccount;
import com.hbasesoft.actsports.portal.constant.CacheCodeDef;
import com.hbasesoft.actsports.portal.constant.ErrorCodeDef;
import com.hbasesoft.actsports.portal.constant.SessionKeyDef;
import com.hbasesoft.actsports.portal.constant.WeChatConstants;
import com.hbasesoft.actsports.portal.service.ConfigInfoService;
import com.hbasesoft.actsports.portal.service.api.ConfigService;
import com.hbasesoft.actsports.portal.util.WeChatUtil;
import com.hbasesoft.framework.cache.core.CacheHelper;
import com.hbasesoft.framework.cache.core.annotation.Cache;
import com.hbasesoft.framework.cache.core.annotation.RmCache;
import com.hbasesoft.framework.common.RemoteServiceException;
import com.hbasesoft.framework.common.ServiceException;
import com.hbasesoft.framework.common.utils.Assert;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.date.DateUtil;
import com.hbasesoft.framework.common.utils.io.HttpUtil;
import com.hbasesoft.framework.common.utils.logger.Logger;

import com.hbasesoft.vcc.wechat.bean.AccountPojo;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月15日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.cbs.rmi <br>
 */
@Service("ConfigRemoteService")
@Transactional(readOnly = true)
public class ConfigRemoteService implements ConfigService {

    private static Logger logger = new Logger(ConfigRemoteService.class);

    @Resource
    private com.hbasesoft.actsports.portal.service.ConfigInfoService configService;


    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @return
     * @throws RemoteServiceException <br>
     */
    @Override
    public WechatAccount getWechatAccount() throws RemoteServiceException {
        try {
            List<AccountPojo> accountList = configService.loadAll(AccountPojo.class);
            if (CommonUtil.isNotEmpty(accountList)) {
                AccountPojo account = accountList.get(0);
                WechatAccount acct = new WechatAccount();
                acct.setAppCode(account.getAccountappid());
                acct.setAppSecret(account.getAccountappsecret());
                return acct;
            }
        }
        catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            throw new RemoteServiceException(e);
        }
        return null;
    }
    
    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @return
     * @throws RemoteServiceException <br>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
//    @Cache(node = CacheCodeDef.WX_ACCESS_TOKEN, key = "default", expireTime = WeChatConstants.TOKEN_CACHE_TIME)
    public String getWechatAccessToken() throws RemoteServiceException {
        RequestAttributes requestAttr = RequestContextHolder.getRequestAttributes();
        HttpSession session = ((ServletRequestAttributes) requestAttr).getRequest().getSession();
        String appid = (String)session.getAttribute(SessionKeyDef.SESSION_APPID);
        logger.info("出來吧...............");
        logger.info("getWechatAccessToken appid = [{0}]",appid);
        logger.info("走開吧...............");
        String token = "";
        AccountPojo account = null;
        try {
            if(CommonUtil.isEmpty(appid)){
                List<AccountPojo> accountList = configService.loadAll(AccountPojo.class);
                if (CommonUtil.isNotEmpty(accountList)) {
                    account = accountList.get(0);
                } 
            } else {
                account = configService.findUniqueByProperty(AccountPojo.class, AccountPojo.ACCOUNT_APPID, appid);
            }
            
            token = account.getAccountaccesstoken();
            
            if (CommonUtil.isNotEmpty(token) && (System.currentTimeMillis()
                - account.getAddtokentime().getTime() <= WeChatConstants.TOKEN_CACHE_TIME * 1000)) {
                return account.getAccountaccesstoken();
            }
            logger.info("appid........... = [{0}]",account.getAccountappid());
            logger.info("appsecret()........... = [{0}]",account.getAccountappsecret());
            token = getAccessToken(account.getAccountappid(), account.getAccountappsecret());
            logger.info("token........... = [{0}]",token);
            if (CommonUtil.isNotEmpty(token)) {
                // 重置token
                account.setAccountaccesstoken(token);
                // 重置事件
                account.setAddtokentime(new Date());
                configService.updateEntity(account);
            }
            
        }
        catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            throw new RemoteServiceException(e);
        }
        logger.info("=========返回的access_tokne：" + token);
        return token;
    }
    

    private String getAccessToken(String appid, String appSecret) {
        String url = MessageFormat.format(WeChatConstants.ACCESS_TOKEN_URL, appid, appSecret);
        logger.info("getAccessToken url [{0}]", url);
        String jsonStr = HttpUtil.doGet(url);
        logger.info("getAccessToken 获取token wechat response: [{0}]", jsonStr);

        JSONObject obj = JSONObject.parseObject(jsonStr);
        String errorCode = obj.getString("errcode");
        if (CommonUtil.isEmpty(errorCode)) {
            return obj.getString("access_token");
        }
        else {
            logger.error("获取AssessToken失败:[" + jsonStr + "]");
        }
        return null;
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @return
     * @throws RemoteServiceException <br>
     */
    @Override
    @RmCache(node = CacheCodeDef.WX_ACCESS_TOKEN, clean = true)
    @Transactional(rollbackFor = Exception.class)
    public String resetWechatAccessToken() throws RemoteServiceException {
        RequestAttributes requestAttr = RequestContextHolder.getRequestAttributes();
        HttpSession session = ((ServletRequestAttributes) requestAttr).getRequest().getSession();
        String appid = (String)session.getAttribute(SessionKeyDef.SESSION_APPID);
        logger.info("resetWechatAccessToken appid = [{0}]",appid);
        AccountPojo account = null;
        try {
            if(CommonUtil.isNotEmpty(appid)){
                account = configService.findUniqueByProperty(AccountPojo.class, AccountPojo.ACCOUNT_APPID, appid);
            } else {
                List<AccountPojo> accountList = configService.loadAll(AccountPojo.class);
                if (CommonUtil.isNotEmpty(accountList)) {
                    account = accountList.get(0);
                }
            }
            
            account.setAccountaccesstoken(null);
            account.setAddtokentime(DateUtil.string2Date("20160101"));
            configService.updateEntity(account);
        }
        catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            throw new RemoteServiceException(e);
        }

        return WeChatUtil.getAccessToken();
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @return
     * @throws RemoteServiceException <br>
     */
    @Override
    @Transactional
    @Cache(node = CacheCodeDef.WX_JS_API_TICKET, key = "default", expireTime = WeChatConstants.TOKEN_CACHE_TIME)
    public String getWechatJsApiTickit() throws RemoteServiceException {
        String accessToken = WeChatUtil.getAccessToken();
        String url = MessageFormat.format(WeChatConstants.JSAPI_TICKET_URL, accessToken);
        logger.info("通过accessToken获取jsapi_ticket-url:[{0}]", url);
        String jsonStr = HttpUtil.doGet(url);
        logger.info("getticket 获取jsapiticket wechat response: " + jsonStr);
        JSONObject obj = JSONObject.parseObject(jsonStr);
        String errorCode = obj.getString("errcode");
        String ticket = obj.getString("ticket");
        logger.info("获取到的ticket为：" + ticket);
        if (CommonUtil.isNotEmpty(ticket)) {
            return obj.getString("ticket");
        }
        else {
            logger.error(obj.getString("errmsg"));
            WeChatUtil.refreshAccessTokenIfNecessary(errorCode);
        }
        return null;
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @return
     * @throws RemoteServiceException <br>
     */
    @Override
    @RmCache(node = CacheCodeDef.WX_JS_API_TICKET, clean = true)
    public String resetWechatJsApiTickit() throws RemoteServiceException {
        return WeChatUtil.getJsApiTicket();
    }
    
    /**
     * Description:根据组织机构id查询组织结构代码 <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param orgId
     * @return
     * @throws RemoteServiceException <br>
     */
    @Override
    public String getOrgCodeByOrgId(String orgId) throws RemoteServiceException {
        try {
            OrgPojo pojo = configService.getOrgById(orgId);
            Assert.notNull(pojo, ErrorCodeDef.ORG_NOT_FOUND_BY_ID, orgId);
            return pojo.getOrgCode();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RemoteServiceException(e);
        }
    }

    /**
     * Description: <br> 
     *  
     * @author ruiluhui<br>
     * @taskId <br>
     * @param appid
     * @return
     * @throws Exception <br>
     */ 
    @Override
    public WechatAccount getWechatAccount(String appid) throws Exception {
        try {
            AccountPojo account = configService.findUniqueByProperty(AccountPojo.class, AccountPojo.ACCOUNT_APPID, appid);
            if (!CommonUtil.isNull(account)) {
                WechatAccount acct = new WechatAccount();
                acct.setAppCode(account.getAccountappid());
                acct.setAppSecret(account.getAccountappsecret());
                return acct;
            }
        }
        catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            throw new RemoteServiceException(e);
        }
        return null;
    }

}
