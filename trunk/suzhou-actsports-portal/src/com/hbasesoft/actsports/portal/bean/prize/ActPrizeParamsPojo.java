package com.hbasesoft.actsports.portal.bean.prize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.hbasesoft.framework.db.core.BaseEntity;

/**
 * <Description> T_ACT_PRIZE_PARAMS的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2017年01月04日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
@Entity(name = "T_ACT_PRIZE_PARAMS")
public class ActPrizeParamsPojo extends BaseEntity {

	public static final String PRIZE_ID = "prizeId";
	
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

    /** rule */
    @Column(name = "rule")
    private String rule;

    /** required */
    @Column(name = "required")
    private String required;

    /** minlength */
    @Column(name = "minlength")
    private String minlength;

    /** maxlength */
    @Column(name = "maxlength")
    private String maxlength;

    /** type */
    @Column(name = "type")
    private String type;

    /** prize_id */
    @Column(name = "prize_id")
    private String prizeId;

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

    public String getRule() {
        return this.rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getRequired() {
        return this.required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getMinlength() {
        return this.minlength;
    }

    public void setMinlength(String minlength) {
        this.minlength = minlength;
    }

    public String getMaxlength() {
        return this.maxlength;
    }

    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrizeId() {
        return this.prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }

}
