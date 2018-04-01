var DEFAULT_, 
OPTION_, 
TYPE_='line', 
TITLE_ = 'Online 动态图表配置',
DATA_, 
ANIMATION_ = true,
SHADOW_ = false, 
f = true, 
BACKGROUND_COLOR = '#FEFEFE',
COO_BACKGROUND_COLOR = '#FEFEFE', 
IMAGE_DATA = "",
BOUNDARYGAP = false;
 
var color = ['#a5c2d5', '#cbab4f', '#76a871', '#c12c44', '#9f7961', '#6f83a5'];

function apiHistory() {
	console.info(0);
	var json = DATA_;
	var legend = [];
	var xAxisData = [];
	var group = {};
	for ( var i=0;i<json.length; i++) {
		var ReportApiHistory = json[i];
		var api_code = ReportApiHistory.name;
		var list = group[api_code];
		if (list) {
			list.push(ReportApiHistory);
		} else {
			group[api_code] = [ ReportApiHistory ];
		}

		if (legend.indexOf(api_code) == -1) {
			legend.push(api_code);
		}
		if (xAxisData.indexOf(ReportApiHistory.date.substring(0,10)) == -1) {
			xAxisData.push(ReportApiHistory.date.substring(0,10));
		}
		
	}
	$.unique(xAxisData);
	xAxisData.sort();

	$.unique(legend);

	var series = [];
	for ( var v=0;v<legend.length; v++) {
		var le = legend[v];
		var countli = apicountListf(group[le], xAxisData);
		series.push(apiSerieFormat(le, countli));
		
	}

	var option = apiRendererChart(legend, series, xAxisData);

	var reportChart = echarts.init($("#canvasDiv").width(
			"100%").height(400)[0]);
	reportChart.setOption(option);
}

function apicountListf(list, xAxisData) {
	var countList = [];
	for ( var u = 0; u < xAxisData.length; u++) {
		var isdefval = true;
		var x = xAxisData[u];
		for ( var li=0;li<list.length;li++) {
			if (x == list[li].date.substring(0,10)) {
				countList.push(list[li].value);
				isdefval = false;
				break;
			}
		}
		if (isdefval) {
			countList.push(0);
		}
	}
	return countList;
}

function apiSerieFormat(name, data) {
	return {
		name : name,
		type : TYPE_,
		stack : 'group1',
		smooth : true,
		data : data
	};
}

