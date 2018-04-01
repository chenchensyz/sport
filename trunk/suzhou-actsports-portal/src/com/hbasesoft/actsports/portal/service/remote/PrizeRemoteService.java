/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service.remote;

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
import com.hbasesoft.actsports.portal.bean.entry.ActEntryPojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeChoosePojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeParamsPojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizePojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeResultChoosePojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeResultCountPojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeResultPojo;
import com.hbasesoft.actsports.portal.bean.prize.ActPrizeSnPojo;
import com.hbasesoft.actsports.portal.bean.prize.PrizeResult;
import com.hbasesoft.actsports.portal.biz.vo.PrizeChooseVo;
import com.hbasesoft.actsports.portal.constant.CommonContant;
import com.hbasesoft.actsports.portal.service.LuckDrawService;
import com.hbasesoft.actsports.portal.service.PrizeInfoService;
import com.hbasesoft.actsports.portal.service.PrizeService;
import com.hbasesoft.actsports.portal.service.UserService;
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
 * @CreateDate 2017年1月4日 <br>
 * @since V0.1-SNAPSHOT<br>
 * @see com.hbasesoft.actSports.portal.service.Impl <br>
 */
@Service("PrizeRemoteService")
public class PrizeRemoteService implements PrizeService {

	@Resource
	private PrizeInfoService prizeService;
	
	@Resource
	private UserService userService;

	@Resource
	private LuckDrawService luckDrawService;
	
	private static Logger logger = new Logger(PrizeRemoteService.class);

