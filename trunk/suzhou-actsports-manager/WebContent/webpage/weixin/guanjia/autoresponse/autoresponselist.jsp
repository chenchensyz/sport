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
		关键字名称：<input type="text" name="keywords" id="keywords" value="${keywords}" >
		<button title="查询"  onclick="searchFind()">查询</button></div>
<div region="center" style="padding: 1px;">

<t:datagrid name="autoresponselist" actionUrl="autoResponseController.do?datagrid"  autoLoadData="false"  fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true" ></t:dgCol>
	<t:dgCol title="关键字" field="keyWord" width="100"></t:dgCol>
	<t:dgCol title="名称" field="templateName" width="100"></t:dgCol>
	<t:dgCol title="时间" field="addTime" width="100"></t:dgCol>
	<t:dgCol title="操作" field="opt"></t:dgCol>
	<t:dgDelOpt title="删除" url="autoResponseController.do?del&id={id}" operationCode="deleteAuto"/>
	<t:dgToolBar title="关键字录入" icon="icon-add" url="keywordController.do?addOrUpdate" funname="addFun" operationCode="addAuto"></t:dgToolBar>
 	<t:dgToolBar title="关键字编辑" icon="icon-edit" url="keywordController.do?addOrUpdate" funname="updateFun" operationCode="updateAuto"></t:dgToolBar>
</t:datagrid>
</div></div>
<script type="text/javascript">
function searchFind(){
	var aId=$('#sq').val();
	var keywords=$('#keywords').val();
	$('#autoresponselist').datagrid({url:'autoResponseController.do?datagrid&field=id,keyWord,templateName,addTime&aId='+aId+'&keywords='+keywords,"queryParams": {}});
}

function addFun() {

	var aId=$('#sq').val();
    var url="keywordController.do?addOrUpdate";
    if(aId){
    	url+="&aId="+aId;
    }
	add("编辑信息",url,"autoresponselist","","");
}


function updateFun() {

	var aId=$('#sq').val();
    var url="keywordController.do?addOrUpdate";
    if(aId){
    	url+="&aId="+aId;
    }
	update("编辑信息",url,"autoresponselist","","");
}

</script>
