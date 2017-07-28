package com.merpyzf.kangyuanmilk.ui.user.bean;

/**
 * Created by wangke on 2017-07-28.
 */

public class UploadAvaterBean {

    /**
     * response : {"result":true}
     * status : 200
     */

    private ResponseBean response;
    private int status;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class ResponseBean {
        /**
         * result : true
         */

        private boolean result;
        private String message;

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
