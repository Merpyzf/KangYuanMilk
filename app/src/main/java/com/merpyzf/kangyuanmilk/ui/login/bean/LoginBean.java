package com.merpyzf.kangyuanmilk.ui.login.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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

        private UserBean user;
        private boolean result;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        @DatabaseTable(tableName = "tab_user")
        public static class UserBean {
            /**
             * user_name : tom
             * user_id : 0
             * user_pwd : 123456
             * user_tel : null
             * user_idcard : null
             * address_content : null
             * user_head : null
             * user_sex : null
             * user_registerdate : null
             * remark : null
             */
            @DatabaseField(generatedId = true) //设置user_id为朱
            private int user_id;
            @DatabaseField(columnName = "user_name")
            private String user_name;
            @DatabaseField(columnName = "user_pwd")
            private String user_pwd;
            @DatabaseField(columnName = "user_tel")
            private String user_tel;
            @DatabaseField(columnName = "user_idcard")
            private String user_idcard;
            @DatabaseField(columnName = "address_content")
            private String address_content;
            @DatabaseField(columnName = "user_head")
            private String user_head;
            @DatabaseField(columnName = "user_sex")
            private boolean user_sex;
            @DatabaseField(columnName = "user_registerdate")
            private String user_registerdate;
            @DatabaseField(columnName = "remark")
            private String remark;


            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_pwd() {
                return user_pwd;
            }

            public void setUser_pwd(String user_pwd) {
                this.user_pwd = user_pwd;
            }

            public String getUser_tel() {
                return user_tel;
            }

            public void setUser_tel(String user_tel) {
                this.user_tel = user_tel;
            }

            public String getUser_idcard() {
                return user_idcard;
            }

            public void setUser_idcard(String user_idcard) {
                this.user_idcard = user_idcard;
            }

            public String getAddress_content() {
                return address_content;
            }

            public void setAddress_content(String address_content) {
                this.address_content = address_content;
            }

            public String getUser_head() {
                return user_head;
            }

            public void setUser_head(String user_head) {
                this.user_head = user_head;
            }

            public boolean isUser_sex() {
                return user_sex;
            }

            public void setUser_sex(boolean user_sex) {
                this.user_sex = user_sex;
            }

            public String getUser_registerdate() {
                return user_registerdate;
            }

            public void setUser_registerdate(String user_registerdate) {
                this.user_registerdate = user_registerdate;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
