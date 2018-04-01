/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service.remote;

import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbasesoft.actsports.portal.bean.ActFormPojo;
import com.hbasesoft.actsports.portal.bean.ActResp;
import com.hbasesoft.actsports.portal.bean.collect.ActCollectParamsPojo;
import com.hbasesoft.actsports.portal.bean.collect.ActCollectPojo;
import com.hbasesoft.actsports.portal.bean.collect.ActCollectResultAttachmentPojo;
import com.hbasesoft.actsports.portal.bean.collect.ActCollectResultIdeaPojo;
import com.hbasesoft.actsports.portal.bean.collect.ActCollectResultPojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryChoosePojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryPojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryResultChoosePojo;
import com.hbasesoft.actsports.portal.biz.vo.WechatAccount;
import com.hbasesoft.actsports.portal.constant.CommonContant;
import com.hbasesoft.actsports.portal.service.CollectInfoService;
import com.hbasesoft.actsports.portal.service.CollectService;
import com.hbasesoft.actsports.portal.service.UserService;
import com.hbasesoft.actsports.portal.service.api.ConfigService;
import com.hbasesoft.actsports.portal.util.SHA1;
import com.hbasesoft.actsports.portal.util.WeChatUtil;
import com.hbasesoft.framework.cache.core.annotation.CacheLock;
import com.hbasesoft.framework.cache.core.annotation.Key;
import com.hbasesoft.framework.common.FrameworkException;
import com.hbasesoft.framework.common.RemoteServiceException;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.ContextHolder;
import com.hbasesoft.framework.common.utils.PropertyHolder;
import com.hbasesoft.framework.common.utils.logger.Logger;

/**
 * <Description> <br>
 * 
 * @author 查思玮<br>
 * @version 0.1-SNAPSHOT<br>
 * @taskId <br>
 * @CreateDate 2016年12月28日 <br>
 * @since V0.1-SNAPSHOT<br>
 * @see com.hbasesoft.actSports.portal.service.Impl <br>
 */
@Service("CollectRemoteService")
public class CollectRemoteService implements CollectService {

	@Resource
	private CollectInfoService collectService;

	@Resource
	private UserService userService;

	private ConfigService configService;
	
	private static Logger logger = new Logger(CollectRemoteService.class);

