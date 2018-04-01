/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.manager.controller;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hbasesoft.framework.manager.core.util.CommonUtil;
import com.hbasesoft.manager.service.CollectService;
import com.hbasesoft.manager.utils.FileUtil;
import com.hbasesoft.manager.vo.CollectResultVo;

/** 
 * <Description> <br> 
 *  
 * @author 查思玮<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年6月19日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.manager.controller <br>
 */
@Controller
@RequestMapping("/collect")
public class CollectController {

	@Autowired
    private CollectService collectService;
	
	@RequestMapping(params = "export")
	public void exportExcel(String id, HttpServletResponse response) throws Exception{
		
		
		List<CollectResultVo> ervList = collectService.getCollectResultVoByCollectId(id);
		
		List<String> cellNameList = new ArrayList<>();
		cellNameList.add("用户编号");
		cellNameList.add("征集活动编号");
		cellNameList.add("征集名称");
		cellNameList.add("征集日期");
		cellNameList.add("征集意见");
		if(ervList != null && CommonUtil.isNotEmpty(ervList.get(0).getInfo())){
			String[] infos = ervList.get(0).getInfo().split("\\|");
			for(String info: infos){
				String[] ins = info.split(":");
				cellNameList.add(ins[0]);
			}
		}
		
		List<List<String>> paramsList = new ArrayList<>();
		List<String> params;
		for(CollectResultVo erv:ervList){
			params = new ArrayList<>();
			params.add(erv.getUserId());
			params.add(erv.getId());
			params.add(erv.getCollectTitle());
			params.add(String.valueOf(erv.getCreateTime()));
			params.add(erv.getIdea());
			String[] paramsInfo = erv.getInfo().split("\\|");
			for(String info : paramsInfo){
				String[] infos = info.split(":");
				if(infos.length >= 2 && CommonUtil.isNotEmpty(infos[1])){
					params.add(infos[1]);
				}
			}
			paramsList.add(params);
		}
		
		HSSFWorkbook wb = FileUtil.exportExcel(null, cellNameList, paramsList);
		response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(ervList.get(0).getCollectTitle(), "UTF-8") +".xls");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
	}
	
}