function apiRendererChart(legend, series, xAxisData) {
	console.log(xAxisData);
	var subtitle = "";
	var startTime;
	if (xAxisData.length >= 1){
		subtitle = xAxisData[0] + "-" + xAxisData[xAxisData.length - 1];
		console.log(subtitle);
	}
	var titles = TITLE_;
	console.log("title:"+ titles);
	var option = {
		title: {
	        text: titles,
	        left: 'center',
	        textStyle : {
	        	color : "#5AB1EF"
	        },
	        subtext : subtitle,
	        subtextStyle : {
	        	color : '#aaa'
	        }
	    },
		tooltip : {
			trigger : 'axis'
		},
		grid : {
			left : '20px',
			right : '30px',
			bottom : '20px',
			top :'100px',
			containLabel : true
		},
		legend : {
			top : '50px',
			data : legend
		},
		toolbox : {
			top : '0px',
			show : true,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					type : [ 'line', 'bar', 'stack', 'tiled' ]
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		xAxis : [ {
			type : 'category',
			boundaryGap : BOUNDARYGAP,
			data : xAxisData
		} ],
		yAxis : [ {
			type : 'value'
		} ],
		series : series
	};

	return option;
}
function doPie(){
	var businessMap = {};
    $("#canvasDiv").html("");
    //默认配置
    var baseOption = {
        title : {
            text: '',
            subtext: '',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: []
        },
        series : [
            {
                name: '',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    //根据时间段 按业务处理数据
    for(var i = 0;i < DATA_.length;i++){
        if(businessMap[DATA_[i].date]){
            if(businessMap[DATA_[i].date][DATA_[i].name]){
                businessMap[DATA_[i].date][DATA_[i].name] += parseInt(DATA_[i].value);
            }else{
                businessMap[DATA_[i].date][DATA_[i].name] = parseInt(DATA_[i].value);
            }
        }else{
            businessMap[DATA_[i].date] = {};
            businessMap[DATA_[i].date][DATA_[i].name] = parseInt(DATA_[i].value);
        }
    }
    for(var key in businessMap){
        var tmpResult = [];
        for(var k in businessMap[key]){
            var obj = {};
            obj.name = k;
            obj.value = businessMap[key][k];
            tmpResult.push(obj);
        }
        //动态创建DOM 显示饼图
        var option = $.extend(true,baseOption,{});
        option.title.text = key ;
        option.legend.data = Object.keys(businessMap[key]);
        option.series[0].data = tmpResult;
        echarts.init($('<div style="width: 80%;height:400px;"></div>').appendTo("#canvasDiv")[0]).setOption(option);
    }
}
function doColumn(){
	DEFAULT_ = {
			data : DATA_,
			text_space : TYPE_=='Column3D'?16:6,
			coordinate : {
				width : '86%',
				height : '80%',
				color_factor : 0.24,
				board_deep:10,//背面厚度
				pedestal_height:10,//底座高度
				left_board:false,//取消左侧面板
				axis : {
					color : '#c0d0e0',
					width : [0, 0, 1, 0]
				},
				scale : [{
					position : 'left',
					scale_enable : false,
					label : {
						fontsize : 11,
						color : '#4c4f48'
					}
				}]
			},
			label : {
				fontsize : 11,
				color : '#4c4f48'
			},
			zScale:0.5,
			bottom_scale:1.1,
			sub_option : {
				label : {
					fontsize : 11,
					fontweight : 600,
					color : '#4572a7'
				},
				border : {
					width : 2,
					color : '#ffffff'
				}
			}
		};
}
function doBar(){
	DEFAULT_ = {
			data : DATA_,
			coordinate : {
				width : '80%',
				height : '80%',
				axis : {
					color : '#c0d0e0',
					width:[1,1,1,4]
				},
				scale : [{
					position : 'bottom',
					scale_enable : false,
					label : {
						fontsize : 11,
						color : '#4c4f48'
					}
				}]
			},
			label : {
				fontsize : 11,
				color : '#4c4f48'
			},
			zScale:0.5,
			bottom_scale:1.1,
			sub_option : {
				label : {
					fontsize : 11,
					fontweight : 600,
					color : '#4572a7'
				},
				border : {
					width : 2,
					color : '#ffffff'
				}
			}
		};
}

function getRandomColor(){ 
return "#"+("00000"+((Math.random()*16777215+0.5)>>0).toString(16)).slice(-6); 
} 

function doLine(){
	var labels = [], tColor = [], data_l = [];
    for ( var i = 0; i < DATA_.length; i++) {
        if(labels.indexOf(DATA_[i].name) == -1) {
            labels.push(DATA_[i].name);
        }
        
        if(tColor.indexOf(DATA_[i].color) == -1) {
            tColor.push(DATA_[i].color);
            data_l.push({name : '', value : [], color : getRandomColor(), linewidth : 3});
        }
        
        var d = data_l[tColor.indexOf(DATA_[i].color)];
        d.value[labels.indexOf(DATA_[i].name)] = DATA_[i].value;
    }
    
    for(var i = 0; i < data_l.length; i++){
        var tV = data_l[i].value;
        for(var j =0; j < labels.length; j++){
            if(!tV[j]){
                tV[j] = 0;
            }
        }
    }
    
    if (data_l.length > 0 && data_l[0].value.length == 1) {
        alert("折线图至少需要2组数据才能画线哦!请定制数据或者选择其他图形.");
        return;
    }
    
	DEFAULT_ = {
		labels : labels,
		data : data_l,
		sub_option : {
			label : {
				fontsize : 14,
				fontweight:600,
				color : '#4c4f48'
			},
			smooth : OPTION_ == '1',
			hollow_inside:false,
			point_size:16,
			hollow : true
		},
		coordinate : {
			width : '84%',
			height : '75%'
		}
	};
}
function doChart() {
	if (TYPE_ == 'pie') {
		doPie();
	} else if (TYPE_ == 'bar') {
		BOUNDARYGAP=true;
		apiHistory();
	} else if (TYPE_ == 'line') {
		apiHistory();
	}
	if (TITLE_ != '') {
		DEFAULT_.title = TITLE_;
	}
	DEFAULT_.width =$(document.body).width();
	DEFAULT_.height = $(window).height();
	DEFAULT_.render = 'canvasDiv';
	DEFAULT_.animation = ANIMATION_;
	DEFAULT_.shadow = SHADOW_;
	DEFAULT_.shadow_blur = 3;
	DEFAULT_.background_color = BACKGROUND_COLOR;
	DEFAULT_.footnote = "";
	if (DEFAULT_.coordinate) {
		DEFAULT_.coordinate.background_color = COO_BACKGROUND_COLOR;
		DEFAULT_.coordinate.grid_color = iChart.dark(COO_BACKGROUND_COLOR, 0.3,0.1);
	}
	/**
	 * 使导出图片按钮有效
	 */
	DEFAULT_.listeners = {};
	DEFAULT_.listeners[ANIMATION_ ? 'afterAnimation' : 'draw'] = function(c) {
		download.disabled = false;
		IMAGE_DATA = this.target.canvas.toDataURL();
	}

	new iChart[TYPE_](DEFAULT_).draw();
}
function render() {
	if (!TYPE_)
		return;
	download.disabled = true;
	if (f) {
		doChart();
		f = false;
	} else {
		$canvas.fadeOut(300, function() {
			$(this).fadeIn(300);
			doChart();
		});
	}
}
var $form_tbody, $form_tr_temlate, $form_tr_head, $gallery_color_picker, $validateTips, download, download_a;
$(function() {
	$validateTips = $("#validateTips");
	$form_tbody = $("#form_tbody");
	download = document.getElementById("download");
	download_a = document.getElementById("download_a");
	$gallery_color_picker = $("#gallery_color_picker");
	$form_tr_temlate = $('<tr><td><input type="text" class="form_text" /></td><td><input type="text" class="form_text" /></td><td class="td_color"><input type="text" class="form_text"/></td><td><a href="javascript:void(0)" onclick="removeRow(this);">移除</a></td></tr>');
	$form_tr_head = $("#form_tr_head");

	$("#dialog-download").dialog({
		autoOpen : false,
		height : 448,
		width : 848,
		modal : true
	});

	var w = $(document.body).width();
	var datatype, type;
	$canvas = $("#canvasDiv");
	$(".gallery_draggable").click(function() {
		TYPE_ = $(this).attr('type');
		OPTION_ = $(this).attr('option');
		render();
	});

	$form_tr_temlate.find('.td_color .form_text').click(function(e) {
		$current_color = $(this);
		var i = $(this).parents("tr").prevAll().length;
		$gallery_color_picker.css("top", 6 + 29 * i).fadeIn();
		e.stopPropagation();
	});

	$("#gallery_color_picker .color").hover(function() {
		$(this).addClass("gallery_color_hover");
	}, function() {
		$(this).removeClass("gallery_color_hover");
	}).click(function() {
		var color = $(this).attr('color');
		$current_color.val(color);
		$current_color.parent("td").css("background-color", color);
	});

});
function addRow() {
	$form_tbody.append($form_tr_temlate.clone(true));
}
function removeRow(a) {
	$(a).parents("tr").remove();
}
