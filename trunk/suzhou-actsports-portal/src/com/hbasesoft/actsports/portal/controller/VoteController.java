package com.hbasesoft.actsports.portal.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hbasesoft.actsports.portal.bean.ActResp;
import com.hbasesoft.actsports.portal.bean.ShowPojo;
import com.hbasesoft.actsports.portal.constant.SessionKeyDef;
import com.hbasesoft.actsports.portal.service.CommonService;
import com.hbasesoft.actsports.portal.service.VoteService;
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
 * <Description> 微信报名<br>
 * 
 * @author zhasiwei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年12月08日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.actsports.portal.controller <br>
 */
@Api(value = "投票控制器",description = "处理投票")
@RestController
public class VoteController extends BaseController {

	@Resource(name = "VoteRemoteService")
	private VoteService voteService;
	
	
	@Resource
    private CommonService commonService;
	
	/**
	 * Description: 获取投票页面<br>
	 * 
	 * @author zhasiwei<br>
	 * @taskId <br>
	 * @param subsId
	 * @return
	 * @throws FrameworkException
	 *             <br>
	 */
    @ApiOperation(value = "获取投票页面", notes = "传入投票id(voteId)和页码数(pageNum)，获取用户状态,若满足条件则提供页面的显示内容。",response = ActResp.class)
    @ApiResponses(value = {
    	@ApiResponse(code = 200, message = "获取页面成功", response = ActResp.class),
    	@ApiResponse(code = 500, message = "获取页面失败，出现异常", response = ActResp.class) })
	@RequestMapping(value = "/vote/getVotePage", method = {RequestMethod.POST,RequestMethod.GET})
	public ActResp getSignUpPage(@ApiParam(value = "报名活动ID") @RequestBody String getVotePageJson) throws FrameworkException {
		logger.info("<========== 微信投票-获取投票页面");
		try {
		    
		    ActResp resp = new ActResp();
		    String openId = (String)getAttribute(SessionKeyDef.SESSION_OPEN_ID);
            String subscribe = LeadUserUtil.checkSubscribe(openId);
            if(CommonUtil.isEmpty(subscribe) || "0".equals(subscribe)){
                LoggerUtil.info("getVotePage come in");
                resp.setSubscribeCode("1");
                ShowPojo showPojo = commonService.findUniqueByProperty(ShowPojo.class, ShowPojo.NAME, ShowPojo.NAME_VALUE);
                if(!CommonUtil.isNull(showPojo)){
                    resp.setSubscribeUrl(showPojo.getValue());
                }
                return resp;
            }
		    
			JSONObject  jsonObject = JSONObject.parseObject(getVotePageJson); 
			Map<String, Object> map = (Map<String, Object>)jsonObject; 
			String voteId = (String) map.get("voteId");
			Integer pageNum = (Integer) map.get("pageNum");
			Integer pageSize = (Integer) map.get("pageSize");
			//判断voteId中是否含有“&”,并去除
			if(voteId.contains("&")){
				voteId = voteId.substring(0, voteId.indexOf("&"));
			}
			logger.info("voteId = [{0}], pageNum = [{1}]", voteId, pageNum);
			
			String userId = getUserId();
			
			resp = voteService.getVotePage(userId, voteId, pageNum, pageSize);
			return resp;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}
    
    /**
	 * Description: 微信投票<br>
	 * 
	 * @author zhasiwei<br>
	 * @taskId <br>
	 * @param subsId
	 * @return
	 * @throws FrameworkException
	 *             <br>
	 */
    @ApiOperation(value = "微信投票", notes = "传入投票id(voteId)和投票信息，获取对应的活动状态,若满足条件则投票成功。",response = ActResp.class)
    @ApiResponses(value = {
    	@ApiResponse(code = 200, message = "投票成功", response = ActResp.class),
    	@ApiResponse(code = 500, message = "投票失败，出现异常", response = ActResp.class) })
	@RequestMapping(value = "/vote/index", method = {RequestMethod.POST,RequestMethod.GET})
	public ActResp vote(@ApiParam(value = "报名活动ID") @RequestBody String voteJson) throws FrameworkException {
		logger.info("<========== 微信投票-投票");
		try {
			logger.info("voteJson = [{0}]", voteJson);
			JSONObject  jsonObject = JSONObject.parseObject(voteJson); 
			Map<String, Object> map = (Map<String, Object>)jsonObject; 
			String userId = getUserId();
			ActResp resp = new ActResp();
			resp = voteService.vote(userId, map);
			return resp;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}
    
}
