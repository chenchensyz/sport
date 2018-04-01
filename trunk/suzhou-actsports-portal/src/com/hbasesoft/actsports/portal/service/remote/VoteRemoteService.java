/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service.remote;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbasesoft.actsports.portal.bean.ActFormPojo;
import com.hbasesoft.actsports.portal.bean.ActResp;
import com.hbasesoft.actsports.portal.bean.vote.ActVoteChoosePojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVoteChooseRespPojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVoteParamsPojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVotePojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVoteResultChoosePojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVoteResultPojo;
import com.hbasesoft.actsports.portal.constant.CommonContant;
import com.hbasesoft.actsports.portal.service.UserService;
import com.hbasesoft.actsports.portal.service.VoteInfoService;
import com.hbasesoft.actsports.portal.service.VoteService;
import com.hbasesoft.framework.cache.core.annotation.CacheLock;
import com.hbasesoft.framework.cache.core.annotation.Key;
import com.hbasesoft.framework.common.FrameworkException;
import com.hbasesoft.framework.common.RemoteServiceException;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.PropertyHolder;
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
@Service("VoteRemoteService")
public class VoteRemoteService implements VoteService {

	@Resource
	private VoteInfoService voteService;

	@Resource
	private UserService userService;

	private static Logger logger = new Logger(VoteRemoteService.class);

