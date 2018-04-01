/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbasesoft.framework.manager.core.common.service.impl.CommonServiceImpl;
import com.hbasesoft.manager.dao.EntryDao;
import com.hbasesoft.manager.service.EntryService;
import com.hbasesoft.manager.vo.ActPrizeSnPojo;
import com.hbasesoft.manager.vo.EntryResultVo;

/** 
 * <Description> <br> 
 *  
 * @author 查思玮<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年6月19日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.manager.service.impl <br>
 */
@Service("entryService")
@Transactional
public class EntryServiceImpl extends CommonServiceImpl implements EntryService{

	@Autowired
	private EntryDao entryDao;

	/**
	 * Description: <br> 
	 *  
	 * @author 查思玮<br>
	 * @taskId <br>
	 * @param entryId
	 * @return
	 * @throws Exception <br>
	 */ 
	@Override
	public List<EntryResultVo> getEntryResultVoByEntryId(String entryId) throws Exception {
		return entryDao.getEntryResultVo(entryId);
	}

	
	
}
