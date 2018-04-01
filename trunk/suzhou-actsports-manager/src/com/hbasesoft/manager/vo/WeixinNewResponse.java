package com.hbasesoft.manager.vo;

import com.hbasesoft.framework.manager.core.common.entity.IdEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "weixin_autoresponse")
public class WeixinNewResponse extends IdEntity {
	public static final String KEYWORD = "keyWord";
	private String keyWord;
	private String resContent;
	private String templateName;
	private String addTime;
	private String msgType;
	private String accountId;
	private String groupId;

	@Column(name = "accountid", nullable = true, length = 100)
	public String getAccountId() {
		return this.accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Column(name = "keyword", nullable = true, length = 255)
	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	@Column(name = "rescontent", nullable = true, length = 255)
	public String getResContent() {
		return this.resContent;
	}

	public void setResContent(String resContent) {
		this.resContent = resContent;
	}

	@Column(name = "addtime", nullable = true, length = 255)
	public String getAddTime() {
		return this.addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	@Column(name = "msgtype", nullable = true, length = 255)
	public String getMsgType() {
		return this.msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	@Column(name = "templatename", nullable = true, length = 255)
	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@Column(name = "groupid", nullable = true, length = 255)
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}