package com.merpyzf.kangyuanmilk.ui.home.bean;

/**
 * Created by 春水碧于天 on 2017/9/11.
 */

public class Content {


    /**
     * activity_id : 1
     * activity_title : 免费活动
     * activity_image : null
     * activity_content : 测试测试测试测试
     * remark : null
     * splash : false
     */

    private int activity_id;
    private String activity_title;
    private String activity_image;
    private String activity_content;
    private Object remark;
    private boolean splash;

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public String getActivity_title() {
        return activity_title;
    }

    public void setActivity_title(String activity_title) {
        this.activity_title = activity_title;
    }

    public String getActivity_image() {
        return activity_image;
    }

    public void setActivity_image(String activity_image) {
        this.activity_image = activity_image;
    }

    public String getActivity_content() {
        return activity_content;
    }

    public void setActivity_content(String activity_content) {
        this.activity_content = activity_content;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public boolean isSplash() {
        return splash;
    }

    public void setSplash(boolean splash) {
        this.splash = splash;
    }
}