	/**
	 * Description: 获取投票页面 <br>
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
	public ActResp getVotePage(String userId, String voteId, int pageNum, int pageSize) throws FrameworkException {
		try {

			List<ActFormPojo> actFromPojoList = new ArrayList<ActFormPojo>();
			ActResp resp = new ActResp();

			ActVotePojo actVotePojo = voteService.queryVoteById(voteId);

			if (actVotePojo == null) {
				return resp;
			}
			logger.info("getVotePageService = ==========");
			// 验证是否满足条件:是否过期，是否达到个人投票上限，个人是否投票
			if (isActStatus(actVotePojo)) { // 活动是否发布
				logger.info("活动已发布	 ===========");
				if (isStartAct(actVotePojo)) { // 活动是否开始
					logger.info("活动已开始	 ===========");
					if (isEndAct(actVotePojo)) { // 活动是否结束
						logger.info("活动未结束	 ===========");
						
						List<ActVoteResultChoosePojo> actPojo = userService.queryVoteChooseListByUserIdAndVoteId(userId,
								actVotePojo.getId());
						
						if (isEvent(actVotePojo, actPojo)) { // 个人是否能投票：一次或一日一次
							logger.info("个人未投票 ===========");
							// 根据页码获得当前页获取投票活动列表
							List<ActVoteChoosePojo> voteChoosePageList = voteService.queryVoteChooseByVoteIdWithPageNum(
									actVotePojo.getId(), pageNum, pageSize);

							// 获取投票录入项
							List<ActVoteParamsPojo> voteParmasList = voteService
									.queryVoteParamsByVoteId(actVotePojo.getId());

							List<String> rule = null;

							ActFormPojo actFromPojo = null;
							if (voteParmasList != null && voteParmasList.size() > 0) {
								for (ActVoteParamsPojo pojo : voteParmasList) {

									actFromPojo = new ActFormPojo();
									rule = new ArrayList<String>();
									if (pojo.getRequired() != null && !pojo.getRequired().equals(CommonContant.UN_REQUIRED))
										rule.add(pojo.getRequired());
									// 如果规则为text，则Rule不传内容
									if (!pojo.getRule().equals("text"))
										rule.add(pojo.getRule());
									rule.add(pojo.getMinlength() + "-" + pojo.getMaxlength());
									actFromPojo.setTitle(pojo.getTitle());
									actFromPojo.setType(pojo.getType());
									actFromPojo.setRules(rule);
									actFromPojoList.add(actFromPojo);
								}
							}

							// 插入投票项
							List<ActVoteChooseRespPojo> actVoteChoosePojoList = null;
							ActVoteChooseRespPojo actChoose = null;
							actFromPojo = new ActFormPojo();
							actVoteChoosePojoList = new ArrayList<ActVoteChooseRespPojo>();

							actFromPojo.setTitle("投票项目");
							actFromPojo.setType("list");
							if (actVotePojo.getVoteLimit() != null)
								actFromPojo.setChooseLimit(actVotePojo.getVoteLimit());
							else// 若后台未配置个人投票上限，则默认设置个人可投票为1
								actFromPojo.setChooseLimit("1");
							if (voteChoosePageList != null && voteChoosePageList.size() > 0) {
								// 算出序号的长度
								int voteChooseCount = voteService.queryChooseCountByVoteId(voteId);
								int orderNumLength = String.valueOf(voteChooseCount).length();
								for (ActVoteChoosePojo pojo : voteChoosePageList) {
									actChoose = new ActVoteChooseRespPojo();

									actChoose.setId(pojo.getId());
									actChoose.setName(pojo.getTitle());
									actChoose.setCount(pojo.getVoteNum());
									// 算出需要补多少位0
									actChoose.setOrderNum(addZeroForNum(String.valueOf(pojo.getSeq()), orderNumLength));
									actChoose.setImg(
											PropertyHolder.getProperty("server.image.url") +File.separator+ pojo.getChooseImg());
									actChoose.setRemark(pojo.getRemark());

									actVoteChoosePojoList.add(actChoose);
								}
							}
							actFromPojo.setVoteChooseList(actVoteChoosePojoList);
							actFromPojoList.add(actFromPojo);
							resp.setActFormListPojo(actFromPojoList);
							resp.setStatus("doing");
							resp.setMessage(actVotePojo.getVoteRule());
						} else {// 个人不能投票
							resp.setStatus("success");
							if (actVotePojo.getVoteWay().equals(ActVotePojo.ONCE)) { // 一个用户只能投票一次
								resp.setMessage("您已参加过本次投票活动");
							} else { // 一个用户每天可投票一次
								resp.setMessage("您今日已投过票，请明日再来哦");
							}
							
						}
					} else {
						resp.setStatus("end");
						resp.setMessage(actVotePojo.getEndMessage());
					}
				} else {
					resp.setStatus("unStart");
					resp.setMessage("活动未开始");
				}
			} else {
				resp.setStatus("end");
				resp.setMessage(actVotePojo.getEndMessage());
			}
			resp.setSubscribeCode("0");
			return resp;
		} catch (Exception e) {
			logger.error(e);
			throw new RemoteServiceException(e);
		}
	}

	/**
	 * Description: 微信投票 <br>
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
	@CacheLock(value = "vote", key="${vote.get(\"voteId\")}", expireTime = 50, timeOut = 50000)
	public ActResp vote(String userId, @Key("vote") Map<String, Object> map) throws Exception {

		 List<String> chooseList = new ArrayList<String>();
		 String voteId = (String) map.get("voteId");
		 chooseList = (List<String>) map.get("chooseList");
		 ActResp resp = new ActResp();
		//判断voteId中是否含有“&”,并去除
		if(voteId.contains("&")){
			voteId = voteId.substring(0, voteId.indexOf("&"));
		}
		 ActVotePojo actVotePojo = voteService.queryVoteById(voteId);
		
		 logger.info("投票录入 ===========");
		
		 
		 //检验该用户是否可以投票
		 List<ActVoteResultChoosePojo> actPojo = userService.queryVoteChooseListByUserIdAndVoteId(userId,
					actVotePojo.getId());
			
		 if( !isEvent(actVotePojo, actPojo) ||
			 !isEndAct(actVotePojo) || 
			 Integer.valueOf(actVotePojo.getVoteLimit())  <  Integer.valueOf(chooseList.size())){ // 个人是否能投票：一次或一日一次;验证活动是否已经结束;验证投票选项是否小于规定数
			 logger.info("该用户不可以投票 ===========");
			 resp.setStatus("end");
			 resp.setMessage(actVotePojo.getEndMessage());
			 return resp;
		 }
		 
		 
		 //投票入库：actVoteResultChoose;更新actVoteChoose的投票数
		 ActVoteResultChoosePojo pojo;
		 ActVoteChoosePojo actVoteChoose;
		 for(String id : chooseList){
			 //更新数据库actVoteChoose的投票数
			 actVoteChoose = new ActVoteChoosePojo();
			 actVoteChoose = voteService.queryVoteChooseByVoteIdAndChooseId(voteId, id);
			 int count = Integer.valueOf(actVoteChoose.getVoteNum());
			 count ++ ;
			 actVoteChoose.setVoteNum(String.valueOf(count));
			 voteService.updateVoteChoose(actVoteChoose, userId);
			 
			 //存入数据库actVoteResultChoose
			 pojo = new ActVoteResultChoosePojo();
			 pojo.setChoose(id);
			 pojo.setCreateTime(new Date());
			 pojo.setVoteId(voteId);
			 pojo.setUserId(userId);
			 voteService.saveVoteResultChoose(pojo);
			 
			 
		 }
		 //将投票信息录入actVoteResult表
		 String sessionId = (String) map.get("sessionId");
			if(!CommonUtil.isEmpty(sessionId))
				map.remove("sessionId");
		 map.remove("voteId");
		 map.remove("chooseList");
		 ActVoteResultPojo actVoteResultPojo = null;
		 for (String key : map.keySet()) {
			 actVoteResultPojo = new ActVoteResultPojo();
			 actVoteResultPojo.setVoteId(voteId);
			 actVoteResultPojo.setUserId(userId);
			 actVoteResultPojo.setParamKey(key);
			 actVoteResultPojo.setParamValue((String)map.get(key));
			 actVoteResultPojo.setCreateTime(new Date());
			 voteService.saveActVoteResultPojo(actVoteResultPojo);
		 }
		 logger.info("投票成功 = ==========");
		 resp.setStatus("success");
		 resp.setMessage(actVotePojo.getSucessMessage());
		 return resp;
	}

	// 判断活动状态，发布状态返回true
	private boolean isActStatus(ActVotePojo actVotePojo) {
		if (actVotePojo.getStatus() != null && actVotePojo.getStatus().equals(CommonContant.AVALIABLE))
			return true;
		else
			return false;
	}

	// 验证是否已投票过，未投票返回true,已投票返回false
	public boolean isEvent(ActVotePojo actVotePojo, List<ActVoteResultChoosePojo> actPojo) throws Exception {

		if (actVotePojo.getVoteWay().equals(ActVotePojo.ONCE)) { // 一个用户只能投票一次
			if (actPojo != null && actPojo.size() > 0)
				return false;
			else
				return true;
		} else { // 一个用户每天可投票一次
					// 判断今天是否投票
			for (ActVoteResultChoosePojo pojo : actPojo) {
				if (isToday(pojo.getCreateTime()))
					return false;
			}
			return true;
		}

	}

	// 活动是否开始
	public boolean isStartAct(ActVotePojo actVotePage) throws Exception {

		long currentTime = System.currentTimeMillis();

		return actVotePage.getStartTime().getTime() <= currentTime;
	}

	// 活动是否结束
	public boolean isEndAct(ActVotePojo actVotePage) throws Exception {

		long currentTime = System.currentTimeMillis();

		return actVotePage.getEndTime().getTime() >= currentTime;
	}

	public boolean isActUserNumMax(ActVotePojo actVotePojo, String userId) throws Exception {
		List<ActVoteResultChoosePojo> actPojo = userService.queryVoteChooseListByUserIdAndVoteId(userId,
				actVotePojo.getId());

		if (actVotePojo.getVoteLimit() == "0") {// 设置每人投票数为0时
			return false;
		} else if (actPojo == null || (actPojo.size() < Integer.parseInt(actVotePojo.getVoteLimit())))
			return true;
		else
			return false;
	}

	// 判断是否是当天
	public static boolean isToday(Date date) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		if (fmt.format(date).toString().equals(fmt.format(new Date()).toString())) {// 格式化为相同格式
			return true;
		} else {
			return false;
		}
	}

	public static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				// sb.append(str).append("0");//右补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}
}
