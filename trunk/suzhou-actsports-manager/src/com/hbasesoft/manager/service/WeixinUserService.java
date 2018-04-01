package com.hbasesoft.manager.service;


import java.util.List;
import java.util.Map;

import com.hbasesoft.framework.manager.core.common.service.CommonService;
import com.hbasesoft.manager.vo.WeixinAccount;


public interface WeixinUserService extends CommonService{
    
	 List<WeixinAccount> findAll();
	
	Map<String, Object> getTokenByAppid(String appid);
}
