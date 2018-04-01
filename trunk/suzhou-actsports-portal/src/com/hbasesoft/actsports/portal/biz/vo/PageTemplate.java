package com.hbasesoft.actsports.portal.biz.vo;

import java.util.List;

/**
 * <Description> T_CMS_PAGE_TEMPLATE的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2017年06月20日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
public class PageTemplate extends AbstractVo {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public static final String STATE = "state";
	
	public static final String CODE = "code";
	
    /** id */
    private String id;

    /** name */
    private String name;

    /** code */
    private String code;

    /** state */
    private String state;

    /** location */
    private String location;

    private List<WeixinRelease> weixinReleaseList;
    
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<WeixinRelease> getWeixinReleaseList() {
        return weixinReleaseList;
    }

    public void setWeixinReleaseList(List<WeixinRelease> weixinReleaseList) {
        this.weixinReleaseList = weixinReleaseList;
    }

	

}
