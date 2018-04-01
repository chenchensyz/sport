package com.hbasesoft.actsports.portal.service;

import java.util.Map;

import com.hbasesoft.actsports.portal.bean.ActResp;

/**
 * <Description> <br>
 * 
 * @author 查思玮<br>
 * @version 0.1-SNAPSHOT<br>
 * @taskId <br>
 * @CreateDate 2016年12月20日 <br>
 * @since V0.1-SNAPSHOT<br>
 * @see com.hbasesoft.actSports.portal.service <br>
 */
public interface VoteService {
    /**
     * Description: 获取投票页面<br>
     * 
     * @author zhasiwei<br>
     * @taskId <br>
     * @param subsId
     * @throws Exception <br>
     * @return
     */
	ActResp getVotePage(String userId, String voteId, int pageNum, int pageSize) throws Exception;
	 
	 /**
	 * Description: 微信投票<br>
	 * 
	 * @author zhasiwei<br>
	 * @taskId <br>
	 * @param subsId
	 * @return
	 * @throws Exception <br>
	 */
	ActResp vote(String userId, Map<String, Object> map) throws Exception;
	 

}
