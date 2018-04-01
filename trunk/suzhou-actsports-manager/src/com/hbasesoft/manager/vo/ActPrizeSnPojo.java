package com.hbasesoft.manager.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;


/**
 * <Description> T_ACT_PRIZE_SN的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2017年07月16日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
@Entity(name = "T_ACT_PRIZE_SN")
public class ActPrizeSnPojo  {

	public static final String SN = "sn";
	
	public static final String STATUS = "status";
	
	public static final String TYPE = "type";
	
	public static final String NOT_RECEIVE = "0";
	
	public static final String RECEIVED = "1";
	
	public static final String ENTRYID = "entryId";
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

    /** type */
    @Column(name = "type")
    private String type;

    /** sn */
    @Column(name = "sn")
    private String sn;

    /** status */
    @Column(name = "status")
    private String status;

    /** create_date */
    @Column(name = "create_date")
    private java.util.Date createDate;

    /** update_date */
    @Column(name = "update_date")
    private java.util.Date updateDate;

    /** num */
    @Column(name = "num")
    private String num;

    @Column(name = "entry_id")
    private String entryId;
    
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getNum() {
        return this.num;
    }

    public void setNum(String num) {
        this.num = num;
    }

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}
   
}
