/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.manager.vo;

import java.util.Date;

/** 
 * <Description> <br> 
 *  
 * @author 查思玮<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年6月19日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.manager.vo <br>
 */
public class CollectResultVo {
	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private String userId;
    
    private String id;
    
    private String collectTitle;
    
    private Date createTime;
    
    private String info;
    
    private String attachmentRule;
    
    private String idea;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCollectTitle() {
		return collectTitle;
	}

	public void setCollectTitle(String collectTitle) {
		this.collectTitle = collectTitle;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getAttachmentRule() {
		return attachmentRule;
	}

	public void setAttachmentRule(String attachmentRule) {
		this.attachmentRule = attachmentRule;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}
    
    
}
