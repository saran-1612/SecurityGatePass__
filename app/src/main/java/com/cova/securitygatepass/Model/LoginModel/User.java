package com.cova.securitygatepass.Model.LoginModel;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("code")
    private String code;

    @SerializedName("name")
    private String name;

    @SerializedName("u_UserName")
    private String uUserName;

    @SerializedName("u_PassWord")
    private String uPassWord;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUUserName() {
        return uUserName;
    }

    public void setUUserName(String uUserName) {
        this.uUserName = uUserName;
    }

    public String getUPassWord() {
        return uPassWord;
    }

    public void setUPassWord(String uPassWord) {
        this.uPassWord = uPassWord;
    }
}