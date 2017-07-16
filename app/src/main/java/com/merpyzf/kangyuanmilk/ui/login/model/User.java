package com.merpyzf.kangyuanmilk.ui.login.model;

/**
 * Created by wangke on 17-7-16.
 */

public class User {

    private String username;
    private String pwd;

    public User(String username, String pwd) {
        this.username = username;
        this.pwd = pwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
