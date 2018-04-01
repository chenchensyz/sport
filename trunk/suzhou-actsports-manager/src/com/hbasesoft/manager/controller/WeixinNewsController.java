package com.hbasesoft.manager.controller;

import com.hbasesoft.framework.manager.core.common.exception.BusinessException;
import com.hbasesoft.framework.manager.core.common.hibernate.qbc.CriteriaQuery;
import com.hbasesoft.framework.manager.core.common.model.common.UploadFile;
import com.hbasesoft.framework.manager.core.common.model.json.AjaxJson;
import com.hbasesoft.framework.manager.core.common.model.json.DataGrid;
import com.hbasesoft.framework.manager.core.constant.Globals;
import com.hbasesoft.framework.manager.core.extend.hqlsearch.HqlGenerateUtil;
import com.hbasesoft.framework.manager.core.util.LogUtil;
import com.hbasesoft.framework.manager.core.util.MyBeanUtils;
import com.hbasesoft.framework.manager.core.util.MyClassLoader;
import com.hbasesoft.framework.manager.core.util.StringUtil;
import com.hbasesoft.framework.manager.core.util.oConvertUtils;
import com.hbasesoft.framework.manager.tag.core.easyui.TagUtil;
import com.hbasesoft.framework.manager.web.system.pojo.base.TSType;
import com.hbasesoft.framework.manager.web.system.pojo.base.TSTypegroup;
import com.hbasesoft.framework.manager.web.system.service.SystemService;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.NewsItem;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.NewsTemplate;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.TSDocument;
import com.hbasesoft.framework.manager.wechat.guangjia.service.NewsItemServiceI;
import com.hbasesoft.framework.manager.wechat.guangjia.util.DateUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/weixinNewsController"})
public class WeixinNewsController extends AbstractWechatController
{
  private static final Logger logger = Logger.getLogger(WeixinNewsController.class);

  @Autowired
  private NewsItemServiceI newsItemService;

  @Autowired
  private SystemService systemService;

  private String message;

  public String getMessage() { return this.message; }

  public void setMessage(String message)
  {
    this.message = message;
  }

  @RequestMapping(params={"goMessage"})
  public ModelAndView goMessage(HttpServletRequest request)
  {
    String templateId = request.getParameter("templateId");

    if (StringUtil.isNotEmpty(templateId)) {
      String hql = "from NewsItem where newsTemplate.id='" + templateId + "' order by orders asc";
      LogUtil.info("...hql..." + hql);
      List headerList = this.systemService.findByQueryString(hql);
      if (headerList.size() > 0) {
        request.setAttribute("headerNews", headerList.get(0));
        if (headerList.size() > 1) {
          ArrayList list = new ArrayList(headerList);
          list.remove(0);
          request.setAttribute("newsList", list);
        }
      }
      NewsTemplate newsTemplate = (NewsTemplate)this.systemService.getEntity(NewsTemplate.class, templateId);
      String temp = newsTemplate.getAddTime().replace("-", "/");
      Date addTime = new Date(temp);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
      request.setAttribute("addtime", sdf.format(addTime));
    }
    return new ModelAndView("weixin/guanjia/newstemplate/showmessage");
  }

  @RequestMapping(params={"datagrid"})
  public void datagrid(NewsItem weixinArticle, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid)
  {
    CriteriaQuery cq = new CriteriaQuery(NewsItem.class, dataGrid);

    HqlGenerateUtil.installHql(cq, weixinArticle, request
      .getParameterMap());

    cq.add();
    this.newsItemService.getDataGridReturn(cq, true);
    TagUtil.datagrid(response, dataGrid);
  }

