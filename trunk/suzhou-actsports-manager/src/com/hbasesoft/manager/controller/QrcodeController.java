package com.hbasesoft.manager.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hbasesoft.framework.manager.core.common.controller.BaseController;
import com.hbasesoft.framework.manager.core.common.service.CommonService;
import com.hbasesoft.framework.manager.core.util.PropertiesUtil;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.WeixinAccountEntity;
import com.hbasesoft.manager.utils.QRCodeUtil;

/**
 * 通用业务处理
 * 
 * @author 张代浩
 */
@Controller
@RequestMapping("/qrcodeController")
public class QrcodeController extends BaseController {
    /**
     * Logger for this class
     */

	private static Logger logger = LoggerFactory.getLogger(QrcodeController.class);
	
    @Resource(name = "commonService")
    private CommonService commonService;

    public static void main(String[] args) {
        
        String str = "appid=12/sign/id";
        Integer index = str.indexOf("/");
        String prefix = str.substring(0, index);
        String suffix = str.substring(index);       
        System.out.println(prefix+"后缀"+suffix);
        
    }
    
    /**
     * 通过url请求返回图像的字节流
     * @throws Exception 
     */
    @RequestMapping(params = "qrcode")
    public void getIcon(HttpServletRequest request, HttpServletResponse response) throws Exception {

    	
    	String url = request.getParameter("url");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        BufferedImage image;
        try {
            image = QRCodeUtil.buffereEncode(url, null);
            image.flush();
            ImageIO.write(image, "jpg", os);
        }
        catch (Exception e) {
        	logger.error(e.toString());
        }

        response.setContentType("image/jpg");
        OutputStream stream = response.getOutputStream();
        stream.write(os.toByteArray());
        stream.flush();
        stream.close();
        os.flush();
        os.close();
    }
    
    @RequestMapping(params = "queryAccount")
    @ResponseBody
    public void queryAccount(HttpServletRequest request, HttpServletResponse response){
        String resMsg = "<select id='account' name='account'>";
        List<WeixinAccountEntity> weixinAccountList = commonService.findByQueryString("from WeixinAccountEntity where state='A'");
        for(WeixinAccountEntity account : weixinAccountList){
            resMsg += "<option value="+account.getAccountappid()+">"+account.getAccountname()+"</option>";
        }
        resMsg += "</select>";
        try {
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(resMsg);
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    
    @RequestMapping(params = "downloadQrcode")
    public void downloadQrcode(HttpServletRequest request, HttpServletResponse response) throws Exception {

        
        String str = request.getParameter("str");
        Integer index = str.indexOf("/");
        String prefix = str.substring(0, index);
        String suffix = str.substring(index);
        str = prefix+"#"+suffix;
        
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysConfig.properties");
        Properties prop = propertiesUtil.getProperties();
        
        String qrcodeUrl = prop.getProperty("qrcode.url");
        qrcodeUrl += "?"+str;
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        BufferedImage image;
        try {
            image = QRCodeUtil.buffereEncode(qrcodeUrl, null);
            image.flush();
            ImageIO.write(image, "jpg", os);
        }
        catch (Exception e) {
            logger.error(e.toString());
        }

        response.setContentType("image/jpg");
        response.setHeader("Content-Disposition", "attachment; filename="+java.net.URLEncoder.encode("二维码", "UTF-8") + ".jpg");
        OutputStream stream = response.getOutputStream();
        stream.write(os.toByteArray());
        stream.flush();
        stream.close();
        os.flush();
        os.close(); 
    
            
    }
}
