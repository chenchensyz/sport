package com.hbasesoft.manager.controller;

import com.hbasesoft.framework.manager.core.common.hibernate.qbc.CriteriaQuery;
import com.hbasesoft.framework.manager.core.common.model.json.AjaxJson;
import com.hbasesoft.framework.manager.core.common.model.json.DataGrid;
import com.hbasesoft.framework.manager.core.extend.hqlsearch.HqlGenerateUtil;
import com.hbasesoft.framework.manager.core.util.CommonUtil;
import com.hbasesoft.framework.manager.core.util.JSONHelper;
import com.hbasesoft.framework.manager.core.util.PropertiesUtil;
import com.hbasesoft.framework.manager.core.util.ResourceUtil;
import com.hbasesoft.framework.manager.tag.core.easyui.TagUtil;
import com.hbasesoft.framework.manager.web.system.pojo.base.TSDepart;
import com.hbasesoft.framework.manager.web.system.pojo.base.TSUser;
import com.hbasesoft.framework.manager.web.system.service.SystemService;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.NewsItem;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.NewsTemplate;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.TextTemplate;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.WeixinAccountEntity;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.WeixinAllsend;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.WeixinFiletemplatePojo;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.WeixinMediaUpload;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.mass.Articles;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.mass.ArticlesMass;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.mass.UploadVideo;
import com.hbasesoft.framework.manager.wechat.guangjia.service.SendAllServiceI;
import com.hbasesoft.framework.manager.wechat.guangjia.service.WeixinAccountServiceI;
import com.hbasesoft.framework.manager.wechat.guangjia.util.WeixinUtil;
import com.hbasesoft.manager.service.WeixinUserService;
import com.hbasesoft.manager.utils.RestResponse;
import com.hbasesoft.manager.vo.mass.Filter;
import com.hbasesoft.manager.vo.mass.Image;
import com.hbasesoft.manager.vo.mass.ImageMass;
import com.hbasesoft.manager.vo.mass.MpNews;
import com.hbasesoft.manager.vo.mass.MpVideo;
import com.hbasesoft.manager.vo.mass.NewsMass;
import com.hbasesoft.manager.vo.mass.Text;
import com.hbasesoft.manager.vo.mass.TextMass;
import com.hbasesoft.manager.vo.mass.VideoMass;
import com.hbasesoft.manager.vo.mass.Voice;
import com.hbasesoft.manager.vo.mass.VoiceMass;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({ "/sendAccountController" })
public class SendAccountController extends AbstractWechatController {
	private static Logger logger = LoggerFactory.getLogger(SendAccountController.class);

	@Autowired
	private SystemService systemService;

	@Autowired
	private SendAllServiceI sendAllService;

	@Autowired
	private WeixinAccountServiceI weixinAccountService;
	private String message;
	
	@Resource(name = "weixinUserService")
	private WeixinUserService weixinUserService;
//
//	@RequestMapping
//	@ResponseBody
//	public void test(HttpServletRequest request) {
//		
//	}
	@RequestMapping(params = { "list" })
	public ModelAndView list(HttpServletRequest request) {
		logger.info("微信群發...");
		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		
		List accountList = this.weixinAccountService.findByQueryString(
				new StringBuilder().append("from WeixinAccountEntity where state = 'A' and orgCode = '").append(orgCode)
				.append("'").toString());
		
		request.setAttribute("accountList", accountList);
		
		return new ModelAndView("weixin/guanjia/mass/masslist");
	}

