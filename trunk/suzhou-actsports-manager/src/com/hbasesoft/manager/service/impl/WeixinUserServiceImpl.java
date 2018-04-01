package com.hbasesoft.manager.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hbasesoft.framework.manager.core.common.service.impl.CommonServiceImpl;
import com.hbasesoft.manager.service.WeixinUserService;
import com.hbasesoft.manager.vo.WeixinAccount;





@Service("weixinUserService")
@Transactional
public class WeixinUserServiceImpl extends CommonServiceImpl implements WeixinUserService {

	@Resource(name = "hibernateTemplate")
	private HibernateTemplate template;

	@Override
	public List<WeixinAccount> findAll() {
		String hql = "from WeixinAccount";
		return (List<WeixinAccount>) template.find(hql);
	}

	@Override
	public Map<String, Object> getTokenByAppid(String appid) {
		String hql = "select new map(id as id,accountaccesstoken as token) from WeixinAccount where accountappid=?";
		List<Map<String, Object>> list = (List<Map<String, Object>>) template.find(hql, appid);
			if (list.size() > 0) {
				for (int i = 0; i < list.size();) {
					return list.get(0);
				}
			}
			return null;
    }


}