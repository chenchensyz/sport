/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.actsports.portal.service.remote;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbasesoft.actsports.portal.bean.ActUserAccountPojo;
import com.hbasesoft.actsports.portal.bean.ActUserPojo;
import com.hbasesoft.actsports.portal.bean.BeanTransformUtil;
import com.hbasesoft.actsports.portal.biz.vo.LoginReq;
import com.hbasesoft.actsports.portal.biz.vo.LoginResp;
import com.hbasesoft.actsports.portal.biz.vo.OptResp;
import com.hbasesoft.actsports.portal.biz.vo.UserInfo;
import com.hbasesoft.actsports.portal.constant.CacheCodeDef;
import com.hbasesoft.actsports.portal.constant.CommonContant;
import com.hbasesoft.actsports.portal.constant.ErrorCodeDef;
import com.hbasesoft.actsports.portal.service.UserService;
import com.hbasesoft.actsports.portal.service.UserinfoService;
import com.hbasesoft.framework.cache.core.annotation.Cache;
import com.hbasesoft.framework.common.RemoteServiceException;
import com.hbasesoft.framework.common.ServiceException;
import com.hbasesoft.framework.common.utils.date.DateUtil;
import com.hbasesoft.framework.common.utils.logger.Logger;

/**
 * <Description> 用户信息管理<br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月14日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.vcc.cbs.rmi <br>
 */
@Service("UserInfoRemoteService")
public class UserInfoRemoteService implements UserinfoService {

	private Logger logger = new Logger(UserInfoRemoteService.class);

	@Resource
	private UserService userService;

	/**
	 * Description: 注册<br>
	 * 
	 * @author 王伟<br>
	 * @taskId <br>
	 * @param account
	 * @param accountType
	 * @param userInfo
	 * @return
	 * @throws RemoteServiceException
	 *             <br>
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String regist(String account, String accountType, UserInfo userInfo) throws RemoteServiceException {
		try {
			ActUserPojo userPojo = userService.getUserByAccount(account, accountType);

			// 账户已经存在
			if (userPojo != null) {
				BeanTransformUtil.userInfo2User(userInfo, userPojo);
				userService.updateEntity(userPojo);
			} else {
				// 新建账户
				userPojo = BeanTransformUtil.userInfo2User(userInfo, null);
				userService.save(userPojo);

				ActUserAccountPojo userAccountPojo = BeanTransformUtil.getUserAccount(userPojo.getId(), account,
						accountType, null);
				userService.save(userAccountPojo);
			}

			return userPojo.getId();
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			throw new RemoteServiceException(e);
		}
	}

	/**
	 * Description: 登录<br>
	 * 
	 * @author 王伟<br>
	 * @taskId <br>
	 * @param loginReq
	 * @return
	 * @throws RemoteServiceException
	 *             <br>
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public LoginResp login(LoginReq loginReq) throws RemoteServiceException {
		LoginResp resp = new LoginResp();
		try {
			ActUserPojo pojo = userService.getUserByAccount(loginReq.getAccount(), loginReq.getAccountType());

			// 账户信息不存在或者已经删除
			if (pojo == null || CommonContant.UN_AVALIABLE.equals(pojo.getState())) {
				resp.setResultCode(ErrorCodeDef.OPERATOR_NOT_EXSIST + "");
				resp.setResultMsg("账户不存在");
				return resp;
			}

			// 账户被锁
			if (CommonContant.USER_STATE_LOCK.equals(pojo.getState())) {
				resp.setResultCode(ErrorCodeDef.ACCOUNT_IS_LOCK + "");
				resp.setResultMsg("账户不存在");
				return resp;
			}

			// 更新登录信息
			pojo.setBrowser(loginReq.getBrowser());
			pojo.setIp(loginReq.getLoginIp());
			pojo.setLastLoginTime(DateUtil.getCurrentDate());
			pojo.setCount(pojo.getCount() + 1);
			userService.updateEntity(pojo);

			UserInfo userInfo = BeanTransformUtil.user2UserInfo(pojo, null);

			resp.setUserInfo(userInfo);
			resp.setResultCode(CommonContant.SUCCESS_CODE);
		} catch (ServiceException e) {
			resp.setResultCode(CommonContant.FAIL_CODE);
			resp.setResultMsg(e.getMessage());
			logger.error(e.getMessage(), e);
			throw new RemoteServiceException(e);
		}
		return resp;
	}

	/**
	 * Description: 根据账号获取用户信息<br>
	 * 
	 * @author 王伟<br>
	 * @taskId <br>
	 * @param account
	 * @param accountType
	 * @return
	 * @throws RemoteServiceException
	 *             <br>
	 */
	@Override
	@Transactional(readOnly = true)
	@Cache(node = CacheCodeDef.USERINFO_BY_ACCOUNT_TYPE, expireTime = CacheCodeDef.USERINFO_SAVE_TIME)
	public UserInfo getUserInfoByAccount(String account, String accountType) throws RemoteServiceException {
		try {
			ActUserPojo pojo = userService.getUserByAccount(account, accountType);
			if (pojo != null) {
				UserInfo userInfo = BeanTransformUtil.user2UserInfo(pojo, null);
				// if (memberPojo != null) {
				// BeanTransformUtil.member2UserInfo(memberPojo, userInfo);
				// }
				return userInfo;
			}
			return null;
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			throw new RemoteServiceException(e);
		}
	}

