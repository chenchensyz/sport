package com.hbasesoft.actsports.portal.constant;

/**
 * 集团微信的信息配置
 * 
 * @author meiguiyang
 */
public interface WeChatConstants {

    /** token缓存时间 */
    int TOKEN_CACHE_TIME = 90 * 60;

    /**
     * 微信接口请求成功返回码
     */
    String SUCCESS = "0";

    /**
     * 获取access_token请求URL,get
     */
    String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";

    /**
     * 获取jsapi_ticket请求URL,get
     */
    String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi";

    /**
     * 获取media下载请求
     */
    String MEDIA_DOWN_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token={0}&media_id={1}";

    /**
     * 获取code_url,其他redirect_uri,需要urlencode,state 非必填字段,
     */
    String OAUTH2_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type=code&scope={2}&state={3}#wechat_redirect";

    /**
     * 根据code获取openid,oauth2 access_token URL
     */
    String OAUTH2_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";

    /**
     * 发送模板消息URL
     */
    String TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";

    /**
     * 获取用户信息URL
     */
    String USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={0}&openid={1}&lang=zh_CN";

    /**
     * 微信的统一下单地址
     */
    String ORDER_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 退费接口
     */
    String REFUND_API = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 不弹出授权页面，直接跳转，只能获取用户openid）
     */
    String scope_snsapi_base = "snsapi_base";

    /**
     * 弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
     */
    String scope_snsapi_userinfo = "snsapi_userinfo";

    /**
     * 获取用户增减数据
     */
    String get_user_summary = "https://api.weixin.qq.com/datacube/getusersummary?access_token={0}&begin_date={1}&end_date={2}";

    /**
     * 获取累计用户数据
     */
    String get_user_cumulate = "https://api.weixin.qq.com/datacube/getusercumulate?access_token={0}&begin_date={1}&end_date={2}";
    
    /**
     * 上传其他附件media请求
     */
    String MEDIA_UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token={0}";
}
