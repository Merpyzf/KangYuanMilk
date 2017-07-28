package com.merpyzf.kangyuanmilk.ui.base;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by wangke on 2017-07-22.
 * 用户信息
 */
@DatabaseTable(tableName = "tab_user")
public class User {
    @DatabaseField(generatedId = true)
    private int _id;
    @DatabaseField(columnName = "user_id")
    private int user_id;
    @DatabaseField(columnName = "user_name")
    private String user_name;
    @DatabaseField(columnName = "user_pwd")
    private String user_pwd;
    @DatabaseField(columnName = "user_tel")
    private String user_tel;
    @DatabaseField(columnName = "user_idcard")
    private String user_idcard;
    @DatabaseField(columnName = "address_content")
    private String address_content;
    @DatabaseField(columnName = "user_head")
    private String user_head;
    @DatabaseField(columnName = "user_sex")
    private boolean user_sex;
    @DatabaseField(columnName = "user_registerdate")
    private String user_registerdate;
    @DatabaseField(columnName = "remark")
    private String remark;


    public User(int _id, int user_id, String user_name, String user_pwd, String user_tel,
                String user_idcard, String address_content, String user_head, boolean user_sex,
                String user_registerdate, String remark) {
        this._id = _id;
        this.user_id = user_id;
        this.user_name = user_name;
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

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public boolean isUser_sex() {
        return user_sex;
    }

    public void setUser_sex(boolean user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_registerdate() {
        return user_registerdate;
    }

    public void setUser_registerdate(String user_registerdate) {
        this.user_registerdate = user_registerdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

