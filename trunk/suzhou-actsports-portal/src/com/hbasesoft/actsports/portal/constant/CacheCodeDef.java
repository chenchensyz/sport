/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.constant;

import com.hbasesoft.framework.cache.core.CacheConstant;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月14日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.common <br>
 */
public interface CacheCodeDef extends CacheConstant {
    
    /** 客户端缓存时间 */
    int CLIENT_CACHE_TIME = 60;

    /** 用户信息缓存时间 10 分钟 */
    int USERINFO_SAVE_TIME = 60 * 10;

    /** 配置信息缓存一个小时 */
    int CONFIG_SAVE_TIME = 60 * 60;

    /** 开放API信息缓存2个小时 */
    int OPEN_API_SAVE_TIME = 2 * 60 * 60;

    /** access token */
    String OPEN_API_ACCESS_TOKEN = "openapi.access_token";

    /** 最后一次访问的时间戳 */
    String OPEN_API_LAST_ACCESS_TIME = "openapi.last_access_timestap";

    /**
     * 页面跳转缓存
     */
    String REQUEST_PAY_URI = "/REQUEST_URI";

    /** 用户信息 account and type */
    String USERINFO_BY_ACCOUNT_TYPE = "userinfo.account_type";

    /** 字典 */
    String DICT_BY_CODE_DATA_CHANNEL = "dict.code_data_channel";

    /** 字典 */
    String DICT_BY_CODE_CHANNEL = "dict.code_channel";

    String USER_BY_OPEN_ID = "user.open_id";
    
    /** 配置参数 */
    String CONFIG_BY_CODE_CHANNEL = "dict.code_channel";

    /** 微信AccessToken */
    String WX_ACCESS_TOKEN = "wechat.access_token";

    /** 微信JsApi ticket */
    String WX_JS_API_TICKET = "wechat.js_api_ticket";

    /** 微信AppId */
    String WX_ACCOUNT_INFO = "wechat.account_info";

    /** 任务缓存的锁信息 */
    String TASK_LOCK_INFO = "task.lock_info";
    
    /** 微信认证callbackUrl */
    String CALLBACK_URL = "callbackUrl";
    
    /** 微信认证授权NodeNum */
    String WX_AUTH_NODE = "wx_auth_node";
    
    /** 微信认证授权openId */
    String CACHE_OPENID = "cache_openId";
    
    /** FAQ信息 */
    String FAQ_INFO = "fag.info";
    
    /** 页面模板缓存信息 */
    String PAGE_TEMPLATE_INFO = "page_template.info";
    
    /** 根据id查询组织 */
    String ORG_BY_ID = "org.id";
}
