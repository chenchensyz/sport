package com.hbasesoft.actsports.portal.weixin;

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
import com.hbasesoft.actsports.portal.constant.SessionKeyDef;
import com.hbasesoft.actsports.portal.controller.BaseController;
import com.hbasesoft.actsports.portal.service.CommonService;
import com.hbasesoft.actsports.portal.service.SignUpService;
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
public class SignUpController extends BaseController {

	@Resource(name = "SignUpRemoteService")
	private SignUpService signUpService;

	@Resource
	private CommonService commonService;
	
	/**
	 * Description: 获取报名页面<br>
	 * 
	 * @author zhasiwei<br>
	 * @taskId <br>
	 * @param subsId
	 * @return
	 * @throws FrameworkException
	 *             <br>
	 */
    @ApiOperation(value = "获取报名页面", notes = "传入活动id(entryId)，获取用户状态,若满足条件则提供页面的显示内容。",response = ActResp.class)
    @ApiResponses(value = {
    	@ApiResponse(code = 200, message = "获取页面成功", response = ActResp.class),
    	@ApiResponse(code = 500, message = "获取页面失败，出现异常", response = ActResp.class) })
	@RequestMapping(value = "/signUp/getSignUpPage", method = {RequestMethod.POST,RequestMethod.GET})
	public ActResp getSignUpPage(@ApiParam(value = "报名活动ID") @RequestBody String entryId,HttpServletResponse response) throws FrameworkException {
		logger.info("<========== 微信报名-获取报名页面");
		try {
		    ActResp resp = new ActResp();
		    
		    String openId = (String)getAttribute(SessionKeyDef.SESSION_OPEN_ID);
		    String subscribe = LeadUserUtil.checkSubscribe(openId);
            if(CommonUtil.isEmpty(subscribe) || "0".equals(subscribe)){
                LoggerUtil.info("getSignUpPage come in");
                resp.setSubscribeCode("1");
                ShowPojo showPojo = commonService.findUniqueByProperty(ShowPojo.class, ShowPojo.NAME, ShowPojo.NAME_VALUE);
                if(!CommonUtil.isNull(showPojo)){
                    resp.setSubscribeUrl(showPojo.getValue());
                }
                return resp;
            }
		    
			JSONObject  jsonObject = JSONObject.parseObject(entryId); 
			Map<String, Object> map = (Map<String, Object>)jsonObject; 
			entryId = (String) map.get("entryId");
			//判断entryId中是否含有“&”,并去除
			if(entryId.contains("&")){
				entryId = entryId.substring(0, entryId.indexOf("&"));
			}
			logger.info("entryId = [{0}]", entryId);
			
			String userId = getUserId();
			resp = signUpService.getSignUpPage(userId, entryId);
			return resp;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}
    
    
	/**
	 * Description: 微信报名<br>
	 * 
	 * @author zhasiwei<br>
	 * @taskId <br>
	 * @param subsId
	 * @return
	 * @throws FrameworkException
	 *             <br>
	 * @throws IOException 
	 */
    @ApiOperation(value = "报名", notes = "传入活动id(entryId)和报名信息，获取对应的活动状态,若满足条件则报名成功。",response = ActResp.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "报名成功", response = ActResp.class),
    		@ApiResponse(code = 500, message = "报名失败，出现异常", response = ActResp.class) })
	@RequestMapping(value = "/signUp/index", method = {RequestMethod.POST,RequestMethod.GET})
	public ActResp signUp(@ApiParam(value = "用户实体") @RequestBody String entryJSON,HttpServletResponse response) throws FrameworkException, IOException {
		logger.info("<========== 微信报名-报名");
		
		
		try {
			logger.info("entryJSON = [{0}]", entryJSON);
			JSONObject  jsonObject = JSONObject.parseObject(entryJSON); 
			Map<String, Object> map = (Map<String, Object>)jsonObject; 
			String userId = getUserId();
			
			ActResp resp = new ActResp();
			resp = signUpService.signUp(userId, map);
			return resp;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}
}
