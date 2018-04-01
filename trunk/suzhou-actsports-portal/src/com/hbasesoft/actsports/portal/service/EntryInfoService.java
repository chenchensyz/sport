package com.hbasesoft.actsports.portal.service;

import java.util.List;

import com.hbasesoft.actsports.portal.bean.entry.ActEntryChoosePojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryParamsPojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryPojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryResultChoosePojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryResultPojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntrySnPojo;
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
public interface EntryInfoService extends CommonService {

	ActEntryPojo queryEntryById(String entryId) throws ServiceException;

	ActEntryChoosePojo queryEntryChooseById(String Id) throws ServiceException;

	List<ActEntryParamsPojo> queryEntryParamsByEntryId(String entryId) throws ServiceException;

	List<ActEntryChoosePojo> queryEntrychooseByEntryId(String entryId) throws ServiceException;

	void saveActEntryResultChoosePojo(ActEntryResultChoosePojo pojo) throws ServiceException;

	void saveActEntryResultPojo(ActEntryResultPojo pojo) throws ServiceException;

	// 查询当前报名排名
	int queryEntryRankByEntryId(String entryId) throws ServiceException;

	// 查询当前项目entry报名人数
	int queryEntryNumByEntryId(String entryId) throws ServiceException;

	List<ActEntrySnPojo> queryActEntrySnByStatusAndType(String entryId, String type, String status)
			throws ServiceException;

	//查询报名用户报名项目
	List<ActEntryResultChoosePojo> queryByEntryIdAndUserId(String entryId, String userId);

}