	/**
	 * Description:增加登录账号 <br>
	 * 
	 * @author 王伟<br>
	 * @taskId <br>
	 * @param userId
	 * @param account
	 * @param accountType
	 * @param accountValue
	 * @return
	 * @throws RemoteServiceException
	 *             <br>
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public OptResp addAccount(String userId, String account, String accountType, String accountValue)
			throws RemoteServiceException {
		try {
			ActUserAccountPojo accountPojo = userService.getAccoutByAccctAndType(account, accountType);
			OptResp resp = new OptResp();

			if (accountPojo == null) {
				accountPojo = BeanTransformUtil.getUserAccount(userId, account, accountType, null);
				accountPojo.setValue(accountValue);
				userService.save(accountPojo);
			} else {
				if (!userId.equals(accountPojo.getUserId())) {
					logger.info("帐号[{0}]已经存在，将现有帐号[{1}]注销，切换至新的帐号下", accountPojo.getUserId(), userId);

					// Step1 切换帐号
					List<ActUserAccountPojo> newAccountList = new ArrayList<ActUserAccountPojo>();

					List<ActUserAccountPojo> accountList = userService.queryAccountByUserId(userId);
					for (ActUserAccountPojo pojo : accountList) {
						pojo.setState(CommonContant.UN_AVALIABLE);
						userService.updateEntity(pojo);

						ActUserAccountPojo newPojo = new ActUserAccountPojo();
						newPojo.setAccount(pojo.getAccount());
						newPojo.setCreateTime(pojo.getCreateTime());
						newPojo.setExt1(pojo.getExt1());
						newPojo.setState(CommonContant.AVALIABLE);
						newPojo.setType(pojo.getType());
						newPojo.setUserId(accountPojo.getUserId());
						newPojo.setValue(pojo.getValue());
						newAccountList.add(newPojo);
					}
					userService.batchSave(newAccountList);

					// Step2 切换Member信息
					/*
					 * List<MemberPojo> memberList =
					 * userService.findByProperty(MemberPojo.class,
					 * MemberPojo.USER_ID, userId); for (MemberPojo member :
					 * memberList) { member.setUserId(accountPojo.getUserId());
					 * userService.updateEntity(member); }
					 */

					// Step3 注销原来的用户
					ActUserPojo userPojo = userService.get(ActUserPojo.class, userId);
					userPojo.setState(CommonContant.UN_AVALIABLE);
					userService.updateEntity(userPojo);
				}

				if (StringUtils.equals(accountValue, accountPojo.getValue())) {
					accountPojo.setValue(accountValue);
					userService.updateEntity(accountPojo);
				}
			}

			if (CommonContant.ACCT_TYPE_MOBILE.equals(accountType)) {
				ActUserPojo userPojo = userService.get(ActUserPojo.class, accountPojo.getUserId());
				if (!CommonContant.AVALIABLE.equals(userPojo)) {
					userPojo.setState(CommonContant.AVALIABLE);
					userService.updateEntity(userPojo);
				}

			}

			resp.setResultCode(CommonContant.SUCCESS_CODE);
			return resp;
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			throw new RemoteServiceException(e);
		}
	}

	/**
	 * Description: 删除登录帐号<br>
	 * 
	 * @author 王伟<br>
	 * @taskId <br>
	 * @param userId
	 * @param account
	 * @param accountType
	 * @return
	 * @throws RemoteServiceException
	 *             <br>
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public OptResp delAccount(String userId, String account, String accountType) throws RemoteServiceException {
		try {
			OptResp resp = new OptResp();
			ActUserAccountPojo accountPojo = userService.getAccoutByAccctAndType(account, accountType);
			if (accountPojo == null) {
				resp.setResultCode(ErrorCodeDef.USER_NAME_OR_PASSWORD_ERROR + "");
				resp.setResultMsg("帐号不存在。");
			}

			if (!userId.equals(accountPojo.getUserId())) {
				resp.setResultCode(ErrorCodeDef.NO_PERMISSION + "");
				resp.setResultMsg("帐号不在当前用户名下，无权限删除");
				return resp;
			}

			accountPojo.setState(CommonContant.UN_AVALIABLE);
			userService.updateEntity(accountPojo);

			resp.setResultCode(CommonContant.SUCCESS_CODE);
			return resp;
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			throw new RemoteServiceException(e);
		}
	}

}