package com.merpyzf.kangyuanmilk.ui.home.bean;

/**
 * Created by wangke on 2017-08-21.
 * 查询乳品信息的关键字段
 */

public class QueryKey {

    //用于搜索的关键字
    private String key;
    //所在的页
    private int page;
    //每页面返回数据的个数
    private int num;

    public QueryKey(String key, int page, int num) {
        this.key = key;
        this.page = page;
        this.num = num;
    }

    public QueryKey() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
