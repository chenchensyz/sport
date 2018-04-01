package com.hbasesoft.actsports.portal.bean.collect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.hbasesoft.framework.db.core.BaseEntity;

/**
 * <Description> T_ACT_COLLECT的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2017年01月24日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
@Entity(name = "T_ACT_COLLECT")
public class ActCollectPojo extends BaseEntity {

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

    /** create_name */
    @Column(name = "create_name")
    private String createName;

    /** create_by */
    @Column(name = "create_by")
    private String createBy;

    /** create_date */
    @Column(name = "create_date")
    private java.util.Date createDate;

    /** update_name */
    @Column(name = "update_name")
    private String updateName;

    /** update_by */
    @Column(name = "update_by")
    private String updateBy;

    /** update_date */
    @Column(name = "update_date")
    private java.util.Date updateDate;

    /** sys_org_code */
    @Column(name = "sys_org_code")
    private String sysOrgCode;

    /** sys_company_code */
    @Column(name = "sys_company_code")
    private String sysCompanyCode;

    /** collect_title */
    @Column(name = "collect_title")
    private String collectTitle;

    /** start_time */
    @Column(name = "start_time")
    private java.util.Date startTime;

    /** end_time */
    @Column(name = "end_time")
    private java.util.Date endTime;

    /** sucess_message */
    @Column(name = "sucess_message")
    private String sucessMessage;

    /** status */
    @Column(name = "status")
    private String status;

    /** end_message */
    @Column(name = "end_message")
    private String endMessage;

    /** collect_jpg */
    @Column(name = "collect_jpg")
    private String collectJpg;

    /** collect_remark */
    @Column(name = "collect_remark")
    private String collectRemark;

    /** collect_name */
    @Column(name = "collect_name")
    private String collectName;

    /** attachment_rule */
    @Column(name = "attachment_rule")
    private String attachmentRule;

    /** allow_image */
    @Column(name = "allow_image")
    private String allowImage;

    /** collect_min */
    @Column(name = "collect_min")
    private String collectMin;

    /** collect_max */
    @Column(name = "collect_max")
    private String collectMax;
    
    @Column(name = "img_len")
    private String imgLen;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUpdateName() {
        return this.updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public java.util.Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getSysOrgCode() {
        return this.sysOrgCode;
    }

    public void setSysOrgCode(String sysOrgCode) {
        this.sysOrgCode = sysOrgCode;
    }

    public String getSysCompanyCode() {
        return this.sysCompanyCode;
    }

    public void setSysCompanyCode(String sysCompanyCode) {
        this.sysCompanyCode = sysCompanyCode;
    }

    public String getCollectTitle() {
        return this.collectTitle;
    }

    public void setCollectTitle(String collectTitle) {
        this.collectTitle = collectTitle;
    }

    public java.util.Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(java.util.Date startTime) {
        this.startTime = startTime;
    }

    public java.util.Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }

    public String getSucessMessage() {
        return this.sucessMessage;
    }

    public void setSucessMessage(String sucessMessage) {
        this.sucessMessage = sucessMessage;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEndMessage() {
        return this.endMessage;
    }

    public void setEndMessage(String endMessage) {
        this.endMessage = endMessage;
    }

    public String getCollectJpg() {
        return this.collectJpg;
    }

    public void setCollectJpg(String collectJpg) {
        this.collectJpg = collectJpg;
    }

    public String getCollectRemark() {
        return this.collectRemark;
    }

    public void setCollectRemark(String collectRemark) {
        this.collectRemark = collectRemark;
    }

    public String getCollectName() {
        return this.collectName;
    }

    public void setCollectName(String collectName) {
        this.collectName = collectName;
    }

    public String getAttachmentRule() {
        return this.attachmentRule;
    }

    public void setAttachmentRule(String attachmentRule) {
        this.attachmentRule = attachmentRule;
    }

    public String getAllowImage() {
        return this.allowImage;
    }

    public void setAllowImage(String allowImage) {
        this.allowImage = allowImage;
    }

    public String getCollectMin() {
        return this.collectMin;
    }

    public void setCollectMin(String collectMin) {
        this.collectMin = collectMin;
    }

    public String getCollectMax() {
        return this.collectMax;
    }

    public void setCollectMax(String collectMax) {
        this.collectMax = collectMax;
    }

    public String getImgLen() {
        return imgLen;
    }

    public void setImgLen(String imgLen) {
        this.imgLen = imgLen;
    }
    
    

}
