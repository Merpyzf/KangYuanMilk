package com.merpyzf.kangyuanmilk.ui.home.bean;

/**
 * 商品分类
 * Created by wangke on 2017-08-16.
 */

public class Category {

    /**
     * category_id : 1
     * category_name : 纯牛奶
     * remark : null
     */

    private int category_id;
    private String category_name;
    //记录当前分类是否被选中,默认未选中
    private boolean choice = false;
    private Object remark;


    public Category(int category_id, String category_name) {
        this.category_id = category_id;
        this.category_name = category_name;
    }

    public Category() {
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }
}
