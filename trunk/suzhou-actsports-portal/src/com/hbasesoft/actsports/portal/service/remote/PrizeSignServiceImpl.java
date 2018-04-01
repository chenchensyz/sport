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
import com.hbasesoft.actsports.portal.service.EntryInfoService;
import com.hbasesoft.actsports.portal.service.LuckDrawService;
import com.hbasesoft.actsports.portal.service.PrizeInfoService;
import com.hbasesoft.actsports.portal.service.PrizeService;
import com.hbasesoft.actsports.portal.service.PrizeSignService;
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
@Service("PrizeSignService")
public class PrizeSignServiceImpl implements PrizeSignService {

	@Resource
	private PrizeInfoService prizeService;

	@Resource
	private UserService userService;

	@Resource
	private EntryInfoService entryService;

	
	@Resource
	private LuckDrawService luckDrawService;

	private static Logger logger = new Logger(PrizeSignServiceImpl.class);

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
	@CacheLock(value = "PRIZE", key = "${prize.get(\"prizeId\")}", expireTime = 50, timeOut = 50000)
	public ActResp prize(String userId, @Key("prize") Map<String, Object> map) throws Exception {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

		String entryId = (String) map.get("entryId");
		// 判断prizeId中是否含有“&”,并去除
		if (entryId.contains("&")) {
			entryId = entryId.substring(0, entryId.indexOf("&"));
		}
		List<ActFormPojo> actFormPojoList = new ArrayList<ActFormPojo>();

		ActResp resp = new ActResp();
		ActEntryPojo actEntryPojo = entryService.queryEntryById(entryId);
		
		ActPrizeResultChoosePojo aprcPojo = new ActPrizeResultChoosePojo();
		aprcPojo.setCreateTime(new Date());
		aprcPojo.setPrizeId(entryId);
		aprcPojo.setUserId(userId);
		ActPrizePojo actPrizePojo = prizeService.queryPrizeById(entryId);
		List<ActPrizeChoosePojo> choosePojo = prizeService.queryPrizeChooseByPrizeId(entryId);
		int[] gifs = new int[choosePojo.size()];
		int[] gifsTotal = new int[choosePojo.size()];
		if (choosePojo != null && choosePojo.size() > 0) {
			for (int i = 0; i < choosePojo.size(); i++) {
				if (CommonUtil.isNotEmpty(choosePojo.get(i).getRemainNum())) {
					gifs[i] = Integer.valueOf(choosePojo.get(i).getRemainNum());
					gifsTotal[i] = Integer.valueOf(choosePojo.get(i).getCountNum());
				} else {
					gifs[i] = Integer.valueOf(choosePojo.get(i).getCountNum());
					gifsTotal[i] = Integer.valueOf(choosePojo.get(i).getCountNum());
				}
			}
			double probability = Double.parseDouble(actPrizePojo.getProbability());

			int gif = luckDrawService.luckDraw(entryId, userId, gifs, gifsTotal, probability);

			if (gif > 0) {// 中奖了
				logger.info("中奖了,奖品为[{0}],剩余数量为[{1}]============", choosePojo.get(gif - 1).getTitle(), gifs[gif - 1]);
				resp.setStatus("win");
				resp.setMessage(choosePojo.get(gif - 1).getTitle() + " : " + choosePojo.get(gif - 1).getPrizeType());

				// 更新奖品表
				choosePojo.get(gif - 1).setRemainNum(String.valueOf(gifs[gif - 1]));
				prizeService.updatePrizeChoose(choosePojo.get(gif - 1));

				ActFormPojo actFormPojo = null;
				List<String> rule = null;
				List<ActPrizeParamsPojo> paramsList = prizeService.queryActPrizeParamsByPrizeId(entryId);

				if (paramsList != null && paramsList.size() > 0) {
					for (ActPrizeParamsPojo pojo : paramsList) {

						actFormPojo = new ActFormPojo();
						rule = new ArrayList<String>();
						if (pojo.getRequired() != null && !pojo.getRequired().equals(CommonContant.UN_REQUIRED)) {
							rule.add(pojo.getRequired());
						}
						// 如果规则为text，则Rule不传内容
						if (!pojo.getRule().equals("text"))
							rule.add(pojo.getRule());
						rule.add(pojo.getMinlength() + "-" + pojo.getMaxlength());
						if (CommonUtil.isEmpty(pojo.getTitle())) {
							break;
						}
						actFormPojo.setTitle(pojo.getTitle());
						actFormPojo.setType(pojo.getType());
						actFormPojo.setRules(rule);
						actFormPojoList.add(actFormPojo);
					}
				}
				resp.setActFormListPojo(actFormPojoList);

				// 找到奖品编号
				List<ActPrizeSnPojo> actSnPojoList = prizeService.queryActPrizeSnByStatusAndType(
						choosePojo.get(gif - 1).getPrizeType(), ActPrizeSnPojo.NOT_RECEIVE);
				if (CommonUtil.isNotEmpty(actSnPojoList)) {
					// 默认返回第一个
					ActPrizeSnPojo snPojo = actSnPojoList.get(0);
					snPojo.setStatus(ActPrizeSnPojo.RECEIVED);
					snPojo.setUpdateDate(new Date());
					prizeService.updateEntity(snPojo);
					resp.setTitle(choosePojo.get(gif - 1).getTitle());
					resp.setPrizeType(choosePojo.get(gif - 1).getPrizeType());

					// 存入result表中
					ActPrizeResultPojo actPrizeResultPojo = new ActPrizeResultPojo();
					actPrizeResultPojo.setPrizeId(entryId);
					actPrizeResultPojo.setUserId(userId);
					actPrizeResultPojo.setParamKey("编号");
					actPrizeResultPojo.setParamValue(snPojo.getSn());
					actPrizeResultPojo.setCreateTime(new Date());
					prizeService.savePrizeResult(actPrizeResultPojo);
					aprcPojo.setSn(snPojo.getSn());
					aprcPojo.setChoose(choosePojo.get(gif - 1).getId());
				}
			}
		} else {
			// 无奖品
			logger.info("奖品池为空============");
			resp.setStatus("failure");
		}

		// 插入记录表
		prizeService.savePrizeResultChoose(aprcPojo);
		return resp;

	}

