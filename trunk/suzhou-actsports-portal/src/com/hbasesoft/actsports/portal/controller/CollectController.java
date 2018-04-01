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
import com.hbasesoft.actsports.portal.constant.SessionKeyDef;
import com.hbasesoft.actsports.portal.service.CollectService;
import com.hbasesoft.actsports.portal.service.CommonService;
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
 * <Description> 信息征集<br>
 * 
 * @author zhasiwei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年12月27日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.actsports.portal.controller <br>
 */
@Api(value = "征集控制器",description = "处理信息征集")
@RestController
public class CollectController extends BaseController {

	@Resource(name = "CollectRemoteService")
	private CollectService collectService;
	
	@Resource
    private CommonService commonService;
	
	/**
	 * Description: 获取征集页面<br>
	 * 
	 * @author zhasiwei<br>
	 * @taskId <br>
	 * @param subsId
	 * @return
	 * @throws FrameworkException
	 *             <br>
	 */
    @ApiOperation(value = "获取报名页面", notes = "传入征集id(collectId)，获取征集活动状态,若满足条件则提供页面的显示内容。",response = ActResp.class)
    @ApiResponses(value = {
    	@ApiResponse(code = 200, message = "获取页面成功", response = ActResp.class),
    	@ApiResponse(code = 500, message = "获取页面失败，出现异常", response = ActResp.class) })
	@RequestMapping(value = "/collect/getCollectPage", method = {RequestMethod.POST,RequestMethod.GET})
	public ActResp getCollectPage(@ApiParam(value = "征集ID") @RequestBody String collectJson, HttpServletResponse res) throws FrameworkException, IOException {
		logger.info("<========== 微信征集-获取征集页面");
		try {
		    ActResp resp = new ActResp();
		    String openId = (String)getAttribute(SessionKeyDef.SESSION_OPEN_ID);
            String subscribe = LeadUserUtil.checkSubscribe(openId);
            if(CommonUtil.isEmpty(subscribe) || "0".equals(subscribe)){
                LoggerUtil.info("getCollectPage come in");
                resp.setSubscribeCode("1");
                ShowPojo showPojo = commonService.findUniqueByProperty(ShowPojo.class, ShowPojo.NAME, ShowPojo.NAME_VALUE);
                if(!CommonUtil.isNull(showPojo)){
                    resp.setSubscribeUrl(showPojo.getValue());
                }
                return resp;
            }
		    
			JSONObject  jsonObject = JSONObject.parseObject(collectJson); 
			Map<String, Object> map = (Map<String, Object>)jsonObject; 
			String collectId = (String) map.get("collectId");
			String url = (String) map.get("url");
			//判断entryId中是否含有“&”,并去除
			if(collectId.contains("&")){
				collectId = collectId.substring(0, collectId.indexOf("&"));
			}
			
			logger.info("collectId = [{0}]", collectId);
			
			String userId = getUserId();
			
			resp = collectService.getCollectPage(userId, collectId, url);
			
			return resp;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

	/**
	 * Description: 微信征集录入<br>
	 * 
	 * @author zhasiwei<br>
	 * @taskId <br>
	 * @param subsId
	 * @return
	 * @throws FrameworkException
	 *             <br>
	 * @throws IOException 
	 */
    @ApiOperation(value = "征集", notes = "传入征集id(collectId)和征集信息，获取对应的活动状态,若满足条件则征集成功。",response = ActResp.class)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "征集成功", response = ActResp.class),
    		@ApiResponse(code = 500, message = "征集失败，出现异常", response = ActResp.class) })
	@RequestMapping(value = "/collect/index", method = {RequestMethod.POST,RequestMethod.GET})
	public ActResp collect(@ApiParam(value = "征集") @RequestBody String collectJSON) throws FrameworkException, IOException {
		logger.info("<========== 微信报名-报名");
		
		try {
			JSONObject  jsonObject = JSONObject.parseObject(collectJSON);
			logger.info("collect message"+jsonObject);
			Map<String, Object> map = (Map<String, Object>)jsonObject; 
			String userId = getUserId();
			ActResp resp = new ActResp();
			resp = collectService.collect(userId, map);
			return resp;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}
}