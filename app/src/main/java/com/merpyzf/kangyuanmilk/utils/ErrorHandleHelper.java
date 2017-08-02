package com.merpyzf.kangyuanmilk.utils;

import com.merpyzf.kangyuanmilk.ui.base.IBaseView;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by wangke on 2017-07-25.
 * Rx异常处理
 */

public class ErrorHandleHelper {


    public static void handle(Throwable e, IBaseView view) {

        String message = e.getMessage();
        view.cancelLoadingDialog();

        if(message.equals("404")){

            view.showErrorMsg("服务器异常: "+404);


        }else if(message.equals("500")){

            view.showErrorMsg("服务器异常: "+500);


        }else if(e instanceof UnknownHostException || e instanceof ConnectException){

            view.showErrorMsg("网络不可用!请检查网络连接!");


        }else if(e instanceof SocketTimeoutException){

            view.showErrorMsg("连接服务器失败!请稍后再试!");

        }else if(e instanceof IOException){

            view.showErrorMsg("连接服务器失败!请稍后再试!");

        }else {

            view.showErrorMsg("服务器异常，错误能未捕获");
        }
    }


}
