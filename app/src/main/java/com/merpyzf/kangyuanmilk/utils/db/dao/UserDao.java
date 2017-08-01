package com.merpyzf.kangyuanmilk.utils.db.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.ui.base.User;
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

    //内部维护一个静态的用户id
    private static Dao<User, Integer> dao = null;
    private static UserDao userDao = null;

    private UserDao() {

    }

    public static UserDao getInstance() {

        if (userDao == null) {
            synchronized (Object.class) {

                if (userDao == null) {

                    DBHelper dbHelper = DBHelper.getSingleInstance(App.getContext());
                    dao = dbHelper.getDao(User.class);
                    userDao = new UserDao();
                }
            }
        }
        return userDao;
    }


    /**
     * 保存一行数据
     *
     * @param user
     */
    public void createUser(User user) {

        try {

            dao.createOrUpdate(user);
            long count = dao.queryBuilder().countOf();

            int _id = getUserInfo().get_id();

            LogHelper.i("user_id==>  "+_id);

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
    public void updateUser(User user) {

        try {
            int id = getUserInfo().get_id();
            user.set_id(id);
            int update = dao.update(user);
            int _id = getUserInfo().get_id();
            LogHelper.i("更新结果==>"+update);

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

        UpdateBuilder<User, Integer> updateBuilder = dao.updateBuilder();
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
            List<User> userList = dao.queryForAll();

            for(int i=0;i<userList.size();i++){


                User userBean = userList.get(i);

                int delete = dao.delete(userBean);

                LogHelper.i("删除成功 =>" + delete);

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


    public User getUserInfo() {

        User user = null;

        try {
            List<User> userList = null;

            userList = dao.queryForAll();

            if (userList.size() == 1) {
                user = userList.get(0);

                LogHelper.i("用户名==>" + user.getUser_name());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;

    }


}
