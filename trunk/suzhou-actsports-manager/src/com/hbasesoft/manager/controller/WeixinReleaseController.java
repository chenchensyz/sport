package com.hbasesoft.manager.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;
import com.hbasesoft.framework.manager.cms.entity.CmsMenuEntity;
import com.hbasesoft.framework.manager.core.beanvalidator.BeanValidators;
import com.hbasesoft.framework.manager.core.common.controller.BaseController;
import com.hbasesoft.framework.manager.core.common.exception.BusinessException;
import com.hbasesoft.framework.manager.core.common.hibernate.qbc.CriteriaQuery;
import com.hbasesoft.framework.manager.core.common.model.json.AjaxJson;
import com.hbasesoft.framework.manager.core.common.model.json.DataGrid;
import com.hbasesoft.framework.manager.core.constant.Globals;
import com.hbasesoft.framework.manager.core.util.CommonUtil;
import com.hbasesoft.framework.manager.core.util.ExceptionUtil;
import com.hbasesoft.framework.manager.core.util.MyBeanUtils;
import com.hbasesoft.framework.manager.core.util.ResourceUtil;
import com.hbasesoft.framework.manager.core.util.StringUtil;
import com.hbasesoft.framework.manager.tag.core.easyui.TagUtil;
import com.hbasesoft.framework.manager.web.system.service.SystemService;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.NewsItem;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.NewsTemplate;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.WeixinGroup;
import com.hbasesoft.manager.bean.WeixinReleaseEntity;
import com.hbasesoft.manager.service.WeixinReleaseServiceI;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**   
 * @Title: Controller
 * @Description: 微发布
 * @author onlineGenerator
 * @date 2017-07-26 14:16:53
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/weixinReleaseController")
public class WeixinReleaseController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(WeixinReleaseController.class);

    @Autowired
    private WeixinReleaseServiceI weixinReleaseService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private Validator validator;
    
    private String message;
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * 微发布列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("release/weixinReleaseList");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(WeixinReleaseEntity weixinRelease,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(WeixinReleaseEntity.class, dataGrid);
        //查询条件组装器
        com.hbasesoft.framework.manager.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, weixinRelease, request.getParameterMap());
        try{
        //自定义追加查询条件
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
      //查询当前登录用户
        String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
        cq.eq("code",orgCode);
        cq.add();
        this.weixinReleaseService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
    
    
    
    @RequestMapping(params = "getNews")
    public void getNews(HttpServletRequest request, HttpServletResponse response) {
        String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
        String groupName = request.getParameter("wgroup");
        String resMsg = "";
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {
            "newsItemList"
        });
        String hql = "from NewsTemplate t where t.orgCode = '" + orgCode + "' ";
        if(CommonUtil.isNotEmpty(groupName)){
            hql +=  "and groupId = '"+groupName+"'";
        }
        List<NewsTemplate> newsList = this.weixinReleaseService
            .findByQueryString(hql);
        List<NewsItem> newsItemsList = new ArrayList<NewsItem>();
        for(NewsTemplate newsTemplate : newsList){
            newsItemsList.addAll(newsTemplate.getNewsItemList());
        }
        JSONArray json = JSONArray.fromObject(newsItemsList, jsonConfig);
        resMsg = json.toString();
        
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

    /**
     * 删除微发布
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(WeixinReleaseEntity weixinRelease, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        weixinRelease = systemService.getEntity(WeixinReleaseEntity.class, weixinRelease.getId());
        message = "微发布删除成功";
        try{
            weixinReleaseService.delete(weixinRelease);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        }catch(Exception e){
            e.printStackTrace();
            message = "微发布删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    
    /**
     * 批量删除微发布
     * 
     * @return
     */
     @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids,HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        message = "微发布删除成功";
        try{
            for(String id:ids.split(",")){
                WeixinReleaseEntity weixinRelease = systemService.getEntity(WeixinReleaseEntity.class, 
                id
                );
                weixinReleaseService.delete(weixinRelease);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "微发布删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加微发布
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(WeixinReleaseEntity weixinRelease, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String templateId = request.getParameter("templateId");
        weixinRelease.setNewsId(templateId);
        if(CommonUtil.isNotEmpty(weixinRelease.getFatherId())){
            CmsMenuEntity cms = weixinReleaseService.get(CmsMenuEntity.class, weixinRelease.getFatherId());
            if(CommonUtil.isNotEmpty(cms.getParentId())){
                CmsMenuEntity cmsMenu = weixinReleaseService.get(CmsMenuEntity.class, cms.getParentId());
                weixinRelease.setCode(cmsMenu.getMenuCode());
            }
            if(WeixinReleaseEntity.AD_MENU_NAME.equals(cms.getType())){
                weixinRelease.setPosition(WeixinReleaseEntity.AD_POSITION);
            } else if (WeixinReleaseEntity.MENU_NAME.equals(cms.getType())){
                weixinRelease.setPosition(WeixinReleaseEntity.MENU_POSITION);
            }
        }
        message = "微发布添加成功";
        try{
            weixinReleaseService.save(weixinRelease);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }catch(Exception e){
            e.printStackTrace();
            message = "微发布添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    
    /**
     * 更新微发布
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(WeixinReleaseEntity weixinRelease, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String templateId = request.getParameter("templateId");
        weixinRelease.setNewsId(templateId);
        if(CommonUtil.isNotEmpty(weixinRelease.getFatherId())){
            CmsMenuEntity cms = weixinReleaseService.get(CmsMenuEntity.class, weixinRelease.getFatherId());
            if(CommonUtil.isNotEmpty(cms.getParentId())){
                CmsMenuEntity cmsMenu = weixinReleaseService.get(CmsMenuEntity.class, cms.getParentId());
                weixinRelease.setCode(cmsMenu.getMenuCode());
            }
            if(WeixinReleaseEntity.AD_MENU_NAME.equals(cms.getType())){
                weixinRelease.setPosition(WeixinReleaseEntity.AD_POSITION);
            } else if (WeixinReleaseEntity.MENU_NAME.equals(cms.getType())){
                weixinRelease.setPosition(WeixinReleaseEntity.MENU_POSITION);
            }
        }
        message = "微发布更新成功";
        WeixinReleaseEntity t = weixinReleaseService.get(WeixinReleaseEntity.class, weixinRelease.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(weixinRelease, t);
            weixinReleaseService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "微发布更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    

    /**
     * 微发布新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(WeixinReleaseEntity weixinRelease, HttpServletRequest req) {
        String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
        List<WeixinGroup> weixinGroupList = weixinReleaseService.findByQueryString("from WeixinGroup where orgCode = '"+orgCode+"'");
        
        List<CmsMenuEntity> cmsMenuList = weixinReleaseService.findByQueryString("from CmsMenuEntity where orgCode = '"+orgCode+"' and (type = 'wgg' or type = 'wlm')");
        req.setAttribute("cmsMenuList", cmsMenuList);
        req.setAttribute("groupList", weixinGroupList);
        if (StringUtil.isNotEmpty(weixinRelease.getId())) {
            weixinRelease = weixinReleaseService.getEntity(WeixinReleaseEntity.class, weixinRelease.getId());
            req.setAttribute("weixinReleasePage", weixinRelease);
        }
        return new ModelAndView("release/weixinRelease-add");
    }
    /**
     * 微发布编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(WeixinReleaseEntity weixinRelease, HttpServletRequest req) {
        String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
        List<WeixinGroup> weixinGroupList = weixinReleaseService.findByQueryString("from WeixinGroup where orgCode = '"+orgCode+"'");
        
        List<CmsMenuEntity> cmsMenuList = weixinReleaseService.findByQueryString("from CmsMenuEntity where orgCode = '"+orgCode+"' and (type = 'wgg' or type = 'wlm')");
        req.setAttribute("cmsMenuList", cmsMenuList);
        req.setAttribute("groupList", weixinGroupList);
        if (StringUtil.isNotEmpty(weixinRelease.getId())) {
            weixinRelease = weixinReleaseService.getEntity(WeixinReleaseEntity.class, weixinRelease.getId());
            if(CommonUtil.isNotEmpty(weixinRelease.getNewsId())){
                NewsItem newsItem = weixinReleaseService.getEntity(NewsItem.class, weixinRelease.getNewsId());
                if(CommonUtil.isNotEmpty(newsItem.getNewsTemplate().getGroupId())){
                    req.setAttribute("wgroupId", newsItem.getNewsTemplate().getGroupId());
                }
            }
            req.setAttribute("weixinReleasePage", weixinRelease);
        }
        return new ModelAndView("release/weixinRelease-update");
    }
    
    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name","weixinReleaseController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }
    
    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(WeixinReleaseEntity weixinRelease,HttpServletRequest request,HttpServletResponse response
            , DataGrid dataGrid,ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(WeixinReleaseEntity.class, dataGrid);
        com.hbasesoft.framework.manager.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, weixinRelease, request.getParameterMap());
        List<WeixinReleaseEntity> weixinReleases = this.weixinReleaseService.getListByCriteriaQuery(cq,false);
        modelMap.put(NormalExcelConstants.FILE_NAME,"微发布");
        modelMap.put(NormalExcelConstants.CLASS,WeixinReleaseEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("微发布列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
            "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST,weixinReleases);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }
    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(WeixinReleaseEntity weixinRelease,HttpServletRequest request,HttpServletResponse response
            , DataGrid dataGrid,ModelMap modelMap) {
        modelMap.put(NormalExcelConstants.FILE_NAME,"微发布");
        modelMap.put(NormalExcelConstants.CLASS,WeixinReleaseEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("微发布列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
        "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<WeixinReleaseEntity> listWeixinReleaseEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WeixinReleaseEntity.class,params);
                for (WeixinReleaseEntity weixinRelease : listWeixinReleaseEntitys) {
                    weixinReleaseService.save(weixinRelease);
                }
                j.setMsg("文件导入成功！");
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                logger.error(ExceptionUtil.getExceptionMessage(e));
            }finally{
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return j;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<WeixinReleaseEntity> list() {
        List<WeixinReleaseEntity> listWeixinReleases=weixinReleaseService.getList(WeixinReleaseEntity.class);
        return listWeixinReleases;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        WeixinReleaseEntity task = weixinReleaseService.get(WeixinReleaseEntity.class, id);
        if (task == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody WeixinReleaseEntity weixinRelease, UriComponentsBuilder uriBuilder) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<WeixinReleaseEntity>> failures = validator.validate(weixinRelease);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        weixinReleaseService.save(weixinRelease);

        //按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
        String id = weixinRelease.getId();
        URI uri = uriBuilder.path("/rest/weixinReleaseController/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody WeixinReleaseEntity weixinRelease) {
        //调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
        Set<ConstraintViolation<WeixinReleaseEntity>> failures = validator.validate(weixinRelease);
        if (!failures.isEmpty()) {
            return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
        }

        //保存
        weixinReleaseService.saveOrUpdate(weixinRelease);

        //按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        weixinReleaseService.deleteEntityById(WeixinReleaseEntity.class, id);
    }
}
