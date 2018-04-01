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
import com.hbasesoft.manager.dao.VoteDao;
import com.hbasesoft.manager.service.VoteService;
import com.hbasesoft.manager.vo.VoteResultVo;

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
@Service("voteService")
@Transactional
public class VoteServiceImpl extends CommonServiceImpl implements VoteService{

	@Autowired
	private VoteDao voteDao;

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
	public List<VoteResultVo> getVoteResultVoByVoteId(String voteId) throws Exception {
		return voteDao.getVoteResultVo(voteId);
	}
     
	@Override
	public VoteResultVo getVoteByVoteId(String voteId) {
		
		return voteDao.getVoteByVoteId(voteId);
	}
	
}
