${config_iframe}

<!--update-start--Author:luobaoli  Date:20150703 for：将本文档中所有href="#"修改为href="javascript:void(0)",避免rest风格下新增/删除等操作跳转到主页问题-->
<script type="text/javascript">
/**
*表单的高度,表单的宽度
**/
var ${config_id}Fw = 700,${config_id}Fh = 400;
var searchUrl;
<#if vip=="Y">
		searchUrl = 'cgReportController.do';
<#else>
		searchUrl = 'cgAutoListController.do';
</#if>
$(function(){
	$.get("cgFormHeadController.do?checkIsExit&checkIsTableCreate&name=${config_id}",
	function(data){
		data = $.parseJSON(data);
		if(data.success){
			createDataGrid${config_id}();
		}else{
			alertTip('表:<span style="color:red;">${config_id}</span>还没有生成,请到表单配置生成表');
		}
	});
});

function createDataGrid${config_id}(){
	<#if vip=="Y">
		var initUrl = "cgReportController.do?datagrid&configId=${config_id}&field=${fileds}${initquery}${config_params!''}";
	<#else>
		var initUrl = 'cgAutoListController.do?datagrid&configId=${config_id}&field=${fileds}${initquery}';
	</#if>
	initUrl = encodeURI(initUrl);
	$('#${config_id}List').<#if config_istree=="Y">treegrid<#else>datagrid</#if>(
	{
	<#if config_istree=="Y">treeField:'text',</#if>
	url:initUrl,
	idField: 'id', <#if config_istree=="Y">treeField:"${tree_fieldname}",</#if>
	title: '${config_name}',
	fit:true,
	fitColumns:true,
	striped:true,
	autoRowHeight: true,
	pageSize: 10,
	<#if config_ispagination =="Y">pagination:true,</#if>
	<#if config_ischeckbox=="Y">singleSelect:false,<#else>singleSelect:true,</#if>
	pageList:[10,30,50,100],
	sortOrder:'desc',
	rownumbers:true,
	showFooter:true,
	frozenColumns:[[]],
	columns:[
		[	
			<#if config_istree=="Y">
				<#list config_fieldList  as x>  
					<#if x_index==0>{field:"id", title:"${x['field_title']}", hidden:true}, </#if>
					<#if x_index!=0>{field:"${x['field_id']}", title:"${x['field_title']}",<#if x['field_isShow'] == "N" >hidden:true,</#if><#if x['field_href'] != "">
					 formatter:function(value,rec,index){var href='';href+="<a href='javascript:void(0)' onclick=\"addOneTab('字段链接','${x['field_href']}')\" >
					 <u>"+value+"</u></a>";return href;},</#if> width:100}, </#if>
				</#list>
			<#else>
					<#if config_ischeckbox=="Y">{field:'ck',checkbox:true},</#if>
					<#list config_fieldList  as x>  
						 {	field:'${x['field_id']}',
						 	title:'${x['field_title']}',
						 	<#if x['field_isShow'] == "N" >hidden:true,
						 	</#if>
						 	<#if x['field_href'] != "">
						 	formatter:function(value,rec,index){
						 		var href='';
						 		href+=applyHref('查看','${x['field_href']}',value,rec,index);
						 		return href;
						 	},
						 	</#if>
						 	<#if x['field_showType']=="file">
						 	formatter:function(value,rec,index){
						 		var href='';
						 		if(value==null || value.length==0){
						 			return href;
						 		}
						 		if(value.indexOf(".jpg")>-1 || value.indexOf(".gif")>-1 || value.indexOf(".png")>-1){
						 			href+="<img src='"+value+"'/>";
						 		}else{
						 			href+="<a href='"+value+"' target=_blank><u>点击下载</u></a>";
						 		}
						 		return href;
						 	},
						 	</#if>
						 	<#-- update-start--Author: jg_huangxg  Date:20160113 for：TASK #824 【online开发】控件类型扩展增加一个图片类型 image -->
						 	<#if x['field_showType']=="image">
						 	formatter:function(value,rec,index){
						 		var href='';
						 		if(value==null || value.length==0){
						 			return href;
						 		}
						 		href+="<img src='"+value+"' width=100 height=50/>";
						 		return href;
						 	},
						 	styler: function(value,row,index){
								return 'text-align: center;';
						 	},
						 	</#if>
						 	<#-- update-end--Author: jg_huangxg  Date:20160113 for：TASK #824 【online开发】控件类型扩展增加一个图片类型 image -->
						 	sortable:true,
						 	width:${x['field_length']}
						 	},
					</#list>
			</#if>
			{field:'opt',title:'操作',width:200,formatter:function(value,rec,index){
						if(!rec.id){return '';}
						var href='';
						<#if config_noliststr?index_of("delete")==-1>
						href+="[<a href='javascript:void(0)' onclick=delObj('cgAutoListController.do?del&configId=${config_id}&id="+rec.id+"','${config_id}List')>";
						href+="删除</a>]";
						</#if>
						<#list config_buttons as x>
							<#if x['buttonStyle'] == 'link' && x['buttonStatus']=='1' && config_noliststr?index_of("${x['buttonCode']}")==-1>
								var btn_href='';
								<#-- update-end--Author: chaizhi  Date:20160806 按钮exp显示隐藏逻辑 x['exp'] = "id#eq#0"-->
								btn_href+="[<a href='javascript:void(0)' buttonCode='${x['buttonCode']}' formId ='${x['formId']}' ";
								<#if x['optType'] == 'action'>
								var recText=rec.id;
								for(var key in rec){
									if(key !="id"){
										str='&'+key+'='+rec[key];
										recText+=str;
									}
								}
								btn_href+=" onclick=\"doBusButtonForLink('cgFormBuildController.do?doButton&formId=${x['formId']}&buttonCode=${x['buttonCode']}&tableName=${config_id}','${x['buttonName']}','${config_id}List','"+recText+"')\"";
								<#else>
								btn_href+=" onclick=\"${x['buttonCode']}('"+rec.id+"');\"";
								</#if>
								btn_href+=" id=\"${x['buttonCode']}\">";
								btn_href+="${x['buttonName']}</a>]";
								if(rec.${x['buttonCode']}){
									if(rec.${x['buttonCode']} =='show'){
										href+=btn_href;
									}
								}
								else{
									href+=btn_href;
								}
							</#if>
						</#list>
						return href;
						}
			}
		]
	],
	onLoadSuccess:function(data){
		$("#${config_id}List").<#if config_istree=="Y">treegrid<#else>datagrid</#if>("clearSelections");
	},
	onClickRow:function(rowIndex,rowData)
		{rowid=rowData.id;gridname='${config_id}List';}
	});
	$('#${config_id}List').<#if config_istree=="Y">treegrid<#else>datagrid</#if>('getPager').pagination({beforePageText:'',afterPageText:'/{pages}',displayMsg:'{from}-{to}共{total}条',showPageList:true,showRefresh:true});
	$('#${config_id}List').<#if config_istree=="Y">treegrid<#else>datagrid</#if>('getPager').pagination({onBeforeRefresh:function(pageNumber, pageSize){ $(this).pagination('loading');$(this).pagination('loaded'); }});
	//将没有权限的按钮屏蔽掉
	<#list config_nolist as x>
		$("#${config_id}Listtb").find("#${x}").hide();
	</#list>
	}
	//列表刷新
	function reloadTable(){	
		try{
		var gridname='${config_id}List';
		<#if config_istree=="Y">
			$('#'+gridname).treegrid('reload');
		<#else>
			$('#'+gridname).datagrid('reload');
		</#if>
		}catch(ex){
			//donothing
		}
	}
	//列表刷新-推荐使用
	function reload${config_id}List(){
		$('#${config_id}List').<#if config_istree=="Y">treegrid<#else>datagrid</#if>('reload');
	}
	/**
	 * 获取列表中选中行的数据-推荐使用
	 * @param field 数据中字段名
	 * @return 选中行的给定字段值
	 */
	function get${config_id}ListSelected(field){
		var row = $('#${config_id}List').<#if config_istree=="Y">treegrid<#else>datagrid</#if>('getSelected');
		if(row!=null){value= row[field];
		}else{
			value='';
		}
		return value;
	}
	/**
	 * 获取列表中选中行的数据
	 * @param field 数据中字段名
	 * @return 选中行的给定字段值
	 */
	function getSelected(field){
		var row = $('#'+gridname).<#if config_istree=="Y">treegrid<#else>datagrid</#if>('getSelected');
		if(row!=null){value= row[field];
		}else{
			value='';
		}
		return value;
	}
	
	/**
	 * 获取表格对象
	 * @return 表格对象
	 */
	function getDataGrid(){
		var datagrid = $('#'+gridname);
		return datagrid;
	}
	/**
	 * 获取列表中选中行的数据（多行）
	 * @param field 数据中字段名-不传此参数则获取全部数据
	 * @return 选中行的给定字段值，以逗号分隔
	 */
	function get${config_id}ListSelections(field){
		var ids = '';
		var rows = $('#${config_id}List').<#if config_istree=="Y">treegrid<#else>datagrid</#if>('getSelections');
		for(var i=0;i<rows.length;i++){
			ids+=rows[i][field];
			ids+=',';
		}
		ids = ids.substring(0,ids.length-1);
		return ids;
	}
	/**
	 * 列表查询
	 */
	function ${config_id}Listsearch(){
		var queryParams=$('#${config_id}List').<#if config_istree=="Y">treegrid<#else>datagrid</#if>('options').queryParams;
		$('#${config_id}Listtb').find('*').each(
			function(){
			queryParams[$(this).attr('name')]=$(this).val();});
			$('#${config_id}List').<#if config_istree=="Y">treegrid<#else>datagrid</#if>({url:searchUrl+'?datagrid&configId=${config_id}&field=${fileds}${initquery}${config_params!''}',pageNumber:1});
	}
	function dosearch(params){
		var jsonparams=$.parseJSON(params);
		$('#${config_id}List').<#if config_istree=="Y">treegrid<#else>datagrid</#if>({url:searchUrl+'?datagrid&configId=${config_id}&field=${fileds}${config_params!''},',queryParams:jsonparams});
	}
	function ${config_id}Listsearchbox(value,name){
		var queryParams=$('#${config_id}List').<#if config_istree=="Y">treegrid<#else>datagrid</#if>('options').queryParams;
		queryParams[name]=value;
		queryParams.searchfield=name;
		$('#${config_id}List').<#if config_istree=="Y">treegrid<#else>datagrid</#if>('reload');
	}
	$('#${config_id}Listsearchbox').searchbox({
		searcher:function(value,name){
			${config_id}Listsearchbox(value,name);
		},
		menu:'#${config_id}Listmm',
		prompt:'请输入查询关键字'
	});
	//查询重置
	function ${config_id}searchReset(name){ 
		$("#"+name+"tb").find("input[type!='hidden']").val("");
		<#if config_istree=="Y">
		//为树形表单时，删除id查询参数
		delete $('#${config_id}List').treegrid('options').queryParams.id;  
		</#if>
		//${config_id}Listsearch();
	}
	//将字段href中的变量替换掉
	function applyHref(tabname,href,value,rec,index){
		//addOneTab(tabname,href);
		var hrefnew = href;
		var re = "";
		var p1 = /\#\{(\w+)\}/g;
		try{
			var vars =hrefnew.match(p1); 
			for(var i=0;i<vars.length;i++){
				var keyt = vars[i];
				var p2 = /\#\{(\w+)\}/g;
				var key = p2.exec(keyt);
				 hrefnew =  hrefnew.replace(keyt,rec[key[1]]);
			}
		}catch(ex){
		}
		re += "<a href = '#' onclick=\"createdetailwindow('"+tabname+"','"+ hrefnew+"')\" ><u>"+value+"</u></a>";
		return re;
	}
	//SQL增强入口-按钮
	function doBusButton(url,content,gridname){
		var rowData = $('#'+gridname).datagrid('getSelected');
		if (!rowData) {
			tip('请选择一条信息');
			return;
		}	
		url = url + '&id='+rowData.id;
		createdialog('确认 ', '确定'+content+'吗 ?', url,gridname);
	}
	//SQL增强入口-操作列里的链接
	function doBusButtonForLink(url,content,gridname,rowData){
		if (!rowData) {
			tip('请选择一条信息');
			return;
		}	
		url = url + '&id='+rowData;
		createdialog('确认 ', '确定'+content+'吗 ?', url,gridname);
	}
    //----author:jg_xugj---start----date:20151219-------- for：#813 【online表单】扩展出三个请求：独立的添加、查看、编辑请求，原来的保留
	//新增
	function ${config_id}add(){
		//update-begin--Author:luobaoli  Date:20150705 for：请求URL修改为REST风格
		//add('${config_name}录入','rest/cgform/form/${config_id}','${config_id}List',${config_id}Fw,${config_id}Fh);
		//update-end--Author:luobaoli  Date:20150705 for：请求URL修改为REST风格
		
		add('${config_name}录入','cgFormBuildController.do?goAddFtlForm&tableName=${config_id}&olstylecode=${_olstylecode}','${config_id}List',${config_id}Fw,${config_id}Fh);
	}
	//修改
	function ${config_id}update(){
		//update-begin--Author:luobaoli  Date:20150705 for：请求URL修改为REST风格
		//update('${config_name}编辑','rest/cgform/form/${config_id}','${config_id}List',${config_id}Fw,${config_id}Fh,true);
		//update-end--Author:luobaoli  Date:20150705 for：请求URL修改为REST风格
		
		update('${config_name}编辑','cgFormBuildController.do?goUpdateFtlForm&tableName=${config_id}&olstylecode=${_olstylecode}','${config_id}List',${config_id}Fw,${config_id}Fh);
	}
	//查看
	function ${config_id}view(){
		detail('查看','cgFormBuildController.do?goDatilFtlForm&tableName=${config_id}&mode=read&olstylecode=${_olstylecode}','${config_id}List',${config_id}Fw,${config_id}Fh);
	}
    //----author:jg_xugj---end----date:20151219-------- for：#813 【online表单】扩展出三个请求：独立的添加、查看、编辑请求，原来的保留
	
	
	//批量删除
	function ${config_id}delBatch(){
		//获取选中的ID串
		var ids = get${config_id}ListSelections('id');
		if(ids.length<=0){
			tip('请选择至少一条信息');
			return;
		}
		$.dialog.setting.zIndex = getzIndex(false);
		$.dialog.confirm('确定删除吗?', function(r) {
			if(!r){return;}
			$.ajax({
			    url:"cgAutoListController.do?delBatch",
			    data:{'ids':ids,'configId':'${config_id}'},
				type:"Post",
			    dataType:"json",
			    success:function(data){
					tip(data.msg);
					reload${config_id}List();
			    },
				error:function(data){
					$.messager.alert('错误',data.msg);
				}
			});
			}
		);
	}
	
	function exportXls() {
		var submitUrl = "cgExportExcelController.do?exportXls&configId=tt_test";
		var queryParams = "";
		$('#tt_testListtb').find('*').each(function(){
				queryParams+= "&"+$(this).attr('name')+"="+$(this).val();
			}
		);
		submitUrl+=queryParams;
		submitUrl = encodeURI(submitUrl);
		 window.location.href=submitUrl;
	}

	function ${config_id}ExportExcel(){
		var queryParams = $('#${config_id}List').datagrid('options').queryParams;
		$('#${config_id}Listtb').find('*').each(function() {
		    queryParams[$(this).attr('name')] = $(this).val();
		});
		var params = '&';
		$.each(queryParams, function(key, val){
			params+='&'+key+'='+val;
		}); 
		var fields = '&field=';
		$.each($('#${config_id}List').datagrid('options').columns[0], function(i, val){
			if(val.field != 'opt'&&val.field != 'ck'){
				fields+=val.field+',';
			}
		}); 
		<#if vip=="Y">
			window.location.href = "cgExportExcelController.do?exportXls&configId=${config_id}${config_params!''}"+encodeURI(params+fields)
		<#else>
			window.location.href = "excelTempletController.do?exportXls&tableName=${config_id}"+encodeURI(params+fields)
		</#if>
		
		
	}
	
	
	//JS增强
	${config_jsenhance}
