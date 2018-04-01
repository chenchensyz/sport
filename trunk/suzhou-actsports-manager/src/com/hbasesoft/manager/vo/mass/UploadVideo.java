package com.hbasesoft.manager.vo.mass;

public class UploadVideo
{
  private String media_id;
  private String title;
  private String description;

  public String getMedia_id()
  {
    return this.media_id;
  }

  public void setMedia_id(String media_id) {
    this.media_id = media_id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}