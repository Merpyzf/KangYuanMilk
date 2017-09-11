package com.merpyzf.kangyuanmilk.ui.home.bean;


/**
 * Created by 春水碧于天 on 2017/9/11.
 */

public class ContentBean {


    /**
     * status : 200
     * response : {"result":true,"activity":{"activity_id":1,"activity_title":"免费活动","activity_image":null,"activity_content":"测试测试测试测试","remark":null,"splash":false}}
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
         * activity : {"activity_id":1,"activity_title":"免费活动","activity_image":null,"activity_content":"测试测试测试测试","remark":null,"splash":false}
         */

        private boolean result;
        private Content activity;

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public Content getActivity() {
            return activity;
        }

        public void setActivity(Content activity) {
            this.activity = activity;
        }


    }
}
