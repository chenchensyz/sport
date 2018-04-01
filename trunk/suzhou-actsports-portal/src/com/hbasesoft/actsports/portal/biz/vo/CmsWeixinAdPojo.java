package com.hbasesoft.actsports.portal.biz.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.hbasesoft.framework.db.core.BaseEntity;

/**
 * <Description> T_CMS_WEIXIN_AD的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2017年01月16日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
@Entity(name = "T_CMS_WEIXIN_AD")
public class CmsWeixinAdPojo extends BaseEntity {

	public static final String ID = "id";
	
    public static final String ORG_CODE = "orgCode";
    
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

    /** TITLE */
    @Column(name = "TITLE")
    private String title;

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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

}
