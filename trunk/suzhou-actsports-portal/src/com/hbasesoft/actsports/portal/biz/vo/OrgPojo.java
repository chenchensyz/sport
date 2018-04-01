package com.hbasesoft.actsports.portal.biz.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.hbasesoft.framework.db.core.BaseEntity;

/**
 * <Description> T_VCC_ORG的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2016年08月14日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
@Entity(name = "T_VCC_ORG")
public class OrgPojo extends BaseEntity {

    public static final String STATE_TEST = "T";

    public static final String ORG_CODE = "orgCode";

    public static final String SHORT_NAME = "shortName";

    public static final String STATE = "state";

    public static final String HOST = "host";

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** ID */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID")
    private String id;

    /** depart_id */
    @Column(name = "depart_id")
    private String departId;

    /** short_name */
    @Column(name = "short_name")
    private String shortName;

    /** area_code */
    @Column(name = "area_code")
    private String areaCode;

    /** protocol */
    @Column(name = "protocol")
    private String protocol;

    /** state */
    @Column(name = "state")
    private String state;

    /** tel */
    @Column(name = "tel")
    private String tel;

    /** repairTel */
    @Column(name = "repairTel")
    private String repairtel;

    /** create_time */
    @Column(name = "create_time")
    private java.util.Date createTime;

    /** update_time */
    @Column(name = "update_time")
    private java.util.Date updateTime;

    /** update_by */
    @Column(name = "update_by")
    private String updateBy;

    /** org_code */
    @Column(name = "org_code")
    private String orgCode;

    /** org_name */
    @Column(name = "org_name")
    private String orgName;

    /** remark */
    @Column(name = "remark")
    private String remark;

    @Column(name = "host")
    private String host;

    /** remark */
    @Column(name = "price_desc")
    private String priceDesc;
    
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartId() {
        return this.departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRepairtel() {
        return this.repairtel;
    }

    public void setRepairtel(String repairtel) {
        this.repairtel = repairtel;
    }

    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public java.util.Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
    public String getPriceDesc() {
        return priceDesc;
    }

    public void setPriceDesc(String priceDesc) {
        this.priceDesc = priceDesc;
    }
    
    @Override  
    public boolean equals(Object o) {  
        if (o instanceof OrgPojo) {  
            OrgPojo orgPojo = (OrgPojo) o;  
            return this.areaCode.equals(orgPojo.areaCode)  
                    && this.createTime.equals(orgPojo.createTime)  
                    && this.departId.equals(orgPojo.departId)  
                    && this.host.equals(orgPojo.host)
                    && this.id.equals(orgPojo.id)
                    && this.orgCode.equals(orgPojo.orgCode)
                    && this.orgName.equals(orgPojo.orgName)
                    && this.priceDesc.equals(orgPojo.priceDesc)
                    && this.protocol.equals(orgPojo.protocol)
                    && this.remark.equals(orgPojo.remark)
                    && this.repairtel.equals(orgPojo.repairtel)
                    && this.shortName.equals(orgPojo.shortName)
                    && this.state.equals(orgPojo.state)
                    && this.tel.equals(orgPojo.tel)
                    && this.updateBy.equals(orgPojo.updateBy)
                    && this.updateTime.equals(orgPojo.updateTime);
        }  
        return super.equals(o);  
    }  

}
