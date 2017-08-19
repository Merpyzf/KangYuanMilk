package com.merpyzf.kangyuanmilk.ui.login.presenter;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.login.RegisterActivity;
import com.merpyzf.kangyuanmilk.ui.login.bean.RegisterBean;
import com.merpyzf.kangyuanmilk.ui.login.contract.IRegisterContract;
import com.merpyzf.kangyuanmilk.ui.login.model.IRegisterModel;
import com.merpyzf.kangyuanmilk.ui.login.model.RegisterModelImpl;
import com.merpyzf.kangyuanmilk.ui.user.bean.User;
import com.merpyzf.kangyuanmilk.utils.ErrorHandle;
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


                        new ErrorHandle(this, registerBean.getStatus()) {


                            @Override
                            protected void deal() {

                                if (registerBean.getResponse().isResult()) {

                                    mMvpView.registerSuccess(context.getString(R.string.register_success));
                                } else {

                                    mMvpView.showErrorMsg(context.getString(R.string.register_failed));
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

                                    mMvpView.userRepeat(false);

                                    //重复
                                } else {
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
