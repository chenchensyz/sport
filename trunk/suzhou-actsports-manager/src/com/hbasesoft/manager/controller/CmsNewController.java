package com.hbasesoft.manager.controller;

import com.hbasesoft.framework.manager.cms.entity.AdEntity;
import com.hbasesoft.framework.manager.cms.entity.CmsArticleEntity;
import com.hbasesoft.framework.manager.cms.entity.CmsMenuEntity;
import com.hbasesoft.framework.manager.cms.service.CmsMenuServiceI;
import com.hbasesoft.framework.manager.core.common.controller.BaseController;
import com.hbasesoft.framework.manager.core.common.hibernate.qbc.CriteriaQuery;
import com.hbasesoft.framework.manager.core.common.model.json.AjaxJson;
import com.hbasesoft.framework.manager.core.common.model.json.ComboTree;
import com.hbasesoft.framework.manager.core.common.model.json.DataGrid;
import com.hbasesoft.framework.manager.core.common.model.json.TreeGrid;
import com.hbasesoft.framework.manager.core.constant.Globals;
import com.hbasesoft.framework.manager.core.extend.hqlsearch.HqlGenerateUtil;
import com.hbasesoft.framework.manager.core.util.MyBeanUtils;
import com.hbasesoft.framework.manager.core.util.ResourceUtil;
import com.hbasesoft.framework.manager.core.util.StringUtil;
import com.hbasesoft.framework.manager.tag.vo.datatable.SortDirection;
import com.hbasesoft.framework.manager.tag.vo.easyui.TreeGridModel;
import com.hbasesoft.framework.manager.web.system.service.SystemService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/cmsNewController"})
public class CmsNewController extends BaseController
{
  private static final Logger logger = Logger.getLogger(CmsNewController.class)
    ;

  @Autowired
  private CmsMenuServiceI cmsMenuService;

  @Autowired
  private SystemService systemService;
  private String message;

  public String getMessage() { return this.message; }

  public void setMessage(String message)
  {
    this.message = message;
  }

  @RequestMapping(params={"index"})
  public ModelAndView index(HttpServletRequest request, String orgCode, String userid)
  {
    List columnList = this.cmsMenuService.findByProperty(CmsMenuEntity.class, "orgCode", orgCode);

    List adList = this.systemService.findByProperty(AdEntity.class, "orgCode", orgCode);

    request.setAttribute("columnList", columnList);
    request.setAttribute("adList", adList);
    request.setAttribute("orgCode", orgCode);
    request.setAttribute("userid", userid);
    return new ModelAndView("weixin/cms/index");
  }

  @RequestMapping(params={"menu"})
  public ModelAndView menu(HttpServletRequest request)
  {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("weixin/cms/cmsMenuList");
    return mv;
  }

  @RequestMapping(params={"datagrid"})
  @ResponseBody
  public List<TreeGrid> datagrid(TreeGrid treegrid, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid)
  {
    CriteriaQuery cq = new CriteriaQuery(CmsMenuEntity.class, dataGrid);

    HqlGenerateUtil.installHql(cq, request.getParameterMap());
    if (treegrid.getId() != null) {
      cq.eq("pmenu.id", treegrid.getId());
    }
    else {
      cq.isNull("pmenu");
    }
    
    //查询当前登录用户
    String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
    cq.eq("orgCode",orgCode);
    cq.addOrder("orders", SortDirection.asc);
    cq.add();

  
    
    List<CmsMenuEntity> menuList = this.systemService.getListByCriteriaQuery(cq, Boolean.valueOf(false));
    List treeGrids = new ArrayList();
    TreeGridModel treeGridModel = new TreeGridModel();
    treeGridModel.setTextField("name");
    treeGridModel.setIcon("showFlag");
    treeGridModel.setOrder("orders");
    treeGridModel.setSrc("type");
    treeGridModel.setIdField("id");
    treeGridModel.setChildList("menuList");

    

    treeGrids = this.systemService.treegrid(menuList, treeGridModel);
    for (Iterator localIterator1 = treeGrids.iterator(); localIterator1.hasNext(); ) {TreeGrid obj = (TreeGrid)localIterator1.next();
      for (CmsMenuEntity menu : menuList)
        if (obj.getId().equals(menu.getId())) {
          Map attr = new HashMap();
          attr.put("linkUrl", menu.getLinkUrl());
          attr.put("menuCode", menu.getMenuCode());
          obj.setAttributes(attr);
          break;
        }
    }
    TreeGrid obj;
    return treeGrids;
  }

