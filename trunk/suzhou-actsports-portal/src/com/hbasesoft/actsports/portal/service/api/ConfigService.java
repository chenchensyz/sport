/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service.api;

import com.hbasesoft.actsports.portal.biz.vo.WechatAccount;


/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年9月4日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.api.biz <br>
 */
public interface ConfigService{

    WechatAccount getWechatAccount() throws Exception;
    
    String getWechatAccessToken() throws Exception;
    
    String resetWechatAccessToken() throws Exception;
    
    String getWechatJsApiTickit() throws Exception;

    String resetWechatJsApiTickit() throws Exception;
    
    WechatAccount getWechatAccount(String appid) throws Exception;
    
    String getOrgCodeByOrgId(String orgId) throws Exception;

}
