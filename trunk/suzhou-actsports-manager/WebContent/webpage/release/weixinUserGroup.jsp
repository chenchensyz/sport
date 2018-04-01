<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <c:set var="ctx" value="${pageContext.request.contextPath}"/> --%>

<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>会员分组管理</title>
<link href="${pageContext.request.contextPath}/webpage/release/layui/css/common.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/webpage/release/layui/css/layui.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/webpage/release/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/webpage/release/layui/css/modal.css" rel="stylesheet" />
</head>

<body>
<!--公众号的选择-->	
	<div class="layui-row">
		<ul class="layui-nav">
			<li class="layui-nav-item layui-this"><a href="javascript:;" id="wxTitle">请选择需要管理的公众号</a>				
				<dl class="layui-nav-child">
					<c:forEach items="${PublicNumbers}" var="code">
						<button class="layui-btn accou"><span class="wxname">${code.accountname}</span>
							<span style="display: none;" class="appid">${code.accountappid}</span>
							<span style="display: none;" class="wxid">${code.id}</span>
						</button>
					</c:forEach>
				</dl>
			</li>
		</ul>
	</div>
<!--数据表格-->
	<div class="layui-row header">
		<div class="layui-col-xs8">
			<div class="demoTable" id="laybtn">搜索昵称：			  
				<div class="layui-inline"><input class="layui-input" name="wename" id="demoReload" autocomplete="off"></div>
				<button class="layui-btn layui-btn-primary" data-type="reload" id="btnSer">搜索</button>
				<button id="follow" class="qh layui-btn">已关注</button>
				<button class="qh layui-btn layui-btn-primary" id="blist">黑名单</button>
				<button id="sync" class="layui-btn layui-btn-primary">同步</button>
			</div>

			<table id="selGroup"><div id="bbqian" class="yycc abc"></div></table>
			<div class="layui-form layui-border-box layui-table-view" lay-filter="LAY-table-1" style="height: 572px;">
				
				<div class="layui-table-header">
					<table cellspacing="0" cellpadding="0" border="0" class="layui-table" lay-skin="row" lay-even="">
						<thead>
							<tr>
								<th data-field="0" data-type="checkbox" unresize="true"><div class="layui-table-cell laytable-cell-checkbox"><input type="checkbox" name="layTableCheckbox" lay-skin="primary" lay-filter="layTableAllChoose"><div class="layui-unselect layui-form-checkbox" lay-skin="primary"><i class="layui-icon"></i></div></div></th>
								<th data-field="id"><div class="layui-table-cell laytable-cell-1-id"><span>ID</span></div></th>
								<th data-field="username"><div class="layui-table-cell laytable-cell-1-username"><span>用户名</span></div></th>
								<th data-field="remark"><div class="layui-table-cell laytable-cell-1-remark"><span>标签</span></div></th>
								<th data-field="sign"><div class="layui-table-cell laytable-cell-1-sign"><span>备注</span></div></th>
							</tr>
						</thead>
					</table>
				</div>
				<div class="layui-table-body layui-table-main" style="height: 491px;">
					<table cellspacing="0" cellpadding="0" border="0" class="layui-table" lay-skin="row" lay-even="">
						<tbody id="listShow"></tbody>
					</table>
				</div>
			</div>
		</div>
<!--新建分组-->
		<div class="layui-col-xs4">  
			<div class="layui-row" style="margin-top: 48px;">
				<ul class="layui-nav layui-nav-tree layui-bg-cyan layui-inline" lay-filter="demo">
                    <li class="layui-nav-item layui-nav-itemed">
                        <a class="showAll" href="javascript:;">全部分组</a>
                        <dl class="layui-nav-child" id="groupFz"></dl>
                </ul>
			</div>
		</div>
		
	<script src="${pageContext.request.contextPath}/webpage/release/layui/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/webpage/release/layui/layui.all.js"></script>
	<script src="${pageContext.request.contextPath}/webpage/release/layui/layuifix.js"></script>
	<script src="${pageContext.request.contextPath}/webpage/release/bootstrap/js/bootstrap.min.js"></script>  
	<script src="${pageContext.request.contextPath}/webpage/release/layui/custominput.js"></script>
	

</body>
</html>
