
package com.hbasesoft.manager.utils;
/**
 * 账号状态
 * @author YS-004
 *
 */
public interface StateUtils {

    /**
     * 可用
     */
    String AVALIABLE = "A";

    /**
     * 不可用
     */
    String UN_AVALIABLE = "X";

    /**
     * 账号类型微信
     */
    public static final String ACCT_TYPE_WECHAT = "W";

    /**
     * 账号类型手机
     */
    public static final String ACCT_TYPE_MOBILE = "M";

    /**
     * 初始状态
     */
    public static final String USER_STATE_INIT = "I";

    /**
     * 锁定状态
     */
    public static final String USER_STATE_LOCK = "L";

    /**
     * 成功代码
     */
    String SUCCESS_CODE = "0";

    /**
     * 错误代码
     */
    String FAIL_CODE = "1";

    /**
     * 可用
     */
    String ALLOW = "Y";
    
    /**
     * 不可用
     */
    String UN_ALLOW = "N";
    
    /**
     * 不可用
     */
    String UN_REQUIRED = "unrequired";
}
