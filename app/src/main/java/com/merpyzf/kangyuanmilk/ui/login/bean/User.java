package com.merpyzf.kangyuanmilk.ui.login.bean;

import java.util.Date;

/**
 * Created by wangke on 2017-07-22.
 * 用户信息
 */

public class User {

    private String user_name;
    private int user_id;
    private String user_pwd;
    private String user_tel;
    private String user_idcard;
    private String address_content;
    private String user_head;
    private Boolean user_sex;
    private Date user_registerdate;
    private String remark;


    public User(String user_name, int user_id, String user_pwd, String user_tel, String user_idcard, String address_content, String user_head, Boolean user_sex, Date user_registerdate, String remark) {
        this.user_name = user_name;
        this.user_id = user_id;
        this.user_pwd = user_pwd;
        this.user_tel = user_tel;
        this.user_idcard = user_idcard;
        this.address_content = address_content;
        this.user_head = user_head;
        this.user_sex = user_sex;
        this.user_registerdate = user_registerdate;
        this.remark = remark;
    }

    public User() {
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_tel() {
        return user_tel;
    }

    public void setUser_tel(String user_tel) {
        this.user_tel = user_tel;
    }

    public String getUser_idcard() {
        return user_idcard;
    }

    public void setUser_idcard(String user_idcard) {
        this.user_idcard = user_idcard;
    }

    public String getAddress_content() {
        return address_content;
    }

    public void setAddress_content(String address_content) {
        this.address_content = address_content;
    }

    public String getUser_head() {
        return user_head;
    }

    public void setUser_head(String user_head) {
        this.user_head = user_head;
    }

    public Boolean getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(Boolean user_sex) {
        this.user_sex = user_sex;
    }

    public Date getUser_registerdate() {
        return user_registerdate;
    }

    public void setUser_registerdate(Date user_registerdate) {
        this.user_registerdate = user_registerdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

