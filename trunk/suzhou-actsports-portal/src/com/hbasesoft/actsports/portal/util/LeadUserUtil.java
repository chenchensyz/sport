/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.util;

import java.text.MessageFormat;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.hbasesoft.actsports.portal.constant.SessionKeyDef;
import com.hbasesoft.actsports.portal.constant.WeChatConstants;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.io.HttpUtil;
import com.hbasesoft.framework.common.utils.logger.Logger;

/** 
 * <Description> <br> 
 *  
 * @author ruiluhui<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年8月19日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.actsports.portal.util <br>
 */
public class LeadUserUtil {

    private static Logger logger = new Logger(LeadUserUtil.class);
    
    public static String checkSubscribe(String openId){
        String accessToken = WeChatUtil.getAccessToken();
        String subscribe = "test";
        if(CommonUtil.isNotEmpty(openId)){
            String url = MessageFormat.format(WeChatConstants.USER_INFO, accessToken, openId);
            logger.info("LeadUserUtil checkSubscribe url = [{0}]",url);
            String jsonStr = HttpUtil.doGet(url);
            logger.info("LeadUserUtil checkSubscribe jsonStr = [{0}]",jsonStr);
            JSONObject obj = JSONObject.parseObject(jsonStr);
            String errorCode = obj.getString("errcode");
            if(CommonUtil.isEmpty(errorCode)){
                subscribe = obj.getString("subscribe");
                logger.info("LeadUserUtil checkSubscribe subscribe = [{0}]",subscribe);
            }
        }
        
        return subscribe;
    }
    
    
}
