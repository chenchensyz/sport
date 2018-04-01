//按钮的样式
$('.demoTable').on('click', '.qh', function(e) {
	$('.demoTable .qh').addClass('layui-btn-primary');
	$(this).removeClass('layui-btn-primary');
});

//def
var nameGroup = $(".accou .wxname").html();
var appidGroup = $(".accou .appid").html();

var nameDef = nameGroup[0];
var appidDef = appidGroup[0];

	$.ajax({
		type: "post",
		url: "weixinUserInfo/getUserAccount.do",
		async: true,
		data: {
			"accountappid": appidDef,
		},
		success: function(res) {
			console.log(res);
			res = JSON.parse(res);
			layui.use('table', function() {
				var table = layui.table;
				table.render({
					elem: '#selGroup',
					data: res,
					height: 466,
					cols: [
						[ //标题栏
							{
								checkbox: true,
								LAY_CHECKED: false
							} //默认全选
							, {
								field: 'nickName',
								title: '会员昵称',
								width: 120
							}, {
								field: 'groupName',
								title: '标签',
								width: 150
							}, {
								field: 'remark',
								title: '备注',
								width: 150
							}, {
								field: 'account',
								title: '隐藏',
								width: 150
							}
						]
					],
					skin: 'row', //表格风格
					even: true,
					page: true, //是否显示分页
					limit: 10 //每页默认显示的数量
				});
			});
		}
	});
	
		$.ajax({
		type: "post",
		url: "weixinUserInfo/getWeixinGroup.do",
		async: true,
		data: {
			"accountappid": appidDef,
		},
		success: function(res) {
			console.log(res);
			res = JSON.parse(res);
			var h = '';
			var count = [];
			var groupId = [];
			var groupName = [];
			for(var i = 0; i <= res.length - 1; i++) {
				count[i] = res[i].count;
				groupName[i] = res[i].groupName;
				groupId[i] = res[i].groupId;
				var j = i + 1;
				h += '<dd class="fzGroup"><a href="javascript:;"><span class="fzmz">' +
					groupName[i] +
					'</span><span class="fzGroups">' +
					count[i] +
					'</span>' +
					'<span class="ffzz">' +
					groupId[i] +
					'</span>'
				'</a></dd>'
			}
			$("#groupFz").html(h);
			$(document).on("click", '#groupFz .fzGroup', function() {
				$("#groupFz .fzGroup").addClass("default").removeClass("layui-this");
				$(this).addClass("layui-this").removeClass("default");
			});
		}
	});	

