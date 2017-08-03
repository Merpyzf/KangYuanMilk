package com.merpyzf.kangyuanmilk.common.data;

/**
 * Created by wangke on 17-7-24.
 * 存储一些全局的常量
 */

public class Common {

    //用户登录信息保存的sp文件的文件名
    public static final String SP_LOGININFO = "loginInfo";

    //网络请求返回的几种状态
    public static final int HTTP_OK = 200;
    public static final int HTTP_ERROR = 500;
    public static final int HTTP_FAILED = 400;

    //存储用户信息的数据库名
    public static final String USER_DB_NAME = "user.db";
    //七牛云图床访问外链,默认
    public static final String OUTSIDE_CHAIN = "http://otdmrup4y.bkt.clouddn.com/";

}
