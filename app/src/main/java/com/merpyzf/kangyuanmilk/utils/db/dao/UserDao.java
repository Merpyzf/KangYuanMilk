package com.merpyzf.kangyuanmilk.utils.db.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.ui.login.bean.LoginBean;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.db.DBHelper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wangke on 2017-07-26.
 */

public class UserDao {

    private Context context;
    //内部维护一个静态的用户id
    private final Dao<LoginBean.ResponseBean.UserBean, Integer> dao;

    public UserDao(Context context) {

        this.context = context;

        DBHelper dbHelper = DBHelper.getSingleInstance(context);
        dao = dbHelper.getDao(LoginBean.ResponseBean.UserBean.class);

    }

    /**
     * 保存一行数据
     *
     * @param user
     */
    public void createUser(LoginBean.ResponseBean.UserBean user) {

        try {

            long count = dao.queryBuilder().countOf();

            if (count == 1) {
                int i = dao.update(user);
                LogHelper.i("更新");

            } else {

                LogHelper.i("user==>"+user.getUser_name());

                int i = dao.create(user);
                LogHelper.i("创建");
            }


            LogHelper.i("用户信息的数量: " + count);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 更新user中的所有列
     *
     * @param user
     */
    public void updateUser(LoginBean.ResponseBean.UserBean user) {

        try {

            dao.update(user);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 更新user 指定列
     *
     * @param map key - 列名
     *            value - 更新的内容
     */
    public void updateUser(HashMap<String, Object> map) {


        if (map == null) {
            return;
        }

        UpdateBuilder<LoginBean.ResponseBean.UserBean, Integer> updateBuilder = dao.updateBuilder();
        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Object> entry = entries.next();
            try {
                updateBuilder.updateColumnValue(entry.getKey(), entry.getValue());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {

            int update = updateBuilder.update();

            LogHelper.i("UserDao中更新指定列 ==> " + update);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 清除用户信息
     */
    public void clearUser() {

        try {
            List<LoginBean.ResponseBean.UserBean> userList = dao.queryForAll();

            if (userList.size() == 1) {

                LoginBean.ResponseBean.UserBean userBean = userList.get(0);

                int delete = dao.delete(userBean);

                LogHelper.i("删除成功 =>" + delete);


            } else {

                App.showToast("查出来的用户信息不止一条");

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    /**
     * 获取当前表中存储的用户数量
     *
     * @return
     * @throws SQLException
     */
    public int getUserCount() throws SQLException {

        return (int) dao.queryBuilder().countOf();
    }


    public LoginBean.ResponseBean.UserBean getUserInfo() {

        LoginBean.ResponseBean.UserBean user = null;

        try {
            List<LoginBean.ResponseBean.UserBean> userList = null;

            userList = dao.queryForAll();


            if (userList.size() == 1) {

                user = userList.get(0);

                LogHelper.i("用户名==>"+user.getUser_name());


            } else {
                App.showToast("查出来的用户信息不止一条");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return  user;

    }


}
