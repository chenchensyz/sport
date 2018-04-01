package com.hbasesoft.actsports.portal.bean.prize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.hbasesoft.framework.db.core.BaseEntity;

/**
 * <Description> T_ACT_PRIZE_RESULT_CHOOSE的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2017年01月04日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
@Entity(name = "T_ACT_PRIZE_RESULT_CHOOSE")
public class ActPrizeResultChoosePojo extends BaseEntity {

	
	public static final String PRIZE_ID = "prizeId";
	
	public static final String USER_ID = "userId";
	
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

    /** prize_id */
    @Column(name = "prize_id")
    private String prizeId;

    /** user_id */
    @Column(name = "user_id")
    private String userId;

    /** choose */
    @Column(name = "choose")
    private String choose;

    /** sn */
    @Column(name = "sn")
    private String sn;
    
    /** create_time */
    @Column(name = "create_time")
    private java.util.Date createTime;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrizeId() {
        return this.prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
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

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

}
