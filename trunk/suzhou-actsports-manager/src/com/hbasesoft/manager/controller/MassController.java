package com.hbasesoft.manager.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hbasesoft.manager.service.VoteService;
import com.hbasesoft.manager.vo.VoteResultVo;

@Controller
@RequestMapping("/mass")
public class MassController {
	private static final Logger LOGGER =LoggerFactory.getLogger(MassController.class);
	@Autowired
    private VoteService voteService;
	
	@RequestMapping(params = "mass")
	@ResponseBody
	public String mass(String id) throws Exception{
		LOGGER.info("投票结果详情",id);
		VoteResultVo v=voteService.getVoteByVoteId(id);
		String vote=v.getChoose();
		return vote;
	}
	
}