  @RequestMapping(params={"doDel"})
  @ResponseBody
  public AjaxJson doDel(NewsItem weixinArticle, HttpServletRequest request)
  {
    AjaxJson j = new AjaxJson();
    weixinArticle = (NewsItem)this.systemService.getEntity(NewsItem.class, weixinArticle.getId());
    this.message = "微信单图消息删除成功";
    try {
      this.newsItemService.delete(weixinArticle);
      this.systemService.addLog(this.message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
    }
    catch (Exception e) {
      e.printStackTrace();
      this.message = "微信单图消息删除失败";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(this.message);
    return j;
  }

  @RequestMapping(params={"doBatchDel"})
  @ResponseBody
  public AjaxJson doBatchDel(String ids, HttpServletRequest request)
  {
    AjaxJson j = new AjaxJson();
    this.message = "微信单图消息删除成功";
    try {
      for (String id : ids.split(",")) {
        NewsItem weixinArticle = (NewsItem)this.systemService.getEntity(NewsItem.class, id);
        this.newsItemService.delete(weixinArticle);
        this.systemService.addLog(this.message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      this.message = "微信单图消息删除失败";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(this.message);
    return j;
  }

  @RequestMapping(params={"doAdd"})
  @ResponseBody
  public AjaxJson doAdd(NewsItem weixinArticle, HttpServletRequest request)
  {
    AjaxJson j = new AjaxJson();
    this.message = "微信单图消息添加成功";
    try {
      String templateId = request.getParameter("templateId");
      NewsTemplate temp1 = (NewsTemplate)this.systemService.getEntity(NewsTemplate.class, templateId);
      weixinArticle.setNewsTemplate(temp1);
      String accountId = request.getParameter("accountid");
      if (!"-1".equals(accountId))
      {
        this.newsItemService.save(weixinArticle);
      }
      else {
        j.setSuccess(false);
        j.setMsg("请添加一个公众帐号。");
      }
      this.systemService.addLog(this.message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
    }
    catch (Exception e) {
      e.printStackTrace();
      this.message = "微信单图消息添加失败";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(this.message);
    return j;
  }

  @RequestMapping(params={"doUpdate"})
  @ResponseBody
  public AjaxJson doUpdate(NewsItem weixinArticle, HttpServletRequest request)
  {
    AjaxJson j = new AjaxJson();
    this.message = "微信单图消息更新成功";
    NewsItem t = (NewsItem)this.newsItemService.get(NewsItem.class, weixinArticle.getId());
    try {
      MyBeanUtils.copyBeanNotNull2Bean(weixinArticle, t);
      this.newsItemService.saveOrUpdate(t);
      this.systemService.addLog(this.message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
    }
    catch (Exception e) {
      e.printStackTrace();
      this.message = "微信单图消息更新失败";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(this.message);
    return j;
  }

  @RequestMapping(params={"goAdd"})
  public ModelAndView goAdd(NewsItem weixinArticle, HttpServletRequest req)
  {
    String aId = req.getParameter("aId");
    req.setAttribute("accountid", aId);
    String templateId = req.getParameter("templateId");
    req.setAttribute("templateId", templateId);
    if (StringUtil.isNotEmpty(weixinArticle.getId())) {
      weixinArticle = (NewsItem)this.newsItemService.getEntity(NewsItem.class, weixinArticle.getId());
      req.setAttribute("weixinArticlePage", weixinArticle);
    }
    return new ModelAndView("weixin/guanjia/newstemplate/weixinArticle-add");
  }

  @RequestMapping(params={"goUpdate"})
  public ModelAndView goUpdate(NewsItem weixinArticle, HttpServletRequest req)
  {
    if (StringUtil.isNotEmpty(weixinArticle.getId())) {
      weixinArticle = (NewsItem)this.newsItemService.getEntity(NewsItem.class, weixinArticle.getId());
      req.setAttribute("weixinArticle", weixinArticle);
    }
    return new ModelAndView("weixin/guanjia/newstemplate/weixinArticle-update");
  }

  @RequestMapping(params={"upload"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public AjaxJson upload(MultipartHttpServletRequest request, HttpServletResponse response)
  {
    AjaxJson j = new AjaxJson();
    Map attributes = new HashMap();
    TSTypegroup tsTypegroup = this.systemService.getTypeGroup("fieltype", "文档分类");
    TSType tsType = this.systemService.getType("files", "附件", tsTypegroup);
    String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));
    String documentTitle = oConvertUtils.getString(request.getParameter("documentTitle"));
    TSDocument document = new TSDocument();
    if (StringUtil.isNotEmpty(fileKey)) {
      document.setId(fileKey);
      document = (TSDocument)this.systemService.getEntity(TSDocument.class, fileKey);
      document.setDocumentTitle(documentTitle);
    }

    document.setSubclassname(MyClassLoader.getPackPath(document));
    document.setCreatedate(DateUtils.gettimestamp());
    document.setTSType(tsType);
    UploadFile uploadFile = new UploadFile(request, document);
    uploadFile.setCusPath("files");
    uploadFile.setSwfpath("swfpath");
    document = (TSDocument)this.systemService.uploadFile(uploadFile);
    attributes.put("url", document.getRealpath());
    attributes.put("fileKey", document.getId());
    attributes.put("name", document.getAttachmenttitle());
    attributes.put("viewhref", "commonController.do?openViewFile&fileid=" + document.getId());
    attributes.put("delurl", "commonController.do?delObjFile&fileKey=" + document.getId());
    j.setMsg("文件添加成功");
    j.setAttributes(attributes);

    return j;
  }
}