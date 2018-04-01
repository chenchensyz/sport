<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="weixinReleaseList" checkbox="true" fitColumns="false" title="微发布" actionUrl="weixinReleaseController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="320"></t:dgCol>
   <t:dgCol title="所属栏目"  field="fatherId"    queryMode="group" dictionary="t_cms_weixin_menu,id,name"  width="320"></t:dgCol>
   <t:dgCol title="图文"  field="newsId"    queryMode="group" dictionary="weixin_newsitem,id,title"  width="320"></t:dgCol>
   <t:dgCol title="顺序"  field="orders"   query="true" queryMode="single"  width="320"></t:dgCol>
   <t:dgCol title="位置"  field="position"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="weixinReleaseController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="weixinReleaseController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="weixinReleaseController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="weixinReleaseController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="weixinReleaseController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/release/weixinReleaseList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'weixinReleaseController.do?upload', "weixinReleaseList");
}

//导出
function ExportXls() {
	JeecgExcelExport("weixinReleaseController.do?exportXls","weixinReleaseList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("weixinReleaseController.do?exportXlsByT","weixinReleaseList");
}
 </script>