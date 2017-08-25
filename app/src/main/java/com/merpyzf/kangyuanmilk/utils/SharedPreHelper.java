package com.merpyzf.kangyuanmilk.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.data.Common;
import com.merpyzf.kangyuanmilk.utils.db.dao.UserDao;

/**
 * Created by wangke on 17-7-24.
 * SharedPreferences文件存储的工具类
 */

public final class SharedPreHelper {

    private SharedPreHelper() {
        throw new UnsupportedOperationException("u can't instantiante me……");
    }


    private static Context context;

    static {

        context = App.getInstance().getApplicationContext();

    }


    public static void putString(String sp_name, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(key, value)
                .commit();

    }


    public static String getString(String sp_name, String key, String default_value) {

        String value = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE)
                .getString(key, default_value);

        return value;
    }


    public static void putInt(String sp_name, String key, int value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putInt(key, value)
                .commit();

    }


    public static int getInt(String sp_name, String key, int default_value) {

        int value = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE)
                .getInt(key, default_value);

        return value;
    }












    /**
     * 保存登录信息
     *
     * @param username
     * @param pwd
     */
    public static void saveLoginInfo(String username, String pwd) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(Common.SP_LOGININFO, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString("username", username)
                .putString("pwd", pwd)
                .commit();

    }

    /**
     * 获取登录信
     *
     * @return null 没有读取到登录信息
     */
    public static LoginInfo getLoginInfo() {

        String username = context.getSharedPreferences(Common.SP_LOGININFO, Context.MODE_PRIVATE)
                .getString("username", "null");

        String password = context.getSharedPreferences(Common.SP_LOGININFO, Context.MODE_PRIVATE)
                .getString("pwd", "null");

        if (username.equals("null") || password.equals("null")) {
            return null;
        }

        return new LoginInfo(username, password);

    }


    /**
     * 清除保存的用户信息
     */
    public static void clearLoginInfo() {

        context.getSharedPreferences(Common.SP_LOGININFO, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .commit();
        UserDao.getInstance().clearUser();

    }

    /**
     * 封装用户名密码
     */
    public static class LoginInfo {

        String userName;
        String password;

        public LoginInfo(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public LoginInfo() {
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    /**
     * 记录app的使用
     */
    public static void saveUseActions() {

        putString(Common.SP_GUIDE, "isFirstGuide", "no");

    }

    /**
     * 是否是第一次使用
     * @return
     */
    public static boolean isFirstUse() {

        String firstUse = getString(Common.SP_GUIDE, "isFirstGuide", "yes");

        return "yes".equals(firstUse);


    }


    /**
     * 保存上一次选择的分类id
     * @param id
     */
    public static void saveOldChoiceCategory(int id){

        putInt("sp_category","oldCategory",id);

    }

    /**
     * 获取上一次选择的分类id
     * @return
     */
    public static int getOldChoiceCategory(){

        return getInt("sp_category","oldCategory",1);
    }



}
