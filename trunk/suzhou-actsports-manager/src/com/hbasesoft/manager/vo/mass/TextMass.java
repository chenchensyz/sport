package com.hbasesoft.manager.vo.mass;

public class TextMass
{
  private Filter filter;
  private Text text;
  private String msgtype;

  public Filter getFilter()
  {
    return this.filter;
  }

  public void setFilter(Filter filter) {
    this.filter = filter;
  }

  public Text getText() {
    return this.text;
  }

  public void setText(Text text) {
    this.text = text;
  }

  public String getMsgtype() {
    return this.msgtype;
  }

  public void setMsgtype(String msgtype) {
    this.msgtype = msgtype;
  }
}