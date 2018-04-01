package com.hbasesoft.manager.vo;



import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="weixin_account", schema="")
public class WeixinAccount implements Serializable{
  private String id;
  private String accountname;
  private String accounttoken;
  private String weixin_accountid;
  private String accounttype;
  private String accountemail;
  private String accountdesc;
  private String accountappid;
  private String accountappsecret;
  private String accountaccesstoken;
  private Date addtoekntime;
  private String orgCode;
  private String state;
  private String jsapiticket;
  private Date jsapitickettime;
  private String host;

  @Column(name="jsapiticket")
  public String getJsapiticket()
  {
    return this.jsapiticket;
  }

  public void setJsapiticket(String jsapiticket) {
    this.jsapiticket = jsapiticket;
  }
  @Column(name="jsapitickettime")
  public Date getJsapitickettime() {
    return this.jsapitickettime;
  }

  public void setJsapitickettime(Date jsapitickettime) {
    this.jsapitickettime = jsapitickettime;
  }

  @Id
  @GeneratedValue(generator="paymentableGenerator")
  @GenericGenerator(name="paymentableGenerator", strategy="uuid")
  @Column(name="id", nullable=false, length=36)
  public String getId()
  {
    return this.id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  @Column(name="accountname")
  public String getAccountname()
  {
    return this.accountname;
  }

  public void setAccountname(String accountname)
  {
    this.accountname = accountname;
  }

  @Column(name="accounttoken")
  public String getAccounttoken()
  {
    return this.accounttoken;
  }

  public void setAccounttoken(String accounttoken)
  {
    this.accounttoken = accounttoken;
  }
  
  @Column(name="WEIXIN_ACCOUNTID")
  public String getWeixin_accountid()
  {
    return this.weixin_accountid;
  }

  public void setWeixin_accountid(String weixin_accountid) {
    this.weixin_accountid = weixin_accountid;
  }

  @Column(name="accounttype")
  public String getAccounttype()
  {
    return this.accounttype;
  }

  public void setAccounttype(String accounttype)
  {
    this.accounttype = accounttype;
  }

  @Column(name="accountemail")
  public String getAccountemail()
  {
    return this.accountemail;
  }

  public void setAccountemail(String accountemail)
  {
    this.accountemail = accountemail;
  }

  @Column(name="accountdesc")
  public String getAccountdesc()
  {
    return this.accountdesc;
  }

  public void setAccountdesc(String accountdesc)
  {
    this.accountdesc = accountdesc;
  }

  @Column(name="accountappid")
  public String getAccountappid()
  {
    return this.accountappid;
  }

  public void setAccountappid(String accountappid)
  {
    this.accountappid = accountappid;
  }

  @Column(name="accountappsecret")
  public String getAccountappsecret()
  {
    return this.accountappsecret;
  }

  public void setAccountappsecret(String accountappsecret)
  {
    this.accountappsecret = accountappsecret;
  }

  @Column(name="accountaccesstoken")
  public String getAccountaccesstoken()
  {
    return this.accountaccesstoken;
  }

  public void setAccountaccesstoken(String accountaccesstoken)
  {
    this.accountaccesstoken = accountaccesstoken;
  }

  @Column(name="ADDTOEKNTIME")
  public Date getAddtoekntime()
  {
    return this.addtoekntime;
  }

  public void setAddtoekntime(Date addtoekntime)
  {
    this.addtoekntime = addtoekntime;
  }

  @Column(name="org_code")
  public String getOrgCode()
  {
    return this.orgCode;
  }

  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }

  @Column(name="state")
  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  @Column(name="host")
  public String getHost() {
    return this.host;
  }

  public void setHost(String host) {
    this.host = host;
  }
}