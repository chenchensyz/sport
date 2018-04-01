<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="north" data-options="border:false" >公众号选择：<select name="sq" id="sq">
		<option value="">---请选择--- </option>
		<c:forEach items="${accountList}" var="alist">
			<option value="${alist.id}" >${alist.accountname}</option>
		</c:forEach>
		</select>
		<button title="查询"  onclick="searchFind()">查询</button></div>
<div region="center" style="padding: 1px;">
<t:datagrid name="gzlist" actionUrl="subscribeController.do?datagrid" fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true" ></t:dgCol>
	<t:dgCol title=" 模板名称" field="templateName"  width="100"></t:dgCol>
	<t:dgCol title="类型" field="msgType" replace="文本_text,图文_news" width="100"></t:dgCol>
	<t:dgCol title="时间" field="addTime" width="100"></t:dgCol>
	<t:dgCol title="操作" field="opt"></t:dgCol>
	<t:dgDelOpt title="删除" url="subscribeController.do?del&id={id}" operationCode="deleteGz" />
	<t:dgToolBar title="信息录入" icon="icon-add" url="subscribeController.do?jumpSuView" funname="addFun" operationCode="addGz"></t:dgToolBar>
 	<t:dgToolBar title="信息编辑" icon="icon-edit" url="subscribeController.do?jumpSuView" funname="updateFun" operationCode="updateGz"></t:dgToolBar>
</t:datagrid>
</div></div>
<script type="text/javascript">
<!--
//-->
function searchFind(){
	var aId=$('#sq').val();
	$('#gzlist').datagrid({url:'subscribeController.do?datagrid&field=id,templateName,msgType,addTime&aId='+aId,"queryParams": {}});
}

function addFun(){
	var aId=$('#sq').val();
	var url="subscribeController.do?jumpSuView";
    if(aId){
    	url+="&aId="+aId;
    }
	add("编辑信息",url,"gzlist","","");
	
}

function updateFun() {

	var aId=$('#sq').val();
    var url="subscribeController.do?jumpSuView";
    if(aId){
    	url+="&aId="+aId;
    }
	update("编辑信息",url,"gzlist","","");
}


function myadd(title,addurl,gname,width,height) {
		gridname=gname;
		var getData = $('#'+gridname).datagrid('getData');
		if(getData.total!=0){
			tip('一个用户只能创建一个关注欢迎语');
			return;
		}
		createwindow(title, addurl,width,height);
	}
</script>

