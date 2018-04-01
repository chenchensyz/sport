<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="system_function_functionList" class="easyui-layout" fit="true">
<div region="center" style="padding:1px;">
<div class="easyui-layout" fit="true">
<div region="north" data-options="border:false" >公众号选择：<select name="sq" id="sq">
		<option value="">---请选择--- </option>
		<c:forEach items="${accountList}" var="alist">
			<option value="${alist.id}" >${alist.accountname}</option>
		</c:forEach>
		</select>
		<button title="查询"  onclick="searchFind()">查询</button></div>
<div region="center" style="padding: 1px;">

<t:datagrid actionUrl="sendAllController.do?datagrid" autoLoadData="false"  name="masslist" queryMode="group" fitColumns="true" idField="id">
<t:dgCol title="编号" field="id" hidden="true" ></t:dgCol>
<t:dgCol title="消息名称" field="templateName" width="100"></t:dgCol>
<t:dgCol title="消息类型" field="msgType" dictionary="massType" width="100"></t:dgCol>
<t:dgCol title="发送时间" field="createDate" width="100" formatter="yyyy-MM-dd"></t:dgCol>
<t:dgCol title="发送状态" field="state" replace="成功_1,失败_0" width="100"></t:dgCol>
<t:dgCol title="操作" field="opt"></t:dgCol>
<t:dgToolBar title="新建群发" icon="icon-add" url="sendAllController.do?goSuView" funname="addFun"></t:dgToolBar>
</t:datagrid>
</div></div>


<script type="text/javascript">
function searchFind(){
	var aId=$('#sq').val();
	$('#masslist').datagrid({url:'sendAccountController.do?datagrid&field=id,templateName,msgType,createDate,state&aId='+aId,"queryParams": {}});
}

function addFun() {

	var aId=$('#sq').val();
    var url="sendAllController.do?goSuView";
    if(aId){
    	url+="&aId="+aId;
    }
	add("编辑信息",url,"masslist","","");
}









</script>
