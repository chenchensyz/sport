/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service;

import java.util.List;

import com.hbasesoft.actsports.portal.bean.ActUserAccountPojo;
import com.hbasesoft.actsports.portal.bean.ActUserPojo;
import com.hbasesoft.actsports.portal.bean.collect.ActCollectResultIdeaPojo;
import com.hbasesoft.actsports.portal.bean.entry.ActEntryResultChoosePojo;
import com.hbasesoft.actsports.portal.bean.vote.ActVoteResultChoosePojo;
import com.hbasesoft.framework.common.ServiceException;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月14日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.cbs.service <br>
 */
public interface UserService extends CommonService {

    /**
     * Description: 根据账户查询 用户信息<br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param account
     * @param acctType
     * @return
     * @throws ServiceException <br>
     */
    ActUserPojo getUserByAccount(String account, String acctType) throws ServiceException;

    ActUserAccountPojo getAccoutByAccctAndType(String account, String acctType) throws ServiceException;

    List<ActUserAccountPojo> queryAccountByUserId(String userId) throws ServiceException;
    
    //查询该用户报的项目的活动列表
    List<ActEntryResultChoosePojo> queryEntryChooseListByUserIdAndEntryId(String userId, String entryId) throws ServiceException;

    //查询该活动一共报了多少人
    List<ActEntryResultChoosePojo> queryEntryChooseListByChooseIdAndEntryId(String chooseId, String entryId) throws ServiceException;
    
    //查询该用户是否已报该活动
    List<ActEntryResultChoosePojo> queryEntryChooseListByChooseIdAndEntryIdAndUserId(String chooseId, String entryId, String userId) throws ServiceException;

    //查询该用户投票的活动列表
    List<ActVoteResultChoosePojo> queryVoteChooseListByUserIdAndVoteId(String userId, String voteId) throws ServiceException;

    //查询该用户的是否投票
    ActCollectResultIdeaPojo queryCollectIdeaByUserIdAndCollectId(String userId, String collectId) throws ServiceException;
}