  @RequestMapping(params={"del"})
  @ResponseBody
  public AjaxJson del(CmsMenuEntity menu, HttpServletRequest request)
  {
    AjaxJson j = new AjaxJson();
    menu = (CmsMenuEntity)this.systemService.getEntity(CmsMenuEntity.class, menu.getId());
    this.message = "栏目管理删除成功";
    this.cmsMenuService.delete(menu);
    this.systemService.addLog(this.message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

    j.setMsg(this.message);
    return j;
  }

  @RequestMapping(params={"save"})
  @ResponseBody
  public AjaxJson save(CmsMenuEntity menu, HttpServletRequest request)
  {
    AjaxJson j = new AjaxJson();
    String pid = request.getParameter("parentId");
    if (StringUtil.isEmpty(pid)) {
      menu.setPmenu(null);
    }
    String orgCode = ResourceUtil.getUserSystemData("sysOrgCode");

    if (menu.getType().equals("02"))
    {
      List articles = this.cmsMenuService
        .findByProperty(CmsArticleEntity.class, "columnId", menu
        .getId());
      if ((null != articles) && (articles.size() > 0)) {
        CmsArticleEntity article = (CmsArticleEntity)articles.get(0);
        article.setColumnId(null);
      }
    }

    String articleId = request.getParameter("articleId");
    CmsArticleEntity article = null;
    if (StringUtil.isNotEmpty(articleId)) {
      article = (CmsArticleEntity)this.cmsMenuService.get(CmsArticleEntity.class, articleId);
    }

    if (StringUtil.isNotEmpty(menu.getId())) {
      this.message = "栏目管理更新成功";
      CmsMenuEntity t = (CmsMenuEntity)this.cmsMenuService.get(CmsMenuEntity.class, menu
        .getId());
      try {
        MyBeanUtils.copyBeanNotNull2Bean(menu, t);
        t.setOrders(menu.getOrders());
        if (!StringUtil.isEmpty(pid)) {
          CmsMenuEntity pmenu = new CmsMenuEntity();
          pmenu = (CmsMenuEntity)this.cmsMenuService.getEntity(CmsMenuEntity.class, pid);

          t.setPmenu(pmenu);
          t.setParentId(pid);
        }

        if ("01".equals(menu.getType()))
        {
          if ((menu.getCusTemplate() != null) && (menu.getCusTemplate() != ""))
          {
            t.setLinkUrl("cmsController.do?goPage&page=" + menu
              .getCusTemplate() + "&id=" + menu
              .getId());
          }
          else t.setLinkUrl("cmsController.do?goPage&page=newsList&id=" + menu
              .getId());

        }
        else if ("02".equals(menu.getType()))
        {
          if ((menu.getCusTemplate() != null) && (menu.getCusTemplate() != ""))
          {
            t.setLinkUrl("cmsController.do?goPage&page=" + menu
              .getCusTemplate() + "&articleid=" + menu
              .getId());
          }
          else t.setLinkUrl("cmsController.do?goPage&page=article&articleid=" + articleId);

        }

        this.cmsMenuService.saveOrUpdate(t);

        if (article != null) {
          article.setColumnId(t.getId());
        }

        this.systemService.addLog(this.message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
      }
      catch (Exception e) {
        e.printStackTrace();
        this.message = "栏目管理更新失败";
      }
    } else {
      this.message = "栏目管理添加成功";
      menu.setOrgCode(orgCode);

      if (!StringUtil.isEmpty(pid)) {
        CmsMenuEntity pmenu = new CmsMenuEntity();
        pmenu = (CmsMenuEntity)this.cmsMenuService.getEntity(CmsMenuEntity.class, pid);
        menu.setPmenu(pmenu);
        menu.setParentId(pid);
      }

      String id = (String)this.cmsMenuService.save(menu);
      CmsMenuEntity menuNew = new CmsMenuEntity();
      menuNew = (CmsMenuEntity)this.cmsMenuService.getEntity(CmsMenuEntity.class, id);
      if ("01".equals(menu.getType()))
      {
        if ((menu.getCusTemplate() != null) && (menu.getCusTemplate() != ""))
        {
          menuNew.setLinkUrl("cmsController.do?goPage&page=" + menu
            .getCusTemplate() + "&id=" + id);
        }
        else menuNew.setLinkUrl("cmsController.do?goPage&page=newsList&id=" + id);

      }
      else if ("02".equals(menu.getType()))
      {
        if ((menu.getCusTemplate() != null) && (menu.getCusTemplate() != ""))
        {
          menuNew.setLinkUrl("cmsController.do?goPage&page=" + menu
            .getCusTemplate() + "&articleid=" + id);
        }
        else menuNew.setLinkUrl("cmsController.do?goPage&page=article&articleid=" + id);

      }

      if (article != null) {
        article.setColumnId(menu.getId());
      }

      this.systemService.addLog(this.message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
    }

    j.setMsg(this.message);
    return j;
  }

  @RequestMapping(params={"addorupdate"})
  public ModelAndView addorupdate(CmsMenuEntity menu, HttpServletRequest req)
  {
    ModelAndView mv = new ModelAndView();
    if (StringUtil.isNotEmpty(menu.getId())) {
      menu = (CmsMenuEntity)this.cmsMenuService.getEntity(CmsMenuEntity.class, menu.getId());
      req.setAttribute("columnPage", menu);
      if (menu.getPmenu() != null) {
        req.setAttribute("parentId", menu.getPmenu().getId());
      }
    }
    String visitType = menu.getVisitType();
    if (StringUtil.isEmpty(visitType))
      req.setAttribute("visitType", "1");
    else {
      req.setAttribute("visitType", visitType);
    }
    String parentId = req.getParameter("parentId");
    if (StringUtil.isNotEmpty(parentId)) {
      CmsMenuEntity tmenu = (CmsMenuEntity)this.cmsMenuService.getEntity(CmsMenuEntity.class, parentId);

      CmsMenuEntity pmenu = new CmsMenuEntity();
      pmenu.setId(tmenu.getId());
      pmenu.setName(tmenu.getName());
      req.setAttribute("pmenu", pmenu);
      req.setAttribute("parentId", parentId);
    }
    if ((menu != null) && 
      (menu.getType() != null) && (menu.getType().equals("02")))
    {
      List articles = this.cmsMenuService
        .findByProperty(CmsArticleEntity.class, "columnId", menu
        .getId());
      if ((null != articles) && (articles.size() > 0)) {
        CmsArticleEntity article = (CmsArticleEntity)articles.get(0);
        req.setAttribute("articleId", article.getId());
        req.setAttribute("articleTitle", article.getTitle());
      }

    }

    mv.setViewName("weixin/cms/cmsMenu");
    return mv;
  }

  @RequestMapping(params={"treeMenu"})
  @ResponseBody
  public List<TreeGrid> treeMenu(HttpServletRequest request, ComboTree comboTree) {
    CriteriaQuery cq = new CriteriaQuery(CmsMenuEntity.class);
    if (StringUtil.isNotEmpty(comboTree.getId())) {
      cq.eq("pmenu.id", comboTree.getId());
    }
    if (StringUtil.isEmpty(comboTree.getId())) {
      cq.isNull("pmenu.id");
    }
    cq.eq("orgCode", ResourceUtil.getUserSystemData("sysOrgCode"));
    cq.add();
    List menuList = this.cmsMenuService.getListByCriteriaQuery(cq, 
      Boolean.valueOf(false));

    List treeGrids = new ArrayList();
    TreeGridModel treeGridModel = new TreeGridModel();

    treeGridModel.setTextField("name");
    treeGridModel.setParentText("pmenu_name");
    treeGridModel.setParentId("pmenu_id");
    treeGridModel.setSrc("imageHref");
    treeGridModel.setIdField("id");
    treeGridModel.setChildList("menuList");
    treeGrids = this.systemService.treegrid(menuList, treeGridModel);

    return treeGrids;
  }
}