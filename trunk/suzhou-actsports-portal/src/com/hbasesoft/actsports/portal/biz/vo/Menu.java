package com.hbasesoft.actsports.portal.biz.vo;

import java.util.List;

public class Menu extends Content {

	/**
	 * serialVersionUID <br>
	 */
	private static final long serialVersionUID = -1724871384807614501L;
	
	private String name;
	
	private String code;
	
	private String position;
	
	private List<NewsItem> newsItemList;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

    public List<NewsItem> getNewsItemList() {
        return newsItemList;
    }

    public void setNewsItemList(List<NewsItem> newsItemList) {
        this.newsItemList = newsItemList;
    }

	

}
