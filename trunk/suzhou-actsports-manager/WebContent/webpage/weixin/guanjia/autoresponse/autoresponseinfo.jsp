<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title></title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="messagefrom" dialog="true" usePlugin="password" layout="table" action="keywordController.do?doSave">
   <input id="id" name="id" type="hidden" value="${id}">
   <input id="templateName" name="templateName" type="hidden" value="${templateName}"/>
   <table style="width:600px;" cellpadding="0" cellspacing="1" class="formtable">
   
   <!-- 公众号选择 -->
   <tr>
     <td align="right"><label class="Validform_label">公众号选择：</label></td>
     <td class="value">
        <select name="wechatId" id="wechatId">
          <option value="">---请选择---</option>
          <c:forEach items="${weixinAccountList}" var="weixinAccount">
             <option value="${weixinAccount.id}"
                <c:if test="${selectedWechatId==weixinAccount.id}">selected="selected"</c:if>>${weixinAccount.accountname}</option>
          </c:forEach>
        </select>
     </td>
   </tr>
   
    <tr>
     <td align="right">
      <label class="Validform_label">
     		关键字:
      </label>
     </td>
     <td class="value">

       <input id="keyWord" class="inputxt" name="keyWord"  value="${keyWord}" datatype="*" nullmsg="关键字不能为空！">
      <span class="Validform_checktip">请输入关键字！</span>
     </td>
    </tr>
    
    <tr>
     <td align="right">
      <label class="Validform_label">
     	消息类型:
      </label>
     </td>
     <td class="value">
          <select name="msgType" id="msgType">
          		<option value="text"  <c:if test="${msgType=='text'}">selected="selected"</c:if>>文本消息</option>
          		<option value="news"  <c:if test="${msgType=='news'}">selected="selected"</c:if>>图文消息</option>
          		<option value="image"  <c:if test="${msgType=='image'}">selected="selected"</c:if>>图片消息</option>
          </select>
     </td>
    </tr>
    
    <tr>
     <td align="right">
      <label class="Validform_label">
     	分组选择:
      </label>
     </td>
     <td class="value">
          <select name="msgGroup" onchange="selAb()" id="msgGroup" >
           <option value="">--请选择--</option>
          <c:forEach items="${materials}" var="mat">
                <option value="${mat.id}" <c:if test="${groupId==mat.id}">selected="selected"</c:if>>${mat.name}</option>
          	</c:forEach>
           </select>
        </td>
    </tr>
    
      <tr>
     <td align="right">
      <label class="Validform_label">
     	消息模板:
      </label>
     </td>
     <td class="value" id="textId">
          <select name="resContent" id="textContent">
          	 <option value="">--请选择--</option>

          </select>
     </td>

    </tr>
    
   </table>
  </t:formvalid>
 </body>
 
 <script type="text/javascript">
   var a=$("#imageContent").val();
  console.log(a);
 </script>
 
<script>
	function selAb(){
		var aaa = $("#msgType").find("option:selected").val();
		var bbb = $("#msgGroup").find("option:selected").val();
		$.ajax({
			type:"post",
			url:"keywordController/gettemplate.do",
			async:true,
			data: {"msgType":aaa,"msgGroup":bbb},          
			success: function(res){			
				res = JSON.parse(res);
				var h = '';
				for(var i=0; i<res.data.length; i++){
						h += '<option value="'
						   + res.data[i]["id"]
						   + '">'
						   + res.data[i]["templateName"]
						   + '</option>'
					};
				$("#textContent").html(h);
		 	}
		});
	}
</script>