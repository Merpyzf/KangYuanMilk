package com.merpyzf.kangyuanmilk.ui.home.bean;

import java.util.List;

/**
 * Created by wangke on 2017-08-23.
 * <p>
 * 服务端返回牛奶分类的json对应的bean对象
 */

public class CategoryBean {


    /**
     * status : 200
     * response : {"result":true,"milkCategory":[{"category_id":1,"category_name":"纯牛奶","remark":null},{"category_id":2,"category_name":"酸奶","remark":null},{"category_id":3,"category_name":"儿童嘟嘟","remark":null},{"category_id":4,"category_name":"植物活力","remark":null},{"category_id":5,"category_name":"可尔贝斯","remark":null},{"category_id":6,"category_name":"畅优","remark":null},{"category_id":7,"category_name":"儿童健能","remark":null},{"category_id":8,"category_name":"基础鲜奶","remark":null},{"category_id":9,"category_name":"配方奶粉","remark":null}]}
     */

    private int status;
    private ResponseBean response;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * result : true
         * milkCategory : [{"category_id":1,"category_name":"纯牛奶","remark":null},{"category_id":2,"category_name":"酸奶","remark":null},{"category_id":3,"category_name":"儿童嘟嘟","remark":null},{"category_id":4,"category_name":"植物活力","remark":null},{"category_id":5,"category_name":"可尔贝斯","remark":null},{"category_id":6,"category_name":"畅优","remark":null},{"category_id":7,"category_name":"儿童健能","remark":null},{"category_id":8,"category_name":"基础鲜奶","remark":null},{"category_id":9,"category_name":"配方奶粉","remark":null}]
         */

        private boolean result;
        private List<Category> milkCategory;

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public List<Category> getMilkCategory() {
            return milkCategory;
        }

        public void setMilkCategory(List<Category> milkCategory) {
            this.milkCategory = milkCategory;
        }


    }
}
