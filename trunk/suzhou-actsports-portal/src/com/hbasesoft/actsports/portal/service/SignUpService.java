package com.hbasesoft.actsports.portal.service;

import java.util.Map;

import com.hbasesoft.actsports.portal.bean.ActResp;
import com.hbasesoft.actsports.portal.bean.prize.PrizeResult;

/**
 * <Description> <br>
 * 
 * @author 查思玮<br>
 * @version 0.1-SNAPSHOT<br>
 * @taskId <br>
 * @CreateDate 2016年12月08日 <br>
 * @since V0.1-SNAPSHOT<br>
 * @see com.hbasesoft.actSports.portal.service <br>
 */
public interface SignUpService {
    /**
     * Description: 获取报名页面<br>
     * 
     * @author zhasiwei<br>
     * @taskId <br>
     * @param subsId
     * @return
     * @throws Exception <br>
     */
	ActResp getSignUpPage(String userId, String entryId) throws Exception;
	 
	 /**
	 * Description: 微信報名<br>
	 * 
	 * @author zhasiwei<br>
	 * @taskId <br>
	 * @param subsId
	 * @return
	 * @throws Exception <br>
	 */
	ActResp signUp(String userId, Map<String, Object> map) throws Exception;
	 
	/**
	 * Description: 查询奖项结果<br>
	 * 
	 * @author zhasiwei<br>
	 * @taskId <br>
	 * @param 
	 * @return
	 * @throws Exception <br>
	 */
	PrizeResult queryPrizeList(String userId, Map<String, Object> map) throws Exception;

}
