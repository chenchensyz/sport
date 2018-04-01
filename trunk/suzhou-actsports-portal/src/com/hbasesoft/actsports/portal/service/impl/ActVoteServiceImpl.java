package com.hbasesoft.actsports.portal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.hbasesoft.actsports.portal.bean.vote.ActVoteChoosePojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVoteParamsPojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVotePojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVoteResultChoosePojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVoteResultPojo;
import com.hbasesoft.actsports.portal.biz.vo.WeixinReleasePojo;
import com.hbasesoft.actsports.portal.dao.vote.ActVoteDao;
import com.hbasesoft.actsports.portal.service.VoteInfoService;
import com.hbasesoft.framework.common.ServiceException;
import com.hbasesoft.framework.db.core.DaoException;

@Service
public class ActVoteServiceImpl extends CommonServiceImpl implements VoteInfoService {

	@Resource
	private ActVoteDao actVoteDao;
	
	@Override
	public ActVotePojo queryVoteById(String voteId) throws ServiceException {
		return get(ActVotePojo.class, voteId);
	}

	@Override
	public List<ActVoteChoosePojo> queryVoteChooseByVoteIdWithPageNum(String voteId, int pageNum, int pageSize) throws ServiceException {
		try {
			return actVoteDao.queryVoteChooseByVoteIdWithPageNum(voteId, pageNum, pageSize);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		//return null;
	}

	@Override
	public List<ActVoteParamsPojo> queryVoteParamsByVoteId(String voteId) throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActVoteParamsPojo.class);
		criteria.add(Restrictions.eq(ActVoteParamsPojo.VOTE_ID, voteId));
		return getListByCriteriaQuery(criteria);
	}

	@Override
	public int queryChooseCountByVoteId(String voteId) throws ServiceException {
		try {
			return actVoteDao.queryChooseCountByVoteId(voteId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void saveVoteResultChoose(ActVoteResultChoosePojo actResultChoose) throws ServiceException {
		try {
			actVoteDao.saveVoteResultChoose(actResultChoose);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ActVoteChoosePojo queryVoteChooseByVoteIdAndChooseId(String voteId, String chooseId)
			throws ServiceException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ActVoteChoosePojo.class);
		criteria.add(Restrictions.eq(ActVoteChoosePojo.VOTE_ID, voteId));
		criteria.add(Restrictions.eq(ActVoteChoosePojo.ID, chooseId));
		return getCriteriaQuery(criteria);
	}

	@Override
	public void updateVoteChoose(ActVoteChoosePojo actVoteChoose, String userId) throws ServiceException {
		try {
			actVoteDao.updateVoteChoose(actVoteChoose, userId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void saveActVoteResultPojo(ActVoteResultPojo actVoteResult) throws ServiceException {
		try {
			actVoteDao.saveActVoteResultPojo(actVoteResult);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

    /**
     * Description: <br> 
     *  
     * @author ruiluhui<br>
     * @taskId <br>
     * @return
     * @throws ServiceException <br>
     */ 
    @Override
    public List<WeixinReleasePojo> getWexinReleaseList(String templateCode) throws ServiceException {
        try {
            return actVoteDao.getWeixinReleaseList(templateCode);
        }
        catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

	
}
