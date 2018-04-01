package com.hbasesoft.manager.service;

import com.hbasesoft.framework.manager.core.common.service.CommonService;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.AutoResponse;
import com.hbasesoft.manager.vo.WeixinNewResponse;

import java.io.Serializable;

public abstract interface WeixinNewResponseService extends CommonService
{
  public abstract <T> void delete(T paramT);

  public abstract <T> Serializable save(T paramT);

  public abstract <T> void saveOrUpdate(T paramT);

  public abstract boolean doAddSql(WeixinNewResponse paramAutoResponse);

  public abstract boolean doUpdateSql(WeixinNewResponse paramAutoResponse);

  public abstract boolean doDelSql(WeixinNewResponse paramAutoResponse);
}