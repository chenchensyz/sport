package com.hbasesoft.actsports.portal.bean.prize;

import java.util.List;

import com.hbasesoft.actsports.portal.biz.vo.PrizeChooseVo;

public class PrizeResult {

	private List<PrizeChooseVo> prizeChooseList;
	
	private String receiveMsg;

	public List<PrizeChooseVo> getPrizeChooseList() {
		return prizeChooseList;
	}

	public void setPrizeChooseList(List<PrizeChooseVo> prizeChooseList) {
		this.prizeChooseList = prizeChooseList;
	}

	public String getReceiveMsg() {
		return receiveMsg;
	}

	public void setReceiveMsg(String receiveMsg) {
		this.receiveMsg = receiveMsg;
	}
	
	
}
