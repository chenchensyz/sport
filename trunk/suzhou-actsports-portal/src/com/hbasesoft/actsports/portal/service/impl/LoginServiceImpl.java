/**************************************************************************************** 
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbasesoft.actsports.portal.bean.ActUserPojo;
import com.hbasesoft.actsports.portal.biz.vo.LoginReq;
import com.hbasesoft.actsports.portal.biz.vo.LoginResp;
import com.hbasesoft.actsports.portal.biz.vo.UserInfo;
import com.hbasesoft.actsports.portal.constant.CacheCodeDef;
import com.hbasesoft.actsports.portal.constant.CommonContant;
import com.hbasesoft.actsports.portal.service.LoginService;
import com.hbasesoft.actsports.portal.service.UserinfoService;
import com.hbasesoft.actsports.portal.util.WeChatUtil;
import com.hbasesoft.framework.cache.core.annotation.Cache;
import com.hbasesoft.framework.cache.core.annotation.Key;
import com.hbasesoft.framework.cache.core.annotation.RmCache;
import com.hbasesoft.framework.common.ErrorCodeDef;
import com.hbasesoft.framework.common.FrameworkException;
import com.hbasesoft.framework.common.ServiceException;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月24日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.wx.service.impl <br>
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource(name = "UserInfoRemoteService")
    private UserinfoService userinfoService;

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param openId
     * @param browser
     * @param ip
     * @return
     * @throws FrameworkException
     * @throws ServiceException <br>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cache(node = CacheCodeDef.USER_BY_OPEN_ID, key = "$!{openId}", expireTime = CacheCodeDef.USERINFO_SAVE_TIME)
    public ActUserPojo loginOrRegist(@Key("openId") String openId, String browser, String ip) throws FrameworkException {
        LoginReq loginReq = new LoginReq();
        loginReq.setAccount(openId);
        loginReq.setAccountType(CommonContant.ACCT_TYPE_WECHAT);
        loginReq.setBrowser(browser);
        loginReq.setLoginIp(ip);

        try {

            LoginResp resp = userinfoService.login(loginReq);
            UserInfo userInfo = new UserInfo();
            if (CommonContant.SUCCESS_CODE.equals(resp.getResultCode())) {
                userInfo = resp.getUserInfo();
            }
            else if ((ErrorCodeDef.OPERATOR_NOT_EXSIST + "").equals(resp.getResultCode())) {
                userInfo = WeChatUtil.getUserInfoMessage(openId);
                if(userInfo == null)
                	userInfo = new UserInfo();
                userInfo.setState(CommonContant.USER_STATE_INIT);
                String userId = userinfoService.regist(openId, CommonContant.ACCT_TYPE_WECHAT, userInfo);
                userInfo.setUserId(userId);
            }
            else {
                throw new ServiceException(ErrorCodeDef.SYSTEM_ERROR_10001, "登录或注册失败");
            }

            ActUserPojo pojo = new ActUserPojo();
            pojo.setId(userInfo.getUserId());
            pojo.setState(userInfo.getState());
            return pojo;

        }
        catch (ServiceException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ServiceException(ErrorCodeDef.SYSTEM_ERROR_10001, "登录或注册失败", e);
        }

    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param openId
     * @throws ServiceException <br>
     */
    @Override
    @RmCache(node = CacheCodeDef.USER_BY_OPEN_ID, key = "$!{openId}")
    public void removeUserinfoByOpenid(@Key("openId") String openId) {
    }

}
