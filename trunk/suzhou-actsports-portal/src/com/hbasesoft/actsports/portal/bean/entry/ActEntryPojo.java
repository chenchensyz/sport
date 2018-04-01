package com.hbasesoft.actsports.portal.bean.entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.hbasesoft.framework.db.core.BaseEntity;

/**
 * <Description> T_ACT_ENTRY的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2016年12月13日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
@Entity(name = "T_ACT_ENTRY")
public class ActEntryPojo extends BaseEntity {

	public static final String ID = "id";
	
	public static final String DISPLAY_RANK = "display";
	
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

    /** entry_name */
    @Column(name = "entry_name")
    private String entryName;

    /** start_time */
    @Column(name = "start_time")
    private java.util.Date startTime;

    /** end_time */
    @Column(name = "end_time")
    private java.util.Date endTime;

    /** entry_jpg */
    @Column(name = "entry_jpg")
    private String entryJpg;

    /** entry_title */
    @Column(name = "entry_title")
    private String entryTitle;

    /** choose_limit */
    @Column(name = "choose_limit")
    private String chooseLimit;

    /** entry_remark */
    @Column(name = "entry_remark")
    private String entryRemark;

    /** status */
    @Column(name = "status")
    private String status;

    /** sucess_message */
    @Column(name = "sucess_message")
    private String sucessMessage;

    /** end_message */
    @Column(name = "end_message")
    private String endMessage;

    /** entry_rank */
    @Column(name = "entry_rank")
    private String entryRank;

    @Column(name = "entry_prize")
    private String entryPrize;
    
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

    public String getEntryName() {
        return this.entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
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

    public String getEntryJpg() {
        return this.entryJpg;
    }

    public void setEntryJpg(String entryJpg) {
        this.entryJpg = entryJpg;
    }

    public String getEntryTitle() {
        return this.entryTitle;
    }

    public void setEntryTitle(String entryTitle) {
        this.entryTitle = entryTitle;
    }

    public String getChooseLimit() {
        return this.chooseLimit;
    }

    public void setChooseLimit(String chooseLimit) {
        this.chooseLimit = chooseLimit;
    }

    public String getEntryRemark() {
        return this.entryRemark;
    }

    public void setEntryRemark(String entryRemark) {
        this.entryRemark = entryRemark;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSucessMessage() {
        return this.sucessMessage;
    }

    public void setSucessMessage(String sucessMessage) {
        this.sucessMessage = sucessMessage;
    }

    public String getEndMessage() {
        return this.endMessage;
    }

    public void setEndMessage(String endMessage) {
        this.endMessage = endMessage;
    }

    public String getEntryRank() {
        return this.entryRank;
    }

    public void setEntryRank(String entryRank) {
        this.entryRank = entryRank;
    }

	public String getEntryPrize() {
		return entryPrize;
	}

	public void setEntryPrize(String entryPrize) {
		this.entryPrize = entryPrize;
	}

    
}
