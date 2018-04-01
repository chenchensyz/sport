<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html>
<html>

<head>
	<title>群发消息录入</title>
	<t:base type="jquery,easyui,tools"></t:base>
	<script type="text/javascript">
	    	    
/* 	    $(function(){
	    	$("#wechatName").change(function(){
				
				var accountId = $("#wechatName").val();
				
				$("#accountid").val(accountId);
				var templateObj = $("#groupid");
				
				templateObj.html("");
				
				$.ajax({
					url: "sendAllController.do?getuserlist",
					data: {
						"accountId": accountId
					},
					dataType: "JSON",
					type: "POST",
					success: function(data) {
						var msg = "";
						msg += "<option value=''>------</option>";
						for(var i = 0; i < data.length; i++) {
							msg += "<option value='" + data[i].id + "'>" +
											data[i].name + "</option>";
						templateObj.html(msg);
						}
					}
				});
				
			})
	    }) */
	    
	
		
		$(document).ready(function() {
			var fid = "${fatherId}";
			var msgType = "${msgType}";
			var type = "${type}";
			var templateId = "${templateId}";
			
			//var supMenuId="${fatherName}";
			if(templateId) {
				var templateObj = $("#templateId");
				templateObj.html("");
				$.ajax({
					url: "snedAllController.do?gettemplate&accountId="+$("#wechatName").val(),
					data: {
						"msgType": msgType
					},
					dataType: "JSON",
					type: "POST",
					success: function(data) {
						var msg = "";
						for(var i = 0; i < data.length; i++) {
							if(templateId == data[i].id) {
								if(msgType == "expand") {
									msg += "<option value='" + data[i].id + "' selected='selected'>" +
										data[i].name +
										"</option>";
								} else {
									msg += "<option value='" + data[i].id + "' selected='selected'>" +
										data[i].templateName +
										"</option>";
								}
							} else {
								if(msgType == "expand") {
								   msg += "<option value='" + data[i].id + "'>" + data[i].name +"</option>";
								} else {
								   msg += "<option value='" + data[i].id + "'>" + data[i].templateName + "</option>";
								}
							}
						}
						templateObj.html(msg);
					}
				});
			}
			
			$("#groupName").change(function(){
				var textVal=$("#mtype").val();
				
				if("text" == textVal) {
					getTemplates("text");
				} else if("expand" == textVal) {
					getTemplates("expand");
				} else if("news" == textVal) {
					getTemplates("news");
				} else if("image" == textVal) {
					getTemplates("image");
				} else if("voice" == textVal) {
					getTemplates("voice");
				} else if("video" == textVal) {
					getTemplates("video");
				}
				
			})			
			
			
			$("#mtype").change(function(){
				var textVal=$("#mtype").val();
				
				if("text" == textVal) {
					getTemplates("text");
				} else if("expand" == textVal) {
					getTemplates("expand");
				} else if("news" == textVal) {
					getTemplates("news");
				} else if("image" == textVal) {
					getTemplates("image");
				} else if("voice" == textVal) {
					getTemplates("voice");
				} else if("video" == textVal) {
					getTemplates("video");
				}
				
			})			
			//获取动作设置选中的项,用于初始化依赖dom元素
			var typeVal = $("#type").val(); // 动作设置选中项的值
			if("click" == typeVal) {
				$("#xxtr").show();
				$("#trurl").hide();
				$("#templateTr").show();
				//设置消息类型和验证
				$("#msgType").attr("datatype", "*");
				$("#templateId").attr("datatype", "*");
			} else if("view" == typeVal) {
				$("#xxtr").hide();
				$("#trurl").show();
				$("#templateTr").hide();
				$("#msgType").removeAttr("datatype");
				$("#templateId").removeAttr("datatype");
			}

			if(typeof(fatherId) == "undefined") {
				$("#msgType").removeAttr("datatype");
				$("#templateId").removeAttr("datatype");
			}

		});

		function getTemplates(msgType) {
			var templateObj = $("#templateId");
			var wgroup = $("#groupName").val();
			
			templateObj.html("");
			$.ajax({
				url: "sendAccountController.do?gettemplate&accountId="+$("#wechatName").val(),
				data: {
					"msgType": msgType,
					"wgroup":wgroup
				},
				dataType: "JSON",
				type: "POST",
				success: function(data) {
					var msg = "";
					msg += "<option value=''>------</option>";
					for(var i = 0; i < data.length; i++) {
						if(msgType == "expand") {
							msg += "<option value='" + data[i].id + "'>" + data[i].name +
								"</option>";
								
						} else {
							msg += "<option value='" + data[i].id + "'>" +
								data[i].templateName + "</option>";
						}
					}
					templateObj.html(msg);
				}
			});
		}
		   
	       function selAb() {
	       		var accountappid = $("#wechatName").find("option:selected").val();
				console.log(accountappid);
				$.ajax({
					type:"post",
					url:"weixinUserInfo/getWeixinGroup.do",
					async:true,
					data: {
					"accountappid": accountappid,
					},
					success: function(res) {
					res = JSON.parse(res); 
					console.log(res);
					var h = '<option value="">----</option>';
					var j = '';
					for(var i = 0; i < res.length ; i++) {     
						h += '<option value="'+res[i].groupId+'">' + res[i].groupName +'</option>' ;
					}
					$("#groupid").html(h);
	
				},			
				});
	       };
	       
	       function Confirm() {
	       		var tagid = $("#groupid").find("option:selected").val();
				console.log(tagid);
	            $.dialog({
	                content:'<h2><b>请再次确认！</b></h2>',
	                drag:false,
	                lock:true,
					zIndex:100011,
	                title:'提示框',
	                opacity:0.3,
	                width:300,
	                height:100,drag:false,min:false,max:false,
	                okVal:'确认',
	                ok:function () {
	                    $("#formobj")[0].action = "sendAccountController.do?massMessage";
	                    var form=$("#formobj").Validform();
	                    form.submitForm(true);
	                  $(window.parent.document).find(".ui_state_visible ").attr("style","display:none");
	                },
	                cancelVal:'关闭',
	                cancel:true
	            }).zIndex();
	       }	
	</script>
