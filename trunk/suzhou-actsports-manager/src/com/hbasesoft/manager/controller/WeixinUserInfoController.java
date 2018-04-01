package com.hbasesoft.manager.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hbasesoft.framework.manager.core.common.service.CommonService;
import com.hbasesoft.manager.service.MaterialService;
import com.hbasesoft.manager.service.WeixinGroupNewService;
import com.hbasesoft.manager.service.WeixinUserAccountService;
import com.hbasesoft.manager.service.WeixinUserService;
import com.hbasesoft.manager.utils.CommonUtil;
import com.hbasesoft.manager.utils.RestResponse;
import com.hbasesoft.manager.vo.WeixinAccount;
import com.hbasesoft.manager.vo.WeixinGroupNew;
import com.hbasesoft.manager.vo.WeixinUserAccount;
import com.hbasesoft.manager.vo.WeixinUserInfo;

@Controller
@RequestMapping("/weixinUserInfo")
public class WeixinUserInfoController {
	private static final Logger LOGGER = LoggerFactory.getLogger(WeixinUserInfoController.class);
	@Resource(name = "weixinUserAccountService")
	private WeixinUserAccountService weixinUserAccountService;

	@Resource
	private CommonService commonService;

	@Resource(name = "weixinGroupNewService")
	private WeixinGroupNewService weixinGroupNewService;

	@Resource(name = "weixinUserService")
	private WeixinUserService weixinUserService;

	@Resource(name = "materialService")
	private MaterialService materialService;

	@RequestMapping(params = "weixinUserInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String SelectWeixinInfo(Model model) throws Exception {
		LOGGER.info("查看公众号信息");
		List<WeixinAccount> PublicNumbers = weixinUserService.findAll();
		model.addAttribute("PublicNumbers", PublicNumbers);
		return "release/weixinUserGroup";
	}

	// 用户列表
	@RequestMapping(value = "getUserAccount", method = RequestMethod.POST)
	@ResponseBody
	public List<WeixinUserAccount> getUserAccount(String accountappid, String groupId) {
		LOGGER.info("用户列表");
		List<WeixinUserAccount> users = weixinUserAccountService.findUserAccountId(accountappid);
		return users;
	}

