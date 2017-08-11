package com.merpyzf.kangyuanmilk.common.data;

import java.util.List;

/**
 * Created by Administrator on 2017-08-11.
 */

public class Response {

    List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {

        //返回数据格式的类型
        private String type;
        //对应的数据集合
        private List<DataInfo> dataInfoList;



        public List<DataInfo> getDataInfoList() {
            return dataInfoList;
        }

        public void setDataInfoList(List<DataInfo> dataInfoList) {
            this.dataInfoList = dataInfoList;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class DataInfo {


        //对应要显示的那条信息的id
        private int id;
        //对应要展示信息的图片
        private String imageview;
        //要展示的文字/公(乳品的名字)
        private String title;
        //规格
        private String spec;
        //价格
        private String price;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageview() {
            return imageview;
        }

        public void setImageview(String imageview) {
            this.imageview = imageview;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}





