package com.hbasesoft.manager.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.hbasesoft.manager.service.MaterialService;
import com.hbasesoft.manager.vo.MaterialMage;

@Controller
@RequestMapping("/plug-in/ueditor/dialogs/image")
public class MaterialController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MaterialController.class);

	@Resource(name = "materialService")
	private MaterialService materialService;

	// 获取素材分组
	@RequestMapping(value = "getMaterialGroup")
	@ResponseBody
	public List<Map<String, Object>> getMaterialGroup() {
		LOGGER.info("获取素材分组...");
		List<Map<String, Object>> materials = materialService.findMaterialGroup();
		return materials;
	}

	// 根据分组获取图片
	@RequestMapping(value = "getMaterial")
	@ResponseBody
	public List<MaterialMage> getMaterial(String groupId) {
		LOGGER.info("根据分组获取图片getMaterial()...{}", groupId);
		List<MaterialMage> materia=Lists.newArrayList();
		List<MaterialMage> m = materialService.findByGroupId(groupId);
		for (MaterialMage materialMage : m) {
			if ("image".equals(materialMage.getType())) {
				materia.add(materialMage);
			}
		}
		return materia;
	}

	// 图文获取图片
	@RequestMapping(value = "getImage")
	@ResponseBody
	public List<MaterialMage> getImage(@RequestParam("id") String... imageId) {
		LOGGER.info("图文获取图片...", imageId);
		List<MaterialMage> materialMage = materialService.findById(imageId);
		return materialMage;
	}
}
