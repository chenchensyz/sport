package com.hbasesoft.actsports.portal.util;
import java.io.File;
import java.io.FileInputStream;

import java.security.KeyStore;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.dom4j.Document;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONObject;
import com.hbasesoft.framework.common.GlobalConstants;
import com.hbasesoft.framework.common.RemoteServiceException;
import com.hbasesoft.framework.common.utils.Assert;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.ContextHolder;
import com.hbasesoft.framework.common.utils.PropertyHolder;
import com.hbasesoft.framework.common.utils.URLUtil;
import com.hbasesoft.framework.common.utils.UtilException;
import com.hbasesoft.framework.common.utils.bean.JsonUtil;
import com.hbasesoft.framework.common.utils.date.DateUtil;
import com.hbasesoft.framework.common.utils.io.HttpUtil;
import com.hbasesoft.framework.common.utils.logger.Logger;
import com.hbasesoft.framework.common.utils.security.DataUtil;
import com.hbasesoft.framework.common.utils.xml.XmlBeanUtil;
import com.hbasesoft.framework.common.utils.xml.XmlUtil;
import com.hbasesoft.actsports.portal.service.api.ConfigService;
import com.hbasesoft.actsports.portal.biz.vo.UserInfo;
import com.hbasesoft.actsports.portal.biz.vo.WechatAccount;
import com.hbasesoft.actsports.portal.constant.ContentType;
import com.hbasesoft.actsports.portal.constant.ErrorCodeDef;
import com.hbasesoft.actsports.portal.constant.WeChatConstants;
import com.hbasesoft.actsports.portal.bean.UnifiedOrderResult;
import com.hbasesoft.actsports.portal.bean.WXResult;
import com.hbasesoft.actsports.portal.bean.WxTemplate;

/**
 * 微信常用方法
 * 
 * @author meiguiyang
 */
@SuppressWarnings("deprecation")
public final class WeChatUtil {
    private static final Logger LOG = new Logger(WeChatUtil.class);

    private static ConfigService configService;

    private WeChatUtil() {
    }

    private static ConfigService getConfigService() {
        if (configService == null) {
            configService = ContextHolder.getContext().getBean(ConfigService.class);
        }
        return configService;
    }

    public static String getAccessToken() {
        try {
            return getConfigService().getWechatAccessToken();
        }
        catch (Exception e) {
            LOG.error("获取AccessToken失败", e);
            throw new RuntimeException();
        }
    }

    /**
     * 针对全局acesstoken 的errcode = 40001错误的特殊处理,网上说是access_token失效导致的
     * 
     * @param message
     * @throws RemoteServiceException
     */
    public static void refreshAccessTokenIfNecessary(String errorcode) {
        if ("40001".equals(errorcode)) { // 需要刷新全局accessToken
            try {
                getConfigService().resetWechatAccessToken();
            }
            catch (Exception e) {
                LOG.error("重置AccessToken失败", e);
                throw new RuntimeException();
            }
        }
    }

    public static String getJsApiTicket() {
        try {
            return getConfigService().getWechatJsApiTickit();
        }
        catch (Exception e) {
            LOG.error("获取JsApiTicket失败", e);
            throw new RuntimeException();
        }
    }

