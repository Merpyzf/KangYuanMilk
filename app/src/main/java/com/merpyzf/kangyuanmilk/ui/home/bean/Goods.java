package com.merpyzf.kangyuanmilk.ui.home.bean;

/**
 * Created by wangke on 2017-08-21.
 * 商品对象
 *
 */

public class Goods {
    /**
     * id : 1
     * imageview : null
     * price : 9
     * spec : 500ml
     * title : 酸奶
     */
    private int id;
    private Object imageview;
    private int price;
    private String spec;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getImageview() {
        return imageview;
    }

    public void setImageview(Object imageview) {
        this.imageview = imageview;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