	/**
	 * Description: 获取抽奖状态 <br>
	 * 
	 * @author 查思玮<br>
	 * @taskId <br>
	 * @param 
	 * @return
	 * @throws FrameworkException
	 *             <br>
	 */
	@Override
	@Transactional
	public ActResp getPrizePage(String userId, String prizeId) throws FrameworkException {
		try {
			
			ActResp resp = new ActResp();
			
			ActPrizePojo actPrizePojo = prizeService.queryPrizeById(prizeId);
			
			if (actPrizePojo == null ) {
				resp.setStatus("end");
				resp.setMessage("活动错误");
				return resp;
			}
			logger.info("getSignUpPageService = ==========");
			// 验证是否满足条件:是否过期，是否能抽奖
			if (isActStatus(actPrizePojo)) { // 活动是否发布
				if(isStartAct(actPrizePojo)){ // 活动是否开始
					if(isEndAct(actPrizePojo)){	// 活动是否结束
						logger.info("未过期 = ==========");
						
							resp.setMessage("打开页面成功！");
							resp.setStatus("doing");
					}else{
						resp.setStatus("end");
						resp.setMessage(actPrizePojo.getEndMessage());
					}
				}else{
					resp.setStatus("unStart");
					resp.setMessage("投票未开始");
				}
			}else{
				resp.setStatus("end");
				resp.setMessage(actPrizePojo.getEndMessage());
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
	@Override
	@Transactional
	@CacheLock(value = "PRIZE", key="${prize.get(\"prizeId\")}", expireTime = 50, timeOut = 50000)
	public ActResp prize(String userId, @Key("prize") Map<String, Object> map) throws Exception {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

		String prizeId = (String) map.get("prizeId");
		//判断prizeId中是否含有“&”,并去除
		if(prizeId.contains("&")){
			prizeId = prizeId.substring(0, prizeId.indexOf("&"));
		}
		List<ActFormPojo> actFormPojoList = new ArrayList<ActFormPojo>();
		
		ActResp resp = new ActResp();
		boolean flag = false;
		boolean isAgin = true;
		ActPrizePojo actPrizePojo = prizeService.queryPrizeById(prizeId);
		logger.info("开始抽奖，检验是否可以抽奖 ===========");
		//检验该用户是否达到抽奖上限
		int count = 0;
		ActPrizeResultCountPojo countPojo = prizeService.queryPrizeResultCountByUserIdAndPrizeId(userId, actPrizePojo.getId());
		if(CommonUtil.isNull(countPojo)){
			countPojo = new ActPrizeResultCountPojo();
			countPojo.setCount("0");
			countPojo.setPrizeId(prizeId);
			countPojo.setUserId(userId);
			prizeService.savePrizeResultCount(countPojo);
		}else{
			count = Integer.valueOf(countPojo.getCount());
		}
		
		//判断总机会是否用完
		if(count >= Integer.parseInt(actPrizePojo.getPrizeCount())){
			logger.info("该用户抽奖达到上限 ===========");
			resp.setStatus("done");
			resp.setMessage("您的 "+actPrizePojo.getPrizeCount()+" 次机会已用完，请关注我们下次的活动");
			resp.setAgain(false);
			return resp;
		}else{
			//得到该用户的投票记录，判断今日是否用完机会
			List<ActPrizeResultChoosePojo> aprcPojoList = prizeService.queryPrizeResultChooseByUserIdAndPrizeId(userId, prizeId);
			List<ActPrizeResultChoosePojo> aprcPojoListResult = new ArrayList<>();
			for(ActPrizeResultChoosePojo pojo : aprcPojoList){//获得今日的抽奖列表
				if (fmt.format(pojo.getCreateTime()).toString().equals(fmt.format(new Date()).toString())) {// 格式化为相同格式
//					aprcPojoList.remove(pojo);
					aprcPojoListResult.add(pojo);
				}
			}
			if(aprcPojoListResult != null && aprcPojoListResult.size()>0){
				if(aprcPojoListResult.size() < Integer.parseInt(actPrizePojo.getPrizeWay())){
					logger.info("满足抽奖条件============");
					flag = true;
				}
			}else{
				flag = true;
			}
			//判断是否有第二次
			if(count >= (Integer.parseInt(actPrizePojo.getPrizeCount())-1)){
				isAgin = false;
			}
		}
		if(flag){
			logger.info("开始抽奖============");
			
			ActPrizeResultChoosePojo aprcPojo = new ActPrizeResultChoosePojo();
			aprcPojo.setCreateTime(new Date());
			aprcPojo.setPrizeId(prizeId);
			aprcPojo.setUserId(userId);
			
			// 判断今日出奖数量
            List<ActPrizeResultChoosePojo> aprcPojoList = prizeService.queryPrizeResultChooseByUserIdAndPrizeId(null, prizeId);
            List<ActPrizeResultChoosePojo> aprcPojoListResult = new ArrayList<>();
            if(CommonUtil.isNotEmpty(aprcPojoList)){
                for(ActPrizeResultChoosePojo pojo : aprcPojoList){//获得今日的抽奖列表
                    if (fmt.format(pojo.getCreateTime()).toString().equals(fmt.format(new Date()).toString())) {// 格式化为相同格式
                        aprcPojoListResult.add(pojo);
                    }
                }
            }

			boolean canStart = false;
			if(aprcPojoListResult != null && aprcPojoListResult.size() >0){
				if(aprcPojoListResult.size() >= Integer.parseInt(actPrizePojo.getPrizeHit())){//今日出奖达到上限，不会不出奖
					resp.setStatus("failure");
					canStart = false;
				}else{
					canStart = true;
				}
			}else{
				canStart = true;
			}
			if(canStart){
				List<ActPrizeChoosePojo> choosePojo = prizeService.queryPrizeChooseByPrizeId(prizeId);
				int[] gifs = new int[choosePojo.size()];
				int[] gifsTotal = new int[choosePojo.size()];
				if(choosePojo !=null && choosePojo.size()>0){
					for(int i=0;i<choosePojo.size();i++){
						if(CommonUtil.isNotEmpty(choosePojo.get(i).getRemainNum())){
							gifs[i] = Integer.valueOf(choosePojo.get(i).getRemainNum());
							gifsTotal[i] = Integer.valueOf(choosePojo.get(i).getCountNum());
						}else{
							gifs[i] = Integer.valueOf(choosePojo.get(i).getCountNum());
							gifsTotal[i] = Integer.valueOf(choosePojo.get(i).getCountNum());
						}
					}
					double probability = Double.parseDouble(actPrizePojo.getProbability());
					
					int gif = luckDrawService.luckDraw(prizeId, userId, gifs, gifsTotal, probability);
					
					if (gif > 0) {// 中奖了
						logger.info("中奖了,奖品为[{0}],剩余数量为[{1}]============",choosePojo.get(gif-1).getTitle(),gifs[gif - 1]);
						resp.setStatus("win");
						resp.setMessage(choosePojo.get(gif-1).getTitle() + " : " + choosePojo.get(gif-1).getPrizeType());
						
						
						
						// 更新奖品表
						choosePojo.get(gif-1).setRemainNum(String.valueOf(gifs[gif - 1]));
						prizeService.updatePrizeChoose(choosePojo.get(gif-1));
						
						ActFormPojo actFormPojo = null;
						List<String> rule = null;
						List<ActPrizeParamsPojo> paramsList = prizeService.queryActPrizeParamsByPrizeId(prizeId);
						
						if (paramsList != null && paramsList.size() > 0) {
							for (ActPrizeParamsPojo pojo : paramsList) {
										
								actFormPojo = new ActFormPojo();
								rule = new ArrayList<String>();
								if(pojo.getRequired() != null && !pojo.getRequired().equals(CommonContant.UN_REQUIRED)){
									rule.add(pojo.getRequired());
								}
								//如果规则为text，则Rule不传内容
								if(!pojo.getRule().equals("text"))
									rule.add(pojo.getRule());
								rule.add(pojo.getMinlength() + "-" + pojo.getMaxlength());
								if(CommonUtil.isEmpty(pojo.getTitle())){
									break;
								}
								actFormPojo.setTitle(pojo.getTitle());
								actFormPojo.setType(pojo.getType());
								actFormPojo.setRules(rule);
								actFormPojoList.add(actFormPojo);
							}
						}
						resp.setActFormListPojo(actFormPojoList);
						
						//找到奖品编号
						List<ActPrizeSnPojo> actSnPojoList = prizeService.queryActPrizeSnByStatusAndType(choosePojo.get(gif-1).getPrizeType(), ActPrizeSnPojo.NOT_RECEIVE);
						if(CommonUtil.isNotEmpty(actSnPojoList)){
							//默认返回第一个
							ActPrizeSnPojo snPojo = actSnPojoList.get(0);
							snPojo.setStatus(ActPrizeSnPojo.RECEIVED);
							snPojo.setUpdateDate(new Date());
							prizeService.updateEntity(snPojo);
							
							resp.setTitle(choosePojo.get(gif-1).getTitle());
							resp.setPrizeType(choosePojo.get(gif-1).getPrizeType());
							
							//存入result表中
							ActPrizeResultPojo actPrizeResultPojo = new ActPrizeResultPojo();
							actPrizeResultPojo.setPrizeId(prizeId);
							actPrizeResultPojo.setUserId(userId);	
							actPrizeResultPojo.setParamKey("编号");
							actPrizeResultPojo.setParamValue(snPojo.getSn());
							actPrizeResultPojo.setCreateTime(new Date());
							prizeService.savePrizeResult(actPrizeResultPojo);
							
							aprcPojo.setSn(snPojo.getSn());
							aprcPojo.setChoose(choosePojo.get(gif-1).getId());
						}
					}else{//没中奖
			        	resp.setStatus("failure");
			        }
				}else{
					//无奖品，没中奖
					logger.info("奖品池为空============");
					resp.setStatus("failure");
				}
			}
			// 插入记录表
			prizeService.savePrizeResultChoose(aprcPojo);
			// 更新计数器
			count++;
			countPojo.setCount(String.valueOf(count));
			prizeService.updatePrizeResultCount(countPojo);
		}else{// 今日已达到上限
			logger.info("该用户抽奖达到上限 ===========");
			resp.setStatus("done");
			resp.setMessage("您今日的 "+actPrizePojo.getPrizeWay()+" 次机会已用完，请明日再来");
		}
		resp.setAgain(isAgin);
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
	private boolean isActStatus(ActPrizePojo actPrizePojo) {
		if(actPrizePojo.getStatus() != null && actPrizePojo.getStatus().equals(CommonContant.AVALIABLE))
			return true;
		else
			return false;
	}
	
	
	//活动是否开始
	public boolean isStartAct(ActPrizePojo actPrizePojo) throws Exception {
		
		long currentTime = System.currentTimeMillis();
		
		return actPrizePojo.getStartTime().getTime() <= currentTime;
	}
	
	//活动是否结束
	public boolean isEndAct(ActPrizePojo actPrizePojo) throws Exception {
		
		long currentTime = System.currentTimeMillis();
		
		return actPrizePojo.getEndTime().getTime() >= currentTime;
	}


	@Override
	@Transactional
	public ActResp savePrizeResult(String userId, Map<String, Object> map) throws Exception {
		
		ActResp resp = new ActResp();
		
		String prizeId = (String) map.get("prizeId");
		//判断prizeId中是否含有“&”,并去除
		if(prizeId.contains("&")){
			prizeId = prizeId.substring(0, prizeId.indexOf("&"));
		}
		//将征集的用户信息录入数据库
		map.remove("prizeId");
		ActPrizeResultPojo actPrizeResultPojo = null;
		for (String key : map.keySet()) {  	
			actPrizeResultPojo = new ActPrizeResultPojo();
			actPrizeResultPojo.setPrizeId(prizeId);
			actPrizeResultPojo.setUserId(userId);	
			actPrizeResultPojo.setParamKey(key);
			actPrizeResultPojo.setParamValue((String)map.get(key));
			actPrizeResultPojo.setCreateTime(new Date());
			prizeService.savePrizeResult(actPrizeResultPojo);
	    } 
				
		logger.info("中奖用户信息录入成功 ===========");
		resp.setStatus("success");
		return resp;
		
	}


	@Override
	@Transactional
	public PrizeResult queryPrizeList(String userId, Map<String, Object> map) throws Exception {
		String prizeId = (String) map.get("prizeId");
		//判断prizeId中是否含有“&”,并去除
		if(prizeId.contains("&")){
			prizeId = prizeId.substring(0, prizeId.indexOf("&"));
		}
		
		//根据prizeId和userId，查询中奖结果 
		List<PrizeChooseVo> prizeChooseVoList = new ArrayList<>();
		List<ActPrizeResultChoosePojo> resultChoosePojoList = prizeService.queryPrizeChooseByUserIdAndPrizeId(userId, prizeId);
		String serverImagePath = PropertyHolder.getProperty("server.image.url");
		String queryListPath = PropertyHolder.getProperty("server.query.list");
		if(CommonUtil.isNotEmpty(resultChoosePojoList)){
			PrizeChooseVo prizeChooseVo;
			ActPrizeChoosePojo choose;
			ActPrizeSnPojo sn;
			for(ActPrizeResultChoosePojo pojo:resultChoosePojoList){
				prizeChooseVo = new PrizeChooseVo();
				choose = prizeService.get(ActPrizeChoosePojo.class, pojo.getChoose());
				sn = prizeService.findUniqueByProperty(ActPrizeSnPojo.class, ActPrizeSnPojo.SN, pojo.getSn());
				prizeChooseVo.setChooseId(pojo.getChoose());
				prizeChooseVo.setPrizeType(choose.getPrizeType());
				prizeChooseVo.setTitle(choose.getTitle());
				prizeChooseVo.setUrl(serverImagePath + queryListPath +"&sn=" + pojo.getSn());
				prizeChooseVo.setState(sn.getStatus());
				prizeChooseVoList.add(prizeChooseVo);
			}
		}
		
		//获取兑奖说明
		ActPrizePojo actPrizePojo = prizeService.queryPrizeById(prizeId);
		
		PrizeResult resp = new PrizeResult();
		resp.setPrizeChooseList(prizeChooseVoList);
		resp.setReceiveMsg(actPrizePojo.getReceiveMsg());

		return resp;
	}

}
