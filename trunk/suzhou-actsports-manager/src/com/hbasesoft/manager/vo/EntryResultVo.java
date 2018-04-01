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
public class EntryResultVo {
	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private String userId;
    
    private String entryRank;
    
    private String entryId;
    
    private String entryTitle;
    
    private Date createTime;
    
    private String info;
    
    private String choose;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEntryRank() {
		return entryRank;
	}

	public void setEntryRank(String entryRank) {
		this.entryRank = entryRank;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public String getEntryTitle() {
		return entryTitle;
	}

	public void setEntryTitle(String entryTitle) {
		this.entryTitle = entryTitle;
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

	public String getChoose() {
		return choose;
	}

	public void setChoose(String choose) {
		this.choose = choose;
	}
    
    
}
