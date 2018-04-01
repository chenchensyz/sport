(function($,doc){
	$.init({
		pullRefresh: {
			container: '#pullrefresh',
		
			up: {
				contentrefresh: '正在加载...',
				callback: pullupRefresh
			}
		}
	});
	console.log(JSON.parse(pageTemplate))
	var navData=[],navHtml = '',contentHtml='',pullIndex=0,slideData = [],slideHtml = '',slideIndicator = '';
	$.each(JSON.parse(pageTemplate),function(index,items){
		var navItem = {},slideItem = {};
		if(items.position==1){
			$.ajax(BASEPATH+"queryAritycle/"+items.newsId,{
				dataType:'json',//服务器返回json格式数据
				type:'get',//HTTP请求类型
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				timeout:10000000,//超时时间设置为10秒；
				async:false,
				success:function(data){
					if(data.code == '0'){console.log(data)
							slideItem.id = data.newsItem.id;
							slideItem.title = data.newsItem.title;
							slideItem.imagepath = data.newsItem.imagepath;
							slideItem.url =  data.newsItem.url;
					}else{
						$.alert(data.message)
					}
				},
				error:function(xhr,type,errorThrown){
					if(type=='abort'||type=='timeout'){mui.alert('网络无连接，请刷新重试')}else{mui.alert('访问失败，请联系客服')} 
				}
			});
		}else{
			$.ajax(BASEPATH+"cms/queryMenu",{
				dataType:'json',//服务器返回json格式数据
				type:'get',//HTTP请求类型
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				timeout:10000000,//超时时间设置为10秒；
				async:false,
				data:{
					orgCode:'SUZHOU',
					contentId:items.fatherId
				},
				success:function(data){
					if(data.code == '0'){console.log(data)
						navItem.id = data.menu.id;
						navItem.title = data.menu.name;
						navItem.pageIndex=1;
						navItem.pageSize=3;
					}
				},
				error:function(xhr,type,errorThrown){
					if(type=='abort'||type=='timeout'){mui.alert('网络无连接，请刷新重试')}else{mui.alert('访问失败，请联系客服')} 
				}
			});
		}
		if(navItem.title){
			navData.push(navItem);	
		}
		if(slideItem.id){
			slideData.push(slideItem);	
		}
	});
	
	console.log(navData);
	console.log(slideData);
	//轮播列表
	slideHtml = '<div class="mui-slider-group mui-slider-loop"><div class="mui-slider-item mui-slider-item-duplicate">'
				+'<a href='+slideData[slideData.length-1].url+'><img src='+slideData[slideData.length-1].imagepath+'><p class="mui-slider-title">'+slideData[slideData.length-1].title+'</p></a></div>';
	slideIndicator ='<div class="mui-slider-indicator mui-text-right">';
	$.each(slideData,function(index,item){
		slideHtml+='<div class="mui-slider-item">'
				+'<a href='+slideData[index].url+'><img src='+slideData[index].imagepath+'><p class="mui-slider-title">'+slideData[index].title+'</p></a></div>';
		if(index==0&&slideData.length>1){
			slideIndicator+='<div class="mui-indicator mui-active"></div>'
		}
		if(index>0){
			slideIndicator+='<div class="mui-indicator"></div>'
		}
	});
	slideHtml+= '<div class="mui-slider-item mui-slider-item-duplicate">'
				+'<a href='+slideData[0].url+'><img src='+slideData[0].imagepath+'><p class="mui-slider-title">'+slideData[0].title+'</p></a></div>';
	slideHtml+='</div>';
	slideIndicator+='</div>';
	console.log(slideHtml);
	console.log(slideIndicator);
	doc.querySelector('#slider').innerHTML = slideHtml+slideIndicator+'</div>';
	if(slideData.length>1){
		mui('#slider').slider({
			interval: 5000
		});
	}
	
	//导航列表
	$.each(navData,function(index,items){
		if(index==0){
			navHtml+='<li class="active" id='+items.id+' style="width:'+(100/navData.length)+'%">'+items.title+'</li>';
		}else{
			navHtml+='<li id='+items.id+' style="width:'+(100/navData.length)+'%">'+items.title+'</li>';
		}
	});
	doc.querySelector('.nav ul').innerHTML = navHtml;
	var navLi = doc.querySelectorAll('.nav li');
	mui(".nav ul").on("tap","li",function(){
		var idName = this.id;
		$.each(navData,function(index,items){
			if(items.id == idName){
				pullIndex = index;
				navData[pullIndex].pageIndex = 1;
				doc.querySelector('#pullrefresh ul').innerHTML = '';
				//pullupRefresh();
				mui('#pullrefresh').pullRefresh().refresh(true);
				mui('#pullrefresh').pullRefresh().pullupLoading();
				
			}
		});
		$.each(navLi, function(index,items) {
			items.className = '';
		});
		this.className = this.className+' active';
	});


	function pullupRefresh(){
		var mediaList = doc.querySelector('#pullrefresh ul');
		$.ajax(BASEPATH+"cms/queryMenu",{
				dataType:'json',//服务器返回json格式数据
				type:'get',//HTTP请求类型
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				timeout:10000000,//超时时间设置为10秒；
				async:false,
				data:{
					"orgCode":'SUZHOU',
					"contentId":navData[pullIndex].id,
					"pageIndex": navData[pullIndex].pageIndex++,
					"pageSize": navData[pullIndex].pageSize
				},
				success:function(data){
					if(data.code == '0'){console.log(data)
						var articleList = data.menu.newsItemList;
						mui('#pullrefresh').pullRefresh().endPullupToRefresh(articleList.length==0); //参数为true代表没有更多数据了。
						if(articleList.length>0){
		
							$.each(articleList, function(index,items) {
								var Li = doc.createElement('li');
								Li.className = 'mui-table-view-cell mui-media';
								var itemHtml = '';
								itemHtml+='<a href='+items.url+'><img class="mui-media-object mui-pull-left" src='+items.imagepath+'><div class="mui-media-body"><p>'
										+items.title+'</p></div>'
								Li.innerHTML = itemHtml;
								mediaList.appendChild(Li);
							});
					
						}
					}
				},
				error:function(xhr,type,errorThrown){
					if(type=='abort'||type=='timeout'){mui.alert('网络无连接，请刷新重试')}else{mui.alert('访问失败，请联系客服')} 
				}
			});
	}

	$.ready(function(){
		$("#pullrefresh .mui-table-view").on('tap',"li a" ,function(){
			var href = this.getAttribute('href');
			window.location.href = href;
       });
	})

	if (mui.os.plus) { 
		mui.plusReady(function() {
			setTimeout(function() {
				mui('#pullrefresh').pullRefresh().pullupLoading();
			}, 1000); 

		});
	} else {
		mui.ready(function() {
			mui('#pullrefresh').pullRefresh().pullupLoading();
		});
	}



})(mui,document)