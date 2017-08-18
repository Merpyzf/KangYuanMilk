package com.merpyzf.kangyuanmilk.ui.user.bean;

import java.io.Serializable;

/**
 * Created by wangke on 2017-08-08.
 * 用户地址信息
 */

public class Address implements Serializable {

    private int address_id;

    private String address_all;
    //地址
    private String address_content;
    //收货人姓名
    private String consignee;
    //收货人的联系方式
    private String consignee_tel;
    //用户id
    private int user_id;
    //选择的最小区域的id
    private int ads_id;
    //是否是默认
    private boolean isDefault = false;


    public Address() {
    }

    public Address(int address_id, String address_content, String consignee, String consignee_tel, int user_id, int ads_id) {
        this.address_id = address_id;
        this.address_content = address_content;
        this.consignee = consignee;
        this.consignee_tel = consignee_tel;
        this.user_id = user_id;
        this.ads_id = ads_id;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getAddress_content() {
        return address_content;
    }

    public void setAddress_content(String address_content) {
        this.address_content = address_content;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsignee_tel() {
        return consignee_tel;
    }

    public void setConsignee_tel(String consignee_tel) {
        this.consignee_tel = consignee_tel;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAds_id() {
        return ads_id;
    }

    public void setAds_id(int ads_id) {
        this.ads_id = ads_id;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getAddress_all() {
        return address_all;
    }

    public void setAddress_all(String address_all) {
        this.address_all = address_all;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (address_id != address.address_id) return false;
        if (user_id != address.user_id) return false;
        if (ads_id != address.ads_id) return false;
        if (isDefault != address.isDefault) return false;
        if (!address_all.equals(address.address_all)) return false;
        if (!address_content.equals(address.address_content)) return false;
        if (!consignee.equals(address.consignee)) return false;
        return consignee_tel.equals(address.consignee_tel);
    }

}
