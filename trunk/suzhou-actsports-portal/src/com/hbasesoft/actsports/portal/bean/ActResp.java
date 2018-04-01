package com.hbasesoft.actsports.portal.bean;

import java.util.List;
import java.util.Map;

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
public class ActResp {


    private String status;

    private String message;
    
    private int currentNum;
    
    private int min;
    
    private int max;
    
    private boolean isAgain;
    
    private List<ActFormPojo>  actFormListPojo;

    private Map<String, String> weixinConfig;
    
    private String title;
    
    private String prizeType;
    
    private int imgLen;
    
    private String subscribeCode;
    
    private String subscribeUrl;
    
    private String entryId;
    
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;
	}

	public List<ActFormPojo>  getActFormListPojo() {
		return actFormListPojo;
	}

	public void setActFormListPojo(List<ActFormPojo> actFromPojoList) {
		this.actFormListPojo = actFromPojoList;
	}

	public Map<String, String> getWeixinConfig() {
		return weixinConfig;
	}

	public void setWeixinConfig(Map<String, String> weixinConfig) {
		this.weixinConfig = weixinConfig;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public boolean isAgain() {
		return isAgain;
	}

	public void setAgain(boolean isAgain) {
		this.isAgain = isAgain;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

    public int getImgLen() {
        return imgLen;
    }

    public void setImgLen(int imgLen) {
        this.imgLen = imgLen;
    }

    public String getSubscribeCode() {
        return subscribeCode;
    }

    public void setSubscribeCode(String subscribeCode) {
        this.subscribeCode = subscribeCode;
    }

    public String getSubscribeUrl() {
        return subscribeUrl;
    }

    public void setSubscribeUrl(String subscribeUrl) {
        this.subscribeUrl = subscribeUrl;
    }

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	
    
}
