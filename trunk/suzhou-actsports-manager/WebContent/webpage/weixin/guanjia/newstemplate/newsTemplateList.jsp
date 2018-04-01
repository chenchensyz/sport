<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="newstemplatelist" checkbox="true"  actionUrl="newsTemplateController.do?datagrid"     fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true" ></t:dgCol>
	
	<t:dgCol title="图文名称" field="templateName" query="true" width="100"></t:dgCol>
	<t:dgCol title="时间" field="addTime" width="100"></t:dgCol>
	<t:dgCol title="分组"  field="groupId"  query="true"  queryMode="single" dictionary="weixin_group,id,name"  width="120"></t:dgCol>
	<t:dgCol title="操作" field="opt"></t:dgCol>
	<t:dgDelOpt title="删除" url="newsTemplateController.do?del&id={id}" operationCode="deleteNews" />
	<t:dgFunOpt funname="addItem(id)" title="添加图文" operationCode="addItem"></t:dgFunOpt>
	<t:dgFunOpt funname="readNews(id)" title="查看图文" operationCode="readItem"></t:dgFunOpt>
	<t:dgToolBar title="图文录入" icon="icon-add" url="newsTemplateController.do?goSuView" funname="addFun" operationCode="addNews"></t:dgToolBar>
 	<t:dgToolBar title="图文编辑" icon="icon-edit" url="newsTemplateController.do?goSuView" funname="updateFun" operationCode="readNews"></t:dgToolBar>
 	<t:dgToolBar title="批量删除"  icon="icon-remove" url="newsTemplateController.do?doBatchDel" funname="deleteALLSelect('newsTemplateController.do?doBatchDel','newstemplatelist')" operationCode="batchDelete"></t:dgToolBar>
</t:datagrid>
<script type="text/javascript">
function searchFind(){
	var aId=$('#sq').val();
	var keyword=$('#keyword').val();
	$('#newstemplatelist').datagrid({url:'newsTemplateController.do?datagrid&field=id,templateName,addTime,groupId&keyword='+keyword+'&aId='+aId,"queryParams": {}});
}




function addFun() {

	var aId=$('#sq').val();
    var url="newsTemplateController.do?goSuView";
    if(aId){
    	url+="&aId="+aId;
    }
	add("编辑信息",url,"newstemplatelist","","");
}


function updateFun() {

	var aId=$('#sq').val();
    var url="newsTemplateController.do?goSuView";
    if(aId){
    	url+="&aId="+aId;
    }
	update("编辑信息",url,"newstemplatelist","","");
}


function readNews(id){
	var url = "weixinArticleController.do?goMessage&templateId="+id;
	createdetailwindow('图文编辑','weixinArticleController.do?goMessage&templateId='+id,'newstemplatelist','100%', '100%');
}

function addItem(id){
	
	add('添加图文','weixinArticleController.do?goAdd&templateId='+id,'newstemplatelist' ,'100%', '100%');
}


function deleteALLSelect(url,gname) {
	gridname=gname;
    var ids = [];
    var rows = $("#"+gname).datagrid('getSelections');
    if (rows.length > 0) {
    	$.dialog.setting.zIndex = getzIndex(false);
    	$.dialog.confirm('你确定永久删除该数据吗?', function(r) {
		   if (r) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				$.ajax({
					url : url,
					type : 'post',
					data : {
						ids : ids.join(',')
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#"+gname).datagrid('unselectAll');
							ids='';
						}
					}
				});
			}
		});
	} else {
		tip("请选择需要删除的数据");
	}
}


</script>

