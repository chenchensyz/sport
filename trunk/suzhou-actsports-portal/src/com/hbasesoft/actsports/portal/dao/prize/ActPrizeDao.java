package com.hbasesoft.actsports.portal.dao.prize;

import com.hbasesoft.actsports.portal.bean.prize.ActPrizeResultCountPojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeResultPojo;
import com.hbasesoft.framework.db.core.DaoException;
import com.hbasesoft.framework.db.core.annotation.Dao;
import com.hbasesoft.framework.db.core.annotation.Param;

/**
 * <Description> <br>
 * 
 * @author zhasiwei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年1月4日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.actsports.portal.dao.ActEntry <br>
 */
@Dao

public interface ActPrizeDao {
	
	ActPrizeResultCountPojo queryPrizeResultCountByUserIdAndPrizeId(@Param("userId") String userId, @Param("prizeId") String prizeId)throws DaoException;
	
	void savePrizeResult(@Param("pojo") ActPrizeResultPojo pojo) throws DaoException;
}
