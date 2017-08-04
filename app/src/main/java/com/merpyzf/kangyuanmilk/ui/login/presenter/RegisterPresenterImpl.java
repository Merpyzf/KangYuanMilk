package com.merpyzf.kangyuanmilk.ui.login.presenter;

import com.merpyzf.kangyuanmilk.utils.ErrorHandle;
import com.merpyzf.kangyuanmilk.common.data.Common;
import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.login.RegisterActivity;
import com.merpyzf.kangyuanmilk.ui.login.bean.RegisterBean;
import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.login.contract.IRegisterContract;
import com.merpyzf.kangyuanmilk.ui.login.model.IRegisterModel;
import com.merpyzf.kangyuanmilk.ui.login.model.RegisterModelImpl;
import com.merpyzf.kangyuanmilk.utils.ErrorHandleHelper;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by  on 2017-07-25.
 */

public class RegisterPresenterImpl extends BasePresenter<IRegisterContract.IRegisterView> implements IRegisterContract.IRegisterPresenter {

    private IRegisterModel mRegisterModel;

    public RegisterPresenterImpl() {

        mRegisterModel = new RegisterModelImpl();

    }

    /**
     * 注册
     *
     * @param context Activity
     * @param user    user对象
     */
    @Override
    public void register(RegisterActivity context, User user) {

        mMvpView.showLoadingDialog();

        mRegisterModel.Register(user)

                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegisterBean registerBean) {

                        if (registerBean.getStatus() == Common.HTTP_OK) {

                            if (registerBean.getResponse().isResult()) {

                                mMvpView.registerSuccess("注册成功,跳转到登录页面");
                            } else {

                                mMvpView.showErrorMsg("注册失败");
                            }
                        } else {
                            mMvpView.showErrorMsg("服务器异常");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mMvpView.showErrorMsg("连接超时");
                    }

                    @Override
                    public void onComplete() {
                        mMvpView.cancelLoadingDialog();
                    }
                });
    }

    /**
     * 检查注册的用户名是否重复
     *
     * @param context Activity
     * @param user    user对象
     */
    @Override
    public void checkUserRepeat(RegisterActivity context, User user) {


        mRegisterModel.checkUserRepeat(user)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegisterBean registerBean) {


                        new ErrorHandle(this, registerBean.getStatus()) {
                            @Override
                            protected void deal() {

                                //不重复
                                if (registerBean.getResponse().isResult()) {

                                    LogHelper.i("不重复");
                                    mMvpView.userRepeat(false);

                                    //重复
                                } else {

                                    LogHelper.i("重复");
                                    mMvpView.userRepeat(true);


                                }

                            }
                        };

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        ErrorHandleHelper.handle(e, mMvpView);
                    }

                    @Override
                    public void onComplete() {

                        mMvpView.cancelLoadingDialog();
                    }
                });

    }
}
