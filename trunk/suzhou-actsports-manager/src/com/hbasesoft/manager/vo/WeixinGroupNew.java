package com.hbasesoft.manager.vo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_act_user_group")
public class WeixinGroupNew implements Serializable {
	private String id;
	private String groupId;
	private String groupName;
	private String accoundId;
	private String count;
	private List<WeixinUserAccount> weixinUserAccount;
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "groupid")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "groupname")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "accountid")
	public String getAccoundId() {
		return accoundId;
	}

	public void setAccoundId(String accoundId) {
		this.accoundId = accoundId;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

//	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="weixinGroupNew")  
//	public List<WeixinUserAccount> getWeixinUserAccount() {
//		return weixinUserAccount;
//	}
//
//	public void setWeixinUserAccount(List<WeixinUserAccount> weixinUserAccount) {
//		this.weixinUserAccount = weixinUserAccount;
//	}

	

}