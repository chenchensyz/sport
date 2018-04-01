package com.hbasesoft.manager.vo.mass;

public class VoiceMass
{
  private Filter filter;
  private Voice voice;
  private String msgtype;

  public Filter getFilter()
  {
    return this.filter;
  }

  public void setFilter(Filter filter) {
    this.filter = filter;
  }

  public Voice getVoice() {
    return this.voice;
  }

  public void setVoice(Voice voice) {
    this.voice = voice;
  }

  public String getMsgtype() {
    return this.msgtype;
  }

  public void setMsgtype(String msgtype) {
    this.msgtype = msgtype;
  }
}