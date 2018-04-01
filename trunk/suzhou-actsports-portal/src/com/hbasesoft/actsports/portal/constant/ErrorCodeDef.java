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
public interface ErrorCodeDef extends com.hbasesoft.framework.common.ErrorCodeDef {

    /** 创建消息头失败 */
    int CREATE_HEAD_ERROR = 50001;

    /** 查询组织机构失败 */
    int FIND_ORG_CODE_ERROR = 50002;

    /** 下单失败 */
    int CREATE_ORDER_ERROR = 50003;

    /** webservice异常 */
    int WEB_SERVICE_ERROR = 50004;

    /** 气费查询异常 */
    int QUERY_GAS_FEE_ERROR = 50005;

    /** 未找到任务执行者 */
    int TASK_EXECUTOR_NOT_FOUND = 50006;

    /** 退费错误 */
    int REFUND_ERROR = 50007;

    /** 令牌获取失败 */
    int ACCESS_TOKEN_NOT_FOUND = 50008;

    /** 渠道获取失败 */
    int CHANNEL_NOT_FOUND = 50009;

    /** 令牌获取失败 */
    int ACCESS_TOKEN_EXPIRE = 50010;
    
    /** 微信登录openid获取失败 */
    int ACCESS_OPENID_ERROR = 50011;
    
    /** 栏目不能为空 */
    int WEIXIN_MENU_NOT_EMPTY = 50052;
    
    /** 组织机构没找到 */
    int ORG_NOT_FOUND_BY_ID = 60003;
    
    /** 参数不能为空 */
    int PARAM_IS_EMPTY = 50015;
    
}
