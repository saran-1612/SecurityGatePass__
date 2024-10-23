package com.cova.securitygatepass.Model.LoginModel;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("password")
    private String password;

    @SerializedName("u_UserName")
    private String uUserName;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUUserName() {
        return uUserName;
    }

    public void setUUserName(String uUserName) {
        this.uUserName = uUserName;
    }
}