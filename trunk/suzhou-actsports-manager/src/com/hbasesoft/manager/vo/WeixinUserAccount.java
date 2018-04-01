package com.hbasesoft.manager.vo;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_act_user_account")
public class WeixinUserAccount {
	
	@Id
	@GeneratedValue(generator = "uuid") 
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
    
	@Column(name="user_id")
    private String user_id;
    
	@Column(name="type")
    private String type;
   
	@Column(name="account")
    private String account;
   
	@Column(name="value")
    private String value;
    
	@Column(name="state")
    private String state;
    
	@Column(name="create_time")
    private Date create_time;
    
	@Column(name="ext1")
    private String ext1;
    
	@Column(name="ext2")
    private String ext2;
    
	@Column(name="accountid")
    private String accountId;
	
	@Column(name="nickname")
    private String nickName;
	
	@Column(name="groupid")
    private String groupId;
	
	
    @Column(name="remark")
    private String remark;
	
	@Column(name="blacklist")
    private String blackList;
	
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
//	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)  
//    @JoinColumn(name="groupid") 
//	public WeixinGroupNew getWeixinGroupNew() {
//		return weixinGroupNew;
//	}
//
//	public void setWeixinGroupNew(WeixinGroupNew weixinGroupNew) {
//		this.weixinGroupNew = weixinGroupNew;
//	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    public String getBlackList() {
		return blackList;
	}

	public void setBlackList(String blackList) {
		this.blackList = blackList;
	}

	
	
}