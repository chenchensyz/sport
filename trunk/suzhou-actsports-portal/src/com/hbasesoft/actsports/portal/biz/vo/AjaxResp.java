/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.biz.vo;

import java.util.HashMap;


/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月23日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.wx.bean <br>
 */
public class AjaxResp extends HashMap<String, Object> {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = 1048418226196535689L;

    /** 
     *  
     */
    public AjaxResp() {
        super.put("code", "0");
    }

    /**
     * @param code
     * @param message
     */
    public AjaxResp(String code, String message) {
        super.put("code", code);
        super.put("message", message);
    }

    public void setCode(String code) {
        super.put("code", code);
    }

    public void setMessage(String message) {
        super.put("message", message);
    }

}