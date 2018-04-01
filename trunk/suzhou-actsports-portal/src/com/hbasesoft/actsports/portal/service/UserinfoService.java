/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service;

import com.hbasesoft.actsports.portal.biz.vo.LoginReq;
import com.hbasesoft.actsports.portal.biz.vo.LoginResp;
import com.hbasesoft.actsports.portal.biz.vo.OptResp;
import com.hbasesoft.actsports.portal.biz.vo.UserInfo;
import com.hbasesoft.framework.common.RemoteServiceException;


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
public interface UserinfoService {


	String regist(String account, String accountType, UserInfo userInfo) throws RemoteServiceException;
	
	
    /**
     * Description: 登录<br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param loginReq
     * @return
     * @throws Exception <br>
     */
    LoginResp login(LoginReq loginReq) throws Exception;

    /**
     * Description: 获取用户信息<br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param account
     * @param accountType
     * @return
     * @throws Exception <br>
     */
    UserInfo getUserInfoByAccount(String account, String accountType) throws Exception;

    /**
     * Description: 添加账号<br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param userId
     * @param account
     * @param accountType
     * @param accountValue
     * @return
     * @throws Exception <br>
     */
    OptResp addAccount(String userId, String account, String accountType, String accountValue) throws Exception;

    /**
     * Description: 删除账号<br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param userId
     * @param account
     * @param accountType
     * @return
     * @throws Exception <br>
     */
    OptResp delAccount(String userId, String account, String accountType) throws Exception;



}
