<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>会员分组管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  $(document).ready(function() {
	  
	  var templateId = '${weixinReleasePage.newsId}';
	  if(templateId) {
		  var wgroup = $("#groupName").val();
			var templateObj = $("#templateId");
			templateObj.html("");
			$.ajax({
				url: "weixinReleaseController.do?getNews",
				data: {
					"wgroup":wgroup
				},
				dataType: "JSON",
				type: "POST",
				success: function(data) {
					var msg = "";
					msg += "<option value=''>------</option>";
					for(var i = 0; i < data.length; i++) {
						if(templateId == data[i].id) {
								msg += "<option value='" + data[i].id + "' selected='selected'>" +
									data[i].title +
									"</option>";
						} else {
							   msg += "<option value='" + data[i].id + "'>" + data[i].title + "</option>";
						}
					}
					templateObj.html(msg);
				}
			});
		}
	  
	  
	  
	  $("#groupName").change(function(){
		  var templateObj = $("#templateId");
			var wgroup = $("#groupName").val();
			templateObj.html("");
			$.ajax({
				url: "weixinReleaseController.do?getNews",
				data: {
					"wgroup":wgroup
				},
				dataType: "JSON",
				type: "POST",
				success: function(data) {
					var msg = "";
					msg += "<option value=''>------</option>";
					for(var i = 0; i < data.length; i++) {
						msg += "<option value='" + data[i].id + "'>" +
						data[i].title + "</option>";
					}
					templateObj.html(msg);
				}
			});
			
		})
	  
  })
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="weixinReleaseController.do?doUpdate" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${weixinReleasePage.id }">
					<input id="position" name="position" type="hidden" value="${weixinReleasePage.position }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								所属栏目:
							</label>
						</td>
						<td class="value">
							<select id="fatherId" name="fatherId">
								<c:forEach items="${cmsMenuList}" var = "cms">
									<option value="${cms.id}" <c:if test="${weixinReleasePage.fatherId == cms.id}">selected=selected</c:if>>${cms.name}</option>
								</c:forEach>
							</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">所属栏目</label>
						</td>
					</tr>
					<tr>
					<td align="right" style="width: 65px"><label class="Validform_label">分组选择：</label></td>
					<td colspan="3" class="value">
						<select name="groupName" id="groupName">
							<option value="">------</option>
								<c:forEach items="${groupList}" var="wgroup">
									<option value="${wgroup.id}" <c:if test="${wgroupId==wgroup.id}">selected="selected"</c:if>>${wgroup.name}</option>
								</c:forEach>
						</select>
					</td>
				</tr>
				<tr id="templateTr" style="width: 65px">
					<td align="right"><label class="Validform_label"> 选择模板:</label></td>
					<td class="value" colspan="3">
						<select name="templateId" id="templateId" datatype="*" ></select> 
						<span class="Validform_checktip">选择模板</span>
					</td>
				</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								顺序:
							</label>
						</td>
						<td class="value">
						     	 <input id="orders" name="orders" type="text" style="width: 150px" class="inputxt"  value='${weixinReleasePage.orders}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">顺序</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/release/weixinRelease.js"></script>		
