package com.hbasesoft.actsports.portal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.hbasesoft.actsports.portal.bean.entry.ActEntryChoosePojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryParamsPojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryPojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryResultChoosePojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryResultPojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntrySnPojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeSnPojo;
import com.hbasesoft.actsports.portal.dao.entry.ActEntryDao;
import com.hbasesoft.actsports.portal.service.EntryInfoService;
import com.hbasesoft.framework.common.ServiceException;
import com.hbasesoft.framework.db.core.DaoException;

@Service
public class ActEntryServiceImpl extends CommonServiceImpl implements EntryInfoService {

	@Resource
	private ActEntryDao actEntryDao;
	
	@Override
	public ActEntryPojo queryEntryById(String entryId) throws ServiceException {
		return get(ActEntryPojo.class, entryId);
	}

	@Override
	public List<ActEntryParamsPojo> queryEntryParamsByEntryId(String entryId) throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActEntryParamsPojo.class);
		criteria.add(Restrictions.eq(ActEntryParamsPojo.ENTRY_ID, entryId));
		return getListByCriteriaQuery(criteria);
	}

	@Override
	public List<ActEntryChoosePojo> queryEntrychooseByEntryId(String entryId) throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActEntryChoosePojo.class);
		criteria.add(Restrictions.eq(ActEntryChoosePojo.ENTRY_ID, entryId));
		return getListByCriteriaQuery(criteria);
	}

	@Override
	public void saveActEntryResultChoosePojo(ActEntryResultChoosePojo pojo) throws ServiceException {
//		 save(pojo);
		 try {
			 	actEntryDao.addEntryResultChoose(pojo);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
	}

	@Override
	public void saveActEntryResultPojo(ActEntryResultPojo pojo) throws ServiceException {
//		 save(pojo);
		 try {
		 	actEntryDao.addEntryResult(pojo);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ActEntryChoosePojo queryEntryChooseById(String Id) throws ServiceException {
		return get(ActEntryChoosePojo.class, Id);
	}

	@Override
	public int queryEntryRankByEntryId(String entryId) throws ServiceException {
		try {
			return actEntryDao.queryEntryRankByEntryId(entryId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public int queryEntryNumByEntryId(String entryId) throws ServiceException {
		try {
			return actEntryDao.queryEntryRankByEntryId(entryId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<ActEntrySnPojo> queryActEntrySnByStatusAndType(String entryId, String type, String status)
			throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActEntrySnPojo.class);
		criteria.add(Restrictions.eq(ActEntrySnPojo.ENTRYID, entryId));
		criteria.add(Restrictions.eq(ActEntrySnPojo.STATUS, status));
		criteria.add(Restrictions.eq(ActEntrySnPojo.TYPE, type));
		return getListByCriteriaQuery(criteria);
	}

	@Override
	public List<ActEntryResultChoosePojo> queryByEntryIdAndUserId(String entryId, String userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActEntryResultChoosePojo.class);
		criteria.add(Restrictions.eq(ActEntryResultChoosePojo.ENTRY_ID, entryId));
		criteria.add(Restrictions.eq(ActEntryResultChoosePojo.USER_ID, userId));
		return getListByCriteriaQuery(criteria);
	}
	
}
