/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service.impl;

import org.springframework.stereotype.Service;

import com.hbasesoft.actsports.portal.biz.vo.OrgPojo;
import com.hbasesoft.actsports.portal.constant.CacheCodeDef;
import com.hbasesoft.actsports.portal.service.ConfigInfoService;
import com.hbasesoft.framework.cache.core.annotation.Cache;
import com.hbasesoft.framework.common.ServiceException;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月15日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.cbs.service.impl <br>
 */
@Service
public class ConfigServiceImpl extends CommonServiceImpl implements ConfigInfoService {

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param orgId
     * @return
     * @throws ServiceException <br>
     */
    @Override
    @Cache(node = CacheCodeDef.ORG_BY_ID, expireTime = CacheCodeDef.CONFIG_SAVE_TIME)
    public OrgPojo getOrgById(String orgId) throws ServiceException {
        return get(OrgPojo.class, orgId);
    }

}
