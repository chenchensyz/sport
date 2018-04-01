/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.hbasesoft.actsports.portal.biz.vo.CmsWeixinMenuPojo;
import com.hbasesoft.actsports.portal.biz.vo.WeixinReleasePojo;
import com.hbasesoft.actsports.portal.service.FaqInfoService;
import com.hbasesoft.actsports.portal.service.api.FaqService;
import com.hbasesoft.framework.common.ServiceException;
import com.hbasesoft.framework.db.core.DaoException;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月19日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.cbs.service.impl <br>
 */
@Service
public class FaqServiceImpl extends CommonServiceImpl implements FaqInfoService {
    
	@Override
	public CmsWeixinMenuPojo getMenuByOrgCodeAndMenuCode(String orgCode, String menuCode) throws ServiceException {
		  DetachedCriteria criteria = DetachedCriteria.forClass(CmsWeixinMenuPojo.class);
	      criteria.add(Restrictions.eq(CmsWeixinMenuPojo.ORG_CODE, orgCode));
	      criteria.add(Restrictions.eq(CmsWeixinMenuPojo.MENU_CODE, menuCode));
	      return getCriteriaQuery(criteria);
	}

	@Override
	public List<CmsWeixinMenuPojo> getMenuByMenuCode(String menuCode)
			throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(CmsWeixinMenuPojo.class);
		criteria.add(Restrictions.eq(CmsWeixinMenuPojo.MENU_CODE, menuCode));
		return getListByCriteriaQuery(criteria);
	}


}
