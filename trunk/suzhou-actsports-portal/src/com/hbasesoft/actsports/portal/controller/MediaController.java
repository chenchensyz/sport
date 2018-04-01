package com.hbasesoft.actsports.portal.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hbasesoft.framework.common.ErrorCodeDef;
import com.hbasesoft.framework.common.FrameworkException;
import com.hbasesoft.framework.common.GlobalConstants;
import com.hbasesoft.framework.common.ServiceException;
import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.PropertyHolder;
import com.hbasesoft.framework.common.utils.date.DateConstants;
import com.hbasesoft.framework.common.utils.date.DateUtil;
import com.hbasesoft.framework.common.utils.io.IOUtil;
import com.hbasesoft.framework.common.utils.security.DataUtil;

/**
 *
 */
@Controller
@RequestMapping(value = { "/media" })
public class MediaController {

	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(MediaController.class);

	/**
	 * contentTypes
	 */
	private Map<String, String> contentTypes;
	private Map<String, Long> mediaSize;

	public MediaController() {
		contentTypes = new HashMap<String, String>();
		contentTypes.put("image/pjpeg", "jpg");
		contentTypes.put("image/x-png", "png");
		contentTypes.put("image/jpeg", "jpg");
		contentTypes.put("image/png", "png");
		
		mediaSize = new HashMap<String, Long>();
        mediaSize.put("text", 1024 * 1024 * 2014l);
        mediaSize.put("image", 1024 * 1024 * 1024l);
        mediaSize.put("voice", 2 * 1024 * 1024 * 1024l);
        mediaSize.put("file", 2 * 1024 * 1024 * 1024l);
	}
	
	/**
	 * Description: 图片上传接口
	 * <br>
	 *
	 * @author he.jiawen <br>
	 * @param request
	 * @throws FrameworkException
	 */
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Map<String, Object> upload(HttpServletRequest request) throws FrameworkException {

		Map<String, Object> result = new HashMap<String, Object>();

		if (!(request instanceof MultipartHttpServletRequest)) {
			throw new ServiceException(ErrorCodeDef.FILE_NOT_FIND_20013, "未找到上传的文件");
		}
		
		boolean isBase64 = StringUtils.equals(request.getParameter("base64"), "true");
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 
		if (CommonUtil.isEmpty(fileMap)) {
            throw new ServiceException(ErrorCodeDef.FILE_NOT_FIND_20013, "未找到上传的文件");
        }
		for (Entry<String, MultipartFile> fileEntry : fileMap.entrySet()) {
            MultipartFile file = fileEntry.getValue();
            String contentType = contentTypes.get(file.getContentType());
            checkFile(contentType, file.getSize());
            
            String type = getType(contentType);
            
            String absolutePath = PropertyHolder.getProperty("file.path", "upload/files");
    		String path = DateUtil.date2String(new Date(), DateConstants.DATE_FORMAT_8);
    		File dir = new File(absolutePath, path);
    		if (!dir.exists()) {
    			dir.mkdirs();
    		}
    		try {
    			int i = 0;
    			String fileName = GlobalConstants.PATH_SPLITOR + DateUtil.getCurrentTimestamp() + (i++) + "." + contentType;
    			
    			 if (isBase64) {
                     String content = IOUtil.readString(file.getInputStream());
                     byte[] fileContent = DataUtil.base64Decode(content);
                     IOUtil.writeFile(fileContent, new File(dir.getAbsolutePath() + fileName));
                 }
                 else {
                     IOUtil.copyFileFromInputStream(dir.getAbsolutePath() + fileName, file.getInputStream(),
                         "text".equals(type) ? GlobalConstants.DEFAULT_CHARSET : null);
                 }
    			result.put(file.getName(), path + fileName);
    		} catch (IOException e) {
    			logger.warn(e.getMessage(), e);
    			throw new ServiceException(ErrorCodeDef.READ_PARAM_ERROR_10027, e);
    		}
        }
		return result;
	}

	private void checkFile(String contentType, long fileSize) {
		if (CommonUtil.isEmpty(contentType) || !isImage(contentType)) {
			throw new ServiceException(ErrorCodeDef.READ_PARAM_ERROR_10027, "文件格式错误");
		}
		String type = getType(contentType);
        Long size = mediaSize.get(type);
        if (size == null) {
            throw new ServiceException(ErrorCodeDef.READ_PARAM_ERROR_10027, "不支持的媒体类型");
        }
        logger.info("接收到大小为{0}的文件",fileSize);
        if (fileSize - size > 0) {
            throw new ServiceException(ErrorCodeDef.FILE_IS_TO_LARGER_20011, "文件大小超限");
        }
	}

	public String getType(String type) {
		String tempType = "," + type + ",";

		if (",xml,html,json,css,js,txt,".indexOf(tempType) != -1) {
			return "text";
		} else if (",jpg,png,gif,".indexOf(tempType) != -1) {
			return "image";
		} else if (",mp3,amr,".indexOf(tempType) != -1) {
			return "voice";
		} else {
			return "file";
		}
	}
	
	private boolean isImage(String type) {
		return getType(type).equals("image");
	}

	public Map<String, String> getContentTypes() {
		return contentTypes;
	}

	public void setContentTypes(Map<String, String> contentTypes) {
		this.contentTypes = contentTypes;
	}

}
