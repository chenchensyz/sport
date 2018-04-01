package com.hbasesoft.manager.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.ResultType;

import com.hbasesoft.manager.vo.CollectResultVo;


@MiniDao
public interface CollectDao {
    @Arguments({ "collectId"})
    @ResultType(CollectResultVo.class)
    public List<CollectResultVo> getCollectResultVo(String collectId);
    
}
