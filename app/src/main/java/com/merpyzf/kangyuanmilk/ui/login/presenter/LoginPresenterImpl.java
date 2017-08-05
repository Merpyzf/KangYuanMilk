package com.merpyzf.kangyuanmilk.ui.login.presenter;

import com.merpyzf.kangyuanmilk.common.data.Common;
import com.merpyzf.kangyuanmilk.common.observer.UserInfoSubject;
import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.login.LoginActivity;
import com.merpyzf.kangyuanmilk.ui.login.bean.LoginBean;
import com.merpyzf.kangyuanmilk.ui.login.contract.ILoginContract;
import com.merpyzf.kangyuanmilk.ui.login.model.ILoginModel;
import com.merpyzf.kangyuanmilk.ui.login.model.LoginModelImpl;
import com.merpyzf.kangyuanmilk.ui.user.bean.MessageBean;
import com.merpyzf.kangyuanmilk.utils.ErrorHandle;
import com.merpyzf.kangyuanmilk.utils.ErrorHandleHelper;
import com.merpyzf.kangyuanmilk.utils.HashHelper;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.SharedPreHelper;
import com.merpyzf.kangyuanmilk.utils.db.dao.UserDao;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by wangke on 2017-07-24.
 */

public class LoginPresenterImpl extends BasePresenter<ILoginContract.ILoginView> implements ILoginContract.ILoginPresenter {

    private ILoginModel mModel = null;

    public LoginPresenterImpl() {

        mModel = new LoginModelImpl();

    }

    /**
     * 登录
     * @param context Activity
     * @param username 用户名
     * @param pwd 密码
     */
    @Override
    public void login(LoginActivity context, String username, String pwd) {

        mMvpView.showLoadingDialog();

        //登录的时候进行md5加密
        mModel.login(username, HashHelper.getMD5String(pwd))
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                //使用rxlifecycle来根据Activity的生命周期来取消观察者与被观察者之间的订阅，防止出现内存泄露的问题
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onNext(LoginBean loginBean) {

                        new ErrorHandle(this, loginBean.getStatus()) {
                            @Override
                            protected void deal() {

                                boolean result = loginBean.getResponse().isResult();

                                if (result) {

                                    mMvpView.loginSuccess("登录成功", username, pwd);
                                    //登录成功之后，将用户信息存储在数据库中
                                    saveUserInfo(loginBean.getResponse().getUser());

                                    //通知刷新
                                    UserInfoSubject instance = UserInfoSubject.getInstance();
                                    instance.notifyChange();

                                } else {

                                    mMvpView.loginError("用户名或密码错误");


                                }


                            }
                        };


                    }

                    @Override
                    public void onError(Throwable e) {

                        ErrorHandleHelper.handle(e, mMvpView);

                    }

                    @Override
                    public void onComplete() {

                        mMvpView.cancelLoadingDialog();


                    }
                });

    }

    /**
     * 当用户输入用户名结束的时候,获取头像
     * @param context Activity
     * @param user user对象
     */
    @Override
    public void getAvater(LoginActivity context, User user) {

        mModel.userAvater(user)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<MessageBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onNext(@NonNull MessageBean messageBean) {

                        new ErrorHandle(this, messageBean.getStatus()) {
                            @Override
                            protected void deal() {

                                mMvpView.showAvater(Common.OUTSIDE_CHAIN+messageBean.getResponse().getMessage());
                                LogHelper.i("用户头像==>"+messageBean.getResponse().getMessage());
                            }
                        };
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {

                        ErrorHandleHelper.handle(e, mMvpView);

                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 保存登录信息(用户名,密码)
     * @param username
     * @param pwd
     */
    @Override
    public void saveLoginInfo(String username, String pwd) {

        //保存用户的登录信息
        SharedPreHelper.saveLoginInfo(username, pwd);

    }

    /**
     * 保存用户信息到数据库
     * @param user user对象
     */
    @Override
    public void saveUserInfo(User user) {
        UserDao userDao = UserDao.getInstance();
        //保存用户信息
        userDao.createUser(user);
    }


    @Override
    public void detachView() {
        super.detachView();
    }


}
