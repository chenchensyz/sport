package com.hbasesoft.manager.controller;

import com.hbasesoft.framework.manager.core.common.controller.BaseController;
import com.hbasesoft.framework.manager.core.util.ContextHolderUtils;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.WeixinAccountEntity;
import com.hbasesoft.framework.manager.wechat.guangjia.service.WeixinAccountServiceI;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractWechatController extends BaseController
{

  @Autowired
  private WeixinAccountServiceI weixinAccountService;

  protected String getWeiXinAccountId()
  {
    return getWeiXinAccount().getWeixin_accountid();
  }

  protected WeixinAccountEntity getWeiXinAccount()
  {
    HttpSession session = ContextHolderUtils.getSession();
    WeixinAccountEntity entity = (WeixinAccountEntity)session.getAttribute("WEIXIN_ACCOUNT");
    if (entity == null) {
      entity = this.weixinAccountService.findLoginWeixinAccount();
      session.setAttribute("WEIXIN_ACCOUNT", entity);
    }
    return entity;
  }
}