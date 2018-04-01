package com.beumu.print.web.product;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.MediaTypes;
import org.springside.modules.web.Servlets;
import com.beumu.print.entity.AlbumProducers;
import com.beumu.print.entity.LayoutInfo;
import com.beumu.print.entity.LayoutInfoDetails;
import com.beumu.print.entity.PhotoProduct;
import com.beumu.print.entity.PlatformPhotoProduct;
import com.beumu.print.entity.ProductTemplateIndex;
import com.beumu.print.entity.ProductTemplateLayoutinfo;
import com.beumu.print.entity.ProductTemplateList;
import com.beumu.print.entity.ProductTemplateTag;
import com.beumu.print.entity.RestResponse;
import com.beumu.print.model.LayoutInfosSonList;
import com.beumu.print.model.LayoutInfosSonModel;
import com.beumu.print.model.LayoutInfosTemplateModel;
import com.beumu.print.model.LayoutInfosteListModel;
import com.beumu.print.service.album.AlbumProducersService;
import com.beumu.print.service.album.LayoutInfoDetailsService;
import com.beumu.print.service.album.LayoutInfoService;
import com.beumu.print.service.album.LayoutInfosService;
import com.beumu.print.service.product.PhotoProductService;
import com.beumu.print.service.product.PlatformPhotoProductService;
import com.beumu.print.service.product.ProductTemplateIndexService;
import com.beumu.print.service.product.ProductTemplateLayoutinfoService;
import com.beumu.print.service.product.ProductTemplateListService;
import com.beumu.print.service.product.ProductTemplateTagService;
import com.beumu.print.utils.DateUtils;
import com.beumu.print.utils.StateUtils;
import com.beumu.print.web.account.BaseController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "template")
public class ProductTemplateListController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductTemplateListController.class);
	private static final String PAGE_SIZE = "5";
	private static final String CLEAR = "clear";// 赋予图片特定值更换图片

	@Autowired
	private ProductTemplateTagService productTemplateTagService;

	@Autowired
	private ProductTemplateListService productTemplateListService;

	@Autowired
	private LayoutInfoService layoutInfoService;

	@Autowired
	private ProductTemplateLayoutinfoService productTemplateLayoutinfoService;

	@Autowired
	private PhotoProductService photoProductService;

	@Autowired
	private PlatformPhotoProductService platformPhotoProductService;

	@Autowired
	private AlbumProducersService albumProducersService;

	@Autowired
	private LayoutInfosService layoutInfosService;

	@Autowired
	private ProductTemplateIndexService productTemplateIndexService;

	@Autowired
	private LayoutInfoDetailsService layoutInfoDetailsService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(@RequestParam Long productId, @RequestParam(value = "justme", required = false) String justme,
			@RequestParam(value = "sortType", defaultValue = "createtime") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize, Model model) {
		LOGGER.info(" --->跳转模板管理~", productId);
		Map<String, Object> map = Maps.newHashMap();
		map.put("EQ_state", StateUtils.TEMPLATE_STATE_PASS);
		PhotoProduct pp = photoProductService.getPhotoProductById(productId);
		AlbumProducers alb = pp.getAlbumProducers();
		map.put("EQ_photoProduct.albumSize", pp.getAlbumSize());
		// 只看我启用的
		if (justme != null && "true".equalsIgnoreCase(justme)) {
			map.put("EQ_supply.id", getRootAlbumProducers(alb).getId());
		}
		List<ProductTemplateList> pt = productTemplateListService.getListByParams(map,
				new Sort(Direction.DESC, sortType));
		pt = init(pt, alb);

		List<ProductTemplateList> l = Lists.newArrayList();
		int i = 0;
		for (ProductTemplateList p : pt) {
			if (i >= (pageNumber - 1) * pageSize && i < pageNumber * pageSize) {
				l.add(p);
			}
			i = i + 1;
		}
		model.addAttribute("productId", productId);
		model.addAttribute("productTemplates", l);
		model.addAttribute("sortType", sortType);
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("total", pt.size());
		model.addAttribute("page", pt.size() % pageSize == 0 ? pt.size() / pageSize : pt.size() / pageSize + 1);
		return "template/templeteManage";
	}

	public List<ProductTemplateList> init(List<ProductTemplateList> pt, AlbumProducers alb) {
		Long userid = getRootAlbumProducers(alb).getId();
		List<ProductTemplateList> list = Lists.newArrayList();
		for (ProductTemplateList productTemplateList : pt) {
			AlbumProducers supply = productTemplateList.getSupply();
			if (supply != null) {
				if (supply.getId() == userid.intValue() || productTemplateList.isOpen()) {
					list.add(productTemplateList);
				}
			} else {
				if (productTemplateList.isOpen() == true) {
					list.add(productTemplateList);
				}
			}
		}
		return list;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize, Model model,
			ServletRequest request) {
		LOGGER.info(" 查找出当前系统中所有符合条件的模板");
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		searchParams = searchParams(searchParams, model);
		PageRequest page = buildPageRequest(initCommonModel(pageSize, pageNumber, "desc", "createtime"));
		try {
			Page<ProductTemplateList> productTemplates = productTemplateListService.getListPageByParams(page,
					searchParams);
			model.addAttribute("productTemplates", productTemplates);
			model.addAttribute("currentPage", pageNumber);
			model.addAttribute("total", productTemplates.getTotalElements());
			model.addAttribute("page", productTemplates.getTotalPages());
			// 将搜索条件编码成字符串，用于排序，分页的URL
			model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
			addSpecList(searchParams, model);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.info(e.getMessage());
		}

		return "template/list";
	}

	public Map<String, Object> searchParams(Map<String, Object> searchParams, Model model) {
		LOGGER.info("模板 搜索条件");
		// 开始时间
		String start = searchParams.get("EQ_startTime") == null ? "" : searchParams.get("EQ_startTime").toString();
		// 结束时间
		String end = searchParams.get("EQ_endTime") == null ? "" : searchParams.get("EQ_endTime").toString();
		// 台历挂历类型分类
		if (searchParams.get("EQ_ly") == null || "".equals(searchParams.get("EQ_ly"))) {
			searchParams.remove("EQ_ly");
		}
		// 台历挂历类型分类
		if (searchParams.get("EQ_lx") == null || "".equals(searchParams.get("EQ_lx"))) {
			searchParams.remove("EQ_lx");
		}
		// 产品分类
		if (searchParams.get("EQ_productMaterial.id") == null || "".equals(searchParams.get("EQ_productMaterial.id"))) {
			searchParams.remove("EQ_productMaterial.id");
		}
		// 尺寸分类
		if (searchParams.get("EQ_productSpec.id") == null || "".equals(searchParams.get("EQ_productSpec.id"))) {
			searchParams.remove("EQ_productSpec.id");
		}
		// 尺寸大小
		if (searchParams.get("EQ_productSize.id") == null || "".equals(searchParams.get("EQ_productSize.id"))) {
			searchParams.remove("EQ_productSize.id");
		}
		// 第三方产品选择
		if (searchParams.get("EQ_platformPhotoProduct.id") == null
				|| "".equals(searchParams.get("EQ_platformPhotoProduct.id"))) {
			searchParams.remove("EQ_platformPhotoProduct.id");
		} else {
			model.addAttribute("ppId", searchParams.get("EQ_platformPhotoProduct.id"));
		}
		// 厂商产品选择
		if (searchParams.get("EQ_photoProduct.id") == null || "".equals(searchParams.get("EQ_photoProduct.id"))) {
			searchParams.remove("EQ_photoProduct.id");
		} else {
			model.addAttribute("syspId", searchParams.get("EQ_photoProduct.id"));
		}

		// 审核状态
		if (searchParams.get("EQ_state") == null || "".equals(searchParams.get("EQ_state"))) {
			searchParams.remove("EQ_state");
		}
		// 标签名称
		if (searchParams.get("LIKE_tagsName") == null || "".equals(searchParams.get("LIKE_tagsName"))) {
			searchParams.remove("LIKE_tagsName");
		}
		// 模板名称
		if (searchParams.get("LIKE_templatename") == null || "".equals(searchParams.get("LIKE_templatename"))) {
			searchParams.remove("LIKE_templatename");
		}
		if (StringUtils.isNotBlank(start)) {
			Date startDate = DateUtils.parse(start, DateUtils.YMD_DASH);
			searchParams.put("GTE_createtime", startDate);
		}
		if (StringUtils.isNotBlank(end)) {
			Date endDate = DateUtils.parse(end, DateUtils.YMD_DASH);
			searchParams.put("LT_createtime", DateUtils.dayOffset(endDate, 1));
		}
		searchParams.remove("EQ_startTime");
		searchParams.remove("EQ_endTime");
		List<Integer> allState = Lists.newArrayList();
		allState.add(StateUtils.TEMPLATE_STATE_TO_CHEACK);
		allState.add(StateUtils.TEMPLATE_STATE_NOT_PASS);
		allState.add(StateUtils.TEMPLATE_STATE_PASS);
		allState.add(StateUtils.TEMPLATE_STATE_NOTUSE);
		searchParams.put("IN_state", allState);
		return searchParams;
	}

	public void addSpecList(Map<String, Object> searchParams, Model model) {
		LOGGER.info("添加产品搜索");
		String lx = searchParams.get("EQ_lx") == null ? "" : searchParams.get("EQ_lx").toString();
		String ly = searchParams.get("EQ_ly") == null ? "" : searchParams.get("EQ_ly").toString();
		if (StringUtils.isNotBlank(lx)) {
			Map<String, Object> map = Maps.newHashMap();
			if (ly.equals("0")) {
				map.put("EQ_state", StateUtils.PLATFORM_PRODUCT_STATE_UP);
				if (lx.equals("0")) {
					map.put("EQ_type.id", 0);
				} else {
					map.put("GT_type.id", 0);
				}
				List<PhotoProduct> sysProduct = photoProductService.getPhotoProductByParams(map);
				model.addAttribute("sysProduct", sysProduct);
			} else {
				map.put("EQ_state", StateUtils.PLATFORM_PRODUCT_STATE_UP);
				map.put("EQ_sysState", StateUtils.PLATFORM_PRODUCT_STATE_UP);
				if (lx.equals("0")) {
					map.put("EQ_photoProduct.type.id", 0);
				} else {
					map.put("GT_photoProduct.type.id", 0);
				}
				List<PlatformPhotoProduct> platformPhotoProduct = platformPhotoProductService
						.getPlatformPhotoProductByParams(map);
				model.addAttribute("platformPhotoProduct", platformPhotoProduct);
			}
		}
	}

	@RequestMapping(value = "pre-add/{productId}", method = RequestMethod.GET)
	public String preAdd(@PathVariable("productId") Long productId, Model model) {
		LOGGER.info("跳转~新增模板,产品ID-->", productId);
		PhotoProduct photoProduct = photoProductService.getPhotoProductById(productId);
		model.addAttribute("product", photoProduct);
		Long needphotoes = getNeedPhoto(photoProduct);
		model.addAttribute("needphotoes", needphotoes);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_state", StateUtils.TEMPLATE_TAG_USE);
		List<ProductTemplateTag> taglist = productTemplateTagService.getListByParams(map);
		model.addAttribute("taglist", taglist);
		Long type = photoProduct.getType().getId();
		model.addAttribute("type", type);
		return "template/addPhotoTemplate";
	}

	@RequestMapping(value = "pre-update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable("id") Long id, Model model) {
		LOGGER.info("跳转~编辑模板,模板ID-->", id);
		ProductTemplateList productTemplateList = productTemplateListService.getById(id);
		model.addAttribute("template", productTemplateList);

		PhotoProduct photoProduct = productTemplateList.getPhotoProduct();
		model.addAttribute("product", photoProduct);
		// 封页可编辑
		Long needphotoes = getNeedPhoto(photoProduct);
		model.addAttribute("needphotoes", needphotoes);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_state", StateUtils.TEMPLATE_TAG_USE);
		List<ProductTemplateTag> taglist = productTemplateTagService.getListByParams(map);
		model.addAttribute("taglist", taglist);
		map.clear();
		Long type = photoProduct.getType().getId();
		model.addAttribute("type", type);
		return "template/updatePhotoTemplate";

	}

	/**
	 * 获取所需上传的图片
	 * @param photoProduct
	 * @return
	 */
	public Long getNeedPhoto(PhotoProduct photoProduct) {
		long needphotoes = photoProduct.getProductTemplate().getNeedphotoes();
		//封面可编辑
		if (photoProduct.getCoverEdit()) {
			needphotoes++;
		}
		//封底可编辑
		if (photoProduct.getBackcoverEdit()) {
			needphotoes++;
		}
		//扉页扉底可编辑
		if (photoProduct.isFirstpageEdit()) {
			needphotoes += 2;
		}
		return needphotoes;
	}

	@RequestMapping(value = "getLayouSelected", method = { RequestMethod.GET })
	@ResponseBody
	public RestResponse getLayouSelected(@RequestParam Long layoutid, @RequestParam Long templateid) {
		List<ProductTemplateLayoutinfo> infolist = productTemplateLayoutinfoService.findByInfoIdAndTempId(layoutid,
				templateid);
		LayoutInfo layoutInfo = layoutInfoService.findById(layoutid);
		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("infolist", infolist);
		map.put("layoutInfo", layoutInfo);
		return RestResponse.success().setData(infolist).setData(map);
	}

	@RequestMapping(value = "deleteTemplateLayout", method = { RequestMethod.POST })
	@ResponseBody
	public RestResponse deleteTemplateLayout(@RequestParam Long templateid, @RequestParam Long layoutid) {
		if (templateid == null || layoutid == null) {
			return RestResponse.failure("参数有误");
		}
		List<ProductTemplateLayoutinfo> selectedLayout = productTemplateLayoutinfoService
				.findByTemplateidGroupByMain(templateid);
		if (selectedLayout.size() > 1) {
			productTemplateLayoutinfoService.deleteByInfoIdAndTempId(layoutid, templateid);
			return RestResponse.success();
		} else {
			return RestResponse.failure("删除失败:至少保留一个内容");
		}
	}

	@RequestMapping(value = "addTemplate", method = RequestMethod.POST)
	@ResponseBody
	public RestResponse addTemplate(@Valid ProductTemplateList productTemplateList, Model model) {
		LOGGER.info("保存新增模板");
		if (productTemplateList.getTemplatename() == null || "".equals(productTemplateList.getTemplatename())) {
			return RestResponse.failure("模板名称不能为空");
		}
		ProductTemplateList newTemplate = saveProductTemplateList(productTemplateList);
		List<ProductTemplateLayoutinfo> templateLayoutinfos = Lists.newArrayList();
		String tempImgList = productTemplateList.getTempImgList();

		// 去除空字符串
		if (tempImgList.startsWith(",")) {
			tempImgList = tempImgList.substring(tempImgList.indexOf(",") + 1, tempImgList.length());
		}
		String[] imgs = tempImgList.split(",");

		List<String> paramTempList = Lists.newArrayList();
		for (int i = 0; i < imgs.length; i++) {
			if (StringUtils.isNotBlank(imgs[i])) {
				paramTempList.add(imgs[i]);
			}
		}
		PhotoProduct photoProduct = productTemplateList.getPhotoProduct();
		if (photoProduct == null || photoProduct.getProductTemplate() == null) {
			return RestResponse.failure("没有对应产品，无法创建修改模板");
		}
		Long needphotoes = getNeedPhoto(photoProduct);
		if (paramTempList.size() > needphotoes) {
			return RestResponse.failure("上传模板数量大于" + needphotoes + "张");
		} else if (paramTempList.size() < needphotoes) {
			return RestResponse.failure("上传模板数量小于" + needphotoes + "张");
		}
		// 保存标签名
		List<String> tags = Lists.newArrayList();
		String tagIds = newTemplate.getTagIds();
		String[] ids = tagIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			ProductTemplateTag tag = productTemplateTagService.getTagById(Long.parseLong(ids[i]));
			tags.add(tag.getTagName());
		}
		newTemplate.setTagsName(tags.toString());
		// 保存模板图片
		for (int i = 0; i < imgs.length; i++) {
			if (StringUtils.isNoneBlank(imgs[i])) {
				ProductTemplateLayoutinfo layoutinfo = new ProductTemplateLayoutinfo();
				layoutinfo.setPreviewImg(imgs[i]);
				layoutinfo.setTemplateJpgimageurl(imgs[i]);
				layoutinfo.setTemplatePngimageurl(imgs[i]);
				layoutinfo.setProductTemplateList(productTemplateList);
				layoutinfo.setParentId(0l);
				layoutinfo.setState(StateUtils.COM_STATE_NORMAL);
				templateLayoutinfos.add(layoutinfo);
			}
		}
		newTemplate.setTemplateLayoutinfos(templateLayoutinfos);
		newTemplate.setState(StateUtils.COM_STATE_TO_CHEACK);
		productTemplateListService.saveOrUpdate(newTemplate);

		return RestResponse.success().setData(newTemplate);
	}

	@RequestMapping(value = "checkTemplate", method = RequestMethod.POST)
	@ResponseBody
	public RestResponse checkTemplate(@Valid ProductTemplateList productTemplateList) {
		LOGGER.info("审核模板");
		if (productTemplateList.getTemplatename() == null || "".equals(productTemplateList.getTemplatename())) {
			return RestResponse.failure("模板名称不能为空");
		}
		ProductTemplateList newTemplate = saveProductTemplateList(productTemplateList);
		if (productTemplateList.getLx() == 1) {
			if (productTemplateList.getTemplateLayoutinfos() == null
					|| productTemplateList.getTemplateLayoutinfos().size() <= 0) {
				return RestResponse.failure("模板内容不能为空");
			}
		}

		// 图片字符串
		String tempImgList = productTemplateList.getTempImgList();
		// 查询tempId对应模板图片信息
		List<ProductTemplateLayoutinfo> templateLayoutinfos = productTemplateLayoutinfoService
				.findByTemplateid(productTemplateList.getId());
		List<ProductTemplateIndex> indexs = productTemplateIndexService.getListBytempId(productTemplateList.getId());
		// 去除空字符串
		if (tempImgList.startsWith(",")) {
			tempImgList = tempImgList.substring(tempImgList.indexOf(",") + 1, tempImgList.length());
		}
		String[] imgs = tempImgList.split(",");

		List<String> paramTempList = Lists.newArrayList();
		for (int i = 0; i < imgs.length; i++) {
			if (StringUtils.isNotBlank(imgs[i])) {
				paramTempList.add(imgs[i]);
			}
		}
		ProductTemplateList templateList = productTemplateListService.getById(productTemplateList.getId());
		if (templateList.getPhotoProduct() == null || templateList.getPhotoProduct().getProductTemplate() == null) {
			return RestResponse.failure("没有对应产品，无法创建修改模板");
		}
		PhotoProduct photoProduct = templateList.getPhotoProduct();
		Long needphotoes = getNeedPhoto(photoProduct);
		if (paramTempList.size() > needphotoes) {
			return RestResponse.failure("上传模板数量大于" + needphotoes + "张");
		} else if (paramTempList.size() < needphotoes) {
			return RestResponse.failure("上传模板数量小于" + needphotoes + "张");
		}

		// 保存标签名
		List<String> tags = Lists.newArrayList();
		String tagIds = newTemplate.getTagIds();
		String[] ids = tagIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			ProductTemplateTag tag = productTemplateTagService.getTagById(Long.parseLong(ids[i]));
			tags.add(tag.getTagName());
		}
		newTemplate.setTagsName(tags.toString());
		// 查询所有未修改的模板
		List<String> exitLayoutInfoList = dealTempLayoutinfo(templateLayoutinfos, paramTempList);
		List<String> l = Lists.newArrayList();
		
		for (String tempLayout : paramTempList) {
			// 判断当前templayout是否新建/修改
			if (!exitLayoutInfoList.contains(tempLayout)) {
				l.add(tempLayout);
			}
		}
		int i = 0;
		for (String string : exitLayoutInfoList) {
			// 新增TempLayoutInfo templayoutIndex layoutInfo
			ProductTemplateLayoutinfo info = productTemplateLayoutinfoService.findByTemplateJpgimageurl(string);
			if (info != null) {
				info.setProductTemplateList(newTemplate);
				info.setPreviewImg(l.get(i));
				info.setTemplateJpgimageurl(l.get(i));
				info.setTemplatePngimageurl(l.get(i));
				productTemplateLayoutinfoService.save(info);
				if (!indexs.isEmpty()) {
					ProductTemplateIndex index = productTemplateIndexService.findByTempLayoutId(info.getId());
					if (index != null) {
						index.setProductTemplateLayoutinfo(info);
						index.setTempId(productTemplateList.getId());
						index.setOrderIndex(i + 1);
						index.setPreviewImg(info.getPreviewImg());
						index.setCreatetime(new Date());
						productTemplateIndexService.save(index);
					}
				}
			}
			i++;
		}
		
		if (productTemplateList.getState() == StateUtils.TEMPLATE_STATE_PASS) {
			if (!productTemplateList.getIsPassedUsable()) {
				newTemplate.setState(StateUtils.TEMPLATE_STATE_NOTUSE);
			}
		}
		productTemplateListService.saveOrUpdate(newTemplate);

		return RestResponse.success();
	}

	/**
	 * 提交审核 已删除模板容器己遍历出 未修改的模板容器
	 * 
	 * @param templateLayoutinfos
	 * @param paramTempList
	 * @return
	 */
	public List<String> dealTempLayoutinfo(List<ProductTemplateLayoutinfo> templateLayoutinfos,
			List<String> paramTempList) {
		List<String> existsTempLayoutInfoList = Lists.newArrayList();
		for (ProductTemplateLayoutinfo infos : templateLayoutinfos) {
			if (!paramTempList.contains(infos.getTemplatePngimageurl())) {
				LOGGER.info("存在");
				existsTempLayoutInfoList.add(infos.getTemplatePngimageurl());
		}
			}
		return existsTempLayoutInfoList;
	}

	public ProductTemplateList saveProductTemplateList(ProductTemplateList productTemplateList) {
		if (productTemplateList.getId() != null) {
			ProductTemplateList oldTemplate = productTemplateListService.getById(productTemplateList.getId());
			oldTemplate.setTemplatename(productTemplateList.getTemplatename());
			oldTemplate.setState(productTemplateList.getState());
			oldTemplate.setTotalpages(productTemplateList.getTotalpages());
			oldTemplate.setTagIds(productTemplateList.getTagIds());
			oldTemplate.setTagsName(productTemplateList.getTagsName());
			oldTemplate.setSourcePsdFile(productTemplateList.getSourcePsdFile());
			oldTemplate.setIsPassedUsable(productTemplateList.getIsPassedUsable());
			oldTemplate.setOpen(productTemplateList.isOpen());
			oldTemplate.setCoverimg(productTemplateList.getCoverimg());
			oldTemplate.setBackcoverimg(productTemplateList.getBackcoverimg());
			oldTemplate.setFirstPage(productTemplateList.getFirstPage());
			oldTemplate.setLastPage(productTemplateList.getLastPage());
			oldTemplate.setUpdatetime(new Date());
			productTemplateList = oldTemplate;
		} else {
			PhotoProduct photoProduct = photoProductService
					.getPhotoProductById(productTemplateList.getPhotoProduct().getId());
			productTemplateList.setPhotoProduct(photoProduct);
			productTemplateList.setLy(StateUtils.TEMPLATE_LY_SUPPLY);
			productTemplateList.setWorktype(photoProduct.getType().getId().intValue());
			productTemplateList.setType(photoProduct.getType());
			productTemplateList.setChuxue("0");
			productTemplateList.setOpen(productTemplateList.isOpen());
			productTemplateList.setTagIds(productTemplateList.getTagIds());
			productTemplateList.setTagsName(productTemplateList.getTagsName());
			productTemplateList.setTemplatelogo(productTemplateList.getCoverimg());
			AlbumProducers supply = albumProducersService.getAlbumProducersById(
					getRootAlbumProducers(productTemplateList.getPhotoProduct().getAlbumProducers()).getId());
			productTemplateList.setSupply(supply);
			productTemplateList.setAlbumSize(photoProduct.getAlbumSize());
			productTemplateList.setCreatetime(new Date());
			// 1是台历,0是相册
			if (photoProduct.getType().getId() > 0) {
				productTemplateList.setLx(1);
			} else {
				productTemplateList.setLx(0);
			}
		}
		return productTemplateList;
	}

	@RequestMapping(value = "addTag", method = RequestMethod.POST)
	@ResponseBody
	public RestResponse addTag(@Valid ProductTemplateTag tag) {
		LOGGER.info("新增标签~");
		if (tag.getTagName() == null || "".equals(tag.getTagName())) {
			return RestResponse.failure("标签名称不能为空");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_state", StateUtils.TEMPLATE_TAG_USE);
		map.put("EQ_tagName", tag.getTagName());
		List<ProductTemplateTag> taglist = productTemplateTagService.getListByParams(map);
		if (taglist.size() > 0) {
			return RestResponse.failure("标签已存在");
		} else {
			tag.setCreatetime(new Date());
			tag.setState(StateUtils.TEMPLATE_TAG_USE);
			productTemplateTagService.saveOrUpdate(tag);
		}
		return RestResponse.success().setData(tag.getId());
	}

	@RequestMapping(value = "removeTag", method = RequestMethod.GET)
	@ResponseBody
	public RestResponse removeTag(@RequestParam Long id) {
		LOGGER.info("移除标签~");
		ProductTemplateTag tag = productTemplateTagService.getTagById(id);
		if (tag == null) {
			return RestResponse.failure("标签不存在");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("LIKE_tagIds", id);
		List<ProductTemplateList> productTemplateList = productTemplateListService.getListByParams(map);
		if (productTemplateList.size() > 0) {
			return RestResponse.failure("标签已被使用,不能删除");
		}
		tag.setState(StateUtils.TEMPLATE_TAG_DELETE);
		tag.setUpdatetime(new Date());
		productTemplateTagService.saveOrUpdate(tag);
		return RestResponse.success();
	}

	@RequestMapping("download")
	public void download(@RequestParam Long id, HttpServletResponse response) {
		LOGGER.info("文件下载--~", id);
		ProductTemplateList list = productTemplateListService.getById(id);
		String urlStr = list.getSourcePsdFile();
		String fileName = list.getTemplatename() + ".pdf";
		try {

			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			BufferedInputStream br = new BufferedInputStream(conn.getInputStream());
			byte[] buf = new byte[1024];
			int len = 0;
			response.reset();
			response.setHeader("Content-type", "application/pdf");
			response.setHeader("Content-Disposition",
					"attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			ServletOutputStream out = response.getOutputStream();
			while ((len = br.read(buf)) > 0)
				out.write(buf, 0, len);
			br.close();
			out.flush();
			out.close();
			conn.disconnect();
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.info(e.getMessage());
		}

	}

	@RequestMapping(value = "taglist", method = RequestMethod.GET)
	@ResponseBody
	public RestResponse taglist() {
		LOGGER.info("标签列表~");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_state", StateUtils.TEMPLATE_TAG_USE);
		List<ProductTemplateTag> taglist = productTemplateTagService.getListByParams(map);
		return RestResponse.success().setData(taglist);
	}

	public String doPreviewImg(String url) {
		LOGGER.info("处理七牛图片缩略图");
		String d = "?imageView2/0/w/400";
		if (url.contains(d))
			return url;
		url += d;
		return url;
	}

	@RequestMapping(value = "temp/area", method = RequestMethod.GET)
	public String sortTemp(@RequestParam("tempId") long tempId, Model model) {
		LOGGER.info("模板~排序! tempId:{}", tempId);
		List<ProductTemplateLayoutinfo> templateLayoutinfos = Lists.newArrayList();
		List<ProductTemplateIndex> PTIndex = productTemplateIndexService.getListBytempIdOrder(tempId);

		if (PTIndex != null && PTIndex.size() > 0) {
			for (ProductTemplateIndex productTemplateIndex : PTIndex) {
				ProductTemplateLayoutinfo layoutInfo = productTemplateIndex.getProductTemplateLayoutinfo();
				layoutInfo.setPreviewImg(productTemplateIndex.getPreviewImg());
				templateLayoutinfos.add(layoutInfo);
			}
		} else {
			ProductTemplateList template = productTemplateListService.getById(tempId);
			templateLayoutinfos = template.getTemplateLayoutinfos();
			for (ProductTemplateLayoutinfo pti : templateLayoutinfos) {
				pti.setPreviewImg(pti.getTemplatePngimageurl());
			}
		}
		model.addAttribute("PTIndex", PTIndex);
		model.addAttribute("tempLayoutList", templateLayoutinfos);
		List<Integer> orderIndex = Lists.newArrayList();
		orderIndex.add(-1);
		orderIndex.add(-2);
		List<ProductTemplateIndex> FIndex = productTemplateIndexService.findByOrderIndexNotInAndTempId(orderIndex,
				tempId);
		model.addAttribute("FIndex", FIndex);
		return "template/templateArea";
	}

	@RequestMapping(value = "temp/saveLayout", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public RestResponse saveLayout(@RequestParam("jsonData") String jsonData) {
		LOGGER.info("模板~保存! jsonData：" + jsonData);
		Gson g = new Gson();
		LayoutInfosTemplateModel model = g.fromJson(jsonData, LayoutInfosTemplateModel.class);
		if (model.getParent() == null || "".equals(model.getParent())) {
			return RestResponse.failure("图片尺寸不能为空");
		}
		Long tempId = model.getTempId();
		// 查询tempId对应模板信息
		ProductTemplateList ptl = productTemplateListService.getById(tempId);
		// 查询tempId对应模板图片信息
		// 查询tempId对应的子容器productTemplateIndexs信息
		List<ProductTemplateIndex> productTemplateIndexs = productTemplateIndexService.getListBytempId(tempId);
		int main = 0;
		long sub = 0;
		int count = 1;
		List<LayoutInfosSonModel> layoutInfosSonLists = model.getSon();
		for (int i = 0; i < layoutInfosSonLists.size(); i++) {
			// 子容器,x,y,w,h集合
			LayoutInfosteListModel child = layoutInfosSonLists.get(i).getChild();
			List<LayoutInfosSonList> layoutList = child.getLayout();
			LayoutInfosSonList text = child.getText();
			ProductTemplateLayoutinfo pimage = productTemplateLayoutinfoService.findById(new Long(child.getTempId()));
			LayoutInfo layoutInfo = pimage.getLayoutInfo();
			if (layoutInfo == null) {
				layoutInfo = new LayoutInfo();
				layoutInfo.setCreatetime(new Date());
			} else {
				layoutInfo.setUpdatetime(new Date());
			}
			// 求main的值
			main = layoutList.size();
			// 如果main未改变，sub不改变
			if (pimage != null && pimage.getLayoutInfo() != null && main == pimage.getMain()) {
				sub = pimage.getLayoutInfo().getSub();
				layoutInfo.setSub(sub);
				layoutInfo.setLayoutName(main + "-" + sub);
			} else {// 如果main发生改变，查找main对应的sub最大值+1
				List<?> subList = layoutInfosService.selectMaxSubByMain(main);
				// /如果查询不到sub，则为1
				if (subList != null && subList.size() > 0) {
					String subString = subList.get(0) + "";
					if (StringUtils.isBlank(subString)) {
						sub = 1;
					} else {
						sub = Integer.valueOf(subString);
					}

					long newSub = sub + 1;// sub值
					layoutInfo.setSub(newSub);
					layoutInfo.setLayoutName(main + "-" + newSub);
				}
			}
			layoutInfo.setMain(main);
			layoutInfo.setState(StateUtils.LAYOUT_INFO_STATE_USE);
			layoutInfo.setPicCount(main);
			layoutInfo.setScale(model.getParent().getScale());
			if (model.getParent().getWidth() == 0 && model.getParent().getHeight() == 0) {
				layoutInfo.setWidth(0);
				layoutInfo.setHeight(0);
			} else {
				// 如果父容器的高修改使用修改后的值,第一次编辑父容器框中值为0
				if (child.getParent() != null && child.getParent().getHeight() != 0
						&& model.getParent().getHeight() != child.getParent().getHeight()) {
					layoutInfo.setHeight(child.getParent().getHeight());
				} else {
					layoutInfo.setHeight(model.getParent().getHeight());
				}
				// 如果父容器的宽修改使用修改后的值
				if (child.getParent() != null && child.getParent().getWidth() != 0
						&& model.getParent().getWidth() != child.getParent().getWidth()) {
					layoutInfo.setWidth(child.getParent().getWidth());
				} else {
					layoutInfo.setWidth(model.getParent().getWidth());
				}
			}
			layoutInfo.setTx(text.getX());
			layoutInfo.setTy(text.getY());
			layoutInfo.setTheight(text.getHeight());
			layoutInfo.setTwidth(text.getWidth());
			List<LayoutInfoDetails> ld = Lists.newArrayList();
			// 查询LayoutId对应的子容器LayoutInfoDetails信息

			List<LayoutInfoDetails> layoutSelece = pimage.getLayoutInfo() == null ? (new ArrayList<LayoutInfoDetails>())
					: pimage.getLayoutInfo().getListdetails();
			if (layoutList.size() < layoutSelece.size()) {
				for (int m = layoutList.size(); m < layoutSelece.size(); m++) {
					layoutInfoDetailsService.deleteById(layoutSelece.get(m).getId());
				}
			}
			for (int j = 0; j < layoutList.size(); j++) {
				LayoutInfoDetails layoutInfoDetails = new LayoutInfoDetails();
				if (j < layoutSelece.size()) {
					layoutInfoDetails = layoutSelece.get(j);
				}
				// 新增子容器时不设置id
				layoutInfoDetails.setHeight(layoutList.get(j).getHeight());
				layoutInfoDetails.setWidth(layoutList.get(j).getWidth());
				layoutInfoDetails.setX(layoutList.get(j).getX());
				layoutInfoDetails.setY(layoutList.get(j).getY());
				layoutInfoDetails.setLayoutInfo(layoutInfo);
				layoutInfoDetails.setLayoutIndex(j + 1);
				ld.add(layoutInfoDetails);
			}
			layoutInfo.setListdetails(ld);
			layoutInfoService.saveLayoutInfo(layoutInfo);// 保存容器
			// 设置ProductTemplateLayoutinfo
			pimage.setProductTemplateList(ptl);
			pimage.setLayoutInfo(layoutInfo);
			pimage.setMain(layoutInfo.getMain());
			pimage.setSub(layoutInfo.getSub());
			// pimage.setTemplatePngimageurl(child.getImgUrl().substring(0,child.getImgUrl().indexOf("?")));
			productTemplateLayoutinfoService.save(pimage);
			// 设置ProductTemplateIndex
			ProductTemplateIndex index = new ProductTemplateIndex();

			if (!productTemplateIndexs.isEmpty()) {
				index.setId(productTemplateIndexs.get(i).getId());
			}
			index.setProductTemplateLayoutinfo(pimage);
			index.setTempId(tempId);
			// 设置封面、封底、扉页、扉底（-1，-2，-3，-4）
			if (StringUtils.isNotBlank(child.getType())) {
				index.setOrderIndex(Integer.parseInt(child.getType()));
			} else {
				index.setOrderIndex(count++);
			}
			// index.setPreviewImg(pimage.getPreviewImg());
			index.setPreviewImg(child.getImgUrl().substring(0, child.getImgUrl().indexOf("?")));
			index.setCreatetime(new Date());
			productTemplateIndexService.save(index);
		}
		return RestResponse.success();
	}

}
