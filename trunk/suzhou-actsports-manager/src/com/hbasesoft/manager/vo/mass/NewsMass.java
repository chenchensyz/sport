package com.hbasesoft.manager.vo.mass;

public class NewsMass
{
  public static final String SEND_IGNORE_REPRINT = "1";
  private Filter filter;
  private MpNews mpnews;
  private String msgtype;
  private String send_ignore_reprint;

  public Filter getFilter()
  {
    return this.filter;
  }

  public void setFilter(Filter filter) {
    this.filter = filter;
  }

  public MpNews getMpnews() {
    return this.mpnews;
  }

  public void setMpnews(MpNews mpnews) {
    this.mpnews = mpnews;
  }

  public String getMsgtype() {
    return this.msgtype;
  }

  public void setMsgtype(String msgtype) {
    this.msgtype = msgtype;
  }

  public String getSend_ignore_reprint() {
    return this.send_ignore_reprint;
  }

  public void setSend_ignore_reprint(String send_ignore_reprint) {
    this.send_ignore_reprint = send_ignore_reprint;
  }
}