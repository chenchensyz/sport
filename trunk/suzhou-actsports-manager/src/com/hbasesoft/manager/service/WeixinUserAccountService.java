package com.hbasesoft.manager.service;


import java.util.List;
import com.hbasesoft.framework.manager.core.common.service.CommonService;
import com.hbasesoft.manager.vo.WeixinAccount;
import com.hbasesoft.manager.vo.WeixinUserAccount;


public interface WeixinUserAccountService extends CommonService{
    
	 
	
    void deleteByAccount(String account);
    
    WeixinUserAccount findByAccount(String account);
    
    List<WeixinUserAccount> findUserAccountId(String accountId);
    
    List<WeixinUserAccount> findUserAccountByPage(int start,int pageSize,String groupId);
    
    List<WeixinUserAccount> findNicknameLike(String weixinId,String nickName);
    
}
