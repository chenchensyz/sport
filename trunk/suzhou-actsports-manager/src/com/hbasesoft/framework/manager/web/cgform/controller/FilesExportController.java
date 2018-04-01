package com.hbasesoft.framework.manager.web.cgform.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hbasesoft.framework.manager.core.common.controller.BaseController;
import com.hbasesoft.framework.manager.core.common.exception.BusinessException;
import com.hbasesoft.framework.manager.core.common.model.json.AjaxJson;
import com.hbasesoft.framework.manager.core.util.BrowserUtils;
import com.hbasesoft.framework.manager.core.util.CommonUtil;
import com.hbasesoft.framework.manager.core.util.ResourceUtil;
import com.hbasesoft.framework.manager.web.cgreport.service.core.CgReportServiceI;
import com.hbasesoft.framework.manager.web.system.service.SystemService;
import com.hbasesoft.manager.utils.FileUtil;
/**
 *
 * @Title:FilesExportController
 * @description:文件导出
 * @author 赵俊夫
 * @date Aug 1, 2013 10:52:26 AM
 * @version V1.0
 */
@Controller
@RequestMapping("/filesExportController")
public class FilesExportController extends BaseController {
	
	
	@Autowired
	private CgReportServiceI cgReportService;
	
	@Autowired
    private SystemService systemService;
	/**
	 * 将报表导出为excel
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SuppressWarnings("all")
	@RequestMapping(params = "exportFile")
	public void exportFiles(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws IOException {
		 Map<String, String> retParams = new HashMap<String, String>();  
		 
		//step.1 设置，获取路径
	    String filePath = request.getParameter("filePath");
	    String codedFileName = request.getParameter("fileName");
	    File file = new File(filePath);
	    FileInputStream is = new FileInputStream(file);
	    if (BrowserUtils.isIE(request)) {
	    	response.setHeader("content-disposition","attachment;filename=" + java.net.URLEncoder.encode(codedFileName,"UTF-8"));
	    } else {
	    	String newtitle = new String(codedFileName.getBytes("UTF-8"),"ISO8859-1");
	    	response.setHeader("content-disposition","attachment;filename=" + newtitle);
	    }
	    
        ServletOutputStream out = response.getOutputStream();
        // 创建缓冲区
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();
        System.out.println("flush完成");
        // 删除文件夹和压缩文件
      
        FileUtil.DeleteFolder(filePath);
        System.out.println("文件删除完成");
       
	}
	
	@RequestMapping(params = "export")
	@ResponseBody
	public AjaxJson doDel(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) {
		AjaxJson j = new AjaxJson();
		String message= null;
		String code= null;
		try{
			//step.1 设置，获取配置信息
		    String collectId = request.getParameter("collectId");
		    String attachmentRule = request.getParameter("attachmentRule");
		    String collectTitle = request.getParameter("collectTitle");
		    String querySql = "SELECT t1.user_id,t1.param_value,t2.image_path FROM `t_act_collect_result` t1 LEFT JOIN  t_act_collect_result_attachment t2 on t1.collect_id=t2.collect_id and t1.user_id = t2.user_id "
		    		+ "where t1.param_key = '"+ attachmentRule +
		    		"' and t1.collect_id='" + collectId+"' ORDER BY t1.user_id";
		    Map<String, String> params = new HashMap<String, String>();  
		    List<Map<String, Object>> result=null;
		    
		    result= cgReportService.queryByCgReportSql(querySql, params, null, null, null, -1, -1);
		    
		    // 判断是否查出数据
		    
		    if(null ==  result|| result.size() == 0){
		    	code = "fail";
		    	message = "导出失败！导出规则错误";
		    	j.setMsg(message);
		    	j.setCode(code);
		        return j ;
		    }
		    String applicationpath = ResourceUtil.getConfigByName("upload_basepath");
		    //创建总目录文件件
		    String rootPath = applicationpath + File.separator + collectTitle;
		    FileUtil.createFolder(rootPath);
		    System.out.println("创建根目录 = " + rootPath);
		    String personPath = null;
		    String paramValue = null;
		    String imgName = null;
		    String imgPath = null;
		    for(Map<String, Object> map : result){
		    	//根据 rule 创建文件夹并存放文件
		    	paramValue = (String) map.get("param_value");
		    	if(!CommonUtil.isNotEmpty(paramValue))
		    		paramValue = (String) map.get("user_id");
		    	personPath = rootPath + File.separator + paramValue;
		    	FileUtil.createFolder(personPath);
		    	//复制文件
		    	imgName = (String) map.get("image_path");
		    	if(CommonUtil.isNotEmpty(imgName)){
		    		imgPath = applicationpath + File.separator + imgName;
		    		FileUtil.copyFile(imgPath,personPath + File.separator + new File(imgPath.trim()).getName());
		    	}
		    }
		    
		    String codedFileName = collectTitle +".zip";
		    System.out.println("压缩文件夹 = " + rootPath);
		    System.out.println("压缩好的文件名 = " + applicationpath + File.separator + codedFileName);
		    FileUtil.zip(rootPath, applicationpath + File.separator + codedFileName);
		    System.out.println("压缩结束");
		    FileUtil.DeleteFolder(rootPath);
		    Map<String, Object> reParma = new HashMap<String, Object>();  
		    reParma.put("fileName", codedFileName);
		    reParma.put("filePath", applicationpath + File.separator + codedFileName);
		    j.setAttributes(reParma);
	        message = "导出成功！";
	        code = "success";
		}catch(Exception e){
			e.printStackTrace();
			message = "导出失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setCode(code);
		return j;
	}
   
}
