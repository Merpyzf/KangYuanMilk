package com.merpyzf.kangyuanmilk.ui.login.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.login.LoginActivity;
import com.merpyzf.kangyuanmilk.ui.login.contract.ILoginContract;
import com.merpyzf.kangyuanmilk.ui.login.model.ILoginModel;
import com.merpyzf.kangyuanmilk.ui.login.model.LoginModelImpl;
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

        mLoginModel.login(username, pwd)
                //使用rxlifecycle来根据Activity的生命周期来取消观察者与被观察者之间的订阅，防止出现内存泄露的问题
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .map(loginBean -> loginBean.getResponse().isResult())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onNext(Boolean value) {

                        if (value) {

                            mMvpView.loginSuccess("登录成功",username,pwd);

                        } else {
                            mMvpView.loginError("登录失败");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        mMvpView.showErrorMsg("服务器错误");

                    }

                    @Override
                    public void onComplete() {

                        mMvpView.cancelLoadingDialog();


                    }
                });

    }

    @Override
    public void detachView() {
        super.detachView();


    }
}
