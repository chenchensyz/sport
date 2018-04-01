package com.hbasesoft.actsports.portal;

import java.io.OutputStream;
import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.hbasesoft.actsports.portal.constant.CacheCodeDef;
import com.hbasesoft.actsports.portal.constant.ErrorCodeDef;
import com.hbasesoft.actsports.portal.constant.SessionKeyDef;
import com.hbasesoft.actsports.portal.service.LoginService;
import com.hbasesoft.framework.cache.core.CacheHelper;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.ContextHolder;
import com.hbasesoft.framework.common.utils.PropertyHolder;
import com.hbasesoft.framework.common.utils.io.HttpUtil;
import com.hbasesoft.framework.common.utils.logger.Logger;

public class OAuth2Interceptor extends HandlerInterceptorAdapter {

    private static Logger logger = new Logger(OAuth2Interceptor.class);

    private LoginService LoginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        HttpSession session = request.getSession();
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (handler instanceof HandlerMethod) {
            // 不需要获取openId的功能
            Method method = ((HandlerMethod) handler).getMethod();
            if (method.getAnnotation(OAuthOff.class) != null) {
                return true;
            }
            String state = request.getParameter("sessionId");
            
//            String openId = (String) session.getAttribute(SessionKeyDef.SESSION_OPEN_ID);
            
            logger.info("state from web  sessionId = [{0}]", state);
            
            String openId = null;
            if(CommonUtil.isEmpty(state)){
            	state = session.getId();
            }
            logger.info("state2 sessionId = [{0}]", state);
            
            openId = CacheHelper.getCache().get(CacheCodeDef.WX_AUTH_NODE, state);
            
            logger.info("第一次get openId = [{0}],sessionId = [{1}]" ,openId,session.getId());
            // 如果OpenId为空 则跳转 微信鉴权页面 获取 openid
            if (CommonUtil.isEmpty(openId)) {
                if ("dev".equalsIgnoreCase(PropertyHolder.getProperty("project.model"))
                    && CommonUtil.isNotEmpty(PropertyHolder.getProperty("test.data.openid"))) {
                    openId = PropertyHolder.getProperty("test.data.openid");
                    session.setAttribute(SessionKeyDef.SESSION_OPEN_ID, openId);
                    session.setAttribute(SessionKeyDef.SESSION_APPID, "wx4107dbba328d9dac");
                }
                else {
                	logger.info("进入拦截器  ，openId = [{0}]" ,openId);
//                    String sourceUri = HttpUtil.getRequestURI(request, true);
//                    String state = DataUtil.base64Encode(sourceUri.getBytes());
//                    String url = WeChatUtil.getOauth2Url(WebConstant.OATH_CALLBACK_URI, state,
//                        WeChatConstants.scope_snsapi_userinfo);
//                    logger.info(">>> [微信OAuth2认证授权跳转] location = [{0}]", url);
                    
                	
                    OutputStream outputStream = response.getOutputStream();//获取OutputStream输出流
                    response.setHeader("content-type", "text/html;charset=UTF-8");//通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
                    /**
                    * data.getBytes()是一个将字符转换成字节数组的过程，这个过程中一定会去查码表，
                    * 如果是中文的操作系统环境，默认就是查找查GB2312的码表，
                    * 将字符转换成字节数组的过程就是将中文字符转换成GB2312的码表上对应的数字
                    * 比如： "中"在GB2312的码表上对应的数字是98
                     *         "国"在GB2312的码表上对应的数字是99
                     */
                    /**
                    * getBytes()方法如果不带参数，那么就会根据操作系统的语言环境来选择转换码表，如果是中文操作系统，那么就使用GB2312的码表
                    */
                    logger.info(" setHeader" );
                    byte[] dataByteArr = String.valueOf(ErrorCodeDef.ACCESS_OPENID_ERROR).getBytes("UTF-8");//将字符转换成字节数组，指定以UTF-8编码进行转换
                    outputStream.write(dataByteArr);//使用OutputStream流向客户端输出字节数组
                    logger.info("outputStream.write");
                    return false;
                }
            }else{
            	 logger.info("opneId存入Session openId= [{0}]", openId);
            	 session.setAttribute(SessionKeyDef.SESSION_OPEN_ID, openId);
            }

            getLoginService().loginOrRegist(openId, HttpUtil.getClientInfo(request),
                    HttpUtil.getRequestIp(request));
        }
        return true;
    }


    private LoginService getLoginService() {
        if (LoginService == null) {
            LoginService = ContextHolder.getContext().getBean(LoginService.class);
        }

        return LoginService;
    }
    
}
