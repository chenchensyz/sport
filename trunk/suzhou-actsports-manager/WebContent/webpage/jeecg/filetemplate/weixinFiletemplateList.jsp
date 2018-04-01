<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

  <t:datagrid name="weixinFiletemplateList" checkbox="true" fitColumns="true"   autoLoadData="false" actionUrl="weixinFiletemplateController.do?datagrid" idField="id" fit="true" queryMode="group">
   <input type="hidden" id="maintype" name="maintype" value="${maintype}" />
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="名称"  field="templateName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="素材类型"  field="type"   query="false" queryMode="single" dictionary="sc_type" width="120"></t:dgCol>
   <%-- <t:dgCol title="图片预览" field="scFilepath"  image="true"  imageSize="40,40" width="120"></t:dgCol> --%>
   <t:dgCol title="创建时间"  field="createTime" formatter="yyyy-MM-dd"  query="false" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="素材简介"  field="introduction"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="分组"  field="groupId"  query="true"  queryMode="single" dictionary="weixin_group,id,name"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <t:dgDelOpt title="删除" url="weixinFiletemplateController.do?doDel&id={id}" />
   <t:dgFunOpt funname="previewFile(id)" title="预览"></t:dgFunOpt>
   <t:dgToolBar title="录入" icon="icon-add" url="weixinFiletemplateController.do?goAdd" funname="addFile()"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="weixinFiletemplateController.do?goUpdate" funname="updateFile()"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="weixinFiletemplateController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="weixinFiletemplateController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>

		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 		var maintype=$('#maintype').val();
		$('#weixinFiletemplateList').datagrid({url:'weixinFiletemplateController.do?datagrid&field=id,scFilepath,templateName,type,createTime,introduction,groupId&maintype='+maintype,"queryParams": {}});
 }); 
 
 function weixinFiletemplateListsearch() {
	 	var maintype=$('#maintype').val();
	    if ($("#weixinFiletemplateListForm").Validform({
	        tiptype: 3
	    }).check()) {
	        var queryParams = $('#weixinFiletemplateList').datagrid('options').queryParams;
	        $('#weixinFiletemplateListtb').find('*').each(function() {
	            queryParams[$(this).attr('name')] = $(this).val();
	        });
	        $('#weixinFiletemplateList').datagrid({
	            url: 'weixinFiletemplateController.do?datagrid&field=id,id_begin,id_end,templateName,type,createTime,createTime_begin,createTime_end,introduction,groupId&maintype='+maintype,
	            pageNumber: 1
	        });
	    }
	}
 
 function previewFile(id){
	 var maintype = "${maintype}";
	 var content=""
	 var url = "";
	 
	 screenW = (window.top.document.body.offsetWidth)*0.5;
	 screenH =(window.top.document.body.offsetHeight-100)*0.6;
	 
	 $.ajax({
			url: "weixinFiletemplateController.do?previewFile",
			data: {
				"id": id
			},
			async:false,
			dataType: "text",
			type: "POST",
			success: function(data) {
				url = data;
			}
		});
	 
	 if(maintype == "image"){
		 content='<img style="-webkit-user-select: none" src="'+url+'">';
	 } else {
		 content='<video  width="'+screenW+'" height="'+screenH+'" controls autoplay><source src="'+url+'" ><object data="movie.mp4"  width="'+screenW+'" height="'+screenH+'"><embed  width="'+screenW+'" height="'+screenH+'" src=""></object></video>';
	 }
	 
	 $.dialog({
			content: content,
			zIndex: getzIndex(false),
			title : '预览',
			cache:false,
			lock : true,
			width: screenW,
		    height: screenH
		});
 }
 
 function addFile(){
	 var maintype=$('#maintype').val();
	 var url = "weixinFiletemplateController.do?goAdd&maintype="+maintype;
	 add('录入',url,'weixinFiletemplateList',null,null)
 }
 
 function updateFile(){
	 var maintype=$('#maintype').val();
	 var url = "weixinFiletemplateController.do?goUpdate&maintype="+maintype;
	 update('编辑',url,'weixinFiletemplateList',null,null)
 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'weixinFiletemplateController.do?upload', "weixinFiletemplateList");
}

//导出
function ExportXls() {
	JeecgExcelExport("weixinFiletemplateController.do?exportXls","weixinFiletemplateList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("weixinFiletemplateController.do?exportXlsByT","weixinFiletemplateList");
}
 </script>