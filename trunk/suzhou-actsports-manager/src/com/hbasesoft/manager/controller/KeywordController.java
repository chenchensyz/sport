package com.hbasesoft.manager.controller;

import com.google.common.collect.Maps;
import com.hbasesoft.framework.manager.core.common.hibernate.qbc.CriteriaQuery;
import com.hbasesoft.framework.manager.core.common.model.json.AjaxJson;
import com.hbasesoft.framework.manager.core.common.model.json.DataGrid;
import com.hbasesoft.framework.manager.core.constant.Globals;
import com.hbasesoft.framework.manager.core.extend.hqlsearch.HqlGenerateUtil;
import com.hbasesoft.framework.manager.core.util.CommonUtil;
import com.hbasesoft.framework.manager.core.util.MyBeanUtils;
import com.hbasesoft.framework.manager.core.util.ResourceUtil;
import com.hbasesoft.framework.manager.core.util.StringUtil;
import com.hbasesoft.framework.manager.tag.core.easyui.TagUtil;
import com.hbasesoft.framework.manager.web.system.service.SystemService;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.AutoResponse;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.NewsTemplate;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.TextTemplate;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.WeixinAccountEntity;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.WeixinFiletemplatePojo;
import com.hbasesoft.framework.manager.wechat.guangjia.service.AutoResponseServiceI;
import com.hbasesoft.framework.manager.wechat.guangjia.service.SendAllServiceI;
import com.hbasesoft.manager.service.MaterialService;
import com.hbasesoft.manager.service.WeixinNewResponseService;
import com.hbasesoft.manager.vo.WeixinNewResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/keywordController")
public class KeywordController extends AbstractWechatController {
	private static final Logger LOGGER = LoggerFactory.getLogger(KeywordController.class);
	@Autowired
	private SystemService systemService;

	@Resource(name = "materialService")
	private MaterialService materialService;

	@Autowired
	private SendAllServiceI sendAllService;

	@Autowired
	private AutoResponseServiceI autoResponseService;
	
	@Autowired
	private WeixinNewResponseService weixinNewResponseService;
	private String message;

	@RequestMapping(params = { "list" })
	public ModelAndView list(HttpServletRequest request) {
		LOGGER.info("关键字列表");
		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		List accountList = this.autoResponseService
				.findByQueryString("from WeixinAccountEntity where state = 'A' and orgCode = '" + orgCode + "'");
		request.setAttribute("accountList", accountList);
		return new ModelAndView("weixin/guanjia/autoresponse/autoresponselist");
	}

	@RequestMapping(params = { "datagrid" })
	@ResponseBody
	public void datagrid(AutoResponse autoResponse, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		String aId = request.getParameter("aId");

		String keywords = request.getParameter("keywords");

		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();

		CriteriaQuery cq = new CriteriaQuery(AutoResponse.class, dataGrid);

		List weixinAccountList = this.autoResponseService.findByQueryString(
				"from WeixinAccountEntity where  id = '" + aId + "' and orgCode = '" + orgCode + "'");

		if (weixinAccountList.size() == 0) {
			cq.isNull("accountId");
		} else {
			String[] wechatArray = new String[weixinAccountList.size()];
			for (int i = 0; i < weixinAccountList.size(); i++) {
				wechatArray[i] = ((WeixinAccountEntity) weixinAccountList.get(i)).getId();
			}
			cq.in("accountId", wechatArray);
		}
		cq.like("keyWord", "%" + keywords + "%");

		HqlGenerateUtil.installHql(cq, autoResponse);
		this.autoResponseService.getDataGridReturn(cq, true);

		TagUtil.datagrid(response, dataGrid);
	}

