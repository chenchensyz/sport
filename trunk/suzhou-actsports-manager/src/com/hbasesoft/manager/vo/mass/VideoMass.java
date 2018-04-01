package com.hbasesoft.manager.vo.mass;

public class VideoMass
{
  private Filter filter;
  private MpVideo mpvideo;
  private String msgtype;

  public Filter getFilter()
  {
    return this.filter;
  }

  public void setFilter(Filter filter) {
    this.filter = filter;
  }

  public MpVideo getMpvideo() {
    return this.mpvideo;
  }

  public void setMpvideo(MpVideo mpvideo) {
    this.mpvideo = mpvideo;
  }

  public String getMsgtype() {
    return this.msgtype;
  }

  public void setMsgtype(String msgtype) {
    this.msgtype = msgtype;
  }
}