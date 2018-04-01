/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.biz.vo;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年9月4日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.api <br>
 */
public abstract class AbstractVo implements Serializable {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = 1183912316631720681L;

    /*
     * (非 Javadoc) <p>Title: </p>
     */
    @Override
    public String toString() {
    	return JSONObject.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss", new SerializerFeature[0]);
//        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}