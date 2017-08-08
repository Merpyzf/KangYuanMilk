package com.merpyzf.kangyuanmilk.ui.user.bean;

import java.util.List;

/**
 * Created by wangke on 2017-08-08.
 *
 * 用户下的所有地址信息返回json所对应的Bean对象
 */

public class UserAddressBean {


    /**
     * status : 200
     * response : {"result":true,"addresses":[{"address_id":12,"address_content":"新疆巴音郭楞州库尔勒市巴州沙依东园艺场新疆 巴音郭楞州 库尔勒市 巴州沙依东园艺场seed","consignee":"Wangke","consignee_tel":"17714574929","user_id":46,"ads_id":52693},{"address_id":13,"address_content":"新疆巴音郭楞州且末县县城内新疆 巴音郭楞州 且末县 县城内well","consignee":"SSSI","consignee_tel":"17714574929","user_id":46,"ads_id":52623}],"defaultId":13}
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
         * addresses : [{"address_id":12,"address_content":"新疆巴音郭楞州库尔勒市巴州沙依东园艺场新疆 巴音郭楞州 库尔勒市 巴州沙依东园艺场seed","consignee":"Wangke","consignee_tel":"17714574929","user_id":46,"ads_id":52693},{"address_id":13,"address_content":"新疆巴音郭楞州且末县县城内新疆 巴音郭楞州 且末县 县城内well","consignee":"SSSI","consignee_tel":"17714574929","user_id":46,"ads_id":52623}]
         * defaultId : 13
         */

        private boolean result;
        //用户默认地址的id
        private int defaultId;

        private List<Address> addresses;

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public int getDefaultId() {
            return defaultId;
        }

        public void setDefaultId(int defaultId) {
            this.defaultId = defaultId;
        }

        public List<Address> getAddresses() {
            return addresses;
        }

        public void setAddresses(List<Address> addresses) {
            this.addresses = addresses;
        }

    }
}
