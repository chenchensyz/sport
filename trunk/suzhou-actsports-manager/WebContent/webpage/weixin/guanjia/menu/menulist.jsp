<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="system_function_functionList" class="easyui-layout" fit="true">
<div region="center" style="padding:1px;">
<t:datagrid actionUrl="menuManagerController.do?datagrid" autoLoadData="false"  name="menuList" queryMode="group" treegrid="true" fitColumns="true" idField="id">
<t:dgCol field="id" treefield="id" title="主键" hidden="true"></t:dgCol>
<t:dgCol field="name" treefield="text" title="菜单名称" query="false" width="100"></t:dgCol> 
<t:dgCol field="type" treefield="src" title="菜单类型" replace="消息触发类_click,网页链接类_view" width="100" ></t:dgCol>
<t:dgCol field="orders" treefield="order" title="顺序" width="100"></t:dgCol>

请选择公众号：<select name="sq" id="sq">
	<option value="">---请选择--- </option>
	<c:forEach items="${accountList}" var="alist">
		<option value="${alist.id}" >${alist.accountname}</option>
	</c:forEach>
</select>
<button title="查询"  onclick="searchFind()">查询</button>

<t:dgCol title="操作" field="opt"></t:dgCol>
<t:dgDelOpt title="删除" url="menuManagerController.do?del&id={id}" operationCode="deleteMenu" />
<t:dgToolBar title="录入菜单" icon="icon-add" url="menuManagerController.do?jumpSuView" funname="addFun" operationCode="addMenu"></t:dgToolBar>
<t:dgToolBar title="菜单编辑" icon="icon-edit" url="menuManagerController.do?jumpSuView" funname="update" operationCode="updateMenu"></t:dgToolBar>
<t:dgToolBar title="菜单同步到微信" icon="icon-edit" url="menuManagerController.do?sameMenu" funname="sameMenu" operationCode="sameMenu"></t:dgToolBar>
</t:datagrid>
</div></div>

<script type="text/javascript">
function searchFind(){
	var aId=$('#sq').val();
	$('#menuList').treegrid({url:'menuManagerController.do?datagrid&field=id,name,type,orders&aId='+aId,"queryParams": {}});
}

function addFun() {
	var aId=$('#sq').val();
    var row = $('#menuList').datagrid('getSelected');
    var url = "menuManagerController.do?jumpSuView";
    if(row){
    	url += "&fatherId="+row.id;
    	
    }
    if(aId){
    	url += "&aId="+aId;
    }
	add("编辑信息",url,"menuList","","");
}

function edite(id) {
    var url = "menuManagerController.do?jumpSuView&id="+id;
	add("编辑信息",url,"menuList","","");
}


function sameMenu(){
	var aId=$('#sq').val();
	$.ajax({
		url:"menuManagerController.do?sameMenu&aId="+aId,
		type:"GET",
		dataType:"JSON",
		success:function(data){
			if(data.success){
				tip(data.msg);
			}
		}
	});
}

</script>