</head>

<body style="overflow-y: hidden" scroll="no">
	<t:formvalid beforeSubmit="Confirm()" formid="formobj" dialog="true" usePlugin="password" layout="table" >
		<input id="state" name="state" type="hidden">
		<c:if test="${menuEntity.id!=null}">
			<input id="id" name="id" type="hidden" value="${menuEntity.id}">
		</c:if>
		<input id="accountid" name="accountid" type="hidden" value="${accountid}">
		<table style="width: 100%" cellpadding="0" cellspacing="1" class="formtable">

			<!-- 公众号选择 -->
			<tr>
				<td align="right" style="width: 65px"><label class="Validform_label">公众号选择：</label></td>
				<td colspan="3" class="value">
					<select name="wechatName" id="wechatName" onchange="selAb()">
						<option >------</option>
						<c:forEach items="${weixinAccountList}" var="weixinAccount">
							<option class="selAcc" value="${weixinAccount.accountappid}"   <c:if test="${selectedAccountId==weixinAccount.id}">selected="selected" </c:if>>${weixinAccount.accountname}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<!-- 目标用户选择 -->
			<tr>
				<td align="right" style="width: 65px"><label class="Validform_label">用户选择：</label></td>
				<td colspan="3" class="value">
					<select name="groupid" id="groupid"></select>
					<span class="Validform_checktip">选择分组</span>
				</td>
			</tr>
			

			<tr id="xxtr" style="width: 65px">
				<td align="right">
				   <label class="Validform_label"> 消息类型:</label>
			    </td>
				<td class="value" colspan="3">
				   <t:dictSelect field="mtype" id="mtype" type="select"
									typeGroupCode="menuType" defaultVal="${msgType}"  hasLabel="false" datatype="*"></t:dictSelect>
				   <span class="Validform_checktip">选择消息类型</span>
				</td>
			</tr>
			
			<tr>
				<td align="right" style="width: 65px"><label class="Validform_label">分组选择：</label></td>
				<td colspan="3" class="value">
					<select name="groupName" id="groupName">
						<option value="">------</option>
						<c:forEach items="${groupList}" var="wgroup">
							<option value="${wgroup.id}" <c:if test="${selectedAccountId==wgroup.id}">selected="selected" </c:if>>${wgroup.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>

			<tr id="templateTr" style="width: 65px">
				<td align="right"><label class="Validform_label"> 选择模板:</label></td>
				<td class="value" colspan="3">
					<select name="templateId" id="templateId" datatype="*"></select> 
					<span class="Validform_checktip">选择模板</span>
				</td>
			</tr>		
		</table>
	</t:formvalid>
</body>