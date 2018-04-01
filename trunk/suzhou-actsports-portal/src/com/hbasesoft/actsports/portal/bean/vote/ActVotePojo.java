package com.hbasesoft.actsports.portal.bean.vote;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.hbasesoft.framework.db.core.BaseEntity;

/**
 * <Description> T_ACT_VOTE的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2016年12月20日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
@Entity(name = "T_ACT_VOTE")
public class ActVotePojo extends BaseEntity {

	public static final String START_VOTE = "A";
	
	public static final String ONCE = "once";//vote_way未Once时,用户只能投票一次
	
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

    /** vote_jpg */
    @Column(name = "vote_jpg")
    private String voteJpg;

    /** vote_remark */
    @Column(name = "vote_remark")
    private String voteRemark;

    /** vote_limit */
    @Column(name = "vote_limit")
    private String voteLimit;

    /** vote_name */
    @Column(name = "vote_name")
    private String voteName;

    /** vote_title */
    @Column(name = "vote_title")
    private String voteTitle;

    /** vote_rule */
    @Column(name = "vote_rule")
    private String voteRule;

    /** vote_way */
    @Column(name = "vote_way")
    private String voteWay;

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

    public String getVoteJpg() {
        return this.voteJpg;
    }

    public void setVoteJpg(String voteJpg) {
        this.voteJpg = voteJpg;
    }

    public String getVoteRemark() {
        return this.voteRemark;
    }

    public void setVoteRemark(String voteRemark) {
        this.voteRemark = voteRemark;
    }

    public String getVoteLimit() {
        return this.voteLimit;
    }

    public void setVoteLimit(String voteLimit) {
        this.voteLimit = voteLimit;
    }

    public String getVoteName() {
        return this.voteName;
    }

    public void setVoteName(String voteName) {
        this.voteName = voteName;
    }

    public String getVoteTitle() {
        return this.voteTitle;
    }

    public void setVoteTitle(String voteTitle) {
        this.voteTitle = voteTitle;
    }

    public String getVoteRule() {
        return this.voteRule;
    }

    public void setVoteRule(String voteRule) {
        this.voteRule = voteRule;
    }

    public String getVoteWay() {
        return this.voteWay;
    }

    public void setVoteWay(String voteWay) {
        this.voteWay = voteWay;
    }

}
