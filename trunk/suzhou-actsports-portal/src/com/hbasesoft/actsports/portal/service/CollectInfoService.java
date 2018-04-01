package com.hbasesoft.actsports.portal.service;

import java.util.List;

import com.hbasesoft.actsports.portal.bean.collect.ActCollectParamsPojo;
import com.hbasesoft.actsports.portal.bean.collect.ActCollectPojo;
import com.hbasesoft.actsports.portal.bean.collect.ActCollectResultAttachmentPojo;
import com.hbasesoft.actsports.portal.bean.collect.ActCollectResultIdeaPojo;
import com.hbasesoft.actsports.portal.bean.collect.ActCollectResultPojo;
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
public interface CollectInfoService extends CommonService{
	
	ActCollectPojo queryCollectById(String collectId) throws ServiceException;
	
	List<ActCollectParamsPojo> queryCollectParamsByEntryId(String collectId) throws ServiceException;
	
	void saveActCollectResultAttachmentPojo(ActCollectResultAttachmentPojo pojo) throws ServiceException;
	
	void saveActCollectResultIdeaPojo(ActCollectResultIdeaPojo pojo) throws ServiceException;
	
	void saveActCollectResultPojo(ActCollectResultPojo pojo) throws ServiceException;
	
}
