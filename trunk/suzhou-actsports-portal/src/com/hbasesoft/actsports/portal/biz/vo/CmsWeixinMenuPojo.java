package com.hbasesoft.actsports.portal.biz.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.hbasesoft.framework.db.core.BaseEntity;

/**
 * <Description> T_CMS_WEIXIN_MENU的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2017年01月16日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
@Entity(name = "T_CMS_WEIXIN_MENU")
public class CmsWeixinMenuPojo extends BaseEntity {

	
	public static final String ORG_CODE = "orgCode";
	
	public static final String MENU_CODE = "menuCode";
	
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** ID */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID")
    private String id;

    /** ACCOUNTID */
    @Column(name = "ACCOUNTID")
    private String accountid;

    /** CREATE_BY */
    @Column(name = "CREATE_BY")
    private String createBy;

    /** CREATE_DATE */
    @Column(name = "CREATE_DATE")
    private java.util.Date createDate;

    /** CREATE_NAME */
    @Column(name = "CREATE_NAME")
    private String createName;

    /** IMAGE_HREF */
    @Column(name = "IMAGE_HREF")
    private String imageHref;

    /** IMAGE_NAME */
    @Column(name = "IMAGE_NAME")
    private String imageName;

    /** NAME */
    @Column(name = "NAME")
    private String name;

    /** TYPE */
    @Column(name = "TYPE")
    private String type;

    /** SHOW_FLAG */
    @Column(name = "SHOW_FLAG")
    private String showFlag;

    /** ORDERS */
    @Column(name = "ORDERS")
    private Integer orders;

    /** LINK_URL */
    @Column(name = "LINK_URL")
    private String linkUrl;

    /** VISIT_TYPE */
    @Column(name = "VISIT_TYPE")
    private String visitType;

    /** PARENT_ID */
    @Column(name = "PARENT_ID")
    private String parentId;

    /** pid */
    @Column(name = "pid")
    private String pid;

    /** CUS_TEMPLATE */
    @Column(name = "CUS_TEMPLATE")
    private String cusTemplate;

    /** MENU_CODE */
    @Column(name = "MENU_CODE")
    private String menuCode;

    /** ORG_CODE */
    @Column(name = "ORG_CODE")
    private String orgCode;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountid() {
        return this.accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public java.util.Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateName() {
        return this.createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getImageHref() {
        return this.imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    public String getImageName() {
        return this.imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShowFlag() {
        return this.showFlag;
    }

    public void setShowFlag(String showFlag) {
        this.showFlag = showFlag;
    }

    public Integer getOrders() {
        return this.orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public String getLinkUrl() {
        return this.linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getVisitType() {
        return this.visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCusTemplate() {
        return this.cusTemplate;
    }

    public void setCusTemplate(String cusTemplate) {
        this.cusTemplate = cusTemplate;
    }

    public String getMenuCode() {
        return this.menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

}
