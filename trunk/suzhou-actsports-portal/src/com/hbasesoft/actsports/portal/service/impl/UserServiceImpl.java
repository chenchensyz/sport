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

import com.hbasesoft.actsports.portal.bean.ActUserAccountPojo;
import com.hbasesoft.actsports.portal.bean.ActUserPojo;
import com.hbasesoft.actsports.portal.bean.collect.ActCollectResultIdeaPojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryResultChoosePojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVoteResultChoosePojo;
import com.hbasesoft.actsports.portal.constant.CommonContant;
import com.hbasesoft.actsports.portal.dao.user.UserDao;
import com.hbasesoft.actsports.portal.service.UserService;
import com.hbasesoft.framework.common.ServiceException;
import com.hbasesoft.framework.db.core.DaoException;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月14日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.cbs.service.impl <br>
 */
@Service
public class UserServiceImpl extends CommonServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	/**
	 * Description: <br>
	 * 
	 * @author 王伟<br>
	 * @taskId <br>
	 * @param account
	 * @param acctType
	 * @return
	 * @throws ServiceException
	 *             <br>
	 */
	@Override
	public ActUserPojo getUserByAccount(String account, String acctType) throws ServiceException {
		try {
			return userDao.getUserByAccount(acctType, account);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Description: <br>
	 * 
	 * @author 王伟<br>
	 * @taskId <br>
	 * @param account
	 * @param acctType
	 * @return
	 * @throws ServiceException
	 *             <br>
	 */
	@Override
	public ActUserAccountPojo getAccoutByAccctAndType(String account, String acctType) throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActUserAccountPojo.class);
		criteria.add(Restrictions.eq(ActUserAccountPojo.ACCOUNT, account));
		criteria.add(Restrictions.eq(ActUserAccountPojo.TYPE, acctType));
		criteria.add(Restrictions.eq(ActUserAccountPojo.STATE, CommonContant.AVALIABLE));
		return getCriteriaQuery(criteria);
	}

	/**
	 * Description: <br>
	 * 
	 * @author 王伟<br>
	 * @taskId <br>
	 * @param userId
	 * @return
	 * @throws ServiceException
	 *             <br>
	 */
	@Override
	public List<ActUserAccountPojo> queryAccountByUserId(String userId) throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActUserAccountPojo.class);
		criteria.add(Restrictions.eq(ActUserAccountPojo.USER_ID, userId));
		criteria.add(Restrictions.eq(ActUserAccountPojo.STATE, CommonContant.AVALIABLE));
		return getListByCriteriaQuery(criteria);
	}

	@Override
	public List<ActEntryResultChoosePojo> queryEntryChooseListByUserIdAndEntryId(String userId, String entryId)
			throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActEntryResultChoosePojo.class);
		criteria.add(Restrictions.eq(ActEntryResultChoosePojo.USER_ID, userId));
		criteria.add(Restrictions.eq(ActEntryResultChoosePojo.ENTRY_ID, entryId));
		return getListByCriteriaQuery(criteria);
	}

	@Override
	public List<ActEntryResultChoosePojo> queryEntryChooseListByChooseIdAndEntryId(String chooseId, String entryId)
			throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActEntryResultChoosePojo.class);
		criteria.add(Restrictions.eq(ActEntryResultChoosePojo.CHOOSE_ID, chooseId));
		criteria.add(Restrictions.eq(ActEntryResultChoosePojo.ENTRY_ID, entryId));
		return getListByCriteriaQuery(criteria);
	}

	@Override
	public List<ActEntryResultChoosePojo> queryEntryChooseListByChooseIdAndEntryIdAndUserId(String chooseId, String entryId, String userId)
			throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActEntryResultChoosePojo.class);
		criteria.add(Restrictions.eq(ActEntryResultChoosePojo.CHOOSE_ID, chooseId));
		criteria.add(Restrictions.eq(ActEntryResultChoosePojo.ENTRY_ID, entryId));
		criteria.add(Restrictions.eq(ActEntryResultChoosePojo.USER_ID, userId));
		return getListByCriteriaQuery(criteria);
	}

	@Override
	public List<ActVoteResultChoosePojo> queryVoteChooseListByUserIdAndVoteId(String userId, String voteId)
			throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActVoteResultChoosePojo.class);
		criteria.add(Restrictions.eq(ActVoteResultChoosePojo.USER_ID, userId));
		criteria.add(Restrictions.eq(ActVoteResultChoosePojo.VOTE_ID, voteId));
		return getListByCriteriaQuery(criteria);
	}

	@Override
	public ActCollectResultIdeaPojo queryCollectIdeaByUserIdAndCollectId(String userId, String collectId)
			throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActCollectResultIdeaPojo.class);
		criteria.add(Restrictions.eq(ActCollectResultIdeaPojo.USER_ID, userId));
		criteria.add(Restrictions.eq(ActCollectResultIdeaPojo.COLLECT_ID, collectId));
		return getCriteriaQuery(criteria);
	}

}