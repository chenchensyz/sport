<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>素材管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
		<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css" />
		<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
  <script type="text/javascript">
  var serverMsg="";
	$(function(){
		$('#scFilepath').uploadify({
			buttonText:'添加文件',
			auto:false,
			progressData:'speed',
			multi:true,
			height:25,
			overrideEvents:['onDialogClose'],
			fileTypeDesc:'文件格式:',
			queueID:'filediv_file',
			fileTypeExts:'*.rar;*.zip;*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;*.jpg;*.gif;*.png;*.amr;*.mp4',
			fileSizeLimit:'15MB',
			swf:'plug-in/uploadify/uploadify.swf',	
			uploader:'cgUploadController.do?saveFiles&jsessionid='+$("#sessionUID").val()+'',
			onUploadStart : function(file) { 
				var cgFormId=$("input[name='id']").val();
				$('#scFilepath').uploadify("settings", "formData", {
					'cgFormId':cgFormId,
					'cgFormName':'weixin_filetemplate',
					'cgFormField':'sc_filepath'
				});
			} ,
			onQueueComplete : function(queueData) {
				 var win = frameElement.api.opener;
				 win.reloadTable();
				 win.tip(serverMsg)
				 ;
				 frameElement.api.close();
			},
			onUploadSuccess : function(file, data, response) {
				var d=$.parseJSON(data);
				if(d.success){
					var win = frameElement.api.opener;
					serverMsg = d.msg;
				}
			},
			onFallback: function() {
              tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
          },
          onSelectError: function(file, errorCode, errorMsg) {
              switch (errorCode) {
              case - 100 : tip("上传的文件数量已经超出系统限制的" + $('#file').uploadify('settings', 'queueSizeLimit') + "个文件！");
                  break;
              case - 110 : tip("文件 [" + file.name + "] 大小超出系统限制的" + $('#file').uploadify('settings', 'fileSizeLimit') + "大小！");
                  break;
              case - 120 : tip("文件 [" + file.name + "] 大小异常！");
                  break;
              case - 130 : tip("文件 [" + file.name + "] 类型不正确！");
                  break;
              }
          },
          onUploadProgress: function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {}
		});
	});
	
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="weixinFiletemplateController.do?doAdd" tiptype="1" callback="jeecgFormFileCallBack@Override">
		<input type="hidden" name="id" id="id" />
		<input type="hidden" name="maintype" id="maintype" value="${maintype}" />
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="templateName" name="templateName" type="text" style="width: 150px" class="inputxt"  datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							文件路径:
						</label>
					</td>
					<td class="value">
								<table></table>
								<div class="form jeecgDetail"> 
									<span id="file_uploadspan"><input type="file" name="scFilepath" id="scFilepath" /></span> 
								</div> 
								<div class="form" id="filediv_file"></div>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">文件路径</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							素材简介:
						</label>
					</td>
					<td class="value">
					     	 <input id="introduction" name="introduction" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">素材简介</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							分组:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="groupId" type="list"
									dictTable="weixin_group" dictField="id" dictText="name" defaultVal="${weixinFiletemplatePage.groupId}" hasLabel="false"  title="分组"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">分组</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/jeecg/ filetemplate/weixinFiletemplate.js"></script>		
	  	<script type="text/javascript">
	  	
	  	
	  	
	  	
	  	
	  		function jeecgFormFileCallBack(data){
	  			if (data.success == true) {
					uploadFile(data);
				} else {
					if (data.responseText == '' || data.responseText == undefined) {
						$.messager.alert('错误', data.msg);
						$.Hidemsg();
					} else {
						try {
							var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'), data.responseText.indexOf('错误信息'));
							$.messager.alert('错误', emsg);
							$.Hidemsg();
						} catch(ex) {
							$.messager.alert('错误', data.responseText + '');
						}
					}
					return false;
				}
				if (!neibuClickFlag) {
					var win = frameElement.api.opener;
					win.reloadTable();
				}
	  		}
	  		function upload() {
				$('#scFilepath').uploadify('upload', '*');		
			}
			
			var neibuClickFlag = false;
			function neibuClick() {
				neibuClickFlag = true; 
				$('#btn_sub').trigger('click');
			}
			function cancel() {
				$('#scFilepath').uploadify('cancel', '*');
			}
			function uploadFile(data){
				if(!$("input[name='id']").val()){
					if(data.obj!=null && data.obj!='undefined'){
						$("input[name='id']").val(data.obj.id);
					}
				}
				if($(".uploadify-queue-item").length>0){
					upload();
				}else{
					if (neibuClickFlag){
						alert(data.msg);
						neibuClickFlag = false;
					}else {
						var win = frameElement.api.opener;
						win.reloadTable();
						win.tip(data.msg);
						frameElement.api.close();
					}
				}
			}
	  	</script>