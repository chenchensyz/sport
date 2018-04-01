<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="cmsArticleList" title="文章管理" actionUrl="cmsArticleController.do?datagrid" idField="id" fit="true" sortName="createDate" sortOrder="desc" queryMode="group" checkbox="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="标题" field="title" query="true"></t:dgCol>
   <t:dgCol title="所属栏目" field="columnId" dictionary="t_cms_weixin_menu,id,name,orgCode='${orgCode}'" query="true"></t:dgCol>
   <t:dgCol title="摘要" field="summary"></t:dgCol>
   <t:dgCol title="顺序" field="articleSort"></t:dgCol>
   <t:dgCol title="图片名称" field="imageName" hidden="false"></t:dgCol>
   <t:dgCol title="图片地址" field="imageHref" hidden="false"></t:dgCol>
   <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="组织机构" field="orgCode" hidden="true"></t:dgCol>
   <t:dgCol title="内容" field="content" hidden="true"></t:dgCol>
   <t:dgCol title="url" field="url" hidden="true"></t:dgCol>
   <t:dgCol title="操作" field="opt" ></t:dgCol>
   <t:dgDelOpt title="删除" url="cmsArticleController.do?del&id={id}" operationCode="deleteArticle" />
   <t:dgToolBar title="录入" icon="icon-add" url="cmsArticleController.do?addorupdate" funname="add" width="100%" height="100%" operationCode="addArticle" ></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="cmsArticleController.do?doBatchDel" funname="deleteALLSelect" operationCode="deleteBatch" ></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="cmsArticleController.do?addorupdate" funname="update" width="100%" height="100%" operationCode="updateArticle" ></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="cmsArticleController.do?addorupdate" funname="detail" width="100%" height="100%" operationCode="readArticle" ></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 	function previewArticle(id){
 		var url = null;
 		$.ajax({
 			url:"cmsArticleController.do?previewUrl",
 			data: {
				"id": id
			},
			async:false,
 			type:"POST",
 			dataType:"text",
 			success:function(data){
 				url = data;
 			}
 		});
 		createwindow("预览", url,500,500);
 	}
 </script>