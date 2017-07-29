package com.merpyzf.kangyuanmilk.ui.user.model;

import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.user.bean.MessageBean;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.db.dao.UserDao;
import com.merpyzf.kangyuanmilk.utils.http.RetrofitFactory;

import java.sql.SQLException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017-07-27.
 */

public class UserHomeModelImpl implements IUserHomeModel {
    /**
     * 从数据库中读取用户的信息
     *
     * @return
     */
    @Override
    public User getUserInfo() {

        UserDao dao = UserDao.getInstance(App.getContext());
        User user = dao.getUserInfo();

        return user;
    }

    /**
     * 更新数据库中的用户信息
     * @param user
     */
    @Override
    public void updateUserInfo(User user) {

        UserDao dao = UserDao.getInstance(App.getContext());
        dao.updateUser(user);

        try {
            int userCount = dao.getUserCount();

            LogHelper.i("=====================>userCount "+userCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LogHelper.i("保存数据库前 _id ==>"+dao.getUserInfo().getUser_id());
        LogHelper.i("保存数据库前==>"+dao.getUserInfo().getUser_head());

        LogHelper.i("*******上传头像后的更新******");



        LogHelper.i("header==>"+dao.getUserInfo().getUser_head());

        LogHelper.i("*******上传头像后的更新******");



    }

    /**
     * 上传头像
     * @param user
     * @return
     */
    @Override
    public Observable<MessageBean> uploadAvater(User user) {

        return RetrofitFactory.getServiceInstance()
                .upLoadAvater(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
