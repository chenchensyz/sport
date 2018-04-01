/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal;

import com.hbasesoft.framework.common.FrameworkException;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年9月29日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.common <br>
 */
public class RestfulException extends FrameworkException {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = -7203799396915171471L;

    /**
     * @param exception
     */
    public RestfulException(FrameworkException exception) {
        super(exception);
    }

    /**
     * @param code
     * @param params
     */
    public RestfulException(int code, Object... params) {
        super(code, params);
    }

    /**
     * @param code
     * @param t
     */
    public RestfulException(int code, Throwable t) {
        super(code, t);
    }

    /**
     * @param code
     */
    public RestfulException(int code) {
        super(code);
    }

    /**
     * @param t
     * @param code
     * @param params
     */
    public RestfulException(Throwable t, int code, Object... params) {
        super(t, code, params);
    }

    /**
     * @param t
     */
    public RestfulException(Throwable t) {
        super(t);
    }

}