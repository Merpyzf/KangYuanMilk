package com.merpyzf.kangyuanmilk.ui.home.bean;

/**
 * Created by wangke on 2017/9/9.
 * 商品详情
 */

public class GoodsDetailBean {

    /**
     * status : 200
     * response : {"result":true,"milk":{"milk_id":1,"milk_name":"酸奶","category_id":2,"milk_price":9,"milk_introduce":"这是酸奶","milk_spec":"500ml","milk_sales":111,"remark":null}}
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
         * milk : {"milk_id":1,"milk_name":"酸奶","category_id":2,"milk_price":9,"milk_introduce":"这是酸奶","milk_spec":"500ml","milk_sales":111,"remark":null}
         */

        private boolean result;
        private MilkBean milk;

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public MilkBean getMilk() {
            return milk;
        }

        public void setMilk(MilkBean milk) {
            this.milk = milk;
        }

        public static class MilkBean {
            /**
             * milk_id : 1
             * milk_name : 酸奶
             * category_id : 2
             * milk_price : 9
             * milk_introduce : 这是酸奶
             * milk_spec : 500ml
             * milk_sales : 111
             * remark : null
             */

            private int milk_id;
            private String milk_name;
            private int category_id;
            private int milk_price;
            private String milk_introduce;
            private String milk_spec;
            private int milk_sales;
            private Object remark;

            public int getMilk_id() {
                return milk_id;
            }

            public void setMilk_id(int milk_id) {
                this.milk_id = milk_id;
            }

            public String getMilk_name() {
                return milk_name;
            }

            public void setMilk_name(String milk_name) {
                this.milk_name = milk_name;
            }

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public int getMilk_price() {
                return milk_price;
            }

            public void setMilk_price(int milk_price) {
                this.milk_price = milk_price;
            }

            public String getMilk_introduce() {
                return milk_introduce;
            }

            public void setMilk_introduce(String milk_introduce) {
                this.milk_introduce = milk_introduce;
            }

            public String getMilk_spec() {
                return milk_spec;
            }

            public void setMilk_spec(String milk_spec) {
                this.milk_spec = milk_spec;
            }

            public int getMilk_sales() {
                return milk_sales;
            }

            public void setMilk_sales(int milk_sales) {
                this.milk_sales = milk_sales;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }
        }
    }
}
