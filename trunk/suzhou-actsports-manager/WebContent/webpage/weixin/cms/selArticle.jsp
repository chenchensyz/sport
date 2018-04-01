<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="cmsArticleList" title="文章管理" actionUrl="cmsArticleController.do?datagrid" idField="id" fit="true" sortName="createDate" sortOrder="desc" queryMode="group">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="标题" field="title" query="true"></t:dgCol>
   <t:dgCol title="所属栏目" field="columnId" dictionary="t_cms_weixin_menu,id,name,orgCode='${orgCode}'" query="true"></t:dgCol>
   
  </t:datagrid>
  </div>
 </div>