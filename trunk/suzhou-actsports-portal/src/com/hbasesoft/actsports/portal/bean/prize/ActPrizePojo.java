package com.hbasesoft.actsports.portal.bean.prize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.hbasesoft.framework.db.core.BaseEntity;

/**
 * <Description> T_ACT_PRIZE的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2017年01月06日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
@Entity(name = "T_ACT_PRIZE")
public class ActPrizePojo extends BaseEntity {

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

    /** prize_title */
    @Column(name = "prize_title")
    private String prizeTitle;

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

    /** prize_jpg */
    @Column(name = "prize_jpg")
    private String prizeJpg;

    /** prize_remark */
    @Column(name = "prize_remark")
    private String prizeRemark;

    /** prize_way */
    @Column(name = "prize_way")
    private String prizeWay;

    /** prize_count */
    @Column(name = "prize_count")
    private String prizeCount;

    /** prize_name */
    @Column(name = "prize_name")
    private String prizeName;

    /** prize_hit */
    @Column(name = "prize_hit")
    private String prizeHit;

    /** probability */
    @Column(name = "probability")
    private String probability;
    
    /** prize_hit */
    @Column(name = "receive_msg")
    private String receiveMsg;
    
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

    public String getPrizeTitle() {
        return this.prizeTitle;
    }

    public void setPrizeTitle(String prizeTitle) {
        this.prizeTitle = prizeTitle;
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

    public String getPrizeJpg() {
        return this.prizeJpg;
    }

    public void setPrizeJpg(String prizeJpg) {
        this.prizeJpg = prizeJpg;
    }

    public String getPrizeRemark() {
        return this.prizeRemark;
    }

    public void setPrizeRemark(String prizeRemark) {
        this.prizeRemark = prizeRemark;
    }

    public String getPrizeWay() {
        return this.prizeWay;
    }

    public void setPrizeWay(String prizeWay) {
        this.prizeWay = prizeWay;
    }

    public String getPrizeCount() {
        return this.prizeCount;
    }

    public void setPrizeCount(String prizeCount) {
        this.prizeCount = prizeCount;
    }

    public String getPrizeName() {
        return this.prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getPrizeHit() {
        return this.prizeHit;
    }

    public void setPrizeHit(String prizeHit) {
        this.prizeHit = prizeHit;
    }

    public String getProbability() {
        return this.probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

	public String getReceiveMsg() {
		return receiveMsg;
	}

	public void setReceiveMsg(String receiveMsg) {
		this.receiveMsg = receiveMsg;
	}

}
