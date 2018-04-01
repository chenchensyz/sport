package com.hbasesoft.actsports.portal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.hbasesoft.actsports.portal.bean.collect.ActCollectParamsPojo;
import com.hbasesoft.actsports.portal.bean.collect.ActCollectPojo;
import com.hbasesoft.actsports.portal.bean.collect.ActCollectResultAttachmentPojo;
import com.hbasesoft.actsports.portal.bean.collect.ActCollectResultIdeaPojo;
import com.hbasesoft.actsports.portal.bean.collect.ActCollectResultPojo;
import com.hbasesoft.actsports.portal.dao.collect.ActCollectDao;
import com.hbasesoft.actsports.portal.service.CollectInfoService;
import com.hbasesoft.framework.common.ServiceException;
import com.hbasesoft.framework.db.core.DaoException;

@Service
public class ActCollectServiceImpl extends CommonServiceImpl implements CollectInfoService {

	@Resource
	private ActCollectDao actCollectDao;

	@Override
	public ActCollectPojo queryCollectById(String collectId) throws ServiceException {
		return get(ActCollectPojo.class, collectId);
	}

	@Override
	public List<ActCollectParamsPojo> queryCollectParamsByEntryId(String collectId) throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActCollectParamsPojo.class);
		criteria.add(Restrictions.eq(ActCollectParamsPojo.COLLECT_ID, collectId));
		return getListByCriteriaQuery(criteria);
	}

	@Override
	public void saveActCollectResultAttachmentPojo(ActCollectResultAttachmentPojo pojo) throws ServiceException {
		try {
			actCollectDao.saveActCollectResultAttachmentPojo(pojo);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void saveActCollectResultIdeaPojo(ActCollectResultIdeaPojo pojo) throws ServiceException {
		try {
			actCollectDao.saveActCollectResultIdeaPojo(pojo);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void saveActCollectResultPojo(ActCollectResultPojo pojo) throws ServiceException {
		try {
			actCollectDao.saveActCollectResultPojo(pojo);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	
	
}
