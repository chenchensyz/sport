<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid actionUrl="cmsNewController.do?datagrid" pagination="false" name="menuList" queryMode="group" treegrid="true" fitColumns="true" idField="id" >
      <t:dgCol title="编号" field="id" treefield="id" hidden="true"></t:dgCol>
      <t:dgCol title="栏目名称" field="name" treefield="text"></t:dgCol>
      <t:dgCol title="栏目编码" field="menuCode" treefield="menuCode" extendParams="formatter:function(value, rec, index){return rec.attributes.menuCode},"></t:dgCol>
      <t:dgCol title="栏目类型" field="type" treefield="src" dictionary="cms_menu" ></t:dgCol>
      <t:dgCol title="显示顺序" field="orders" treefield="order"></t:dgCol>
      <t:dgCol title="是否显示" field="showFlag" treefield="code" dictionary="yesorno" ></t:dgCol>
      <t:dgCol title="操作" field="opt" treefield="111"></t:dgCol>
      <t:dgDelOpt title="删除" url="cmsMenuController.do?del&id={id}" operationCode="deleteMenu" />
      <t:dgToolBar title="录入" icon="icon-add" url="cmsMenuController.do?addorupdate" funname="addMenu()" width="100%" height="100%" operationCode="addMenu"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="icon-edit" url="cmsMenuController.do?addorupdate" funname="updateMenu()" width="100%" height="100%" operationCode="updateMenu"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="icon-search" url="cmsMenuController.do?addorupdate" funname="detail" width="100%" height="100%" operationCode="readMenu"></t:dgToolBar>
    </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
function addMenu() {

    var row = $('#menuList').datagrid('getSelected');
    var url = "cmsMenuController.do?addorupdate";
    if(row){
    	url += "&parentId="+row.id;
    }
	add("编辑信息",url,"menuList","100%","100%");
}
function updateMenu() {
    var row = $('#menuList').datagrid('getSelected');
    var url = "cmsMenuController.do?addorupdate";
    if(row){
    	url += "&id="+row.id;
    }
	add("编辑信息",url,"menuList","100%","100%");
}

</script>