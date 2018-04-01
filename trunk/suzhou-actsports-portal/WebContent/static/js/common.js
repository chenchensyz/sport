//input fileurl转换
function getObjectURL(file) {
    var url = null;
    if (window.createObjectURL != undefined) {
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) {
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) {
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}
function hasClass(obj, cls) {
    return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));
}
function addClass(obj, cls) {
    if(!this.hasClass(obj, cls)) obj.className += " " + cls;
}
function removeClass(obj, cls) {
    if(hasClass(obj, cls)) {
        var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
        obj.className = obj.className.replace(reg, ' ');
    }
}
function creatNode(org, classes) {
    mui.each(org, function(index, items) {
        var errorNode = document.createElement('p');
        errorNode.className = classes;
        items.appendChild(errorNode);
    });
}
function message(org, tip) {
    var org = org.parentNode.lastChild;
    org.parentNode.querySelector('.input-error').innerText = tip;
}
function onlyNum(org){
    org.value=org.value.replace(/[^\d]/g,'');
}
function noneSymbol(org){
    org.value=org.value.replace(/[^\a-zA-Z\u4E00-\u9FA5\d]/g,'');
}
function nickNameClass(value){
    var x;
    switch (value)
{
case '自家':
  x="home";
  break;
case '亲友':
  x="friend";
  break;
case '子女':
  x="children";
  break;
case "父母":
  x="parent";
  break;
case "房东":
  x="landlord";
  break;
default:
  x="other";
}
return x;
}
function checkMoney(obj){  
            var id = obj.id;  
            var val =obj.value;  
            var regStrs = [
              ['^0$', ''],//禁止录入首字母为0
              ['^\\.\$', ''],//禁止录入首字母为.
                ['^0(\\d+)$', '$1'], //禁止录入整数部分两位以上，但首位为0  
                ['[^\\d\\.]+$', ''], //禁止录入任何非数字和点  
                ['\\.(\\d?)\\.+', '.$1'], //禁止录入两个以上的点  
                ['^(\\d+\\.\\d{2}).+', '$1'] //禁止录入小数点后两位以上  
            ];  
            for(i=0; i<regStrs.length; i++){  
                var reg = new RegExp(regStrs[i][0]);  
                obj.value = obj.value.replace(reg, regStrs[i][1]);  
            }  
          }
function  installClassname(value){
                        var x;
                        switch (value)
                        {
                        case '开户':
                          x="open";
                          break;
                        case '管表安装':
                          x="install";
                          break;
                        case '通气点火':
                          x="fire";
                          break;
                        case "管表维修":
                          x="repair";
                          break;
                        case "紫荆维修":
                          x="service";
                          break;
                        default:
                          x="install";
                        }
                        return x;
                    }
function loadingStart(org){
    var pName = document.createElement('p');
    pName.className = 'loading';
//	pName.innerHTML = '<span class="mui-spinner"></span>正在加载...'
    pName.innerHTML = '<img src='+BASEPATH+'img/loading.gif/>';
    org.appendChild(pName)
}
function loadingEnd(org){
    var delName = org.querySelector('.loading');
    delName.remove();
}
function toDecimal(x) {    
        var f = parseFloat(x);    
        if (isNaN(f)) {    
            return false;    
        }    
        var f = Math.round(x*100)/100;    
        var s = f.toString();    
        var rs = s.indexOf('.');    
        if (rs < 0) {    
            rs = s.length;    
            s += '.';    
        }    
        while (s.length <= rs + 2) {    
            s += '0';    
        }    
        return s;    
    }


//
//function payFun(appId,timeStamp,nonceStr,package,signType,paySign){
//					  function onBridgeReady() {
//				      WeixinJSBridge.invoke(
//				          'getBrandWCPayRequest', {
//				              "appId": appId, //公众号名称，由商户传入     
//				              "timeStamp": timeStamp, //时间戳，自1970年以来的秒数     
//				              "nonceStr": nonceStr, //随机串     
//				              "package": package,
//				              "signType": signType, //微信签名方式：     
//				              "paySign": paySign //微信签名 
//				          },
//				          function(res) {
//				              if (res.err_msg == "get_brand_wcpay_request:ok") {
//				                  window.location.href = BASEPATH+'charge/to/chargeIndex';
//				              } else {
//				//                alert(JSON.stringify(res))
//				              }
//				              // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
//				          }
//				      );
//				  }
//				  
//服务类型
//function serviceName(value){
//		var x;
//		switch (value)
//		{
//			case 'REPAIR_APPOINT_TYPE':
//			  x ='紫荆维修';
//			  break;
//			case 'DEVICE_REPAIR_TYPE':
//			  x ='管表维修';
//			  break;
//			case 'DEVICE_INSTALL_TYPE':
//			  x ='管表安装类型';
//			  break;
//		}
//		return x;
//	}
