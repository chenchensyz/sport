package com.hbasesoft.actsports.portal.service;

import java.util.List;

import com.hbasesoft.actsports.portal.bean.prize.ActPrizeChoosePojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeParamsPojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizePojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeResultChoosePojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeResultCountPojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeResultPojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeSnPojo;
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
public interface PrizeInfoService extends CommonService{
	
	ActPrizePojo queryPrizeById(String prizeId) throws ServiceException;
	
	List<ActPrizeResultChoosePojo> queryPrizeResultChooseByUserIdAndPrizeId(String userId, String prizeId) throws ServiceException;
	
	List<ActPrizeResultChoosePojo> queryPrizeChooseByUserIdAndPrizeId(String userId, String prizeId) throws ServiceException;
	
	ActPrizeResultCountPojo queryPrizeResultCountByUserIdAndPrizeId(String userId, String prizeId) throws ServiceException;
	
	void savePrizeResultChoose(ActPrizeResultChoosePojo pojo) throws ServiceException;
	
	void savePrizeResultCount(ActPrizeResultCountPojo pojo) throws ServiceException;
	
	void savePrizeResult(ActPrizeResultPojo pojo) throws ServiceException;
	
	List<ActPrizeChoosePojo>  queryPrizeChooseByPrizeId(String prizeId) throws ServiceException;
	
	void updatePrizeChoose(ActPrizeChoosePojo pojo) throws ServiceException;
	
	void updatePrizeResultCount(ActPrizeResultCountPojo pojo) throws ServiceException;
	
	List<ActPrizeParamsPojo> queryActPrizeParamsByPrizeId(String prizeId) throws ServiceException;
	
	List<ActPrizeSnPojo> queryActPrizeSnByStatusAndType(String type, String status) throws ServiceException;
	
	ActPrizeChoosePojo queryActPrizeChooseByEntryAndTitle(String entryId, String title);
	
}
