/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service;

import java.util.List;

import com.hbasesoft.actsports.portal.biz.vo.CmsWeixinMenuPojo;
import com.hbasesoft.actsports.portal.biz.vo.WeixinReleasePojo;
import com.hbasesoft.framework.common.ServiceException;

/**
 * <Description> <br>
 * 
 * @author zhasiwei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年1月17日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.cbs.service <br>
 */
public interface FaqInfoService extends CommonService{

    CmsWeixinMenuPojo getMenuByOrgCodeAndMenuCode(String orgCode, String menuCode) throws ServiceException;
    
    List<CmsWeixinMenuPojo> getMenuByMenuCode(String menuCode) throws ServiceException;
    
    
}
