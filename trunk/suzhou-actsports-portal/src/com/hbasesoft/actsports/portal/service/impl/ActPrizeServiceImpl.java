package com.hbasesoft.actsports.portal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.hbasesoft.actsports.portal.bean.prize.ActPrizeChoosePojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeParamsPojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizePojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeResultChoosePojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeResultCountPojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeResultPojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeSnPojo;
import com.hbasesoft.actsports.portal.dao.prize.ActPrizeDao;
import com.hbasesoft.actsports.portal.service.PrizeInfoService;
import com.hbasesoft.framework.common.ServiceException;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.db.core.DaoException;

@Service
public class ActPrizeServiceImpl extends CommonServiceImpl implements PrizeInfoService {

	@Resource
	private ActPrizeDao actPrizeDao;

	@Override
	public ActPrizePojo queryPrizeById(String prizeId) throws ServiceException {
		return get(ActPrizePojo.class, prizeId);
	}

	@Override
	public List<ActPrizeResultChoosePojo> queryPrizeResultChooseByUserIdAndPrizeId(String userId, String prizeId)
			throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActPrizeResultChoosePojo.class);
		criteria.add(Restrictions.eq(ActPrizeResultChoosePojo.PRIZE_ID, prizeId));
		if(!CommonUtil.isEmpty(userId))
			criteria.add(Restrictions.eq(ActPrizeResultChoosePojo.USER_ID, userId));
		else
			criteria.add(Restrictions.isNotNull(ActPrizeResultChoosePojo.CHOOSE_ID));
		return getListByCriteriaQuery(criteria);
	}

	@Override
	public List<ActPrizeResultChoosePojo> queryPrizeChooseByUserIdAndPrizeId(String userId, String prizeId)
			throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActPrizeResultChoosePojo.class);
		criteria.add(Restrictions.eq(ActPrizeResultChoosePojo.PRIZE_ID, prizeId));
		criteria.add(Restrictions.eq(ActPrizeResultChoosePojo.USER_ID, userId));
		criteria.add(Restrictions.isNotNull(ActPrizeResultChoosePojo.CHOOSE_ID));
		return getListByCriteriaQuery(criteria);
	}
	
	@Override
	public ActPrizeResultCountPojo queryPrizeResultCountByUserIdAndPrizeId(String userId, String prizeId)
			throws ServiceException {
		ActPrizeResultCountPojo pojo = new ActPrizeResultCountPojo();
		try {
			pojo = actPrizeDao.queryPrizeResultCountByUserIdAndPrizeId(userId, prizeId);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return pojo;
	}

	@Override
	public void savePrizeResultChoose(ActPrizeResultChoosePojo pojo) throws ServiceException {
		save(pojo);
	}

	@Override
	public void savePrizeResultCount(ActPrizeResultCountPojo pojo) throws ServiceException {
		save(pojo);
	}

	@Override
	public List<ActPrizeChoosePojo> queryPrizeChooseByPrizeId(String prizeId) throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActPrizeChoosePojo.class);
		criteria.add(Restrictions.eq(ActPrizeChoosePojo.PRIZE_ID, prizeId));
		return getListByCriteriaQuery(criteria);
	}

	@Override
	public void updatePrizeChoose(ActPrizeChoosePojo pojo) throws ServiceException {
		updateEntity(pojo);
	}

	@Override
	public void updatePrizeResultCount(ActPrizeResultCountPojo pojo) throws ServiceException {
		updateEntity(pojo);
	}

	@Override
	public List<ActPrizeParamsPojo> queryActPrizeParamsByPrizeId(String prizeId) throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActPrizeParamsPojo.class);
		criteria.add(Restrictions.eq(ActPrizeParamsPojo.PRIZE_ID, prizeId));
		return getListByCriteriaQuery(criteria);
	}

	@Override
	public void savePrizeResult(ActPrizeResultPojo pojo) throws ServiceException {
		try {
			actPrizeDao.savePrizeResult(pojo);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<ActPrizeSnPojo> queryActPrizeSnByStatusAndType(String type, String status) throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActPrizeSnPojo.class);
		criteria.add(Restrictions.eq(ActPrizeSnPojo.STATUS, status));
		criteria.add(Restrictions.eq(ActPrizeSnPojo.TYPE, type));
		return getListByCriteriaQuery(criteria);
	}

	@Override
	public ActPrizeChoosePojo queryActPrizeChooseByEntryAndTitle(String entryId, String title) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActPrizeChoosePojo.class);
		criteria.add(Restrictions.eq(ActPrizeChoosePojo.ENTRYID, entryId));
		criteria.add(Restrictions.eq("title", title));
		return getCriteriaQuery(criteria);
	}
}