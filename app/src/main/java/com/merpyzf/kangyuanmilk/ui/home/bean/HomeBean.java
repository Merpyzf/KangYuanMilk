package com.merpyzf.kangyuanmilk.ui.home.bean;

import java.util.List;

/**
 * Created by wangke on 2017-08-22
 * 主页返回json对象格式
 */

public class HomeBean {


    /**
     * status : 200
     * response : {"result":true,"resultList":[{"dataInfoList":[{"id":1,"imageview":"avatar/aac79e2472fdb78b9cfd44bcc49ae495","price":null,"spec":null,"title":"免费活动"}],"type":"HEADER_BANNER","moduleTitle":"广告"},{"dataInfoList":[{"id":2,"imageview":"avatar/aac79e2472fdb78b9cfd44bcc49ae495","price":null,"spec":null,"title":"感恩节活动"}],"type":"NEW_ACTIVITY","moduleTitle":"最新活动"},{"dataInfoList":[{"id":10,"imageview":"10.png","price":5,"spec":"500ml","title":"酸奶8"},{"id":11,"imageview":"11.png","price":5,"spec":"500ml","title":"酸奶9"},{"id":13,"imageview":"13.png","price":5,"spec":"500ml","title":"酸奶11"},{"id":12,"imageview":"12.jpg","price":5,"spec":"500ml","title":"酸奶10"},{"id":4,"imageview":"4.jpg","price":3,"spec":"600ml","title":"酸奶2"},{"id":6,"imageview":"6.jpg","price":9,"spec":"600ml","title":"酸奶4"}],"type":"HOT_PRODUCT","moduleTitle":"热销产品"},{"dataInfoList":[{"id":1,"imageview":"1.png","price":9,"spec":"500ml","title":"酸奶"},{"id":3,"imageview":"3.jpg","price":9,"spec":"500ml","title":"酸奶13"},{"id":4,"imageview":"4.jpg","price":3,"spec":"600ml","title":"酸奶2"}],"type":"NEW_PRODUCT","moduleTitle":"新品尝鲜"}]}
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
         * resultList : [{"dataInfoList":[{"id":1,"imageview":"avatar/aac79e2472fdb78b9cfd44bcc49ae495","price":null,"spec":null,"title":"免费活动"}],"type":"HEADER_BANNER","moduleTitle":"广告"},{"dataInfoList":[{"id":2,"imageview":"avatar/aac79e2472fdb78b9cfd44bcc49ae495","price":null,"spec":null,"title":"感恩节活动"}],"type":"NEW_ACTIVITY","moduleTitle":"最新活动"},{"dataInfoList":[{"id":10,"imageview":"10.png","price":5,"spec":"500ml","title":"酸奶8"},{"id":11,"imageview":"11.png","price":5,"spec":"500ml","title":"酸奶9"},{"id":13,"imageview":"13.png","price":5,"spec":"500ml","title":"酸奶11"},{"id":12,"imageview":"12.jpg","price":5,"spec":"500ml","title":"酸奶10"},{"id":4,"imageview":"4.jpg","price":3,"spec":"600ml","title":"酸奶2"},{"id":6,"imageview":"6.jpg","price":9,"spec":"600ml","title":"酸奶4"}],"type":"HOT_PRODUCT","moduleTitle":"热销产品"},{"dataInfoList":[{"id":1,"imageview":"1.png","price":9,"spec":"500ml","title":"酸奶"},{"id":3,"imageview":"3.jpg","price":9,"spec":"500ml","title":"酸奶13"},{"id":4,"imageview":"4.jpg","price":3,"spec":"600ml","title":"酸奶2"}],"type":"NEW_PRODUCT","moduleTitle":"新品尝鲜"}]
         */

        private boolean result;
        private List<ResultListBean> resultList;

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public List<ResultListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ResultListBean> resultList) {
            this.resultList = resultList;
        }

        public static class ResultListBean {
            /**
             * dataInfoList : [{"id":1,"imageview":"avatar/aac79e2472fdb78b9cfd44bcc49ae495","price":null,"spec":null,"title":"免费活动"}]
             * type : HEADER_BANNER
             * moduleTitle : 广告
             */

            private String type;
            private String moduleTitle;
            private List<DataInfoListBean> dataInfoList;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getModuleTitle() {
                return moduleTitle;
            }

            public void setModuleTitle(String moduleTitle) {
                this.moduleTitle = moduleTitle;
            }

            public List<DataInfoListBean> getDataInfoList() {
                return dataInfoList;
            }

            public void setDataInfoList(List<DataInfoListBean> dataInfoList) {
                this.dataInfoList = dataInfoList;
            }

            public static class DataInfoListBean {
                /**
                 * id : 1
                 * imageview : avatar/aac79e2472fdb78b9cfd44bcc49ae495
                 * price : null
                 * spec : null
                 * title : 免费活动
                 */

                private int id;
                private String imageview;
                private String price;
                private String spec;
                private String title;

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

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
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
        }
    }
}
