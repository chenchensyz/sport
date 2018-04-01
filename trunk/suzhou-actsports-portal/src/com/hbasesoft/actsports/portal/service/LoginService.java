/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service;

import com.hbasesoft.actsports.portal.bean.ActUserPojo;
import com.hbasesoft.framework.common.FrameworkException;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月23日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.wx.service <br>
 */
public interface LoginService {

    ActUserPojo loginOrRegist(String openId, String browser, String ip) throws FrameworkException;

    void removeUserinfoByOpenid(String openId);
    
}
