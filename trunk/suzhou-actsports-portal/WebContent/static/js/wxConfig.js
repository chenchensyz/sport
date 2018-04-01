var weixinConfig = {
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: _APP_ID, // 必填，公众号的唯一标识
    timestamp:_TIMESTAMP, // 必填，生成签名的时间戳
    nonceStr: _NONCE_STR, // 必填，生成签名的随机串
    signature: _SIGNATURE
}
wx.config(weixinConfig);
wx.ready(function(Api){
    wx.hideOptionMenu();
});