	@Override
	@Transactional
	public ActResp savePrizeResult(String userId, Map<String, Object> map) throws Exception {

		ActResp resp = new ActResp();

		String prizeId = (String) map.get("prizeId");
		// 判断prizeId中是否含有“&”,并去除
		if (prizeId.contains("&")) {
			prizeId = prizeId.substring(0, prizeId.indexOf("&"));
		}
		// 将征集的用户信息录入数据库
		map.remove("prizeId");
		ActPrizeResultPojo actPrizeResultPojo = null;
		for (String key : map.keySet()) {
			actPrizeResultPojo = new ActPrizeResultPojo();
			actPrizeResultPojo.setPrizeId(prizeId);
			actPrizeResultPojo.setUserId(userId);
			actPrizeResultPojo.setParamKey(key);
			actPrizeResultPojo.setParamValue((String) map.get(key));
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
		// 判断prizeId中是否含有“&”,并去除
		if (prizeId.contains("&")) {
			prizeId = prizeId.substring(0, prizeId.indexOf("&"));
		}

		// 根据prizeId和userId，查询中奖结果
		List<PrizeChooseVo> prizeChooseVoList = new ArrayList<>();
		List<ActPrizeResultChoosePojo> resultChoosePojoList = prizeService.queryPrizeChooseByUserIdAndPrizeId(userId,
				prizeId);
		String serverImagePath = PropertyHolder.getProperty("server.image.url");
		String queryListPath = PropertyHolder.getProperty("server.query.list");
		if (CommonUtil.isNotEmpty(resultChoosePojoList)) {
			PrizeChooseVo prizeChooseVo;
			ActPrizeChoosePojo choose;
			ActPrizeSnPojo sn;
			for (ActPrizeResultChoosePojo pojo : resultChoosePojoList) {
				prizeChooseVo = new PrizeChooseVo();
				choose = prizeService.get(ActPrizeChoosePojo.class, pojo.getChoose());
				sn = prizeService.findUniqueByProperty(ActPrizeSnPojo.class, ActPrizeSnPojo.SN, pojo.getSn());
				prizeChooseVo.setChooseId(pojo.getChoose());
				prizeChooseVo.setPrizeType(choose.getPrizeType());
				prizeChooseVo.setTitle(choose.getTitle());
				prizeChooseVo.setUrl(serverImagePath + queryListPath + "&sn=" + pojo.getSn());
				prizeChooseVo.setState(sn.getStatus());
				prizeChooseVoList.add(prizeChooseVo);
			}
		}

		// 获取兑奖说明
		ActPrizePojo actPrizePojo = prizeService.queryPrizeById(prizeId);

		PrizeResult resp = new PrizeResult();
		resp.setPrizeChooseList(prizeChooseVoList);
		resp.setReceiveMsg(actPrizePojo.getReceiveMsg());

		return resp;
	}

}