	@RequestMapping(params = { "gettemplate" })
	public void gettemplate(HttpServletRequest request, HttpServletResponse response) {
		logger.info("根据类型、分组动态获取模板...gettemplate ");
		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		String msgType = request.getParameter("msgType");
		String groupName = request.getParameter("wgroup");
		String resMsg = "";
		if (CommonUtil.isNotEmpty(msgType)) {
			if ("text".equals(msgType)) {
				String hql = new StringBuilder().append("from TextTemplate t where t.orgCode = '").append(orgCode)
						.append("' ").toString();
				if (CommonUtil.isNotEmpty(groupName)) {
					hql = new StringBuilder().append(hql).append("and groupId = '").append(groupName).append("'")
							.toString();
				}

				List textList = this.sendAllService.findByQueryString(hql);

				JSONArray json = JSONArray.fromObject(textList);
				resMsg = json.toString();
			} else if ("news".equals(msgType)) {
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.setExcludes(new String[] { "newsItemList" });

				String hql = new StringBuilder().append("from NewsTemplate t where t.orgCode = '").append(orgCode)
						.append("' ").toString();
				if (CommonUtil.isNotEmpty(groupName)) {
					hql = new StringBuilder().append(hql).append("and groupId = '").append(groupName).append("'")
							.toString();
				}

				List newsList = this.sendAllService.findByQueryString(hql);

				JSONArray json = JSONArray.fromObject(newsList, jsonConfig);
				resMsg = json.toString();
			} else if ("image".equals(msgType)) {
				String hql = "from WeixinFiletemplatePojo t where t.type = 'image' ";
				if (CommonUtil.isNotEmpty(groupName)) {
					hql = new StringBuilder().append(hql).append("and groupId = '").append(groupName).append("'")
							.toString();
				}

				List imageList = this.sendAllService.findByQueryString(hql);

				JSONArray json = JSONArray.fromObject(imageList);
				resMsg = json.toString();
			} else if ("video".equals(msgType)) {
				String hql = "from WeixinFiletemplatePojo t where t.type = 'video' ";
				if (CommonUtil.isNotEmpty(groupName)) {
					hql = new StringBuilder().append(hql).append("and groupId = '").append(groupName).append("'")
							.toString();
				}

				List videoList = this.sendAllService.findByQueryString(hql);

				JSONArray json = JSONArray.fromObject(videoList);
				resMsg = json.toString();
			} else if ("voice".equals(msgType)) {
				String hql = "from WeixinFiletemplatePojo t where t.type = 'voice' ";
				if (CommonUtil.isNotEmpty(groupName)) {
					hql = new StringBuilder().append(hql).append("and groupId = '").append(groupName).append("'")
							.toString();
				}

				List voiceList = this.sendAllService.findByQueryString(hql);

				JSONArray json = JSONArray.fromObject(voiceList);
				resMsg = json.toString();
			} else if ("expand".equals(msgType)) {
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.setExcludes(new String[] { "newsItemList" });

				List newsList = this.sendAllService.findByQueryString(
						new StringBuilder().append("from WeixinExpandconfigEntity t where t.orgCode = '")
								.append(orgCode).append("'").toString());

				JSONArray json = JSONArray.fromObject(newsList, jsonConfig);
				resMsg = json.toString();
			}
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(resMsg);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(params = { "datagrid" })
	@ResponseBody
	public void datagrid(WeixinAllsend massrecord, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		String aId = request.getParameter("aId");

		CriteriaQuery cq = new CriteriaQuery(WeixinAllsend.class, dataGrid);

		List weixinAccountList = this.sendAllService.findByQueryString(new StringBuilder()
				.append("from WeixinAccountEntity where  id = '").append(aId).append("'").toString());

		if (weixinAccountList.size() == 0) {
			cq.isNull("accountId");
		} else {
			String[] wechatArray = new String[weixinAccountList.size()];
			for (int i = 0; i < weixinAccountList.size(); i++) {
				wechatArray[i] = ((WeixinAccountEntity) weixinAccountList.get(i)).getId();
			}
			cq.in("accountId", wechatArray);
		}

		HqlGenerateUtil.installHql(cq, massrecord);

		this.sendAllService.getDataGridReturn(cq, true);

		TagUtil.datagrid(response, dataGrid);
	}

	@RequestMapping(params = { "goSuView" })
	public ModelAndView goSuView(HttpServletRequest req) {
		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		List weixinGroupList = this.sendAllService.findByQueryString(new StringBuilder()
				.append("from WeixinGroup where orgCode = '").append(orgCode).append("'").toString());

		List weixinAccountList = this.sendAllService.findByQueryString(
				new StringBuilder().append("from WeixinAccountEntity w where w.state='A' and w.orgCode = '")
						.append(orgCode).append("'").toString());

		req.setAttribute("weixinAccountList", weixinAccountList);
		req.setAttribute("groupList", weixinGroupList);
		return new ModelAndView("weixin/guanjia/mass/massinfo");
	}

	@RequestMapping(params = { "massMessage" })
	@ResponseBody
	public AjaxJson massMessage(WeixinAllsend massrecord, HttpServletRequest req) throws Exception {
		AjaxJson j = new AjaxJson();
		String accountId = req.getParameter("wechatName");
		String tagId = req.getParameter("groupid");
		String state = null;
		String templateName = null;
		String msgtype = req.getParameter("mtype");
		String templateId = massrecord.getTemplateId();
		TSUser tsUser = ResourceUtil.getSessionUserName();
		String operator = tsUser.getUserName();
		
		logger.info("accountId---:{}",accountId);
		logger.info("tagId---:{}",tagId);
		logger.info("msgtype---:{}",msgtype);
		logger.info("tsUser---:{}",tsUser);
		logger.info("templateId---:{}",templateId);
		logger.info("weixinUserService---{}",weixinUserService);
		logger.info("getTokenByAppid(accountId)---{}",weixinUserService.getTokenByAppid(accountId));
		logger.info("get('token')---{}",weixinUserService.getTokenByAppid(accountId).get("token"));
		String accessToken=weixinUserService.getTokenByAppid(accountId).get("token").toString();
		String weixinid=weixinUserService.getTokenByAppid(accountId).get("id").toString();
		
		String json = null; 

		if ("text".equals(msgtype)) {
			TextTemplate textTemplate = (TextTemplate) this.systemService.getEntity(TextTemplate.class, templateId);
			templateName = textTemplate.getTemplateName();
			String content = textTemplate.getContent();

			Filter filter = new Filter();
			if (StringUtils.isNotBlank(tagId)) {
				filter.setIs_to_all(Boolean.valueOf(false));
				filter.setTag_id(tagId);
			} else {
				filter.setIs_to_all(Boolean.valueOf(true));
			}

			Text text = new Text();
			text.setContent(content);

			TextMass textMass = new TextMass();
			textMass.setFilter(filter);
			textMass.setMsgtype(msgtype);
			textMass.setText(text);

			json = JSONHelper.bean2json(textMass);
		} else if ("image".equals(msgtype)) {
			String mediaid = getAndSetMediaId(templateId, accountId, msgtype);

			WeixinFiletemplatePojo weixinFiletemplatePojo = (WeixinFiletemplatePojo) this.systemService
					.getEntity(WeixinFiletemplatePojo.class, templateId);

			templateName = weixinFiletemplatePojo.getTemplateName();

			Filter filter = new Filter();
			if (StringUtils.isNotBlank(tagId)) {
				filter.setIs_to_all(Boolean.valueOf(false));
				filter.setTag_id(tagId);
			} else {
				filter.setIs_to_all(Boolean.valueOf(true));
			}

			Image i = new Image();
			i.setMedia_id(mediaid);

			ImageMass imageMass = new ImageMass();
			imageMass.setFilter(filter);
			imageMass.setImage(i);
			imageMass.setMsgtype(msgtype);

			json = JSONHelper.bean2json(imageMass);
		} else if ("voice".equals(msgtype)) {
			String mediaid = getAndSetMediaId(templateId, accountId, msgtype);

			WeixinFiletemplatePojo weixinFiletemplatePojo = (WeixinFiletemplatePojo) this.systemService
					.getEntity(WeixinFiletemplatePojo.class, templateId);

			templateName = weixinFiletemplatePojo.getTemplateName();

			Filter filter = new Filter();
			filter.setIs_to_all(Boolean.valueOf(false));
			if (StringUtils.isNotBlank(tagId)) {
				filter.setIs_to_all(Boolean.valueOf(false));
				filter.setTag_id(tagId);
			} else {
				filter.setIs_to_all(Boolean.valueOf(true));
			}

			Voice voice = new Voice();
			voice.setMedia_id(mediaid);

			VoiceMass voiceMass = new VoiceMass();
			voiceMass.setFilter(filter);
			voiceMass.setVoice(voice);
			voiceMass.setMsgtype(msgtype);

			json = JSONHelper.bean2json(voiceMass);
		} else {
			MpVideo mpvideo;
			if ("video".equals(msgtype)) {
				String mediaid = getAndSetMediaId(templateId, accountId, msgtype);

				WeixinFiletemplatePojo weixinFiletemplatePojo = (WeixinFiletemplatePojo) this.systemService
						.getEntity(WeixinFiletemplatePojo.class, templateId);

				templateName = weixinFiletemplatePojo.getTemplateName();

				Filter filter = new Filter();
				filter.setIs_to_all(Boolean.valueOf(false));
				if (StringUtils.isNotBlank(tagId)) {
					filter.setIs_to_all(Boolean.valueOf(false));
					filter.setTag_id(tagId);
				} else {
					filter.setIs_to_all(Boolean.valueOf(true));
				}

				mpvideo = new MpVideo();
				mpvideo.setMedia_id(mediaid);

				VideoMass videoMass = new VideoMass();
				videoMass.setFilter(filter);
				videoMass.setMpvideo(mpvideo);
				msgtype = "mpvideo";
				videoMass.setMsgtype(msgtype);

				json = JSONHelper.bean2json(videoMass);
			} else if ("news".equals(msgtype)) {
				NewsTemplate newsTemplate = (NewsTemplate) this.systemService.getEntity(NewsTemplate.class, templateId);
				templateName = newsTemplate.getTemplateName();

				List<NewsItem> newsItems = this.sendAllService
						.findByQueryString(new StringBuilder().append("from NewsItem where newsTemplate.id = '")
								.append(templateId).append("' order by orders").toString());

				List articles = new ArrayList();

				for (NewsItem newsItem : newsItems) {
					String msgType = "image";
					String imagepath = newsItem.getImagePath();//图片地址
					String thumbUrl = MessageFormat.format(WeixinUtil.upload, new Object[] { accessToken, msgType });
					String title = null;
					String introduction = null;
					
					PropertiesUtil propertiesUtil = new PropertiesUtil("sysConfig.properties");
					Properties prop = propertiesUtil.getProperties();

					String filepath = prop.getProperty("upload_basepath").trim();

					StringBuilder path = new StringBuilder(filepath);
					path.append("/");
					path.append(imagepath);

					String mediaId = WeixinUtil.mediaUpload(thumbUrl, accessToken, path.toString(), msgType, title,
							introduction);

					logger.info(new StringBuilder().append("thumb_media_id ==>").append(mediaId).toString());

					title = newsItem.getTitle();
					
					String str = newsItem.getContent();
					str = str.replace("&nbsp;", " ");
					//如果正文有图片，替换成为微信url
					if (str.indexOf("<img")!=-1) {
						str=com.hbasesoft.manager.utils.CommonUtil.getUrl(accessToken, str,0);
					}
					Articles article = new Articles();
					article.setThumb_media_id(mediaId);
					article.setAuthor(newsItem.getAuthor());
					article.setTitle(title);
					article.setContent_source_url(newsItem.getUrl());
					article.setContent(str);
					article.setDigest(newsItem.getDescription());
					article.setShow_cover_pic("0");
					articles.add(article);
				}

				ArticlesMass articlesMass = new ArticlesMass();
				articlesMass.setArticles(articles);

				json = JSONHelper.bean2json(articlesMass);
				
				String url = WeixinUtil.uploadnews.replace("ACCESS_TOKEN", accessToken);
				JSONObject jsonObject = new JSONObject();
				jsonObject = WeixinUtil.httpRequest(url, "POST", json);
				logger.info(
						new StringBuilder().append("mpnews jsonObject ---->").append(jsonObject.toString()).toString());
				String mediaid = jsonObject.getString("media_id");
				logger.info(new StringBuilder().append("mpnews mediaid ---->").append(mediaid).toString());

				Filter filter = new Filter();
				if (StringUtils.isNotBlank(tagId)) {
					filter.setIs_to_all(Boolean.valueOf(false));
					filter.setTag_id(tagId);
				} else {
					filter.setIs_to_all(Boolean.valueOf(true));
				}

				MpNews mpnews = new MpNews();
				mpnews.setMedia_id(mediaid);
				msgtype = "mpnews";

				NewsMass newsmass = new NewsMass();
				newsmass.setFilter(filter);
				newsmass.setMpnews(mpnews);
				newsmass.setMsgtype(msgtype);
				newsmass.setSend_ignore_reprint("1");

				json = JSONHelper.bean2json(newsmass);
			}
		}
//		new Integer("sgdkj");
		String url = WeixinUtil.groupid_url.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = new JSONObject();
		jsonObject = WeixinUtil.httpRequest(url, "POST", json);
		logger.info(new StringBuilder().append("massmessage jsonObject").append(jsonObject.toString()).toString());
		if (jsonObject != null) {
			if (0 == jsonObject.getInt("errcode")) {
				this.message = "发送群消息成功！";
				state = "1";
			} else {
				this.message = new StringBuilder().append("发送群消息失败！错误码为：").append(jsonObject.getInt("errcode"))
						.append("错误信息为：").append(jsonObject.getString("errmsg")).toString();
				state = "0";
			}

		}
		WeixinAllsend weixinMassrecord = new WeixinAllsend();
		weixinMassrecord.setAccountId(weixinid);
		weixinMassrecord.setCreateDate(new Date());
		weixinMassrecord.setGroupId(tagId);
		weixinMassrecord.setMsgType(msgtype);
		weixinMassrecord.setOperator(operator);
		weixinMassrecord.setState(state);
		weixinMassrecord.setTemplateId(templateId);
		weixinMassrecord.setTemplateName(templateName);

		this.systemService.save(weixinMassrecord);

		return j;
	}

	private String getAndSetMediaId(String templateId, String accountId, String msgType) {
		String mediaId = null;

		String accessToken=weixinUserService.getTokenByAppid(accountId).get("token").toString();
		WeixinFiletemplatePojo weixinFiletemplatePojo = (WeixinFiletemplatePojo) this.systemService
				.getEntity(WeixinFiletemplatePojo.class, templateId);

		String scFilePath = weixinFiletemplatePojo.getScFilepath();

		PropertiesUtil propertiesUtil = new PropertiesUtil("sysConfig.properties");
		Properties prop = propertiesUtil.getProperties();

		String filepath = prop.getProperty("upload_basepath").trim();

		StringBuilder path = new StringBuilder(filepath);
		path.append("/");
		path.append(scFilePath);

		String title = weixinFiletemplatePojo.getTemplateName();
		String introduction = weixinFiletemplatePojo.getIntroduction();

		WeixinMediaUpload weixinMediaUpload = null;
		List<WeixinMediaUpload> weixinMediaUploadList = this.systemService.findByProperty(WeixinMediaUpload.class,
				"fileTemplateId", templateId);

		for (WeixinMediaUpload w : weixinMediaUploadList) {
			if (accountId.equals(w.getAccountId())) {
				weixinMediaUpload = w;
			}
		}

		if (weixinMediaUpload != null) {
			mediaId = weixinMediaUpload.getMediaId();
		} else if (!"video".equals(msgType)) {
			WeixinMediaUpload weixinFiletemplateMedia = new WeixinMediaUpload();

			String url = MessageFormat.format(WeixinUtil.add_material, new Object[] { accessToken, msgType });
			mediaId = WeixinUtil.mediaUpload(url, accessToken, path.toString(), msgType, title, introduction);

			weixinFiletemplateMedia.setMediaId(mediaId);
			weixinFiletemplateMedia.setAccountId(accountId);
			weixinFiletemplateMedia.setCreateTime(new Date());
			weixinFiletemplateMedia.setFileTemplateId(templateId);
			weixinFiletemplateMedia.setType(msgType);

			this.systemService.save(weixinFiletemplateMedia);
		}

		if ("video".equals(msgType)) {
			String url = MessageFormat.format(WeixinUtil.upload, new Object[] { accessToken, msgType });
			mediaId = WeixinUtil.mediaUpload(url, accessToken, path.toString(), msgType, title, introduction);

			UploadVideo uploadVideo = new UploadVideo();
			uploadVideo.setMedia_id(mediaId);
			uploadVideo.setTitle(title);
			uploadVideo.setDescription(introduction);

			String json = JSONHelper.bean2json(uploadVideo);

			String uploadUrl = WeixinUtil.upload_video.replace("ACCESS_TOKEN", accessToken);
			JSONObject jsonObject = new JSONObject();
			jsonObject = WeixinUtil.httpRequest(uploadUrl, "POST", json);

			mediaId = jsonObject.getString("media_id");
		}

		return mediaId;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static void main(String[] args,HttpServletRequest req) {
		 String url = "http://news.xinhuanet.com/politics/leaders/2017-11/07/1121920443_15100628014221n.jpg";    
		 com.hbasesoft.manager.utils.CommonUtil.downLoadFromUrl(url, "E:\\weixinimage\\"+System.currentTimeMillis()+".jpg"); 
	}
}