package com.merpyzf.kangyuanmilk.ui.user.presenter;

import com.merpyzf.kangyuanmilk.common.observer.UserInfoSubject;
import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.login.bean.RegisterBean;
import com.merpyzf.kangyuanmilk.ui.user.ModifyUserInfoActivity;
import com.merpyzf.kangyuanmilk.ui.user.bean.MessageBean;
import com.merpyzf.kangyuanmilk.ui.user.contract.IModifyInfoContract;
import com.merpyzf.kangyuanmilk.ui.user.model.IModifyInfoModel;
import com.merpyzf.kangyuanmilk.ui.user.model.IUserInfoModel;
import com.merpyzf.kangyuanmilk.ui.user.model.ModifyInfoModelImpl;
import com.merpyzf.kangyuanmilk.ui.user.model.UserInfoModelImpl;
import com.merpyzf.kangyuanmilk.utils.ErrorHandle;
import com.merpyzf.kangyuanmilk.utils.ErrorHandleHelper;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.db.dao.UserDao;
import com.merpyzf.kangyuanmilk.utils.http.RetrofitFactory;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangke on 2017-08-02.
 */

public class ModifyInfoPresenterImpl extends BasePresenter<IModifyInfoContract.IModifyInfoView> implements IModifyInfoContract.IModifyInfoPresenter {

    private IModifyInfoModel mModel = null;
    private ModifyUserInfoActivity mContext;

    public ModifyInfoPresenterImpl(ModifyUserInfoActivity context) {
        mModel = new ModifyInfoModelImpl();
        mContext = context;
    }

    @Override
    public void updateUserInfo(User user) {

        mMvpView.showLoadingDialog();
        mModel.updateUserInfo(user)
                .compose(mContext.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<MessageBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onNext(@NonNull MessageBean messageBean) {

                        new ErrorHandle(this, messageBean.getStatus()) {
                            @Override
                            protected void deal() {

                                boolean result = messageBean.getResponse().isResult();

                                if (result) {

                                    //更新本地用户的数据
                                    UserDao.getInstance().updateUser(user);
                                    mMvpView.updateSuccess("保存成功");


                                } else {

                                    mMvpView.showErrorMsg("保存失败");

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

    @Override
    public void getUserInfo() {

        mMvpView.showUserInfo(UserDao.getInstance().getUserInfo());

    }


}
