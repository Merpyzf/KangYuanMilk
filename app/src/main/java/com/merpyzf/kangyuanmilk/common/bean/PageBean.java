package com.merpyzf.kangyuanmilk.common.bean;

/**
 * Created by wangke on 2017-08-21.
 * 封装分页相关的信息
 */

public class PageBean {

    //是否处于刷新状态
    private boolean isRefresh = false;

    //展示页
    private int page = 0;

    //每一页对应展示的个数
    private int num = 10;

    private String remark;


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
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

    //获取当前加载的页数
    public int getLoadPage() {
        if (isRefresh) {
            return page = 1;
        }
        return ++page;
    }

    /**
     * 重置初始状态
     */
    public void reset() {

        isRefresh = false;
        num = 10;
        page = 1;

    }


}