	/**
	 * Description: 获取征集页面 <br>
	 * 
	 * @author 查思玮<br>
	 * @taskId <br>
	 * @param subsId
	 * @return
	 * @throws FrameworkException
	 *             <br>
	 */
	@Override
	@Transactional
	public ActResp getCollectPage(String userId, String collectId, String url) throws FrameworkException {
		try {
			
			url = URLDecoder.decode(url,"UTF-8");
			List<ActFormPojo> actFormPojoList = new ArrayList<ActFormPojo>();
			ActResp resp = new ActResp();
			
			ActCollectPojo actCollectPojo = collectService.queryCollectById(collectId);
			
			if (actCollectPojo == null ) {
				return resp;
			}
			logger.info("getSignUpPageService = ==========");
			// 验证是否满足条件:是否过期，是否达到个人报名上限，个人是否报名
			if (isActStatus(actCollectPojo)) { // 活动是否发布
				if(isStartAct(actCollectPojo)){ // 活动是否开始
					if(isEndAct(actCollectPojo)){	// 活动是否结束
						logger.info("未过期 = ==========");
						if( isEvent(collectId, userId)){ // 个人已提交
							logger.info("个人未提交信息 ===========");
								
							resp.setStatus("doing");
							Map<String, String> wxConfigMap = new HashMap<String, String>();
							//存入页面上传图片需要的微信参数
//				            String applicationpath = PropertyHolder.getProperty("server.wx.url");
				            logger.info("url = [{0}]",url);
//				            url = applicationpath + url.substring(request.getContextPath().length() + 1);
				            String signaturestr = "jsapi_ticket={0}&noncestr={1}&timestamp={2}&url={3}";
				            String nonceStr = UUID.randomUUID().toString();
				            String timestamp = Long.toString(System.currentTimeMillis() / 1000);
				            WechatAccount wechatAccount = getConfigService().getWechatAccount();
				            String appId = wechatAccount.getAppCode();
				            wxConfigMap.put("appId", appId);
				            wxConfigMap.put("timestamp", timestamp);
				            wxConfigMap.put("nonceStr", nonceStr);
				            String jsApiTicket = getConfigService().getWechatJsApiTickit();
				            SHA1 sha1 = new SHA1();// applicationpath
				            //判断collectId中是否含有“&”,并去除
				    		if(url.contains("#")){
				    			url = url.substring(0, url.indexOf("#"));
				    		}
				    		logger.info("加密参数 jsApiTicket[{0}], nonceStr[{1}], timestamp[{2}],  url[{4}]", jsApiTicket,
				    				nonceStr, timestamp, url);
				            signaturestr = MessageFormat.format(signaturestr, jsApiTicket, nonceStr, timestamp, url);
				            logger.info("signaturestr = [{0}]",signaturestr);
				            String signature = sha1.getDigestOfString(signaturestr.getBytes());
				            wxConfigMap.put("signature", signature);
				            logger.info("Get weixin parameter url[{0}], appId[{1}], timestamp[{2}], nonceStr[{3}], signature[{4}]", url,
				                appId, timestamp, nonceStr, signature);
				            
				            resp.setWeixinConfig(wxConfigMap);
							// 获取征集录入项
							List<ActCollectParamsPojo> collectParmasList = collectService.queryCollectParamsByEntryId(actCollectPojo.getId());
							List<String> rule = null;
							
							ActFormPojo actFormPojo =  null;
							//判断最小长度
							if(!CommonUtil.isEmpty(actCollectPojo.getCollectMin())){
								resp.setMin(Integer.valueOf(actCollectPojo.getCollectMin()));
							}else{
								resp.setMin(0);
							}
							
							//判断最大长度
							if(!CommonUtil.isEmpty(actCollectPojo.getCollectMax())){
								resp.setMax(Integer.valueOf(actCollectPojo.getCollectMax()));
							}else{
								resp.setMax(1000);
							}
							
							//判断允许上传附件个数
							if(!CommonUtil.isEmpty(actCollectPojo.getImgLen())){
							    resp.setImgLen(Integer.valueOf(actCollectPojo.getImgLen()));
							} else {
							    resp.setImgLen(1);
							}
							
							
							//判断是否允许图片上传
							if(actCollectPojo.getAllowImage().equals(CommonContant.ALLOW)){
								actFormPojo =  new ActFormPojo();
								actFormPojo.setTitle("上传图片");
								actFormPojo.setType("uploadImg");
								actFormPojoList.add(actFormPojo);
							}
							
							if (collectParmasList != null && collectParmasList.size() > 0) {
								for (ActCollectParamsPojo pojo : collectParmasList) {
											
									actFormPojo = new ActFormPojo();
									rule = new ArrayList<String>();
									if(pojo.getRequired() != null && !pojo.getRequired().equals(CommonContant.UN_REQUIRED))
										rule.add(pojo.getRequired());
									//如果规则为text，则Rule不传内容
									if(!pojo.getRule().equals("text"))
										rule.add(pojo.getRule());
									rule.add(pojo.getMinlength() + "-" + pojo.getMaxlength());
									actFormPojo.setTitle(pojo.getTitle());
									actFormPojo.setType(pojo.getType());
									actFormPojo.setRules(rule);
									actFormPojoList.add(actFormPojo);
								}
							}

							resp.setActFormListPojo(actFormPojoList);
							resp.setMessage("打开页面成功！");
						}else{//个人已提交
							resp.setStatus("success");
							resp.setMessage(actCollectPojo.getSucessMessage());
						}
					}else{
						resp.setStatus("end");
						resp.setMessage(actCollectPojo.getEndMessage());
					}
				}else{
					resp.setStatus("unStart");
					resp.setMessage("征集未开始");
				}
			}else{
				resp.setStatus("end");
				resp.setMessage(actCollectPojo.getEndMessage());
			}
			resp.setSubscribeCode("0");
			return resp;
		} catch (Exception e) {
			logger.error(e);
			throw new RemoteServiceException(e);
		}
	}


