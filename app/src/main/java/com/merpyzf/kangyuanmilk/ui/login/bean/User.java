package com.merpyzf.kangyuanmilk.ui.login.bean;

import java.util.Date;

/**
 * Created by wangke on 2017-07-22.
 * 用户信息
 */

public class User {


    private int id;
    private String name;
    private String pass;
    private String tel;
    private String idCard;
    private int address;
    private String head;
    private String sex;
    private Date date;
    private String remark;


    public User(int id, String name, String pass, String tel, String idCard, int address, String head, String sex, Date date, String remark) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.tel = tel;
        this.idCard = idCard;
        this.address = address;
        this.head = head;
        this.sex = sex;
        this.date = date;
        this.remark = remark;
    }

    public User() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

