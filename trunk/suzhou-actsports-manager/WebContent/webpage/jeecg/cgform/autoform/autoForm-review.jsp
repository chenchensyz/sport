<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <t:base type="jquery,easyui,tools"></t:base>
		<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css"/>
		<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css"/>
	    <link rel="stylesheet" href="plug-in/Formdesign/js/ueditor/formdesign/bootstrap/css/bootstrap.css">
	    
	    <link rel="stylesheet" href="online/template/ledefault/css/app.css">
	    <link rel="stylesheet" href="online/template/ledefault/css/bootstrap.css"></link>
	       
	    <!--[if lte IE 6]>
	    <link rel="stylesheet" type="text/css" href="plug-in/Formdesign/js/ueditor/formdesign/bootstrap/css/bootstrap-ie6.css">
	    <![endif]-->
	    <!--[if lte IE 7]>
	    <link rel="stylesheet" type="text/css" href="plug-in/Formdesign/js/ueditor/formdesign/bootstrap/css/ie.css">
	    <![endif]-->
	     <link rel="stylesheet" href="plug-in/Formdesign/js/ueditor/formdesign/leipi.style.css">
         <style>
            html,body{
                height:100%;
                width:100%;
                padding:0;
                margin:0;
            }
            #preview{
                width:100%;
                height:100%;
                padding:0;
                margin:0;
            }
            #preview *{font-family:sans-serif;font-size:16px;}
            #preview .con-wrapper .show-grid strong{
            	font-size:12px;
            }
            #preview .con-wrapper .show-grid input{
             	display:block;
             	width:100%;
             	height:31px;
             	font-size:12px;
             	line-height：1.428 ；
             	color:#555;
             	padding:6px 2px;
             	border:1px solid #54A5D5;
             	border-radius:4px;
             	box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0.075);
             }
             #preview .con-wrapper .show-grid textarea{
                border:1px solid #54A5D5;
             	border-radius:4px;
             	box-shadow:inset 0 1px 1px rgba(0, 0, 0, 0.075);
             	margin:3px 0 4px 0;
             	font-size:12px;
             }
             #preview .nav-tabs a{
             	font-size:12px;
             	padding:5px 15px;
             }
             #preview .con-wrapper .row > div{
             line-height:1;
             min-height:auto;
             border-left:none
             }
             .navbar-default{
             	background:none;
            	border:none;
             }
             #preview .con-wrapper .row div+div{
             	border-left:1px solid #e4e4e4
             	
             }
              #preview .row.show-grid{
              border-left:none;}
             #preview .con-wrapper .show-grid .Validform_checktip{
             	height:0;
             	font-size:0;
             	line-height:1;
             	display:none;
             }
            #preview .con-wrapper .row>div.text-center{
            	line-height:36px;
            }
            .collapse{
            	display:block;
            }
            .navbar-fixed-bottom .container{
            	width:auto;
            }
            .btn-primary,.btn-primary:hover,.btn-primary:focus,.btn-primary:active,.btn-primary.btn.focus{
            	background-color:#2288cc;
            	border-color:#2288cc;
            }
            .btn{
            	padding:4px 10px;
            }
            .navbar-fixed-bottom .navbar-inner{
            	box-shadow:none;
            	border:none;
            	background:none;
            }
            .nav{
            	border-top-color:transparent;
            }
            
            
        </style>
        <script type="text/javascript">
        $(function(){
        	$(":input").attr("disabled","true");
        });
        </script>
    </head>
    <body class="view">
      <div class="container">
		<%-- <div class="page-header">
		  <h1>预览表单 <small>如无问题请保存你的设计</small></h1>
		</div> --%>
		<div id="preview" style="margin:8px">
			${formContent}
        </div>
	  </div>
        
    </body>
</html>