/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.hbasesoft.actsports.portal.biz.vo;


public class LoginReq extends AbstractVo {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = 7727364210641252869L;

    public String accountType; // required

    public String account; // required

    public String acctValue; // optional

    public String loginIp; // optional

    public String browser; // optional

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAcctValue() {
        return acctValue;
    }

    public void setAcctValue(String acctValue) {
        this.acctValue = acctValue;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

}