	/**
	 * Description: 微信报名 <br>
	 * 
	 * @author 查思玮<br>
	 * @taskId <br>
	 * @param subsId
	 * @return
	 * @throws FrameworkException
	 *             <br>
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@CacheLock(value = "collect", key="${collect.get(\"collectId\")}", expireTime = 50, timeOut = 50000)
	public ActResp collect(String userId, @Key("collect") Map<String, Object> map) throws Exception {
		
		List<String> imgList = new ArrayList<String>();
		
		String collectId = (String) map.get("collectId");
		//判断collectId中是否含有“&”,并去除
		if(collectId.contains("&")){
			collectId = collectId.substring(0, collectId.indexOf("&"));
		}
				
		String collectText = (String) map.get("collectText");
		imgList = (List<String>) map.get("imgList");
		ActResp resp = new ActResp();
		
		ActCollectPojo actCollectPojo = collectService.queryCollectById(collectId);
		
		logger.info("开始征集录入 ===========");
		//检验该用户是否已经征集提交过
		if( !isEvent(collectId, userId)){ //个人已报名
			resp.setStatus("end");
			logger.info("该用户已经提交过 ===========");
			resp.setMessage(actCollectPojo.getEndMessage());
			return resp;
		}
		// 将图片录入数据库
		if( imgList !=null && imgList.size() > 0){
			ActCollectResultAttachmentPojo acraPojo = null;
			for(String path : imgList ){
				acraPojo = new ActCollectResultAttachmentPojo();
				acraPojo.setCollectId(collectId);
				acraPojo.setCreateTime(new Date());
				acraPojo.setUserId(userId);
				acraPojo.setImagePath(path);
				collectService.saveActCollectResultAttachmentPojo(acraPojo);
			}
		}
		
		// 将征集信息录入数据库
		ActCollectResultIdeaPojo acriPojo = new ActCollectResultIdeaPojo();
		acriPojo.setCollectId(collectId);
		acriPojo.setCreateTime(new Date());
		acriPojo.setIdea(collectText);
		acriPojo.setUserId(userId);
		collectService.saveActCollectResultIdeaPojo(acriPojo);
		
		
		//将征集的用户信息录入数据库
		map.remove("collectId");
		map.remove("imgList");
		map.remove("collectText");
		ActCollectResultPojo actCollectResultPojo = null;
		for (String key : map.keySet()) {  
			actCollectResultPojo = new ActCollectResultPojo();
			actCollectResultPojo.setCollectId(collectId);
			actCollectResultPojo.setUserId(userId);
			actCollectResultPojo.setParamKey(key);
			actCollectResultPojo.setParamValue((String)map.get(key));
			actCollectResultPojo.setCreateTime(new Date());
			collectService.saveActCollectResultPojo(actCollectResultPojo);
	    } 
		
		logger.info("征集录入成功 ===========");
		resp.setStatus("success");
		resp.setMessage(actCollectPojo.getSucessMessage());
		return resp;
	}
	
	
	//判断是否需要显示报名排名,需要返回true,否则返回false
	public boolean isShowEventRank(ActEntryPojo pojo) throws Exception {
		if(pojo.getEntryRank() != null && pojo.getEntryRank().equals(ActEntryPojo.DISPLAY_RANK))
			return true;
		else
			return false;
	}
	
	
	//判断活动状态，发布状态返回true
	private boolean isActStatus(ActCollectPojo actCollectPojo) {
		if(actCollectPojo.getStatus() != null && actCollectPojo.getStatus().equals(CommonContant.AVALIABLE))
			return true;
		else
			return false;
	}
	
	//验证是否已报名过，未报名返回true,已报名返回false
	public boolean isEvent(String collectId, String userId) throws Exception {
		ActCollectResultIdeaPojo actPojo = userService.queryCollectIdeaByUserIdAndCollectId(userId,
				collectId);
		if(CommonUtil.isNull(actPojo))
			return true;
		else
			return false;
	}
	
	//活动是否开始
	public boolean isStartAct(ActCollectPojo actCollectPojo) throws Exception {
		
		long currentTime = System.currentTimeMillis();
		
		return actCollectPojo.getStartTime().getTime() <= currentTime;
	}
	
	//活动是否结束
	public boolean isEndAct(ActCollectPojo actCollectPojo) throws Exception {
		
		long currentTime = System.currentTimeMillis();
		
		return actCollectPojo.getEndTime().getTime() >= currentTime;
	}

	public boolean isActUserNumMax(ActEntryPojo actEntry, String userId) throws Exception {
		List<ActEntryResultChoosePojo> actPojo = userService.queryEntryChooseListByUserIdAndEntryId(userId,
				actEntry.getId());

		if (actEntry.getChooseLimit() == "0") {// 设置每人报名数为0时
			return false;
		} else if (actPojo == null || (actPojo.size() < Integer.parseInt(actEntry.getChooseLimit())))
			return true;
		else
			return false;
	}

	public boolean isActChooseNumMax(ActEntryChoosePojo pojo, String entryId) throws Exception {
		List<ActEntryResultChoosePojo> actPojo = userService.queryEntryChooseListByChooseIdAndEntryId(pojo.getId(), entryId);
		if (pojo.getMax() == "0") {// 设置项目报名数为0时
			return false;
		} else if (actPojo == null || (actPojo.size() < Integer.parseInt(pojo.getMax())))
			return true;
		else
			return false;
	}
	

	public boolean isAlreadyEvent(String actResultChooseId,String entryId, String userId) throws Exception {
		List<ActEntryResultChoosePojo> actPojo = userService.queryEntryChooseListByChooseIdAndEntryIdAndUserId(actResultChooseId, entryId, userId);
		
		if(actPojo != null && actPojo.size() >0)//报名过了
			return true;
		else
			return false;
	}
	
	public ConfigService getConfigService() {
        if (configService == null) {
            configService = ContextHolder.getContext().getBean(ConfigService.class);
        }
        return configService;
    }
	
}
