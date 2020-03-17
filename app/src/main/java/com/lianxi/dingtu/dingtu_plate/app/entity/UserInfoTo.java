package com.lianxi.dingtu.dingtu_plate.app.entity;

import java.io.Serializable;

public class UserInfoTo implements Serializable {

    /**
     * AccessToken : ae879ba3-260e-4797-906a-48b862f77292
     * CompanyCode : 1001
     * UserID : c883048c-dec8-4bbc-bb4b-1d29594d87fd
     * Account : 1001
     * ExpirationTime : 2018-11-15 13:34:48
     * PassWord : 100
     */

    private String AccessToken;
    private int CompanyCode;
    private String UserID;
    private String Account;
    private String ExpirationTime;

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String AccessToken) {
        this.AccessToken = AccessToken;
    }

    public int getCompanyCode() {
        return CompanyCode;
    }

    public void setCompanyCode(int CompanyCode) {
        this.CompanyCode = CompanyCode;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String Account) {
        this.Account = Account;
    }

    public String getExpirationTime() {
        return ExpirationTime;
    }

    public void setExpirationTime(String ExpirationTime) {
        this.ExpirationTime = ExpirationTime;
    }

}
