/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hbasesoft.actsports.portal.bean.ActUserPojo;
import com.hbasesoft.actsports.portal.constant.SessionKeyDef;
import com.hbasesoft.actsports.portal.service.LoginService;
import com.hbasesoft.actsports.portal.util.EncodeFilter;
import com.hbasesoft.framework.common.FrameworkException;
import com.hbasesoft.framework.common.ServiceException;
import com.hbasesoft.framework.common.utils.Assert;
import com.hbasesoft.framework.common.utils.AssertException;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.io.HttpUtil;
import com.hbasesoft.framework.common.utils.logger.Logger;
import com.hbasesoft.vcc.wechat.ErrorCodeDef;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月15日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.wx.controller <br>
 */
public abstract class BaseController {

    protected Logger logger = new Logger(BaseController.class);

    /** 分页最大数 */
    private static final int MAX_PAGE_SIZE = 100;

    /** 分页最小数 */
    private static final int MIN_PAGE_SIZE = 1;

    /** 默认页大小 */
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Resource
    private LoginService LoginService;

    /**
     * Description: 获取用户Id <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @return <br>
     * @throws ServiceException
     */
    protected String getUserId() throws FrameworkException {
        ActUserPojo user = getActUserInfo();
        return user == null ? null : user.getId();
    }

    /**
     * Description: 获取用户信息<br>
     * 
     * @author liuxianan<br>
     * @taskId <br>
     * @return
     * @throws ServiceException <br>
     */
    protected ActUserPojo getActUserInfo() throws FrameworkException {
        String openId = (String) getAttribute(SessionKeyDef.SESSION_OPEN_ID);
        Assert.notEmpty(openId, ErrorCodeDef.APPID_NULL);
        return LoginService.loginOrRegist(openId, HttpUtil.getClientInfo(getRequest()),
            HttpUtil.getRequestIp(getRequest()));
    }

    /**
     * Description: 获取参数<br>
     * 
     * @author wang.wei297<br>
     * @taskId 660560<br>
     * @param param 参数名
     * @param errMsg 当该值不为空时，校验参数值是否为空，如果为空的话抛出异常
     * @return 参数值<br>
     * @throws AssertException <br>
     */
    protected String getParameter(String param, int errCode) throws AssertException {
        String value = getRequest().getParameter(param);
           
        Assert.notEmpty(value, errCode);
        return CommonUtil.isNotEmpty(value) ? EncodeFilter.encode(StringUtils.trim(value)) : value;
    }

    /**
     * Description: 获取参数<br>
     * 
     * @author wang.wei297<br>
     * @taskId 660560 <br>
     * @param param 参数名
     * @return <br>
     */
    protected String getParameter(String param) {
        String value = getRequest().getParameter(param);
        return CommonUtil.isNotEmpty(value) ? EncodeFilter.encode(StringUtils.trim(value)) : value;
    }

    /**
     * Description: getLongParameter<br>
     * 
     * @author wang.wei297<br>
     * @taskId <br>
     * @param param param <br>
     * @param errMsg errMsg <br>
     * @return <br>
     * @throws AssertException <br>
     */
    protected Long getLongParameter(String param, int errCode) throws AssertException {
        String value = getParameter(param, errCode);
        return CommonUtil.isEmpty(value) ? null : Long.valueOf(value);
    }

    /**
     * Description: getLongParameter<br>
     * 
     * @author wang.wei297<br>
     * @taskId <br>
     * @param param param
     * @return <br>
     */
    protected Long getLongParameter(String param) {
        String value = getParameter(param);
        return CommonUtil.isEmpty(value) ? null : Long.valueOf(value);
    }

    /**
     * Description: <br>
     * 
     * @author yang.zhipeng <br>
     * @taskId <br>
     * @param param <br>
     * @param errMsg <br>
     * @return <br>
     * @throws AssertException <br>
     */
    protected Integer getIntegerParameter(String param, int errCode) throws AssertException {
        String value = getParameter(param, errCode);
        return CommonUtil.isEmpty(value) ? null : Integer.valueOf(value);
    }

    /**
     * Description: <br>
     * 
     * @author yang.zhipeng <br>
     * @taskId <br>
     * @param param <br>
     * @return <br>
     */
    protected Integer getIntegerParameter(String param) {
        String value = getParameter(param);
        return CommonUtil.isEmpty(value) ? null : Integer.valueOf(value);
    }

    /**
     * Description: <br>
     * 
     * @author yang.zhipeng <br>
     * @taskId <br>
     * @return <br>
     */
    protected int getPageIndex() {
        Integer pageIndex = getIntegerParameter("pageIndex");
        return (pageIndex == null || pageIndex < 1) ? 1 : pageIndex;
    }

    /**
     * Description: <br>
     * 
     * @author yang.zhipeng <br>
     * @taskId <br>
     * @return <br>
     */
    protected int getPageSize() {
        Integer pageSize = getIntegerParameter("pageSize");
        return (pageSize == null || pageSize < MIN_PAGE_SIZE || pageSize > MAX_PAGE_SIZE) ? DEFAULT_PAGE_SIZE
            : pageSize;
    }

    /**
     * Description: 获取request<br>
     * 
     * @author wang.wei297<br>
     * @taskId 660560<br>
     * @return <br>
     */
    private HttpServletRequest getRequest() {
        RequestAttributes requestAttr = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes) requestAttr).getRequest();
    }

    /**
     * Description: 将数据存放至session<br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param key
     * @param value <br>
     */
    protected void setAttribute(String key, Object value) {
        getRequest().getSession().setAttribute(key, value);
    }

    /**
     * Description: 从Session中获取数据 <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param key
     * @return <br>
     */
    protected Object getAttribute(String key) {
        return getRequest().getSession().getAttribute(key);
    }

    /**
     * Description: 删除Session中存储的信息<br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param key <br>
     */
    protected void removeAttribute(String key) {
        getRequest().getSession().removeAttribute(key);
    }

    /**
     * Description: 获取页面参数 <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @return <br>
     */
    protected Map<String, String> getFiltParamMap() {
        HttpServletRequest request = getRequest();
        Map<String, String> paramMap = new HashMap<String, String>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            String value = request.getParameter(key);
            if (CommonUtil.isNotEmpty(value)) {
                paramMap.put(key, EncodeFilter.encodeForJS(EncodeFilter.encode(value)));
            }
        }
        return paramMap;
    }
}