    public static void refreshJsapiTicketIfNecessary(String errorcode) {
        if ("40001".equals(errorcode)) { // 需要刷新全局accessToken
            try {
                getConfigService().resetWechatJsApiTickit();
            }
            catch (Exception e) {
                LOG.error("刷新JsApi失败", e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 微信模板发送消息
     * 
     * @param wxTemplate 模板信息
     * @return WXResult WXResult
     * @throws RemoteServiceException
     */
    public static WXResult sendMessageByTemplate(WxTemplate wxTemplate) {
        // openID 不存在不发送
        if (wxTemplate == null || StringUtils.isEmpty(wxTemplate.getTouser())) {
            LOG.info("模板信息为null ,或者发送对象openID为空");
            return null;
        }

        // 获得集团公众号全局accessToken
        String accessToken = getAccessToken();
        String url = MessageFormat.format(WeChatConstants.TEMPLATE_MESSAGE, accessToken);
        String body = JsonUtil.writeObj2FormatJson(wxTemplate);

        LOG.info("发送微信模板消息-url:[{0}]---body[{1}]", url, body);
        String jsonStr = HttpUtil.doPost(url, body, ContentType.APPLICATION_JSON_UTF_8);
        LOG.info("发送微信模板消息结果代码:[{0}]", jsonStr);
        JSONObject obj = JSONObject.parseObject(jsonStr);
        WXResult wxResult = new WXResult();
        wxResult.setErrcode(obj.getString("errcode"));
        if (CommonUtil.isEmpty(wxResult.getErrcode())) {
            wxResult.setMsgid(obj.getString("msgid"));
        }
        else {
            wxResult.setErrmsg(obj.getString("errmsg"));
            refreshAccessTokenIfNecessary(wxResult.getErrcode());
        }

        return wxResult;
    }

    /**
     * 通过accessToken openId 获取用户信息
     * 
     * @param openId
     * @return
     */
    public static UserInfo getUserInfoMessage(String openId) {
        String url = MessageFormat.format(WeChatConstants.USER_INFO, getAccessToken(), openId);
        LOG.info("获取用户信息:[{0}]", url);
        String jsonStr = HttpUtil.doGet(url);
        LOG.info("获取用户信息结果:[{0}]", jsonStr);
        JSONObject obj = JSONObject.parseObject(jsonStr);
        String errorCode = obj.getString("errcode");
        UserInfo userInfo = null;
        if (CommonUtil.isEmpty(errorCode)) {
            userInfo = new UserInfo();
            userInfo.setCity(obj.getString("city"));
            userInfo.setGender(obj.getString("sex"));
            userInfo.setHeadImg(obj.getString("headimgurl"));
            String nickName = obj.getString("nickname");
            if (CommonUtil.isNotEmpty(nickName)) {
                userInfo.setNickName(EmojiFilter.filterEmoji(nickName));
            }
            userInfo.setProvice(obj.getString("province"));
        }
        else {
            refreshAccessTokenIfNecessary(errorCode);
        }
        return userInfo;
    }

    /**
     * 通过appid,AppSecret,code得到全局accessToken对象
     * 
     * @param appid
     * @param appSecret
     * @param code
     * @return
     */
    public static String getOpenId4Oauth2(String code,String appid) {
        String openId = null;
        try {
            WechatAccount acct = getConfigService().getWechatAccount(appid);
            if (acct != null) {
                openId = getOpenId4Oauth2(code, acct.getAppCode(), acct.getAppSecret());
            }
        }
        catch (Exception e) {
            LOG.error("获取AppId失败", e);
            throw new RuntimeException(e);
        }

        return openId;
    }

    /**
     * 通过appid,AppSecret,code得到全局accessToken对象
     * 
     * @param appid
     * @param appSecret
     * @param code
     * @return
     */
    public static String getOpenId4Oauth2(String code, String appId, String appKey) {
        String url = MessageFormat.format(WeChatConstants.OAUTH2_TOKEN_URL, appId, appKey, code);
        LOG.info("auth2.0 根据code查询openid url[{0}]", url);
        String jsonStr = HttpUtil.doGet(url);
        LOG.info("auth2.0 查询结果反馈:[{0}]", jsonStr);

        JSONObject obj = JSONObject.parseObject(jsonStr);
        String errorCode = obj.getString("errcode");
        return CommonUtil.isEmpty(errorCode) ? obj.getString("openid") : null;
    }

    /**
     * 通过accessToken media_id 下载图片
     * 
     * @param media_id 多媒体文件在微信服务器上的ID
     * @return
     */
    public void mediaDown(String absolutePath, String media_id) {
        String accessToken = getAccessToken();
        String url = MessageFormat.format(WeChatConstants.MEDIA_DOWN_URL, accessToken, media_id);
        LOG.info("begin mediaDownmediaDown 获取mediaDown [{0}]", url);
        HttpUtil.doGetDowloadFile(absolutePath, url);
    }

    public static String getOauth2Url(String redirectURI, String expendParam, String scope) {
        String location = null;

        try {
            WechatAccount acct = getConfigService().getWechatAccount();
            if (acct != null) {
                location = getOauth2Url(acct.getAppCode(), redirectURI, expendParam, scope);
            }
        }
        catch (Exception e) {
            LOG.error("获取AppId失败", e);
            throw new RuntimeException(e);
        }

        return location;
    }

    public static String getOauth2Url(String appId, String redirectURI, String expendParam, String scope) {
        String redirectUrl = URLUtil
            .encode(PropertyHolder.getProperty("server.wx.url", GlobalConstants.BLANK) + redirectURI);

        return MessageFormat.format(WeChatConstants.OAUTH2_CODE_URL, appId, redirectUrl, scope, expendParam);
    }

    public static UnifiedOrderResult createWechatOrder(String key, Map<String, String> paramMap) {

        try {
            // 签名，详见签名生成算法
            paramMap.put("sign", WeChatUtil.sign(key, paramMap));

            String content = WeChatUtil.map2xml(paramMap);
            LOG.info("---begin 发送微信支付订单[{0}]", content);
            String result = HttpUtil.doPost(WeChatConstants.ORDER_API, content, "text/xml");
            LOG.info("---end 发送微信支付订单result[{0}]", result);
            return XmlBeanUtil.xml2Object(result, UnifiedOrderResult.class);
        }
        catch (UtilException e) {
            LOG.error("签名校验失败", e);
            throw new RuntimeException(e);
        }

    }

    public static Map<String, String> refund(String key, Map<String, String> paramMap, String p12Path)
        throws UtilException {
        String mchId = paramMap.get("mch_id");
        try {
            // 签名，详见签名生成算法
            paramMap.put("sign", WeChatUtil.sign(key, paramMap));
            String content = WeChatUtil.map2xml(paramMap);
            LOG.info("---begin 发送微信退费请求[{0}]", content);

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream in = null;
            try {
                in = new FileInputStream(new File(p12Path));
                keyStore.load(in, mchId.toCharArray());
            }
            finally {
                IOUtils.closeQuietly(in);
            }

            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] {
                "TLSv1"
            }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

            String result = HttpUtil.doPost(httpclient, WeChatConstants.REFUND_API, content, "text/xml");

            LOG.info("---end 发送微信退费请求result[{0}]", result);
            Map<String, String> resultMap = xml2map(result);
            String oldSign = resultMap.remove("sign");
            String newSign = WeChatUtil.sign(key, resultMap);
            Assert.equals(oldSign, newSign, com.hbasesoft.vcc.wechat.ErrorCodeDef.REFUND_HASH_ERROR, oldSign, newSign);
            return resultMap;
        }
        catch (Exception e) {
            LOG.error(e);
            throw new UtilException(ErrorCodeDef.REFUND_ERROR, e);
        }
    }

    /**
     * 签名
     * 
     * @param params
     * @param key
     * @return
     * @throws UtilException
     */
    public static String sign(String key, Map<String, String> map) throws UtilException {
        Map<String, String> treeMap = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        // 排序
        treeMap.putAll(map);
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> entry : treeMap.entrySet()) {
            if (CommonUtil.isEmpty(entry.getValue())) {
                continue;
            }
            sb.append(entry.getKey()).append('=').append(entry.getValue()).append('&');
        }
        sb.append("key=");
        sb.append(key);
        return DataUtil.md5(sb.toString()).toUpperCase();
    }

    public static String getPayOrderTradeNo() {
        return new StringBuilder().append("1").append("1").append(DateUtil.getCurrentTimestamp())
            .append(CommonUtil.getRandomNumber(4)).toString();
    }

    public static String getRefundOrderTradeNo() {
        return new StringBuilder().append("1").append("2").append(DateUtil.getCurrentTimestamp())
            .append(CommonUtil.getRandomNumber(4)).toString();
    }

    public static String map2xml(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (Entry<String, String> entry : map.entrySet()) {
            if (CommonUtil.isNotEmpty(entry.getValue())) {
                sb.append(CommonUtil.messageFormat("<{0}>{1}</{2}>", entry.getKey(), entry.getValue(), entry.getKey()));
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String> xml2map(String content) {
        Map<String, String> map = new HashMap<String, String>();
        Document doc = XmlUtil.parseXML(content);
        Element root = doc.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        return map;
    }
    
  
      
    
    
}
