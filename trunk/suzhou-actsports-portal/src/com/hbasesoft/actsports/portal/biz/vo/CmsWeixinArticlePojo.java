package com.hbasesoft.actsports.portal.biz.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.hbasesoft.framework.db.core.BaseEntity;

/**
 * <Description> T_CMS_WEIXIN_ARTICLE的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2017年01月16日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
@Entity(name = "T_CMS_WEIXIN_ARTICLE")
public class CmsWeixinArticlePojo extends BaseEntity {

	public static final String ID = "id";
	
	public static final String COLUMN_ID = "columnId";
	
	public static final String CREATE_DATE = "createDate";
	
    public static final String ORG_CODE = "orgCode";
	
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** id */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id")
    private String id;

    /** title */
    @Column(name = "title")
    private String title;

    /** image_name */
    @Column(name = "image_name")
    private String imageName;

    /** image_href */
    @Column(name = "image_href")
    private String imageHref;

    /** summary */
    @Column(name = "summary")
    private String summary;

    /** content */
    @Column(name = "content")
    private String content;

    /** column_id */
    @Column(name = "column_id")
    private String columnId;

    /** accountid */
    @Column(name = "accountid")
    private String accountid;

    /** create_name */
    @Column(name = "create_name")
    private String createName;

    /** create_by */
    @Column(name = "create_by")
    private String createBy;

    /** create_date */
    @Column(name = "create_date")
    private java.util.Date createDate;

    /** UPDATE_DATE */
    @Column(name = "UPDATE_DATE")
    private java.util.Date updateDate;

    /** UPDATE_BY */
    @Column(name = "UPDATE_BY")
    private String updateBy;

    /** article_sort */
    @Column(name = "article_sort")
    private Integer articleSort;

    /** PARENT_ID */
    @Column(name = "PARENT_ID")
    private String parentId;

    /** PRICE_RANGE */
    @Column(name = "PRICE_RANGE")
    private String priceRange;

    /** org_code */
    @Column(name = "org_code")
    private String orgCode;
    
    @Column(name = "URL")
    private String url;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageName() {
        return this.imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageHref() {
        return this.imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getColumnId() {
        return this.columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getAccountid() {
        return this.accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getCreateName() {
        return this.createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
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

    public java.util.Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getArticleSort() {
        return this.articleSort;
    }

    public void setArticleSort(Integer articleSort) {
        this.articleSort = articleSort;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPriceRange() {
        return this.priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}