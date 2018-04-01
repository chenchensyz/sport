package com.hbasesoft.actsports.portal.constant;

/**
 * Session存储 key
 */
public interface SessionKeyDef {

    /** 短信验证码 */
    String SESSION_SMS_VALIDATE = "sms.validate";

    /** session中存放的openid */
    String SESSION_OPEN_ID = "wx.openid";

    /** 上次访问的URL */
    String LAST_ACCESS_URL = "url.last_access";
    
    /** 上次访问的URL */
    String CALLBACK_URL = "url.auth_callback_url";
    
    /**session中存放的appid*/
    String SESSION_APPID = "wx.appid";
}
