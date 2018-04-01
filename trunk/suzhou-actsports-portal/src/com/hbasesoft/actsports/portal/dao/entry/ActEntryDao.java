package com.hbasesoft.actsports.portal.dao.entry;

import com.hbasesoft.actsports.portal.bean.entry.ActEntryResultChoosePojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryResultPojo;
import com.hbasesoft.framework.db.core.DaoException;
import com.hbasesoft.framework.db.core.annotation.Dao;
import com.hbasesoft.framework.db.core.annotation.Param;

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
public interface ActEntryDao {
	
	 	int queryEntryRankByEntryId(@Param("entryId") String entryId) throws DaoException;
	    
	    int queryEntryNumByEntryId(@Param("entryId") String entryId) throws DaoException;

	    void addEntryResultChoose(@Param("pojo") ActEntryResultChoosePojo pojo) throws DaoException;
	    
	    void addEntryResult(@Param("pojo") ActEntryResultPojo pojo) throws DaoException;
}
