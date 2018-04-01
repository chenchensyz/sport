/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.manager.service;

import java.util.List;

import com.hbasesoft.framework.manager.core.common.service.CommonService;
import com.hbasesoft.manager.vo.PrizeResultVo;

/** 
 * <Description> <br> 
 *  
 * @author 查思玮<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年6月19日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.manager.service <br>
 */
public interface PrizeService extends CommonService{

	public List<PrizeResultVo> getPrizeResultVoByPrizeId(String prizeId) throws Exception;
}
