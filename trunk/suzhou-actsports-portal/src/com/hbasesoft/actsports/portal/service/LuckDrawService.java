/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年1月5日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.test.lock <br>
 */
public interface LuckDrawService {

    int luckDraw(String activityCode, String userCode, int[] gifs, int[] gifsTotal, double probability);
}
