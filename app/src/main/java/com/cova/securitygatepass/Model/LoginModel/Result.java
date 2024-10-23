package com.cova.securitygatepass.Model.LoginModel;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("tokenRole")
    private String tokenRole;

    @SerializedName("user")
    private User user;

    @SerializedName("token")
    private String token;

    public String getTokenRole() {
        return tokenRole;
    }

    public void setTokenRole(String tokenRole) {
        this.tokenRole = tokenRole;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}