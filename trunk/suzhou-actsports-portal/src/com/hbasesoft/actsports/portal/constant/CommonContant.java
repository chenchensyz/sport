/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.constant;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月12日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.common <br>
 */
public interface CommonContant {

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
