<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>奖券兑换</title>
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<t:base type="jquery,easyui,tools"></t:base>
<style>
	body{
		background:beige;
		text-align:center;
	}
	.wrap{
		margin-top:40%;
		
	}
	button{
		background:rgb(56,121,217);
		border:none;
		color:#fff;
		width:80px;
		height:35px;
		border-radius:5px;
		font-size:16px;
	}
	h2{
		font-size:16px;	
	}
	.type{
		padding-bottom:15px;
	}
</style>
<script type="text/javascript">
    $(function(){
    	if(${prize.status }==2){
    		$("#awardType h2").html('奖券状态：已兑换！');
            $('button').hide();
    	}
    	else{
               	    $("#awardType h2").html('奖券状态：未兑换');
               }
        //按钮单击时执行
        $("#testAjax").click(function(){

		

	       $.dialog.confirm('确认兑换?', function(r) {
		   if (r) {
				//取Ajax返回结果
              //为了简单，这里简单地从文件中读取内容作为返回数据
              htmlobj=$.ajax({url:"prize.do?doexchange&sn=${prize.sn }",async:false});
               //显示Ajax返回结果
              // $("#myDiv").html(htmlobj.responseText);
               if(htmlobj.responseText){
               		$("#awardType h2").html('奖券状态：兑换成功！');
               		$('button').hide();
               		
               }else{
               	    $("#awardType h2").html('奖券状态：未兑换');
               }
			}
		});
              
         });
    });
</script>    
</head>
    <body>
    	<div class="wrap">
        <div class="type"><h2>奖券名称：${prize.type }</h2></div>
        <div id="awardType" class="type"><h2>奖券状态：${prize.status }</h2></div>
        <div id="myDiv"><h2></h2></div>
        <button id="testAjax" type="button">兑换</button>
        </div >
    </body>
</html>
