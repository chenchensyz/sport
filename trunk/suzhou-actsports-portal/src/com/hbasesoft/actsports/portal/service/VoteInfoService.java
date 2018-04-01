package com.hbasesoft.actsports.portal.service;

import java.util.List;

import com.hbasesoft.actsports.portal.bean.vote.ActVoteChoosePojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVoteParamsPojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVotePojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVoteResultChoosePojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVoteResultPojo;
import com.hbasesoft.actsports.portal.biz.vo.WeixinReleasePojo;
import com.hbasesoft.framework.common.ServiceException;

/**
 * <Description> <br>
 * 
 * @author zhasiwei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年12月12日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.actsprots.portal.service <br>
 */
public interface VoteInfoService extends CommonService{
	
	// 根据voteId获得vote
	ActVotePojo queryVoteById(String voteId) throws ServiceException;
	
	// 根据voteId和分页获得actVoteChoose列表
	List<ActVoteChoosePojo> queryVoteChooseByVoteIdWithPageNum(String voteId, int pageNum, int pageSize) throws ServiceException;
	
	// 根据voteId和ChooseId获得voteChoose
	ActVoteChoosePojo queryVoteChooseByVoteIdAndChooseId(String voteId, String chooseId) throws ServiceException;
	
	// 根据voteId获得voteParams
	List<ActVoteParamsPojo> queryVoteParamsByVoteId(String voteId) throws ServiceException;
	
	// 根据voteId获得voteChoose总数
	int queryChooseCountByVoteId(String voteId) throws ServiceException;
	
	// 根据对象存voteResultChoose
	void saveVoteResultChoose(ActVoteResultChoosePojo actResultChoose) throws ServiceException;
	
	// 根据对象更新voteChoose
	void updateVoteChoose(ActVoteChoosePojo actVoteChoose, String userId) throws ServiceException;
	
	// 根据对象存actVoteResult
	void saveActVoteResultPojo(ActVoteResultPojo actVoteResult) throws ServiceException;
	
	List<WeixinReleasePojo> getWexinReleaseList(String templateCode) throws ServiceException;
}
