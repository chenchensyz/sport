package com.hbasesoft.actsports.portal.weixin;

/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbasesoft.actsports.portal.bean.ActFormPojo;
import com.hbasesoft.actsports.portal.bean.ActResp;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryChoosePojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryChooseRespPojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryParamsPojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryPojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryResultChoosePojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryResultPojo;
import com.hbasesoft.actsports.portal.bean.prize.PrizeResult;
import com.hbasesoft.actsports.portal.constant.CommonContant;
import com.hbasesoft.actsports.portal.service.EntryInfoService;
import com.hbasesoft.actsports.portal.service.SignUpService;
import com.hbasesoft.actsports.portal.service.UserService;
import com.hbasesoft.framework.cache.core.annotation.CacheLock;
import com.hbasesoft.framework.cache.core.annotation.Key;
import com.hbasesoft.framework.common.FrameworkException;
import com.hbasesoft.framework.common.RemoteServiceException;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.logger.Logger;

/**
 * <Description> <br>
 * 
 * @author 查思玮<br>
 * @version 0.1-SNAPSHOT<br>
 * @taskId <br>
 * @CreateDate 2016年12月08日 <br>
 * @since V0.1-SNAPSHOT<br>
 * @see com.hbasesoft.actSports.portal.service.Impl <br>
 */
public class SignUpRemoteService implements SignUpService {

	// @Resource(name = "UserInfoRemoteService")
	// private UserinfoService userinfoService;

	@Resource
	private EntryInfoService entryService;

	@Resource
	private UserService userService;

	private static Logger logger = new Logger(SignUpRemoteService.class);

