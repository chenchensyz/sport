package com.hbasesoft.manager.service.impl;


import java.util.List;

import javax.annotation.Resource;


import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbasesoft.framework.manager.core.common.service.impl.CommonServiceImpl;
import com.hbasesoft.manager.service.WeixinGroupNewService;
import com.hbasesoft.manager.vo.WeixinGroupNew;
import com.hbasesoft.manager.vo.WeixinUserAccount;





@Service("weixinGroupNewService")
@Transactional
public class WeixinGroupNewServiceImpl extends CommonServiceImpl implements WeixinGroupNewService {

	@Resource(name = "hibernateTemplate")
	private HibernateTemplate template;
	
	@Override
	public WeixinGroupNew findByGroupId(String groupId) {
		String hql = "from WeixinGroupNew where groupid=?";
		List<WeixinGroupNew> list= (List<WeixinGroupNew>) template.find(hql,groupId);
		if (list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				return list.get(0);
			}
		}
		return null;
		
	}

	@Override
	public WeixinGroupNew findByAccoundIdAndGroupId(String accountId, String groupId) {
		String hql = "from WeixinGroupNew where accountid=? and groupid=?";
		List<WeixinGroupNew> list= (List<WeixinGroupNew>) template.find(hql,new String[]{accountId,groupId});
		if (list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				return list.get(0);
			}
		}
		return null;
	}

	@Override
	public List<WeixinGroupNew> findAll() {
		String hql = "from WeixinGroupNew";
		List<WeixinGroupNew> list= (List<WeixinGroupNew>) template.find(hql);
		return list;
	}

	@Override
	public List<WeixinGroupNew> findByAccoundId(String accountId) {
		String hql = "from WeixinGroupNew where accoundId=?";
		List<WeixinGroupNew> list= (List<WeixinGroupNew>) template.find(hql,accountId);
		return list;
	}

	@Override
	public void deleteByGroupId(String groupId) {
		template.bulkUpdate("delete WeixinGroupNew where groupId=?", new Object[]{groupId});
	}
	
}