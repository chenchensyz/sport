/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.manager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hbasesoft.framework.manager.cms.entity.CmsArticleEntity;
import com.hbasesoft.framework.manager.cms.entity.CmsMenuEntity;
import com.hbasesoft.framework.manager.cms.entity.micromatrix.CmsArticle;
import com.hbasesoft.framework.manager.cms.entity.micromatrix.CmsList;
import com.hbasesoft.framework.manager.core.util.CommonUtil;
import com.hbasesoft.framework.manager.wechat.guangjia.controller.AbstractWechatController;
import com.hbasesoft.framework.manager.wechat.guangjia.service.WeixinAccountServiceI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * 微矩阵数据查询
 */
@Controller
@RequestMapping("/micromatrixController")
public class MicromatrixController extends AbstractWechatController  {
    
    private static Logger logger = LoggerFactory.getLogger(MicromatrixController.class);
    
    @Autowired
    private WeixinAccountServiceI weixinAccountService;
    
    
    @RequestMapping(params = "micromatrixMessage")
    @ResponseBody
    public JSONObject micromatrixMessage(HttpServletRequest request, HttpServletResponse response){
        String menuCode = request.getParameter("menuCode");
        List<CmsList> cmsList =new ArrayList<CmsList>();
        CmsMenuEntity cmsMenu = weixinAccountService.findUniqueByProperty(CmsMenuEntity.class, CmsMenuEntity.MENU_CODE, menuCode );
        if(!CommonUtil.isNull(cmsMenu)){
            List<CmsMenuEntity> cmsMenuEntityList = this.weixinAccountService.findByQueryString("from CmsMenuEntity where parentId = '"+cmsMenu.getId()+"'");
            for(CmsMenuEntity cmsMenuEntity : cmsMenuEntityList){
                CmsList cms = new CmsList();
                cms.setId(cmsMenuEntity.getMenuCode());
                cms.setName(cmsMenuEntity.getName());
                
                List<CmsArticleEntity> cmsArticleEntityList = this.weixinAccountService.findByProperty(CmsArticleEntity.class, "parentId", cmsMenuEntity.getId());
                List<CmsArticle> cmsArticleList =new ArrayList<CmsArticle>();
                
                for(CmsArticleEntity cmsArticleEntity : cmsArticleEntityList){
                    CmsArticle cmsArticle = new CmsArticle();
                    cmsArticle.setTitle(cmsArticleEntity.getTitle());
                    cmsArticle.setImg(cmsArticleEntity.getImageHref());
                    cmsArticle.setUrl(cmsArticleEntity.getUrl());
                    cmsArticleList.add(cmsArticle);
                }
                
                cms.setList(cmsArticleList);
                
                cmsList.add(cms);
            }
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        JSONArray jsonArray = JSONArray.fromObject(cmsList);
        logger.info("micromatrix json ----->"+jsonArray.toString());
        
        JSONObject respJson = new JSONObject();
        respJson.put("mirList", jsonArray);
        return respJson;
        
    }
    
    
}
