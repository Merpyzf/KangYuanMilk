package com.merpyzf.kangyuanmilk.ui.home.bean;

import java.util.List;

/**
 * Created by wangke on 2017-08-23.
 * 商品列表json对应的bean对象
 *
 */

public class GoodsBean {


    /**
     * status : 200
     * response : {"result":true,"dataList":[{"id":1,"imageview":null,"price":null,"spec":null,"title":"酸奶"}]}
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
         * dataList : [{"id":1,"imageview":null,"price":null,"spec":null,"title":"酸奶"}]
         */

        private boolean result;
        private List<Goods> dataList;

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public List<Goods> getDataList() {
            return dataList;
        }

        public void setDataList(List<Goods> dataList) {
            this.dataList = dataList;
        }

    }
}