	@RequestMapping(params = { "del" })
	@ResponseBody
	public AjaxJson del(AutoResponse autoResponse, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		autoResponse = (AutoResponse) this.autoResponseService.getEntity(AutoResponse.class, autoResponse.getId());
		this.autoResponseService.delete(autoResponse);
		this.message = "删除信息数据成功！";
		this.systemService.addLog(this.message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(this.message);
		return j;
	}

	@RequestMapping(params = { "addOrUpdate" })
	public ModelAndView addOrUpdate(HttpServletRequest req) {
		LOGGER.info("新增/编辑关键字...");
		String aId = req.getParameter("aId");
		String id = req.getParameter("id");
		String groupId = req.getParameter("msgGroup");
		req.setAttribute("id", id);

		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();

		List textList = this.weixinNewResponseService
				.findByQueryString("from TextTemplate t where t.orgCode = '" + orgCode + "'");

		List newsList = this.weixinNewResponseService
				.findByQueryString("from NewsTemplate n where n.orgCode = '" + orgCode + "'");

		List imgList = this.weixinNewResponseService.findByQueryString(
				"from WeixinFiletemplatePojo w where w.type = 'image' and w.orgCode = '" + orgCode + "'");

		req.setAttribute("textList", textList);
		req.setAttribute("newsList", newsList);
		req.setAttribute("imgList", imgList);

		List weixinAccountList = this.weixinNewResponseService
				.findByQueryString("from WeixinAccountEntity w where w.state='A' and orgCode ='" + orgCode + "'");

		req.setAttribute("weixinAccountList", weixinAccountList);
		// 获取素材分组
		List<Map<String, Object>> materials = materialService.findMaterialGroup();
		req.setAttribute("materials", materials);
		req.setAttribute("selectaid", aId);

		if (StringUtil.isNotEmpty(id)) {
			WeixinNewResponse autoResponse = (WeixinNewResponse) this.weixinNewResponseService.getEntity(WeixinNewResponse.class, id);
			String msgType = autoResponse.getMsgType();
			String resContent = autoResponse.getResContent();
			String keyWord = autoResponse.getKeyWord();
			String templateName = autoResponse.getTemplateName();
			String accountId = autoResponse.getAccountId();
			//获取分组
			if (StringUtils.isNotBlank(autoResponse.getGroupId())) {
				Map<String, Object> group=materialService.findMaterialByGroupId(autoResponse.getGroupId());
				String groupName=group.get("name").toString();
				req.setAttribute("groupName", groupName);
			}
			req.setAttribute("selectedWechatId", accountId);
			req.setAttribute("msgType", msgType);
			req.setAttribute("resContent", resContent);
			req.setAttribute("keyWord", keyWord);
			req.setAttribute("templateName", templateName);
			req.setAttribute("groupId", autoResponse.getGroupId());
			

		}
		return new ModelAndView("weixin/guanjia/autoresponse/autoresponseinfo");
	}

	/**
	 * 根据分组获取模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("gettemplate")
	@ResponseBody
	public Map<String,Object> gettemplate(HttpServletRequest request, HttpServletResponse response) {
		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		String msgType = request.getParameter("msgType");
		String msgGroup = request.getParameter("msgGroup");
		String resMsg = "";
		JSONArray json=null;
		if (CommonUtil.isNotEmpty(msgType)) {
			if ("text".equals(msgType)) {
				String hql = new StringBuilder().append("from TextTemplate t where t.orgCode = '").append(orgCode)
						.append("' ").toString();
				if (CommonUtil.isNotEmpty(msgGroup)) {
					hql = new StringBuilder().append(hql).append("and groupId = '").append(msgGroup).append("'")
							.toString();
				}

				List textList = this.sendAllService.findByQueryString(hql);

				json = JSONArray.fromObject(textList);
				resMsg = json.toString();
			} else if ("news".equals(msgType)) {
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.setExcludes(new String[] { "newsItemList" });

				String hql = new StringBuilder().append("from NewsTemplate t where t.orgCode = '").append(orgCode)
						.append("' ").toString();
				if (CommonUtil.isNotEmpty(msgGroup)) {
					hql = new StringBuilder().append(hql).append("and groupId = '").append(msgGroup).append("'")
							.toString();
				}

				List newsList = this.sendAllService.findByQueryString(hql);

				 json = JSONArray.fromObject(newsList, jsonConfig);
				resMsg = json.toString();
			} else if ("image".equals(msgType)) {
				String hql = "from WeixinFiletemplatePojo t where t.type = 'image' ";
				if (CommonUtil.isNotEmpty(msgGroup)) {
					hql = new StringBuilder().append(hql).append("and groupId = '").append(msgGroup).append("'")
							.toString();
				}

				List imageList = this.sendAllService.findByQueryString(hql);

				 json = JSONArray.fromObject(imageList);
				resMsg = json.toString();
			} else if ("video".equals(msgType)) {
				String hql = "from WeixinFiletemplatePojo t where t.type = 'video' ";
				if (CommonUtil.isNotEmpty(msgGroup)) {
					hql = new StringBuilder().append(hql).append("and groupId = '").append(msgGroup).append("'")
							.toString();
				}

				List videoList = this.sendAllService.findByQueryString(hql);

				 json = JSONArray.fromObject(videoList);
				resMsg = json.toString();
			} else if ("voice".equals(msgType)) {
				String hql = "from WeixinFiletemplatePojo t where t.type = 'voice' ";
				if (CommonUtil.isNotEmpty(msgGroup)) {
					hql = new StringBuilder().append(hql).append("and groupId = '").append(msgGroup).append("'")
							.toString();
				}

				List voiceList = this.sendAllService.findByQueryString(hql);

				 json = JSONArray.fromObject(voiceList);
				resMsg = json.toString();
			} else if ("expand".equals(msgType)) {
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.setExcludes(new String[] { "newsItemList" });

				List newsList = this.sendAllService.findByQueryString(
						new StringBuilder().append("from WeixinExpandconfigEntity t where t.orgCode = '")
								.append(orgCode).append("'").toString());

				json = JSONArray.fromObject(newsList, jsonConfig);
			}
		}
		Map<String,Object> map=Maps.newLinkedHashMap();
		map.put("data", json);
		return map;
	}

	@RequestMapping(params = { "doSave" })
	@ResponseBody
	public AjaxJson doSave(WeixinNewResponse autoResponse, HttpServletRequest req,String msgGroup) {
		LOGGER.info("保存关键字   doSave ...",msgGroup);
		String templateName = "";
		AjaxJson j = new AjaxJson();
		String id = autoResponse.getId();

		String accountId = req.getParameter("wechatId");
		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		if (StringUtil.isNotEmpty(id)) {
			WeixinNewResponse tempAutoResponse = (WeixinNewResponse) this.weixinNewResponseService.getEntity(WeixinNewResponse.class,
					autoResponse.getId());

			templateName = getTempName(autoResponse.getMsgType(), autoResponse.getResContent());
			autoResponse.setTemplateName(templateName);
			autoResponse.setGroupId(msgGroup);
			this.message = "修改关键字回复成功！";
			try {
				MyBeanUtils.copyBeanNotNull2Bean(autoResponse, tempAutoResponse);
				this.weixinNewResponseService.saveOrUpdate(tempAutoResponse);
				this.systemService.addLog(this.message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			autoResponse.setAddTime(sdf.format(new Date()));
			String templateId = autoResponse.getResContent();
			String msgType = autoResponse.getMsgType();
			templateName = getTempName(msgType, templateId);
			autoResponse.setGroupId(msgGroup);
			autoResponse.setTemplateName(templateName);

			WeixinAccountEntity weixinAccountList = (WeixinAccountEntity) this.weixinNewResponseService
					.getEntity(WeixinAccountEntity.class, accountId);

			if (weixinAccountList != null) {
				List autoList = this.weixinNewResponseService.findByQueryString("from AutoResponse where accountId = '"
						+ accountId + "' and keyWord = '" + autoResponse.getKeyWord() + "'");
				if (autoList.size() >= 1) {
					j.setSuccess(false);
					j.setMsg("关键字重复");
				} else {
					autoResponse.setAccountId(accountId);

					this.weixinNewResponseService.save(autoResponse);
				}
			} else {
				j.setSuccess(false);
				j.setMsg("请选择一个公众帐号。");
			}

		}

		return j;
	}

	private String getTempName(String msgType, String templateId) {
		String templateName = "";
		if ("text".equals(msgType)) {
			TextTemplate textTemplate = (TextTemplate) this.autoResponseService.getEntity(TextTemplate.class,
					templateId);
			if (textTemplate != null) {
				templateName = textTemplate.getTemplateName();
			}
		} else if ("news".equals(msgType)) {
			NewsTemplate newsTemplate = (NewsTemplate) this.autoResponseService.getEntity(NewsTemplate.class,
					templateId);
			if (newsTemplate != null)
				templateName = newsTemplate.getTemplateName();
		} else if ("image".equals(msgType)) {
			WeixinFiletemplatePojo weixinFiletemplate = (WeixinFiletemplatePojo) this.autoResponseService
					.getEntity(WeixinFiletemplatePojo.class, templateId);
			if (weixinFiletemplate != null) {
				templateName = weixinFiletemplate.getTemplateName();
			}
		}
		return templateName;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}