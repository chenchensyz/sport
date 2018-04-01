/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.manager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hbasesoft.framework.manager.core.util.ResourceUtil;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年9月22日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.manager <br>
 */
public class FileDownloadServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(FileDownloadServlet.class);

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = 1052266610083820525L;

    private String rootPath = ResourceUtil.getConfigByName("upload_basepath");

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException <br>
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        String prefix = this.getInitParameter("prefix");
        String path = req.getRequestURI().substring( req.getContextPath().length());
        logger.debug("download file path" + path);

        File file = new File(rootPath + path);
        if (!file.exists()) {
            throw new FileNotFoundException(rootPath + path + "文件不存在");
        }
        
        FileInputStream fis = null;
        OutputStream os = null;
        try{
            fis = new FileInputStream(rootPath + path);
            int size =fis.available(); //得到文件大小 
            byte data[]=new byte[size]; 
            fis.read(data);  //读数据 
            resp.setContentType("image/gif"); //设置返回的文件类型
            os = resp.getOutputStream();
            os = resp.getOutputStream();
            os.write(data);
        }
        catch (Exception e) {
            throw new ServletException(e);
        }
        finally {
            if(null!= fis){
                fis.close(); 
            }
            if(null!= os){
                os.flush();
                os.close();    
            }
            
        }
    } 
}