</script>

<table width="100%"   id="${config_id}List" toolbar="#${config_id}Listtb"></table>
<div id="${config_id}Listtb" style="padding:3px; height: auto">
<#if search_show??>
	<div style="display:none">
</#if> 
<div name="searchColums">
<#if config_querymode == "group">
	<#list config_queryList  as x>
		<#if x['field_isQuery']=="Y">
		<span style="display:-moz-inline-box;display:inline-block;">
		<span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap;" title="${x['field_title']}">${x['field_title']}：</span>
		</#if>
		<#if x['field_queryMode']=="group">
			<#if x['field_isQuery']=="Y">
			<input type="text" name="${x['field_id']}_begin"  style="width: 94px"  <#if x['field_type']=="Date">class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"<#elseif x['field_type']=="DateTime">class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"</#if> value="${x['field_value_begin']}" />
			<span style="display:-moz-inline-box;display:inline-block;width: 8px;text-align:right;">~</span>
			<input type="text" name="${x['field_id']}_end"  style="width: 94px" <#if x['field_type']=="Date">class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"<#elseif x['field_type']=="DateTime">class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"</#if> value="${x['field_value_end']}"/>
			<#else>
			<input type="hidden" name="${x['field_id']}_begin"   value="${x['field_value_begin']}"/>
			<input type="hidden" name="${x['field_id']}_end"    value="${x['field_value_end']}"/>
			</#if>
		</#if>
		<#if x['field_queryMode']=="single">
			<#if x['field_isQuery']=="Y">
				<#if  (x['field_dictlist']?size >0)>
					<select name = "${x['field_id']}"  style="width: 104px">
					<option value = "">---请选择---</option>
					<#list x['field_dictlist']  as xd>
						<option value = "${xd['typecode']}">${xd['typename']}</option>
					</#list>
					</select>
				</#if>
				<#if  (x['field_dictlist']?size <= 0)>
					<#if x['field_showType']!='popup'>
					<input type="text" name="${x['field_id']}" style="width: 100px" <#if x['field_type']=="Date">class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"<#elseif x['field_type']=="DateTime">class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"</#if>  value="${x['field_value']?if_exists?default('')}" />
					<#else>
					 <input name="${x['field_id']}" type="hidden" value="${x['field_value']?if_exists?default('')}" id="${x['field_id']}">
               		 <input name="${x['field_dictField']?if_exists?html}" type="text" class="form-control" value="" id="dict_${x['field_id']}" readonly="readonly"/> 		       
					 <a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true" icon="icon-search" onclick="choose_${x['field_dictTable']}()" id="pick">
                			 	选择
                			 </a>
                			 <a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true" icon="icon-redo" onclick="clearAll_${x['field_dictTable']}();" id="pickclear">
                			 	清空
                			 </a>
                			 <script type="text/javascript">
                			 	//var windowapi = frameElement.api, W = windowapi.opener;
                			 	function choose_${x['field_dictTable']}(){
                			 		var url = 'cgReportController.do?list&id=${x['field_dictTable']}';
                			 		var initValue = $('#${x['field_dictField']}').val();
                			 		url += '&ids='+initValue;
                			 		if(typeof(windowapi) == 'undefined'){
                			 			$.dialog({
                			 				content: 'url:'+url,
                			 				zIndex: getzIndex(false),
                			 				title: '列表选择',
                			 				lock : true,
                			 				width :700,
                			 				height :400,
                			 				left :'85%',
                			 				top :'65%',
                			 				opacity : 0.4,
                			 				button : [
                			 					{name : '确定',callback : clickcallback_${x['field_dictTable']},focus : true}, 
                			 					{name : '取消',callback : function() {}} 
                			 				]});
                			 		}else{
                			 			$.dialog({
                			 				content: 'url:'+url,
                			 				zIndex: getzIndex(false),
                			 				title: '列表选择',
                			 				lock : true,
                			 				parent:windowapi,
                			 				width :700,
                			 				height :400,
                			 				left :'85%',
                			 				top :'65%',
                			 				opacity : 0.4,
                			 				button : [
                			 					{name : '确定',callback : clickcallback_${x['field_dictTable']},focus : true},
                			 					{name : '取消',callback : function() {}} 
                			 				]});
                			 		}
                			 	}
                			 	function clearAll_${x['field_dictTable']}(){
                			 		if($('#dict_${x['field_dictField']}').length>=1){
                			 			$('#dict_${x['field_dictField']}').val('');
                			 			$('#dict_${x['field_dictField']}').blur();
                			 		}
                			 		if($("input[name='dict_${x['field_dictField']}']").length>=1){
                			 			$("input[name='dict_${x['field_dictField']}']").val('');
                			 			$("input[name='dict_${x['field_dictField']}']").blur();
                			 		}
                			 		$('#${x['field_id']}').val("");
                			 	}
                			 	function clickcallback_${x['field_dictTable']}(){
                			 		iframe = this.iframe.contentWindow;
                			 		var dict_${x['field_dictText']}=iframe.get${x['field_dictTable']}ListSelections('${x['field_dictText']}');
                			 		if($('#dict_${x['field_dictField']}').length>=1){
                			 			$('#dict_${x['field_dictField']}').val(dict_${x['field_dictText']});
                			 			$('#dict_${x['field_dictField']}').blur();
                			 		}
                			 		if($("input[name='dict_${x['field_dictField']}']").length>=1){
                			 			$("input[name='dict_${x['field_dictField']}']").val(dict_${x['field_dictText']});
                			 			$("input[name='dict_${x['field_dictField']}']").blur();
                			 		}
                			 		var dict_${x['field_dictField']} =iframe.get${x['field_dictTable']}ListSelections('${x['field_dictField']}');
                			 		if (dict_${x['field_dictField']}!== undefined &&dict_${x['field_dictField']}!=""){
                			 			$('#${x['field_id']}').val(dict_${x['field_dictField']});
                			 		}
                			 	}
                			 </script>		       
							       
							       
							       
					</#if>
				</#if>
			<#else>
					<input type="hidden" name="${x['field_id']}"    value="${x['field_value']?if_exists?default('')}" />
			</#if>
		</#if>
		</span>	
	</#list>
