package com.hbasesoft.manager.service.impl;

import com.hbasesoft.framework.manager.core.common.service.impl.CommonServiceImpl;
import com.hbasesoft.framework.manager.wechat.guangjia.entity.AutoResponse;
import com.hbasesoft.manager.service.WeixinNewResponseService;
import com.hbasesoft.manager.vo.WeixinNewResponse;

import java.io.Serializable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("weixinNewResponseService")
@Transactional
public class WeixinNewResponseServiceImpl extends CommonServiceImpl
  implements WeixinNewResponseService
{
  public <T> void delete(T entity)
  {
    super.delete(entity);

    doDelSql((WeixinNewResponse)entity);
  }

  public <T> Serializable save(T entity)
  {
    Serializable t = super.save(entity);

    doAddSql((WeixinNewResponse)entity);
    return t;
  }

  public <T> void saveOrUpdate(T entity)
  {
    super.saveOrUpdate(entity);

    doUpdateSql((WeixinNewResponse)entity);
  }

  public boolean doAddSql(WeixinNewResponse entity)
  {
    return true;
  }

  public boolean doUpdateSql(WeixinNewResponse t)
  {
    return true;
  }

  public boolean doDelSql(WeixinNewResponse t)
  {
    return true;
  }
}