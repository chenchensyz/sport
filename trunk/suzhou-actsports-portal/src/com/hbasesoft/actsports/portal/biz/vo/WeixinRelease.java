package com.hbasesoft.actsports.portal.biz.vo;

/**
 * <Description> T_CMS_PAGE_MODULE的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2017年06月20日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
public class WeixinRelease extends AbstractVo {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    

    /** orders */
    private String orders;

    /** news_id */

    /** father_id */
    private String fatherId;

    private String newsId;
    
    /** position*/
    private String position;
    
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }


    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    
}
