package com.hbasesoft.actsports.portal.bean.entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.hbasesoft.framework.db.core.BaseEntity;

/**
 * <Description> T_ACT_ENTRY_RESULT_CHOOSE的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2016年12月16日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
@Entity(name = "T_ACT_ENTRY_RESULT_CHOOSE")
public class ActEntryResultChoosePojo extends BaseEntity {

	public static final String USER_ID = "userId";
	
	public static final String ENTRY_ID = "entryId";
	
	public static final String CHOOSE_ID = "choose";
	
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

    /** entry_id */
    @Column(name = "entry_id")
    private String entryId;

    /** user_id */
    @Column(name = "user_id")
    private String userId;

    /** choose */
    @Column(name = "choose")
    private String choose;

    /** create_time */
    @Column(name = "create_time")
    private java.util.Date createTime;

    /** entry_rank */
    @Column(name = "entry_rank")
    private Integer entryRank;

    @Column(name = "sn")
    private String sn;
    
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntryId() {
        return this.entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChoose() {
        return this.choose;
    }

    public void setChoose(String choose) {
        this.choose = choose;
    }

    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public Integer getEntryRank() {
        return this.entryRank;
    }

    public void setEntryRank(Integer entryRank) {
        this.entryRank = entryRank;
    }

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

}
