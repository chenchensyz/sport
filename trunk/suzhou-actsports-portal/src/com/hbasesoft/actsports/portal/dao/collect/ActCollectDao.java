package com.hbasesoft.actsports.portal.dao.collect;

import com.hbasesoft.actsports.portal.bean.collect.ActCollectResultAttachmentPojo;
import com.hbasesoft.actsports.portal.bean.collect.ActCollectResultIdeaPojo;
import com.hbasesoft.actsports.portal.bean.collect.ActCollectResultPojo;
import com.hbasesoft.framework.db.core.DaoException;
import com.hbasesoft.framework.db.core.annotation.Dao;
import com.hbasesoft.framework.db.core.annotation.Param;

/**
 * <Description> <br>
 * 
 * @author zhasiwei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年12月29日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.actsports.portal.dao.ActCollect <br>
 */
@Dao
public interface ActCollectDao {
	
	    void saveActCollectResultAttachmentPojo(@Param("pojo") ActCollectResultAttachmentPojo pojo) throws DaoException;
	    
	    void saveActCollectResultIdeaPojo(@Param("pojo") ActCollectResultIdeaPojo pojo) throws DaoException;
	    
	    void saveActCollectResultPojo(@Param("pojo") ActCollectResultPojo pojo) throws DaoException;
	    
}
