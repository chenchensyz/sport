package com.hbasesoft.manager.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbasesoft.framework.manager.core.common.service.impl.CommonServiceImpl;
import com.hbasesoft.manager.service.MaterialService;
import com.hbasesoft.manager.vo.MaterialMage;
import com.hbasesoft.manager.vo.WeixinGroupNew;
import com.hbasesoft.manager.vo.WeixinUserAccount;





@Service("materialService")
@Transactional
public class MaterialServiceImpl extends CommonServiceImpl implements MaterialService {

	@Resource(name = "hibernateTemplate")
	private HibernateTemplate template;

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findMaterialGroup() {
	    String hql="select new map(id as id,name as name) from MaterialGroup";
		List<Map<String,Object>> list=(List<Map<String, Object>>) template.find(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialMage> findByGroupId(String groupId) {
		String hql = "from MaterialMage where groupId=?";
		List<MaterialMage> list = (List<MaterialMage>) template.find(hql, groupId);
		return list;
	}

	@Override
	public Map<String, Object> findMaterialByGroupId(String groupId) {
		String hql = "select new map(id as id,name as name) from MaterialGroup where id=?";
		List<Map<String, Object>> list=  (List<Map<String, Object>>) template.find(hql,groupId);
		if (list.size()>0) {
			for (int i = 0; i < list.size();) {
				return list.get(0);
			}
		}
		return null;
	}

	@Override
	public List<MaterialMage> findById(String[] imageId) {
		String hql = "from MaterialMage where id in ?";
		List<MaterialMage> list=  (List<MaterialMage>) template.find(hql,imageId);
		
		return list;
	}
	
	
	
}