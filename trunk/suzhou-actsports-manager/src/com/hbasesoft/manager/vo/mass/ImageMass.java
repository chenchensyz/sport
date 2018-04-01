package com.hbasesoft.manager.vo.mass;

public class ImageMass
{
  private Filter filter;
  private Image image;
  private String msgtype;

  public Filter getFilter()
  {
    return this.filter;
  }

  public void setFilter(Filter filter) {
    this.filter = filter;
  }

  public Image getImage() {
    return this.image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  public String getMsgtype() {
    return this.msgtype;
  }

  public void setMsgtype(String msgtype) {
    this.msgtype = msgtype;
  }
}