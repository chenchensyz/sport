/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbasesoft.actsports.portal.service.WechatDbService;
import com.hbasesoft.framework.common.ServiceException;
import com.hbasesoft.framework.common.utils.logger.Logger;
import com.hbasesoft.vcc.wechat.bean.AccountPojo;
import com.hbasesoft.vcc.wechat.bean.MenuentityPojo;
import com.hbasesoft.vcc.wechat.bean.TexttemplatePojo;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年6月4日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.wx.service.impl <br>
 */
@Service
public class WechatDbServiceImpl extends CommonServiceImpl implements WechatDbService {
	private static Logger logger = new Logger(WechatDbServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public String getWexinAppid(String id) throws ServiceException {
		AccountPojo account = get(AccountPojo.class, id);
		String weixinAppid = account == null ? "" : account.getAccountappid();
		logger.info("从数据库中查询出的weixinAppid为：：" + weixinAppid);
		return weixinAppid;
	}

	@Override
	@Transactional(readOnly = true)
	public String getWexinAppsecret(String id) throws ServiceException {
		AccountPojo account = get(AccountPojo.class, id);
		String weixinAppsecret = account == null ? "" : account.getAccountappsecret();
		logger.info("从数据库中查询出的weixinAppsecret为：：" + weixinAppsecret);
		return weixinAppsecret;
	}

}