	/**
	 * Description: 获取报名页面 <br>
	 * 
	 * @author 查思玮<br>
	 * @taskId <br>
	 * @param subsId
	 * @return
	 * @throws FrameworkException
	 *             <br>
	 */
	@Override
	@Transactional(readOnly = true)
	public ActResp getSignUpPage(String userId, String entryId) throws FrameworkException {
		logger.info("进入 getSignUpPage Service = ==========");
		
		try {
			
			List<ActFormPojo> actFromPojoList = new ArrayList<ActFormPojo>();
			ActResp resp = new ActResp();
			
			ActEntryPojo actEntryPojo = entryService.queryEntryById(entryId);
			
			if (actEntryPojo == null ) {
				return resp;
			}
			logger.info("getSignUpPageService = ==========");
			// 验证是否满足条件:是否过期，是否达到个人报名上限，个人是否报名
			if (isActStatus(actEntryPojo)) { // 活动是否发布
				if(isStartAct(actEntryPojo)){ //活动是否开始
					if(isEndAct(actEntryPojo)){	// 活动是否结束
						logger.info("未过期 = ==========");
						if( isEvent(entryId, userId)){ //个人未报名
							logger.info("个人未报名 = ==========");
							if( isActUserNumMax(actEntryPojo, userId)){//未达到上限
								// 获取报名活动列表
								List<ActEntryChoosePojo> entryChooseList = entryService
										.queryEntrychooseByEntryId(actEntryPojo.getId());
								
								if(isActMax(actEntryPojo, entryChooseList)){ //项目是否达到上限
									logger.info("未达到上限 = ==========");
									resp.setStatus("doing");
									
									// 获取报名录入项
									List<ActEntryParamsPojo> entryParmasList = entryService
											.queryEntryParamsByEntryId(actEntryPojo.getId());
									List<String> rule = null;
									
									ActFormPojo actFromPojo = null;
									if (entryParmasList != null && entryParmasList.size() > 0) {
										for (ActEntryParamsPojo pojo : entryParmasList) {
											
											actFromPojo = new ActFormPojo();
											rule = new ArrayList<String>();
											if(pojo.getRequired() != null && !pojo.getRequired().equals(CommonContant.UN_REQUIRED))
												rule.add(pojo.getRequired());
											//如果规则为text，则Rule不传内容
											if(!pojo.getRule().equals("text"))
												rule.add(pojo.getRule());
											rule.add(pojo.getMinlength() + "-" + pojo.getMaxlength());
											actFromPojo.setTitle(pojo.getTitle());
											actFromPojo.setType(pojo.getType());
											actFromPojo.setRules(rule);
											actFromPojoList.add(actFromPojo);
										}
									}

									
									List<ActEntryChooseRespPojo> actChoosePojoList = null;
									ActEntryChooseRespPojo actChoose = null;
									if (entryChooseList != null && entryChooseList.size() > 0) {
										actFromPojo = new ActFormPojo();
										actChoosePojoList = new ArrayList<ActEntryChooseRespPojo>();
										
										actFromPojo.setTitle("报名项目");
										if(Integer.parseInt(actEntryPojo.getChooseLimit())>1)
											actFromPojo.setType("checkbox");
										else
											actFromPojo.setType("radio");
										actFromPojo.setChooseLimit(actEntryPojo.getChooseLimit());
										
										for (ActEntryChoosePojo pojo : entryChooseList) {
											actChoose = new ActEntryChooseRespPojo();
											
											actChoose.setId(pojo.getId());
											actChoose.setText(pojo.getTitle());
											
											// 验证是否达到项目报名上限
											if (isActChooseNumMax(pojo, actEntryPojo.getId())) { // 未达到上限
												actChoose.setExecutable(true);
											} else {
												actChoose.setExecutable(false);
											}
											actChoosePojoList.add(actChoose);
										}
										actFromPojo.setEntryChooseList(actChoosePojoList);
										actFromPojoList.add(actFromPojo);
									}
									resp.setActFormListPojo(actFromPojoList);
									resp.setMessage("打开页面成功！");
								}else{
									resp.setStatus("end");
									resp.setMessage(actEntryPojo.getEndMessage());
								}
							}else{//达到上限
								resp.setStatus("end");
								resp.setMessage(actEntryPojo.getEndMessage());
							}
						}else{//个人已报名
							//查询当前的排名
							int currentNum = userService.queryEntryChooseListByUserIdAndEntryId(userId, entryId).get(0).getEntryRank();
							resp.setCurrentNum(currentNum);
							resp.setStatus("success");
							resp.setMessage(actEntryPojo.getSucessMessage());
						}
					}else{
						resp.setStatus("end");
						resp.setMessage(actEntryPojo.getEndMessage());
					}
				}else{
					resp.setStatus("unStart");
					resp.setMessage("活动未开始");
				}
			}else{
				resp.setStatus("end");
				resp.setMessage(actEntryPojo.getEndMessage());
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
	@CacheLock(value = "SignUp", key="${entry.get(\"entryId\")}", expireTime = 50, timeOut = 50000)
	public ActResp signUp(String userId, @Key("entry") Map<String, Object> map) throws Exception {
		
		List<String> chooseList = new ArrayList<String>();
		
		String entryId = (String) map.get("entryId");
		chooseList = (List<String>) map.get("chooseList");
		ActResp resp = new ActResp();
		//判断entryId中是否含有“&”,并去除
		if(entryId.contains("&")){
			entryId = entryId.substring(0, entryId.indexOf("&"));
		}
		ActEntryPojo actEntryPojo = entryService.queryEntryById(entryId);
		
		logger.info("signUp = ==========");
		ActEntryResultChoosePojo pojo;
		//验证每一项是否达到上限
		ActEntryChoosePojo actEntryChoose;
		
		//检验该用户是否已经报名过
		if( !isEvent(entryId, userId)){ //个人已报名
			resp.setStatus("end");
			resp.setMessage(actEntryPojo.getEndMessage());
			return resp;
		}
		
		boolean isError = false;
		String errorMessage = "项目 ";
		// 获取报名活动列表
		List<ActEntryChoosePojo> entryChooseList = entryService
							.queryEntrychooseByEntryId(actEntryPojo.getId());
		if(chooseList.size() == 1){
			if(!isSignUpActMax(actEntryPojo, entryChooseList, chooseList.size()-1)){
				resp.setStatus("end");
				resp.setMessage(actEntryPojo.getEndMessage());
				return resp;
			}
		}else if(!isSignUpActMax(actEntryPojo, entryChooseList, chooseList.size())){
			resp.setStatus("end");
			resp.setMessage(actEntryPojo.getEndMessage());
			return resp;
		}
		for(String id : chooseList){
			actEntryChoose = new ActEntryChoosePojo();
			actEntryChoose = entryService.queryEntryChooseById(id);
			//验证该活动是否达到上限
			if(!isActChooseNumMax(actEntryChoose, entryId)){//达到上限
				errorMessage += actEntryChoose.getTitle() + ",";
				isError = true;
				break;
			}
		}
		if(isError){
			
			logger.info("报名人数已达上限 = ==========");
			errorMessage.substring(0,errorMessage.length()-1);
			errorMessage += " 报名人数已达上限。";
			resp.setStatus("error");
			resp.setMessage(errorMessage);
			return resp;
		}
		//查询当前的排名
		int currentNum = entryService.queryEntryRankByEntryId(entryId) + 1;
		//都可以报名，则开始报名
		for(String id : chooseList){
			
			//存入数据库
			pojo = new ActEntryResultChoosePojo();
			pojo.setChoose(id);
			pojo.setCreateTime(new Date());
			pojo.setEntryId(entryId);
			pojo.setUserId(userId);
			pojo.setEntryRank(currentNum);
			entryService.saveActEntryResultChoosePojo(pojo);
		}
		//将报名信息录入actEntryResult表
		String sessionId = (String) map.get("sessionId");
		if(!CommonUtil.isEmpty(sessionId))
			map.remove("sessionId");
		map.remove("entryId");
		map.remove("chooseList");
		ActEntryResultPojo actEntryResultPojo = null;
		 for (String key : map.keySet()) {  
			 actEntryResultPojo = new ActEntryResultPojo();
			 actEntryResultPojo.setEntryId(entryId);
			 actEntryResultPojo.setUserId(userId);
			 actEntryResultPojo.setParamKey(key);
			 actEntryResultPojo.setParamValue((String)map.get(key));
			 actEntryResultPojo.setCreateTime(new Date());
			 entryService.saveActEntryResultPojo(actEntryResultPojo);
	        } 
			
		//判断是否需要显示报名排名
		if(isShowEventRank(actEntryPojo)){
			logger.info("需要显示报名排名 = ==========");
			resp.setCurrentNum(currentNum);
		}
		logger.info("报名成功 = ==========");
		resp.setStatus("success");
		resp.setMessage(null);
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
	private boolean isActStatus(ActEntryPojo actEntryPojo) {
		if(actEntryPojo.getStatus() != null && actEntryPojo.getStatus().equals(CommonContant.AVALIABLE))
			return true;
		else
			return false;
	}
	
	//验证是否已报名过，未报名返回true,已报名返回false
	public boolean isEvent(String entryId, String userId) throws Exception {
		List<ActEntryResultChoosePojo> actPojo = userService.queryEntryChooseListByUserIdAndEntryId(userId,
				entryId);
		if(actPojo != null && actPojo.size() > 0)
			return false;
		else
			return true;
	}
	
	//活动是否开始
	public boolean isStartAct(ActEntryPojo actEntry) throws Exception {
		
		long currentTime = System.currentTimeMillis();
		
		return actEntry.getStartTime().getTime() <= currentTime;
	}
	
	//活动是否结束
	public boolean isEndAct(ActEntryPojo actEntry) throws Exception {
		
		long currentTime = System.currentTimeMillis();
		
		return actEntry.getEndTime().getTime() >= currentTime;
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
	
	private boolean isActMax(ActEntryPojo actEntryPojo, List<ActEntryChoosePojo> entryChooseList) throws Exception {
		//查询当前项目已报人数
		int entryNum = entryService.queryEntryNumByEntryId(actEntryPojo.getId());
		//查询项目上限人数
		int entryMax = 0;
		if(entryChooseList != null && entryChooseList.size()>0){
			for(ActEntryChoosePojo pojo : entryChooseList){
				entryMax += Integer.valueOf(pojo.getMax());
			}
		}
		return entryNum < entryMax;
	}
	
	private boolean isSignUpActMax(ActEntryPojo actEntryPojo, List<ActEntryChoosePojo> entryChooseList, int chooseListSize) throws Exception {
		//查询当前项目已报人数和当前报名项目的总数
		int entryNum = entryService.queryEntryNumByEntryId(actEntryPojo.getId()) + chooseListSize;
		//查询项目上限人数
		int entryMax = 0;
		if(entryChooseList != null && entryChooseList.size()>0){
			for(ActEntryChoosePojo pojo : entryChooseList){
				entryMax += Integer.valueOf(pojo.getMax());
			}
		}
		return entryNum < entryMax;
	}

	@Override
	public PrizeResult queryPrizeList(String userId, Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
