package com.hbasesoft.manager.service;


import java.util.List;
import com.hbasesoft.framework.manager.core.common.service.CommonService;
import com.hbasesoft.manager.vo.WeixinGroupNew;

public interface WeixinGroupNewService extends CommonService{
      
	WeixinGroupNew findByGroupId(String groupId);
	
	WeixinGroupNew findByAccoundIdAndGroupId(String accountId,String groupId);
	
	List<WeixinGroupNew> findAll();
	
	List<WeixinGroupNew> findByAccoundId(String accountId);
	
	void deleteByGroupId(String groupId);
}
