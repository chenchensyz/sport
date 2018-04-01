/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.manager.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hbasesoft.framework.manager.core.util.CommonUtil;
import com.hbasesoft.framework.manager.core.util.PasswordUtil;
import com.hbasesoft.framework.manager.web.system.service.SystemService;
import com.hbasesoft.manager.service.EntryService;
import com.hbasesoft.manager.utils.FileUtil;
import com.hbasesoft.manager.vo.EntryResultVo;

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
@RequestMapping("/entry")
public class EntryController {

	@Autowired
    private EntryService entryService;
	
	@Autowired
	private SystemService systemService;
	
	@RequestMapping(params = "export")
	public void exportExcel(String id, HttpServletResponse response) throws Exception{
		
		
		List<EntryResultVo> ervList = entryService.getEntryResultVoByEntryId(id);
		
		List<String> cellNameList = new ArrayList<>();
		cellNameList.add("用户编号");
		cellNameList.add("报名活动编号");
		cellNameList.add("报名序号");
		cellNameList.add("活动名称");
		cellNameList.add("报名日期");
		cellNameList.add("报名项目");
		if(ervList != null && CommonUtil.isNotEmpty(ervList.get(0).getInfo())){
			String[] infos = ervList.get(0).getInfo().split("\\|");
			for(String info: infos){
				String[] ins = info.split(":");
				cellNameList.add(ins[0]);
			}
		}
		
		List<List<String>> paramsList = new ArrayList<>();
		List<String> params;
		for(EntryResultVo erv:ervList){
			params = new ArrayList<>();
			params.add(erv.getUserId());
			params.add(erv.getEntryId());
			params.add(erv.getEntryRank());
			params.add(erv.getEntryTitle());
			params.add(String.valueOf(erv.getCreateTime()));
			params.add(erv.getChoose());
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
        response.setHeader("Content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(ervList.get(0).getEntryTitle(), "UTF-8") +".xls");    
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();    
	}
	
	@RequestMapping(params = "toexchange")
	public ModelAndView toexchange(String sn,  HttpServletRequest request, HttpServletResponse response)  throws Exception{
		
		 String sessionAuth = (String) request.getSession().getAttribute("auth");  
		  
	        if (sessionAuth != null) {  
	            nextStep(request, response);  
	            String sql = "select sn,type,status from t_act_entity_sn where sn=?";
        		Map<String, Object>  prize = systemService.findOneForJdbc(sql,sn);
        		request.setAttribute("prize", prize);
        		return new ModelAndView("prize/toSignUpchange");
	        } else {  
	  
	            if(!checkHeaderAuth(request, response)){  
	                response.setStatus(401);  
	                response.setHeader("Cache-Control", "no-store");  
	                response.setDateHeader("Expires", 0);  
	                response.setHeader("WWW-authenticate", "Basic Realm=\"szsport\"");  
	            }else{             
	            	String sql = "select sn,type,status from t_act_entity_sn where sn=?";
	        		Map<String, Object>  prize = systemService.findOneForJdbc(sql,sn);
	        		request.setAttribute("prize", prize);
	        		return new ModelAndView("prize/toSignUpchange");
	            }
	  
	        }  
	        
	        return new ModelAndView("prize/toSignUpchange");
		
	}
	
	@RequestMapping(params = "doexchange")
	@ResponseBody
	public String doexchange(String sn, HttpServletRequest request) throws Exception{
		String sql = "update t_act_entity_sn set status=2 ,update_date=now() where sn=?";
		return systemService.executeSql(sql,sn).toString();
	}
	
	private boolean checkHeaderAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {  
		  
        String auth = request.getHeader("Authorization");  
        System.out.println("auth encoded in base64 is " + getFromBASE64(auth));  
          
        if ((auth != null) && (auth.length() > 6)) {  
            auth = auth.substring(6, auth.length());  
  
            String decodedAuth = getFromBASE64(auth);  
            System.out.println("auth decoded from base64 is " + decodedAuth);  
            
            String[] userInfo = decodedAuth.split(":");
            String password = PasswordUtil.encrypt(userInfo[0], userInfo[1], PasswordUtil.getStaticSalt());
            
            String sql = "select id from t_s_base_user where username = '"+userInfo[0]+"' and password='"+password+"'";
    		Map<String, Object>  user = systemService.findOneForJdbc(sql);
    		if(CommonUtil.isEmpty(user)){
    			return false;
    		}
  
            request.getSession().setAttribute("auth", decodedAuth);  
            return true;  
        }else{  
            return false;  
        }  
    } 
	
	private String getFromBASE64(String s) {  
        if (s == null)  
            return null;  
//        BASE64Decoder decoder = new BASE64Decoder();  
       
        try {  
//            byte[] b = decoder.decodeBuffer(s);  
            return new String( Base64.decodeBase64(s)); 
        } catch (Exception e) {  
            return null;  
        }  
    }  
  
    public void nextStep(HttpServletRequest request, HttpServletResponse response) throws IOException {  
        PrintWriter pw = response.getWriter();  
        pw.println("<html> next step, authentication is : " + request.getSession().getAttribute("auth") + "<br>");  
        pw.println("<br></html>");  
    }  
}