//group page
$(".accou").click(function() {
	var accountappid = $(this).children("span.appid").html();
	var wxcc = $(this).children("span.wxname").html();
	$("#wxTitle").text(wxcc);
	//accountlist
	$.ajax({
		type: "post",
		url: "weixinUserInfo/getUserAccount.do",
		async: true,
		data: {
			"accountappid": accountappid,
		},
		success: function(res) {
			console.log(res);
			res = JSON.parse(res);
			layui.use('table', function() {
				var table = layui.table;
				table.render({
					elem: '#selGroup',
					data: res,
					height: 466,
					cols: [
						[ //标题栏
							{
								checkbox: true,
								LAY_CHECKED: false
							} //默认全选
							, {
								field: 'nickName',
								title: '会员昵称',
								width: 120
							}, {
								field: 'groupName',
								title: '标签',
								width: 150
							}, {
								field: 'remark',
								title: '备注',
								width: 150
							}, {
								field: 'account',
								title: '隐藏',
								width: 150
							}
						]
					],
					skin: 'row', //表格风格
					even: true,
					page: true, //是否显示分页
					limit: 10 //每页默认显示的数量
				});
			});
		}
	});

	//fllow
	$("#follow").click(function() {
		$.ajax({
			type: "post",
			url: "weixinUserInfo/getUserAccount.do",
			async: true,
			data: {
				"accountappid": accountappid,
			},
			success: function(res) {
				console.log(res);
				res = JSON.parse(res);
				layui.use('table', function() {
					var table = layui.table;
					table.render({
						elem: '#selGroup',
						data: res,
						height: 466,
						cols: [
							[ //标题栏
								{
									checkbox: true,
									LAY_CHECKED: false
								} //默认全选
								, {
									field: 'nickName',
									title: '会员昵称',
									width: 120
								}, {
									field: 'groupName',
									title: '标签',
									width: 150
								}, {
									field: 'remark',
									title: '备注',
									width: 150
								}, {
									field: 'account',
									title: '隐藏',
									width: 150
								}
							]
						],
						skin: 'row', //表格风格
						even: true,
						page: true, //是否显示分页
						limit: 10 //每页默认显示的数量
					});
				});
			}
		});
	})

	//getGroup
	$.ajax({
		type: "post",
		url: "weixinUserInfo/getWeixinGroup.do",
		async: true,
		data: {
			"accountappid": accountappid,
		},
		success: function(res) {
			console.log(res);
			res = JSON.parse(res);
			var h = '';
			var count = [];
			var groupId = [];
			var groupName = [];
			for(var i = 0; i <= res.length - 1; i++) {
				count[i] = res[i].count;
				groupName[i] = res[i].groupName;
				groupId[i] = res[i].groupId;
				var j = i + 1;
				h += '<dd class="fzGroup"><a href="javascript:;"><span class="fzmz">' +
					groupName[i] +
					'</span><span class="fzGroups">' +
					count[i] +
					'</span>' +
					'<span class="ffzz">' +
					groupId[i] +
					'</span>'
				'</a></dd>'
			}
			$("#groupFz").html(h);
			$(document).on("click", '#groupFz .fzGroup', function() {
				$("#groupFz .fzGroup").addClass("default").removeClass("layui-this");
				$(this).addClass("layui-this").removeClass("default");
			});
		}
	});

	//dataGrouplist
	$(document).on("click", '#groupFz .fzGroup', function() {
		var groupId = $(this).find(".ffzz").html();
		$.ajax({
			type: "post",
			url: "weixinUserInfo/getTasgOpenids.do",
			async: true,
			data: {
				"accountappid": accountappid,
				"groupId": groupId,
			},
			success: function(res) {
				res = JSON.parse(res);
				res = res.data;
				console.log(res);
				layui.use('table', function() {
					var table = layui.table;
					table.render({
						elem: '#selGroup',
						data: res,
						height: 466,
						cols: [
							[ //标题栏
								{
									checkbox: true,
									LAY_CHECKED: false
								} //默认全选
								, {
									field: 'nickName',
									title: '会员昵称',
									width: 120
								}, {
									field: 'groupName',
									title: '标签',
									width: 150
								}, {
									field: 'remark',
									title: '备注',
									width: 150
								}, {
									field: 'account',
									title: '隐藏',
									width: 150
								}
							]
						],
						skin: 'row' //表格风格
							,
						even: true,
						page: true //是否显示分页
							,
						limit: 10 //每页默认显示的数量
							,
						nickName: 'testReload'
					});
				});
			}
		});
	});

	//blacklist
	$("#blist").click(function() {
		$.ajax({
			type: "post",
			url: "weixinUserInfo/getBlacklist.do",
			async: true,
			data: {
				"accountappid": accountappid,
			},
			success: function(res) {
				console.log(res);
				res = JSON.parse(res);
				layui.use('table', function() {
					var table = layui.table;
					table.render({
						elem: '#selGroup',
						data: res,
						height: 466,
						cols: [
							[ //标题栏
								{
									checkbox: true,
									LAY_CHECKED: false
								} //默认全选
								, {
									field: 'nickName',
									title: '会员昵称',
									width: 120
								}, {
									field: 'groupName',
									title: '标签',
									width: 150
								}, {
									field: 'remark',
									title: '备注',
									width: 150
								}, {
									field: 'account',
									title: '隐藏',
									width: 150
								}
							]
						],
						skin: 'row', //表格风格
						even: true,
						page: true, //是否显示分页
						limit: 10 //每页默认显示的数量
					});
				});
			}
		});
	})

	//select
	$("#btnSer").click(function() {
		var vau_input = $("input[ name='wename']").val();
		$.ajax({
			type: "post",
			url: "weixinUserInfo/selectNickname.do",
			async: true,
			data: {
				"nickName": vau_input,
				"accountappid": accountappid,
			},
			success: function(res) {
				res = JSON.parse(res); 
				layui.use('table', function() {
					var table = layui.table;
					table.render({
						elem: '#selGroup',
						data: res,
						height: 466,
						cols: [
							[ //标题栏
								{
									checkbox: true,
									LAY_CHECKED: false
								} //默认全选
								, {
									field: 'nickName',
									title: '会员昵称',
									width: 120
								}, {
									field: 'groupId',
									title: '标签',
									width: 150
								}, {
									field: 'remark',
									title: '备注',
									width: 150
								}, {
									field: 'account',
									title: '隐藏',
									width: 150
								}
							]
						],
						skin: 'row' //表格风格
							,
						even: true,
						page: true //是否显示分页
							,
						limit: 10 //每页默认显示的数量
							,
						nickName: 'testReload'
					});
				});
			}
		});
	})
	
	//synchronization 
	$("#sync").click(function(){
		$.ajax({
			type:"post",
			url:"weixinUserInfo/synWeixinUser.do",
			async:true,
			data: {
				"accountappid": accountappid,
			},
			success: function(){
				alert("同步成功！");
			}
		});
	})
})