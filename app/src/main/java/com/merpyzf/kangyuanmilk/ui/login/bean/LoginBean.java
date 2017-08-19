package com.merpyzf.kangyuanmilk.ui.login.bean;

import com.merpyzf.kangyuanmilk.ui.user.bean.User;

/**
 * Created by wk on 2017-07-24.
 * 登录的时候返回的数据
 */

public class LoginBean {


    /**
     * response : {"user":{"user_name":"tom","user_id":0,"user_pwd":"123456","user_tel":null,"user_idcard":null,"address_content":null,"user_head":null,"user_sex":null,"user_registerdate":null,"remark":null},"result":true}
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
         * user : {"user_name":"tom","user_id":0,"user_pwd":"123456","user_tel":null,"user_idcard":null,"address_content":null,"user_head":null,"user_sex":null,"user_registerdate":null,"remark":null}
         * result : true
         */

        private User user;

        private boolean result;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

    }
}
