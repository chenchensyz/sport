(function($,doc){
	$.init();
	console.log(JSON.parse(pageTemplate))
	var navData=[],navHtml = '',contentHtml='';
	$.each(JSON.parse(pageTemplate),function(index,items){
			var navItem = {};
			$.ajax(BASEPATH+"queryAritycle/"+items.contentId,{
				dataType:'json',//服务器返回json格式数据
				type:'get',//HTTP请求类型
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				timeout:10000000,//超时时间设置为10秒；
				async:false,
				success:function(data){
					if(data.code == '0'){console.log(data)
						if(items.position==1){
							doc.querySelector('figure img').src = data.newsItem.imageHref;
						}else{
							navItem.id = items.contentId;
							navItem.title = data.newsItem.title;
							navItem.content = data.newsItem.content;
						}
					}else{
						$.alert(data.message)
					}
				},
				error:function(xhr,type,errorThrown){
					if(type=='abort'||type=='timeout'){mui.alert('网络无连接，请刷新重试')}else{mui.alert('访问失败，请联系客服')} 
				}
			});
			if(navItem.title){
				navData.push(navItem);	
			}
					
	});

	$.each(navData,function(index,items){
		if(index==0){
			navHtml+='<li class="active" id='+items.id+' style="width:'+(100/navData.length)+'%">'+items.title+'</li>';
		}else{
			navHtml+='<li id='+items.id+' style="width:'+(100/navData.length)+'%">'+items.title+'</li>';
		}
		contentHtml+='<div class="navContent" toHref ='+items.id+'>'+items.content+'</div>'
	});
	doc.querySelector('.nav ul').innerHTML = navHtml;
	doc.querySelector('.content').innerHTML = contentHtml;

	mui(".nav ul").on("tap","li",function(){
		var idName = this.id;
		var gridList = doc.querySelectorAll('.navContent');
		var navLi = doc.querySelectorAll('.nav li');
		$.each(gridList,function(index,items){
			if(items.getAttribute('toHref') == idName){
				gridList[index].style.display='block';
			}else{
				gridList[index].style.display='none';
			}
		});
		$.each(navLi, function(index,items) {
			items.className = '';
		});
		this.className = this.className+' active';
	});

})(mui,document)