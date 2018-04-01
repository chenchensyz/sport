package com.hbasesoft.actsports.portal.bean.collect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.hbasesoft.framework.db.core.BaseEntity;

/**
 * <Description> T_ACT_COLLECT_PARAMS的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2016年12月28日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
@Entity(name = "T_ACT_COLLECT_PARAMS")
public class ActCollectParamsPojo extends BaseEntity {

	public static final String COLLECT_ID = "collectId";
	
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

    /** collect_id */
    @Column(name = "collect_id")
    private String collectId;

    /** minlength */
    @Column(name = "minlength")
    private String minlength;

    /** maxlength */
    @Column(name = "maxlength")
    private String maxlength;

    /** type */
    @Column(name = "type")
    private String type;

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

    public String getCollectId() {
        return this.collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
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

}
