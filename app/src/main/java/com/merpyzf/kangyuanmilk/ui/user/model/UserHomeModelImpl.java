package com.merpyzf.kangyuanmilk.ui.user.model;

import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.user.bean.UploadAvaterBean;
import com.merpyzf.kangyuanmilk.utils.db.dao.UserDao;
import com.merpyzf.kangyuanmilk.utils.http.RetrofitFactory;

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

    }

    /**
     * 上传头像
     * @param user
     * @return
     */
    @Override
    public Observable<UploadAvaterBean> uploadAvater(User user) {

        return RetrofitFactory.getServiceInstance()
                .upLoadAvater(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
