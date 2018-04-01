package com.hbasesoft.actsports.portal.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.hbasesoft.framework.db.core.BaseEntity;

/**
 * <Description> T_ACT_USER_ACCOUNT的Pojo<br>
 * 
 * @author 工具生成<br>
 * @version 1.0<br>
 * @CreateDate 2016年12月09日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.api.bean.BaseEntity <br>
 */
@Entity(name = "T_ACT_USER_ACCOUNT")
public class ActUserAccountPojo extends BaseEntity {

    public static final String STATE = "state";

    public static final String ACCOUNT = "account";

    public static final String TYPE = "type";

    public static final String USER_ID = "userId";
    
    
    
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

    /** user_id */
    @Column(name = "user_id")
    private String userId;

    /** type */
    @Column(name = "type")
    private String type;

    /** account */
    @Column(name = "account")
    private String account;

    /** value */
    @Column(name = "value")
    private String value;

    /** state */
    @Column(name = "state")
    private String state;

    /** create_time */
    @Column(name = "create_time")
    private java.util.Date createTime;

    /** ext1 */
    @Column(name = "ext1")
    private String ext1;

    /** ext2 */
    @Column(name = "ext2")
    private String ext2;
    
    @Column(name = "blacklist")
    private String blackList;
    
    @Column(name = "accoutId")
    private String accoutid;
    
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public String getExt1() {
        return this.ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return this.ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

	public String getBlackList() {
		return blackList;
	}

	public void setBlackList(String blackList) {
		this.blackList = blackList;
	}

	public String getAccoutid() {
		return accoutid;
	}

	public void setAccoutid(String accoutid) {
		this.accoutid = accoutid;
	}

}
