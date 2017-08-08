package com.merpyzf.kangyuanmilk.ui.user.bean;

/**
 * Created by wangke on 2017-08-07.
 * 地址选择器使用到的AddressBean对象
 */

public class AddressBean {

    //收货人姓名
    private String name;
    //收货人手机号
    private String phone;
    //收货地址
    private String address;

    //地址所对应数据库中的id
    private int id;

    public AddressBean(String name, String phone, String address, int id) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.id = id;
    }

    public AddressBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
