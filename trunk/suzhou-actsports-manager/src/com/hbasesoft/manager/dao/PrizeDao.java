package com.hbasesoft.manager.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.ResultType;

import com.hbasesoft.manager.vo.PrizeResultVo;


@MiniDao
public interface PrizeDao {
    @Arguments({ "prizeId"})
    @ResultType(PrizeResultVo.class)
    public List<PrizeResultVo> getPrizeResultVo(String prizeId);
    
}
