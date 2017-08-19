package com.merpyzf.kangyuanmilk.ui.home.bean;

/**
 * 商品分类
 * Created by wangke on 2017-08-16.
 */

public class Category {

    private String categoryName;
    private int categoryId;

    public Category(String categoryName, int categoryId) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    public Category() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
