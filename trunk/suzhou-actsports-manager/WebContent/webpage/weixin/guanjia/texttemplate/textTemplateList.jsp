<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="texttemplatelist" checkbox="true" actionUrl="textTemplateController.do?datagrid"   fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true" ></t:dgCol>
	<t:dgCol title="文本名称" field="templateName" query="true" width="100"></t:dgCol>
	<t:dgCol field="orgCode" hidden="true" title="组织机构代码"></t:dgCol>
	<t:dgCol title="文本内容" field="content" width="100"></t:dgCol>
	<t:dgCol title="分组"  field="groupId"  query="true"  queryMode="single" dictionary="weixin_group,id,name"  width="120"></t:dgCol>
	<t:dgCol title="时间" field="addTime" width="100"></t:dgCol>
	<t:dgCol title="操作" field="opt"></t:dgCol>
	<t:dgDelOpt title="删除" url="textTemplateController.do?del&id={id}" operationCode="deleteText" />
	<t:dgToolBar title="文本录入" icon="icon-add" url="textTemplateController.do?addorUpdate" funname="addFun" operationCode="addText"></t:dgToolBar>
 	<t:dgToolBar title="文本编辑" icon="icon-edit" url="textTemplateController.do?addorUpdate" funname="updateFun" operationCode="updateText"></t:dgToolBar>
 	<t:dgToolBar title="批量删除"  icon="icon-remove" url="textTemplateController.do?doBatchDel" funname="deleteALLSelect('textTemplateController.do?doBatchDel','texttemplatelist')" operationCode="deleteBatch"></t:dgToolBar>
</t:datagrid>


<script type="text/javascript">
function searchFind(){
	var aId=$('#sq').val();
	var keyword=$('#keyword').val();
	$('#texttemplatelist').datagrid({url:'textTemplateController.do?datagrid&field=id,templateName,content,addTime,groupId&keyword='+keyword+'&aId='+aId,"queryParams": {}});
}

function addFun() {

	var aId=$('#sq').val();
    var url="textTemplateController.do?addorUpdate";
    if(aId){
    	url+="&aId="+aId;
    }
	add("编辑信息",url,"texttemplatelist","","");
}


function updateFun() {

	var aId=$('#sq').val();
    var url="textTemplateController.do?addorUpdate";
    if(aId){
    	url+="&aId="+aId;
    }
	update("编辑信息",url,"texttemplatelist","","");
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