	// 同步数据
	@RequestMapping(value = "synWeixinUser", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public void synWeixinUser(String accountappid) throws Exception {
		LOGGER.info("同步数据");
		Map<String, Object> accessToken = weixinUserService.getTokenByAppid(accountappid);
		// 获取openid列表
		List<String> allWeiXinUser = CommonUtil.getOpenids(accessToken.get("token").toString());
		for (String openId : allWeiXinUser) {
			// 获取openid对应的用户详情
			WeixinUserInfo userInfo = CommonUtil.getUserInfo(accessToken.get("token").toString(), openId);
			// 从数据库中查询openID对应的用户详情
			WeixinUserAccount t = weixinUserAccountService.findByAccount(openId);
			// 账户存在
			if (t != null) {
				t.setNickName(userInfo.getNickname());
				t.setRemark(userInfo.getRemark());
				List<String> list = Lists.newArrayList();
				String[] tags = userInfo.getTagList().split(",");
				for (String tag : tags) {
					tag = tag.replace("[", "").replace("]", "");
					WeixinGroupNew group = weixinGroupNewService.findByGroupId(tag);
					if (group != null&&!"[]".equals(group.getGroupName())) {
						list.add(group.getGroupName().replace("[", "").replace("]", ""));
					}
				}
				t.setGroupId(list.toString());
				t.setAccountId(accountappid);
				weixinUserAccountService.saveOrUpdate(t);
			}
		}
		// 获取分组
		List<WeixinGroupNew> groups = CommonUtil.getGroup(accessToken.get("token").toString());
		for (WeixinGroupNew weixinGroupNew : groups) {
			WeixinGroupNew group = weixinGroupNewService.findByGroupId(weixinGroupNew.getGroupId());
			if (group == null) {
				weixinGroupNewService.deleteByGroupId(weixinGroupNew.getGroupId());
				group = new WeixinGroupNew();
				group.setGroupId(weixinGroupNew.getGroupId());
				group.setGroupName(weixinGroupNew.getGroupName());
				group.setAccoundId(accountappid);
				commonService.save(group);
			} else {
				group.setGroupId(weixinGroupNew.getGroupId());
				group.setGroupName(weixinGroupNew.getGroupName());
				group.setAccoundId(accountappid);
				commonService.saveOrUpdate(group);
			}
		}
	}

	// 修改备注
	@RequestMapping(value = "updateRemark")
	@ResponseBody
	public WeixinUserAccount updateRemark(String accessToken, String openId, String remark) {
		LOGGER.info("修改备注");
		CommonUtil.updateRemark(accessToken, openId, remark);
		WeixinUserAccount user = weixinUserAccountService.findByAccount(openId);
		user.setRemark(remark);
		weixinUserAccountService.saveOrUpdate(user);
		return user;
	}

	// 获取标签下粉丝列表
	@RequestMapping(value = "getTasgOpenids")
	@ResponseBody
	public RestResponse getTasgOpenids(@RequestParam("accountappid") String accountappid,
			@RequestParam("groupId") String groupId) {
		Map<String, Object> accessToken = weixinUserService.getTokenByAppid(accountappid);
		// 获取openid列表
		List<String> tasgOpenids = CommonUtil.getTasgOpenids(accessToken.get("token").toString(), groupId);
		List<Map<String, Object>> users = Lists.newArrayList();
		if (!tasgOpenids.isEmpty()) {
			for (String openid : tasgOpenids) {
				// 根据openid查出对应用户
				WeixinUserInfo user = CommonUtil.getUserInfo(accessToken.get("token").toString(), openid);
				List<String> list = Lists.newArrayList();
				Map<String, Object> map = Maps.newLinkedHashMap();
				if (user != null) {
					map.put("id", user.getSubscribe());
					map.put("account", user.getOpenId());
					map.put("nickName", user.getNickname());
					map.put("groupId", user.getGroupId());
					map.put("groupName", user.getTagList());
					String[] tags = user.getTagList().split(",");
					for (String tag : tags) {
						tag = tag.replace("[", "").replace("]", "");
						WeixinGroupNew group = weixinGroupNewService.findByGroupId(tag);
						list.add(group.getGroupName());
					}
					map.put("groupName", list);
					users.add(map);
				}
			}
		}
		System.out.println(RestResponse.success().setData(users));
		return RestResponse.success().setData(users);
	}

	// 黑名单列表
	@RequestMapping(value = "getBlacklist")
	@ResponseBody
	public List<WeixinUserAccount> getBlacklist(String accountappid, String begin_openid) {
		if (begin_openid == null) {
			begin_openid = "0";
		}
		Map<String, Object> accessToken = weixinUserService.getTokenByAppid(accountappid);
		List<WeixinUserAccount> users = Lists.newArrayList();
		List<String> blacklist = CommonUtil.getBlacklist(accessToken.get("token").toString(), begin_openid);
		for (String openid : blacklist) {
			WeixinUserAccount user = weixinUserAccountService.findByAccount(openid);
			users.add(user);
		}
		return users;
	}

	// 根据昵称查询
	@RequestMapping(value = "selectNickname")
	@ResponseBody
	public List<WeixinUserAccount> selectNickname(String accountappid, String nickName) {
		List<WeixinUserAccount> user = weixinUserAccountService.findNicknameLike(accountappid, nickName);
		return user;
	}

	// 群发分组（标签）列表
	@RequestMapping(value = "getWeixinGroup", method = RequestMethod.POST)
	@ResponseBody
	public List<WeixinGroupNew> getWeixinGroup(String accountappid) {
		Map<String, Object> accessToken = weixinUserService.getTokenByAppid(accountappid);
		String token=accessToken.get("token")==null?null:accessToken.get("token").toString();
		List<WeixinGroupNew> WeixinGroup = null;
		if (StringUtils.isNotBlank(token)) {
			WeixinGroup = CommonUtil.getTags(token);
		}
		return WeixinGroup;
	}

	
}
