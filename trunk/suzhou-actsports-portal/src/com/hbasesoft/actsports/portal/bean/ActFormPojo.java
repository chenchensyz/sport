package com.hbasesoft.actsports.portal.bean;

import java.util.List;

import com.hbasesoft.actsports.portal.bean.entry.ActEntryChooseRespPojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVoteChooseRespPojo;

/**
 * <Description> <br>
 * 
 * @author zhasiwei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年12月12日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.actsports.portal.bean <br>
 */
public class ActFormPojo {

	 private String title;

	 private String type;
	 
	 private List<String> rules;
	 
	 private List<ActEntryChooseRespPojo> entryChooseList;
	 
	 private List<ActVoteChooseRespPojo> voteChooseList;
	 
	 private String chooseLimit;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getRules() {
		return rules;
	}

	public void setRules(List<String> rules) {
		this.rules = rules;
	}

	public List<ActEntryChooseRespPojo> getEntryChooseList() {
		return entryChooseList;
	}

	public void setEntryChooseList(List<ActEntryChooseRespPojo> entryChooseList) {
		this.entryChooseList = entryChooseList;
	}

	public List<ActVoteChooseRespPojo> getVoteChooseList() {
		return voteChooseList;
	}

	public void setVoteChooseList(List<ActVoteChooseRespPojo> voteChooseList) {
		this.voteChooseList = voteChooseList;
	}

	public String getChooseLimit() {
		return chooseLimit;
	}

	public void setChooseLimit(String chooseLimit) {
		this.chooseLimit = chooseLimit;
	}
}
