package com.hbasesoft.manager.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.ResultType;

import com.hbasesoft.manager.vo.VoteResultVo;


@MiniDao
public interface VoteDao {
    @Arguments({ "voteId"})
    @ResultType(VoteResultVo.class)
    public List<VoteResultVo> getVoteResultVo(String voteId);
     
    @Arguments({ "voteId"})
    @ResultType(VoteResultVo.class)
    public VoteResultVo getVoteByVoteId(String voteId);
    
}
