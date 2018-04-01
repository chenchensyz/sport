package com.hbasesoft.manager.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.ResultType;

import com.hbasesoft.manager.vo.EntryResultVo;


@MiniDao
public interface EntryDao {
    @Arguments({ "entryId"})
    @ResultType(EntryResultVo.class)
    public List<EntryResultVo> getEntryResultVo(String entryId);
    
}
