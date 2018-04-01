package com.hbasesoft.manager.service;


import java.util.List;
import java.util.Map;

import com.hbasesoft.framework.manager.core.common.service.CommonService;
import com.hbasesoft.manager.vo.MaterialMage;
/**
 * 获取素材
 * @author YS-004
 *
 */
public interface MaterialService extends CommonService{
      
	List<Map<String, Object>> findMaterialGroup();
	
	List<MaterialMage> findByGroupId(String groupId);
	
	Map<String, Object> findMaterialByGroupId(String groupId);

	List<MaterialMage> findById(String[] imageId);
	
}
