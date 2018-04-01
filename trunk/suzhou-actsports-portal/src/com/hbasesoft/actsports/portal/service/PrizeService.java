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
 * @CreateDate 2017年1月04日 <br>
 * @since V0.1-SNAPSHOT<br>
 * @see com.hbasesoft.actSports.portal.service <br>
 */
public interface PrizeService {
    /**
     * Description: 获取抽奖<br>
     * 
     * @author zhasiwei<br>
     * @taskId <br>
     * @param 
     * @return
     * @throws Exception <br>
     */
	ActResp getPrizePage(String userId, String prizeId) throws Exception;
	 
	 /**
	 * Description: 微信抽奖<br>
	 * 
	 * @author zhasiwei<br>
	 * @taskId <br>
	 * @param 
	 * @return
	 * @throws Exception <br>
	 */
	ActResp prize(String userId, Map<String, Object> map) throws Exception;
	 
	/**
	 * Description: 录入中奖信息<br>
	 * 
	 * @author zhasiwei<br>
	 * @taskId <br>
	 * @param 
	 * @return
	 * @throws Exception <br>
	 */
	ActResp savePrizeResult(String userId, Map<String, Object> map) throws Exception;
	
	/**
	 * Description: 查询微信抽奖结果<br>
	 * 
	 * @author zhasiwei<br>
	 * @taskId <br>
	 * @param 
	 * @return
	 * @throws Exception <br>
	 */
	PrizeResult queryPrizeList(String userId, Map<String, Object> map) throws Exception;
}
