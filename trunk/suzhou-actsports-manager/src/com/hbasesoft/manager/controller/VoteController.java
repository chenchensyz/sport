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
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hbasesoft.framework.manager.core.util.CommonUtil;
import com.hbasesoft.manager.service.VoteService;
import com.hbasesoft.manager.utils.FileUtil;
import com.hbasesoft.manager.vo.VoteResultVo;

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
@RequestMapping("/vote")
public class VoteController {

	@Autowired
    private VoteService voteService;
	
	@RequestMapping(params = "export")
	public void exportExcel(String id, HttpServletResponse response) throws Exception{
		
		
		List<VoteResultVo> ervList = voteService.getVoteResultVoByVoteId(id);
		
		List<String> cellNameList = new ArrayList<>();
		cellNameList.add("用户编号");
		cellNameList.add("投票活动编号");
		cellNameList.add("投票名称");
		cellNameList.add("创建日期");
		cellNameList.add("投票记录");
		if(ervList != null && CommonUtil.isNotEmpty(ervList.get(0).getInfo())){
			String[] infos = ervList.get(0).getInfo().split("\\|");
			for(String info: infos){
			String[] ins = info.split(":");
			cellNameList.add(ins[0]);
			}
		}
		
		List<List<String>> paramsList = new ArrayList<>();
		List<String> params;
		for(VoteResultVo erv:ervList){
			params = new ArrayList<>();
			params.add(erv.getUserId());
			params.add(erv.getVoteId());
			params.add(erv.getVoteTitle());
			params.add(String.valueOf(erv.getCreateTime()));
			params.add(erv.getChoose());
			String[] paramsInfo = erv.getInfo().split("\\|");
			if(!paramsInfo[0].startsWith("姓名:")){
				params.add("-");
			}
			for(String info : paramsInfo){
				System.out.println(info);
				String[] infos = info.split(":");
				if(infos.length >= 2 && StringUtils.isNotEmpty(infos[1])){
				params.add(infos[1]);
				}
			}
			paramsList.add(params);
		}
		
		HSSFWorkbook wb = FileUtil.exportExcel(null, cellNameList, paramsList);
		response.setContentType("application/vnd.ms-excel");    
        response.setHeader("Content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(ervList.get(0).getVoteTitle(), "UTF-8") +".xls");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
	}
	
}
