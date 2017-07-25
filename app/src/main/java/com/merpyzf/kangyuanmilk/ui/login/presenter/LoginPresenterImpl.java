package com.merpyzf.kangyuanmilk.ui.login.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.login.LoginActivity;
import com.merpyzf.kangyuanmilk.ui.login.bean.LoginBean;
import com.merpyzf.kangyuanmilk.ui.login.contract.ILoginContract;
import com.merpyzf.kangyuanmilk.ui.login.model.ILoginModel;
import com.merpyzf.kangyuanmilk.ui.login.model.LoginModelImpl;
import com.merpyzf.kangyuanmilk.utils.ErrorHandle;
import com.merpyzf.kangyuanmilk.utils.ErrorHandleHelper;
import com.merpyzf.kangyuanmilk.utils.SharedPreHelper;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wangke on 2017-07-24.
 */

public class LoginPresenterImpl extends BasePresenter<ILoginContract.ILoginView> implements ILoginContract.ILoginPresenter {

    private ILoginModel mLoginModel = null;

    public LoginPresenterImpl() {

        mLoginModel = new LoginModelImpl();

    }

    @Override
    public void login(LoginActivity context, String username, String pwd) {

        mMvpView.showLoadingDialog();

        //登录的时候进行md5加密
//        HashHelper.getMD5String(pwd);

        mLoginModel.login(username, pwd)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                //使用rxlifecycle来根据Activity的生命周期来取消观察者与被观察者之间的订阅，防止出现内存泄露的问题
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onNext(LoginBean loginBean) {

                        ErrorHandle errorHandle = new ErrorHandle(this,loginBean.getStatus()) {
                            @Override
                            protected void deal() {

                                boolean result = loginBean.getResponse().isResult();

                                if(result){

                                    mMvpView.loginSuccess("登录成功",username,pwd);
                                    //登录成功之后，将用户信息存储在数据库中
                                    saveUserInfo(loginBean.getResponse().getUser());



                                }else {

                                    mMvpView.loginError("用户名或密码错误");


                                }


                            }
                        };




                    }

                    @Override
                    public void onError(Throwable e) {

                        ErrorHandleHelper.handle(e,mMvpView);

                    }

                    @Override
                    public void onComplete() {

                        mMvpView.cancelLoadingDialog();


                    }
                });

    }

    @Override
    public void saveLoginInfo(String username, String pwd) {

        //保存用户的登录信息
        SharedPreHelper.saveLoginInfo(username,pwd);

    }

    @Override
    public void saveUserInfo(LoginBean.ResponseBean.UserBean user) {

    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
