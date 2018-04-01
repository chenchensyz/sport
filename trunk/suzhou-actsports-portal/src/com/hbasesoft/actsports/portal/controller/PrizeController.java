package com.hbasesoft.actsports.portal.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hbasesoft.actsports.portal.bean.ActResp;
import com.hbasesoft.actsports.portal.bean.ShowPojo;
import com.hbasesoft.actsports.portal.bean.prize.PrizeResult;
import com.hbasesoft.actsports.portal.constant.SessionKeyDef;
import com.hbasesoft.actsports.portal.service.CommonService;
import com.hbasesoft.actsports.portal.service.PrizeService;
import com.hbasesoft.actsports.portal.util.LeadUserUtil;
import com.hbasesoft.framework.common.FrameworkException;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.logger.LoggerUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * <Description> 抽奖<br>
 * 
 * @author zhasiwei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年1月4日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.actsports.portal.controller <br>
 */
@Api(value = "抽奖控制器", description = "处理抽奖")
@RestController
public class PrizeController extends BaseController {

	@Resource(name = "PrizeRemoteService")
	private PrizeService prizeService;

	@Resource
	private CommonService commonService;

	/**
	 * Description: 获取抽奖活动状态<br>
	 * 
	 * @author zhasiwei<br>
	 * @taskId <br>
	 * @param subsId
	 * @return
	 * @throws FrameworkException
	 *             <br>
	 */
	@ApiOperation(value = "获取抽奖活动状态", notes = "传入抽奖id(prizeId)，获取抽奖抽奖状态,返回抽奖状态。", response = ActResp.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "获取页面成功", response = ActResp.class),
			@ApiResponse(code = 500, message = "获取页面失败，出现异常", response = ActResp.class) })
	@RequestMapping(value = "/prize/getPrizePage", method = { RequestMethod.POST, RequestMethod.GET })
	public ActResp getPrizePage(@ApiParam(value = "抽奖ID") @RequestBody String prizeJson)
			throws FrameworkException, IOException {
		logger.info("<========== 微信抽奖-获取抽奖页面");

		try {
			ActResp resp = new ActResp();

			String openId = (String) getAttribute(SessionKeyDef.SESSION_OPEN_ID);
			String subscribe = LeadUserUtil.checkSubscribe(openId);
			if (CommonUtil.isEmpty(subscribe) || "0".equals(subscribe)) {
				LoggerUtil.info("getPrizePage come in");
				resp.setSubscribeCode("1");
				ShowPojo showPojo = commonService.findUniqueByProperty(ShowPojo.class, ShowPojo.NAME,
						ShowPojo.NAME_VALUE);
				if (!CommonUtil.isNull(showPojo)) {
					resp.setSubscribeUrl(showPojo.getValue());
				}
				return resp;
			}

			JSONObject jsonObject = JSONObject.parseObject(prizeJson);
			Map<String, Object> map = (Map<String, Object>) jsonObject;
			String prizeId = (String) map.get("prizeId");
			// 判断entryId中是否含有“&”,并去除
			if (prizeId.contains("&")) {
				prizeId = prizeId.substring(0, prizeId.indexOf("&"));
			}

			logger.info("prizeId = [{0}]", prizeId);

			String userId = getUserId();
			resp = prizeService.getPrizePage(userId, prizeId);
			return resp;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

	/**
	 * Description: 微信抽奖<br>
	 * 
	 * @author zhasiwei<br>
	 * @taskId <br>
	 * @param subsId
	 * @return
	 * @throws FrameworkException
	 *             <br>
	 * @throws IOException
	 */
	@ApiOperation(value = "抽奖", notes = "传入抽奖id(prizeId)，开始抽奖。", response = ActResp.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "抽奖成功", response = ActResp.class),
			@ApiResponse(code = 500, message = "抽奖失败，出现异常", response = ActResp.class) })
	@RequestMapping(value = "/prize/index", method = { RequestMethod.POST, RequestMethod.GET })
	public ActResp prize(@ApiParam(value = "抽奖") @RequestBody String prizeJSON) throws FrameworkException, IOException {
		logger.info("<========== 微信抽奖");
		try {
			JSONObject jsonObject = JSONObject.parseObject(prizeJSON);
			Map<String, Object> map = (Map<String, Object>) jsonObject;
			String userId = getUserId();
			ActResp resp = new ActResp();
			resp = prizeService.prize(userId, map);
			return resp;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

	/**
	 * Description: 微信抽奖录入<br>
	 * 
	 * @author zhasiwei<br>
	 * @taskId <br>
	 * @param subsId
	 * @return
	 * @throws FrameworkException
	 *             <br>
	 * @throws IOException
	 */
	@ApiOperation(value = "抽奖", notes = "传入抽奖id(prizeId)，录入信息，录入数据库。", response = ActResp.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "抽奖成功", response = ActResp.class),
			@ApiResponse(code = 500, message = "抽奖失败，出现异常", response = ActResp.class) })
	@RequestMapping(value = "/prize/savePrizeResult", method = { RequestMethod.POST, RequestMethod.GET })
	public ActResp savePrizeResult(@ApiParam(value = "抽奖") @RequestBody String prizeJSON)
			throws FrameworkException, IOException {
		logger.info("<========== 微信抽奖");

		try {
			JSONObject jsonObject = JSONObject.parseObject(prizeJSON);
			Map<String, Object> map = (Map<String, Object>) jsonObject;
			String userId = getUserId();
			ActResp resp = new ActResp();
			resp = prizeService.savePrizeResult(userId, map);
			return resp;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

	/**
	 * 查询中奖结果
	 * 
	 * @param prizeJSON
	 * @return
	 * @throws FrameworkException
	 * @throws IOException
	 */
	@ApiOperation(value = "查询抽奖结果", notes = "传入抽奖id(prizeId)，开始查询抽奖结果。", response = ActResp.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "查询成功", response = ActResp.class),
			@ApiResponse(code = 500, message = "查询失败，出现异常", response = ActResp.class) })
	@RequestMapping(value = "/prize/queryPrizeList", method = { RequestMethod.POST, RequestMethod.GET })
	public PrizeResult queryPrizeList(@ApiParam(value = "查询抽奖结果") @RequestBody String prizeJSON)
			throws FrameworkException, IOException {
		logger.info("<========== 查询微信抽奖结果");
		try {
			JSONObject jsonObject = JSONObject.parseObject(prizeJSON);
			Map<String, Object> map = (Map<String, Object>) jsonObject;
			String userId = getUserId();
			PrizeResult resp = new PrizeResult();
			resp = prizeService.queryPrizeList(userId, map);
			return resp;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}
}