package com.hbasesoft.manager.utils;
public class Filter
{
  public static final Boolean IS_TO_ALL = Boolean.valueOf(true);
  public static final Boolean IS_Not_ALL = Boolean.valueOf(false);
  private Boolean is_to_all;
  private Boolean is_not_all;
  
  public Boolean getIs_to_all()
  {
    return this.is_to_all;
  }

  public void setIs_to_all(Boolean is_to_all) {
    this.is_to_all = is_to_all;
  }

public Boolean getIs_not_all() {
	return this.is_not_all;
}

public void setIs_not_all(Boolean is_not_all) {
	this.is_not_all = is_not_all;
}


  
  
}