</#if>
	</div>
	<div style="height:30px;" class="datagrid-toolbar">
	<span style="float:left;" >
	
	<a  id="add" href="javascript:void(0)"  class="easyui-linkbutton" plain="true"  icon="icon-add" onclick="${config_id}add()">录入</a>
	<a  id="update" href="javascript:void(0)"  class="easyui-linkbutton" plain="true"  icon="icon-edit" onclick="${config_id}update()">编辑</a>
	<a id="delete" href="javascript:void(0)" class="easyui-linkbutton" plain="true"  icon="icon-remove" onclick="${config_id}delBatch()">批量删除</a>
	<a id="detail" href="javascript:void(0)" class="easyui-linkbutton" plain="true"  icon="icon-search" onclick="${config_id}view()">查看</a>
	<a id="import" href="javascript:void(0)"  class="easyui-linkbutton" plain="true"  icon="icon-put" onclick="add('${config_name}Excel数据导入','excelTempletController.do?goImplXls&tableName=${config_id}','${config_id}List')">Excel数据导入</a>
	<a id="excel" href="javascript:void(0)" class="easyui-linkbutton" plain="true" onclick="${config_id}ExportExcel()"  icon="icon-putout">Excel导出</a>
	
	<#list config_buttons as x>
		<#if x['buttonStyle'] == 'button' && x['buttonStatus']=='1'>
			<a id="${x['buttonCode']}" href="javascript:void(0)" class="easyui-linkbutton" plain="true"  icon="${x['buttonIcon']?if_exists?default('pictures')}" 
				<#if x['optType'] == 'action'>
				onclick="doBusButton('cgFormBuildController.do?doButton&formId=${x['formId']}&buttonCode=${x['buttonCode']}&tableName=${config_id}','${x['buttonName']}','${config_id}List')">${x['buttonName']}</a>
				<#else>
				onclick="${x['buttonCode']}();">${x['buttonName']}</a>
				</#if>
		</#if>
	</#list>
	</span>
	
<#if  (config_queryList?size >0)>
	<#if config_querymode == "group" >
		<span style="float:right">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="${config_id}Listsearch()">查询</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="${config_id}searchReset('${config_id}List')">重置</a>
		</span>
	</#if>
	<#if config_querymode == "single">
		<span style="float:right">
		<input id="${config_id}Listsearchbox" class="easyui-searchbox"  data-options="searcher:${config_id}Listsearchbox,prompt:'请输入关键字',menu:'#${config_id}Listmm'"></input>
		<div id="${config_id}Listmm" style="width:120px">
		<#list config_queryList  as x>
			<#if x['field_isQuery']=="Y">
			<div data-options="name:'${x['field_id']}',iconCls:'icon-ok'  ">${x['field_title']}</div>
			<#else>
			</#if>
		</#list>
		</div>
		</span>
	</#if>
</#if>
	</div>
	<#if search_show??>
	</div>
</#if> 
</div>

<!--update-end--Author:luobaoli  Date:20150703 for：将本文档中所有href="#"修改为href="javascript:void(0)",避免rest风格下新增/删除等操作跳转到主页问题-->