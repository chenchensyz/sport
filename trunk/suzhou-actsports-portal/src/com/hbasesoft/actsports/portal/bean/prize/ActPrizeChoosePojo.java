package com.hbasesoft.actsports.portal.bean.prize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.hbasesoft.framework.db.core.BaseEntity;

/**
 * <Description> T_ACT_PRIZE_CHOOSE的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2017年01月06日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
@Entity(name = "T_ACT_PRIZE_CHOOSE")
public class ActPrizeChoosePojo extends BaseEntity {

	public static final String PRIZE_ID = "prizeId";
	
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

    /** title */
    @Column(name = "title")
    private String title;

    /** count_num */
    @Column(name = "count_num")
    private String countNum;

    /** prize_id */
    @Column(name = "prize_id")
    private String prizeId;

    /** remain_num */
    @Column(name = "remain_num")
    private String remainNum;
    
    /** prize_type */
    @Column(name = "prize_type")
    private String prizeType;
    
    @Column(name = "entry_id")
    private String entryId;

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

    public String getCountNum() {
        return this.countNum;
    }

    public void setCountNum(String countNum) {
        this.countNum = countNum;
    }

    public String getPrizeId() {
        return this.prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }

    public String getRemainNum() {
        return this.remainNum;
    }

    public void setRemainNum(String remainNum) {
        this.remainNum = remainNum;
    }

	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

}
