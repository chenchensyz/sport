/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.handler.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.hbasesoft.actsports.portal.constant.CommonContant;
import com.hbasesoft.actsports.portal.service.UserService;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.io.HttpUtil;
import com.hbasesoft.framework.common.utils.logger.LoggerUtil;
import com.hbasesoft.framework.message.core.event.EventData;
import com.hbasesoft.framework.message.core.event.EventLinsener;
import com.hbasesoft.vcc.wechat.WechatConstant;
import com.hbasesoft.vcc.wechat.WechatEventCodeDef;
import com.hbasesoft.vcc.wechat.service.WechatService;
import com.hbasesoft.vcc.wechat.util.WechatUtil;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * <Description> <br>
 * 
 * @author zhasiwei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年6月2日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.cbs.event.push <br>
 */
public class OpenApiPushEventHandler implements EventLinsener {

	public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

	@Resource
	private UserService userService;

	@Resource
	private WechatService wechatService;

	/**
	 * Description: <br>
	 * 
	 * @author zhasiwei<br>
	 * @taskId <br>
	 * @return <br>
	 */
	@Override
	public String[] events() {
		return new String[] { WechatEventCodeDef.WECHAT_OPENAPI_PUSH };
	}

	/**
	 * Description: <br>
	 * 
	 * @author zhasiwei<br>
	 * @taskId <br>
	 * @param arg0
	 * @param arg1
	 *            <br>
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public void onEmmit(String event, EventData data) {

		LoggerUtil.info("start weixin push onEmmit, event=[{0}], EventDate=[{1}]", event, data);
		String pushUrl = data.getParameter("pushUrl");
		String message = data.getParameter("message");
		String accessToken = data.getParameter("accessToken");
		String needResp = data.getParameter("needResp");

		String respMessage = null;
		try {
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
			respMessage = HttpUtil.doPost(httpclient, pushUrl, message, null);

			Map<String, String> requestMap = WechatUtil.parseXml(respMessage);
			if (CommonUtil.isEmpty(respMessage) || WechatConstant.WECHAT_OPENAPI_UN_NEED_RESP.equals(needResp)) {
				return;
			}
	
			String req = format(requestMap);

		
			RequestBody requestBody = RequestBody.create(JSON, req);
			Request request = new Request.Builder().url(MessageFormat.format(WechatConstant.CUSTOM_URL, accessToken))
					.post(requestBody).build();
			OkHttpClient client = new OkHttpClient();
			Response response;
			response = client.newCall(request).execute();
			String body = response.body().string();

			LoggerUtil.info("receive CUSTOM_URL message {0}", body);

			JSONObject obj = JSONObject.parseObject(body);
			if (CommonContant.FAIL_CODE.equals(obj.getString("errcode"))) {
				LoggerUtil.info("WECHAT_OPENAPI_PUSH ERROR, errmsg = [{0}]", obj.getString("errmsg"));
			}
			LoggerUtil.info("end weixin push onEmmit, event=[{0}], EventDate=[{1}], respMessage=[{2}]", event, data,
					respMessage);
		} catch (IOException e) {
			LoggerUtil.error("第三方微信推送 调用异步方式 失败", e);
		}
	}

	private static String format(Map<String, String> respMap) {
		JSONObject json = new JSONObject();
		json.put("touser", respMap.get("ToUserName"));
		String msgtype = respMap.get("MsgType");
		json.put("msgtype", msgtype);

		JSONObject contentJson = new JSONObject();
		// TODO 目前只支持text格式的回复，若需求需要，则应扩展为支持多种格式
		switch (msgtype) {
		case WechatUtil.REQ_MESSAGE_TYPE_TEXT: {
			contentJson.put("content", respMap.get("Content"));
			json.put("text", contentJson);
			break;
		}
		default:
			break;
		}
		return json.toString();
	}

	public static void main(String[] args) throws IOException {
		
		
		
		String accessToken = "EujO6MNRinVmvz6yVXykCxs065__i3gYlOuS82pUfHO5yFftfVM8DyeB7S0GJGsEr0-TknFA-xHWw2bxSrV60MBxQ1feGI_0GLeEWnS9JcQq4JP0h-QIw2b690EYgTJyHNJhACATZV";
//
//		System.out.println("----------------------調用客戶消息接口---------------");
//		String map = "<xml>" + "<ToUserName><![CDATA[oCyoJs1ixAAkST-_crvJN0Sw9j8M]]></ToUserName>"
//				+ "<FromUserName><![CDATA[oCyoJs1ixAAkST-_crvJN0Sw9j8M]]></FromUserName>"
//				+ "<CreateTime>1348831860</CreateTime>" + "<MsgType><![CDATA[text]]></MsgType>"
//				+ "<Content><![CDATA[this is a test]]></Content>" + "</xml>";
//		Map<String, String> requestMap = WechatUtil.parseXml(map);
//		String req = format(requestMap);
//
//		RequestBody requestBody = RequestBody.create(JSON, req);
//		Request request = new Request.Builder().url(MessageFormat.format(WechatConstant.CUSTOM_URL, accessToken))
//				.post(requestBody).build();
//
//		OkHttpClient client = new OkHttpClient();
//		Response response = client.newCall(request).execute();
//		String body = response.body().string();
//		LoggerUtil.info("receive bindCard message {0}", body);
		
		
//		System.out.println("----------------------触发事件---------------");
		 String a = "<xml>"
		 +"<ToUserName><![CDATA[gh_c1e006482d73]]></ToUserName>"
		 +"<FromUserName><![CDATA[oCyoJs1ixAAkST-_crvJN0Sw9j8M]]></FromUserName>"
		 +"<CreateTime>1348831860</CreateTime>"
		 +"<MsgType><![CDATA[text]]></MsgType>"
		 +"<Image>"
		 +"<MediaId><![CDATA[media_id]]></MediaId>"
		 +"</Image>"
		 +"</xml>";
System.out.println(WechatUtil.parseXml(a));
		 WechatUtil.asyncSendMessage("http://localhost:8082/ff8080815be6ab36015bf145dad20016?wechat",
		 a,accessToken, WechatConstant.WECHAT_OPENAPI_NEED_RESP);

	}

}
