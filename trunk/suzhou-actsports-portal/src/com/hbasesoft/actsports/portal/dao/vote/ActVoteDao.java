package com.hbasesoft.actsports.portal.dao.vote;

import java.util.List;

import com.hbasesoft.actsports.portal.bean.vote.ActVoteChoosePojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVoteResultChoosePojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVoteResultPojo;
import com.hbasesoft.actsports.portal.biz.vo.WeixinReleasePojo;
import com.hbasesoft.framework.db.core.DaoException;
import com.hbasesoft.framework.db.core.annotation.Dao;
import com.hbasesoft.framework.db.core.annotation.Param;
import com.hbasesoft.framework.db.core.annotation.Sql;

/**
 * <Description> <br>
 * 
 * @author zhasiwei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年12月13日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.actsports.portal.dao.ActEntry <br>
 */
@Dao

public interface ActVoteDao {
	@Sql(bean = ActVoteChoosePojo.class)
	List<ActVoteChoosePojo> queryVoteChooseByVoteIdWithPageNum(@Param("voteId") String voteId,
			@Param(Param.PAGE_INDEX) int PAGE_INDEX, @Param(Param.PAGE_SIZE) int pageSize) throws DaoException;

	int queryChooseCountByVoteId(@Param("voteId") String voteId) throws DaoException;
	
	void saveVoteResultChoose(@Param("pojo") ActVoteResultChoosePojo pojo) throws DaoException;
	
	void updateVoteChoose(@Param("pojo") ActVoteChoosePojo pojo, @Param("userId") String userId) throws DaoException ;
	
	void saveActVoteResultPojo(@Param("pojo") ActVoteResultPojo pojo) throws DaoException;
	
	@Sql(bean = WeixinReleasePojo.class)
    List<WeixinReleasePojo> getWeixinReleaseList(@Param("templateCode") String templateCode);
}
