/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.handler.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import com.hbasesoft.framework.common.FrameworkException;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.date.DateUtil;
import com.hbasesoft.framework.common.utils.io.HttpUtil;
import com.hbasesoft.framework.common.utils.logger.LoggerUtil;
import com.hbasesoft.vcc.wechat.WechatConstant;
import com.hbasesoft.vcc.wechat.WechatEventCodeDef;
import com.hbasesoft.vcc.wechat.bean.AccountPojo;
import com.hbasesoft.vcc.wechat.bean.OpenapiChannelPojo;
import com.hbasesoft.vcc.wechat.handler.AbstractMessageHandler;
import com.hbasesoft.vcc.wechat.service.WechatService;
import com.hbasesoft.vcc.wechat.util.SignUtil;
import com.hbasesoft.vcc.wechat.util.WechatUtil;

/**
 * 消息推送给第三方插件
 * @author 査思玮
 *
 */
@Service("OpenApiPushMessageHandler")
public class OpenApiPushMessageHandler extends AbstractMessageHandler {

    @Resource(name = "WechatServiceImpl")
    private WechatService wechatService;
	/**
	 * Description: <br>
	 * 
	 * @author 查思玮<br>
	 * @taskId <br>
	 * @param msgId
	 * @param toUserName
	 * @param entity
	 * @param content
	 * @return
	 * @throws VccException
	 *             <br>
	 */
	@Override
	public String process(String msgId, String toUserName, AccountPojo entity, String content,
			Map<String, String> requestMap, String imagePath, String serverPath, String message) throws FrameworkException {
		
		LoggerUtil.info("start OpenApiPushMessageHandler.process, msgId=[{0}], toUserName=[{1}], entity=[{2}], content=[{3}], requestMap=[{4}], imagePath=[{5}], serverPath=[{6}]",
				msgId, toUserName, entity, content, requestMap, imagePath, serverPath);
		
		OpenapiChannelPojo openapiChannel = wechatService.getOpenapiChannelByAppId(entity.getAccountappid());
		
		String respMessage = null;
		try {
			if(null != openapiChannel && CommonUtil.isNotEmpty(openapiChannel.getPushUrl()) && openapiChannel.getOpenapiState().equals(OpenapiChannelPojo.AVALIABLE)){
				
				//设置请求超时时间为5秒
				RequestConfig defaultRequestConfig = RequestConfig.custom()
					  .setSocketTimeout(5000)
					  .setConnectTimeout(5000)
					  .setConnectionRequestTimeout(5000)
					  .setStaleConnectionCheckEnabled(true)
					  .build();
				CloseableHttpClient httpclient = HttpClients.custom()
		    			.setDefaultRequestConfig(defaultRequestConfig)
		    			.build();
				respMessage = HttpUtil.doPost(httpclient, urlAddParam(openapiChannel), message, null);
				
			}else{
				LoggerUtil.info("该openapiChannel[{0}]未上线", openapiChannel);
			}
		} catch (Exception e) {
			LoggerUtil.info("OpenApiPushMessageHandler.process() doPost fail, url=[{0}], message=[{1}]", openapiChannel.getPushUrl(), message);
			LoggerUtil.error(e);
		}
		
		
		LoggerUtil.info("end OpenApiPushMessageHandler.process, respMessage = [{1}]", respMessage);
		
		return respMessage;
		
	}
	
	/**
	 * 异步消息返回
	 */
	@Override
	public void asynProcess(String msgId, String toUserName, AccountPojo entity, String content,
			Map<String, String> requestMap, String imagePath, String serverPath, String message) throws FrameworkException {
		LoggerUtil.info("start OpenApiPushMessageHandler.asynProcess, msgId=[{0}], toUserName=[{1}], entity=[{2}], content=[{3}], requestMap=[{4}], imagePath=[{5}], serverPath=[{6}]",
				msgId, toUserName, entity, content, requestMap, imagePath, serverPath);
		
		OpenapiChannelPojo openapiChannel = wechatService.getOpenapiChannelByAppId(entity.getAccountappid());
		try {
			if(null != openapiChannel && CommonUtil.isNotEmpty(openapiChannel.getPushUrl()) && openapiChannel.getOpenapiState().equals(OpenapiChannelPojo.AVALIABLE)){
				
					WechatUtil.asyncSendMessage(urlAddParam(openapiChannel), message, entity.getAccountaccesstoken(),WechatConstant.WECHAT_OPENAPI_NEED_RESP);
				
			}else{
				LoggerUtil.info("该openapiChannel[{0}]未上线", openapiChannel);
			}
		} catch (Exception e) {
			LoggerUtil.info("OpenApiPushMessageHandler.asynProcess() doPost fail, url=[{0}], message=[{1}]", openapiChannel.getPushUrl(), message);
			LoggerUtil.error(e);
			throw new FrameworkException(e);
		}
		LoggerUtil.info("end OpenApiPushMessageHandler.asynProcess, eventName =[{0}]", WechatEventCodeDef.WECHAT_OPENAPI_PUSH);
		
	}
	
	
	private String urlAddParam(OpenapiChannelPojo openapiChannel){
		String timestamp = DateUtil.getCurrentTimestamp();
		String nonce = CommonUtil.getRandomNumber(32);
		String signature = SignUtil.signature(openapiChannel.getToken(), timestamp, nonce).toLowerCase();
		String echostr = CommonUtil.getRandomChar(16);
		
		StringBuffer pushUrl = new StringBuffer(openapiChannel.getPushUrl());
		if(pushUrl.toString().indexOf("?") == -1){
			pushUrl.append("?");
		}else{
			pushUrl.append("&");
		}
		pushUrl.append("signature=").append(signature).append("&nonce=").append(nonce).append("&timestamp=").append(timestamp).append("&echostr=").append(echostr);
		return pushUrl.toString();
	}
	
	public static void main(String[] args) {
				 String a = "<xml>"
		 +"<ToUserName><![CDATA[gh_c1e006482d73]]></ToUserName>"
		 +"<FromUserName><![CDATA[oCyoJs1ixAAkST-_crvJN0Sw9j8M]]></FromUserName>"
		 +"<CreateTime>1348831860</CreateTime>"
		 +"<MsgType><![CDATA[text]]></MsgType>"
		 +"<Image>"
		 +"<MediaId><![CDATA[media_id]]></MediaId>"
		 +"</Image>"
		 +"</xml>";
				 
				 RequestConfig defaultRequestConfig = RequestConfig.custom()
						  .setSocketTimeout(10000)
						  .setConnectTimeout(10000)
						  .setConnectionRequestTimeout(10000)
						  .setStaleConnectionCheckEnabled(true)
						  .build();
		CloseableHttpClient httpclient = HttpClients.custom()
						    			.setDefaultRequestConfig(defaultRequestConfig)
						    			.build();
//		String respMessage = HttpUtil.doPost(httpclient, "http://test01.towngasvcc.com/mms/wechat/portal?signature=b296a51f111235c2e1855ed6821746cd3d6bb878&timestamp=1496645349&nonce=3015559136&echostr=14361620016577511415", a, null);
		try {
			
			String respMessage = HttpUtil.doPost(httpclient, "http://localhost:8080/402881e6598307d20159835d2ee40008?wechat", a, null);
		} catch (Exception e) {
			System.out.println("catch");
		}
		System.out.println("111");
//		System.out.println(CommonUtil.isEmpty(respMessage));
	}
}
