package com.hbasesoft.manager.vo.mass;
public class Filter
{
  public static final Boolean IS_TO_ALL = Boolean.valueOf(true);
  
  private Boolean is_to_all;
  private String tag_id;
  
  public Boolean getIs_to_all()
  {
    return this.is_to_all;
  }

  public void setIs_to_all(Boolean is_to_all) {
    this.is_to_all = is_to_all;
  }

public String getTag_id() {
	return this.tag_id;
}

public void setTag_id(String tag_id) {
	this.tag_id = tag_id;
}




  
  
}