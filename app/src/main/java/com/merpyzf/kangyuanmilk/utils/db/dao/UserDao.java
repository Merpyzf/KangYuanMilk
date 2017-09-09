package com.merpyzf.kangyuanmilk.utils.db.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.ui.user.bean.User;
import com.merpyzf.kangyuanmilk.ui.user.bean.Address;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.db.DBHelper;

import java.sql.SQLException;
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
            //请空所有的用户数据，保证数据只有一条
            clearUser();
            LogHelper.i("用户的默认地址==> " + user.getAddress_content());
            dao.createOrUpdate(user);
            long count = dao.queryBuilder().countOf();
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
            int update = dao.update(user);

            dao.updateBuilder()
                    .updateColumnValue("user_name", user.getUser_name())
                    .updateColumnValue("user_pwd", user.getUser_pwd())
                    .updateColumnValue("user_head", user.getUser_head())
                    .updateColumnValue("user_sex", user.isUser_sex())
                    .updateColumnValue("user_idcard", user.getUser_idcard())
                    .updateColumnValue("address_content", user.getAddress_content())
                    .updateColumnValue("user_tel", user.getUser_tel())
                    .updateColumnValue("remark", user.getRemark())
                    .where()
                    .eq("user_id", user.getUser_id());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新user的指定列
     *
     * @param map key - 列名
     *            value - 更新的内容
     */
    public void updateColumns(int userId, Map<String, String> map) {

        if (map == null) {
            return;
        }

        UpdateBuilder<User, Integer> updateBuilder = dao.updateBuilder();
        Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            try {
                updateBuilder.updateColumnValue(entry.getKey(), entry.getValue()).where().eq("user_id", userId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 清除用户信息
     */
    public void clearUser() {

        try {
            List<User> userList = dao.queryForAll();

            for (int i = 0; i < userList.size(); i++) {


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


    /**
     * 获取用户的信息从数据库中
     *
     * @return
     */
    public User getUserInfo() {
        User user = null;
        try {
            List<User> userList = null;
            userList = dao.queryForAll();
            LogHelper.i("userList==>size==>" + userList.size());
            if (userList.size() == 1) {
                user = userList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;

    }

    /***
     * 更新用户的默认地址
     * @param address
     */
    public void setUserDefaultAds(Address address) {

        String address_content;


        if (address == null) {

            address_content = "";


        } else {

            address_content = address.getConsignee()
                    + "-" + address.getConsignee_tel() + "-" + address.getAddress_all()
                    + " " + address.getAddress_content();
        }


        try {
            Where<User, Integer> eq = dao.updateBuilder()
                    .updateColumnValue("address_content", address_content)
                    .where()
                    .eq("user_id", getUserInfo().getUser_id());


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public boolean isLogin() {

        return UserDao.getInstance().getUserInfo() != null;
    }


}
