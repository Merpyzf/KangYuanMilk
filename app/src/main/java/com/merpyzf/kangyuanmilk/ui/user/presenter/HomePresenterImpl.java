package com.merpyzf.kangyuanmilk.ui.user.presenter;

import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.login.bean.LoginBean;
import com.merpyzf.kangyuanmilk.ui.login.model.LoginModelImpl;
import com.merpyzf.kangyuanmilk.ui.user.HomeActivity;
import com.merpyzf.kangyuanmilk.ui.user.contract.IHomeContract;
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
 * Created by Administrator on 2017-07-29.
 */

public class HomePresenterImpl extends BasePresenter<IHomeContract.IHomeView> implements IHomeContract.IHomePresenter {

    private LoginModelImpl mModel = null;
    public HomePresenterImpl() {

        mModel = new LoginModelImpl();

    }
    /**
     *  首先请求网络检查用户当前的状态是否合法
     *
     *  => 合法 : 将用户的信息存储在数据库中 => 调用观察者的update方法并更新用户的数据
     *  => 不合法 : 提示用户用户名和密码验证错误,清空数据库中的信息 => 跳转到登录界面进行重新登录
     *
     **/
    @Override
    public void checkUserCurrentStatus(HomeActivity context) {

        //1.首先判断用户是否已经处于登录状态

        SharedPreHelper.LoginInfo loginInfo = SharedPreHelper.getLoginInfo();
        if(loginInfo == null){
            mMvpView.currentStatus(false);
            return;
        }

        String userName = loginInfo.getUserName();
        //这里还没有进行md5加密
        String pwd = loginInfo.getPassword();
        //处于未登录状态
        if(userName.equals("null") || userName.equals("null")){
           mMvpView.currentStatus(false);
        }else {


            mModel.login(userName, HashHelper.getMD5String(pwd))
                    .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                    .subscribe(new Observer<LoginBean>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }
                        @Override
                        public void onNext(@NonNull LoginBean loginBean) {

                            new ErrorHandle(this, loginBean.getStatus()) {
                                // 200
                                @Override
                                protected void deal() {
                                    boolean result = loginBean.getResponse().isResult();
                                    //登录成功
                                    if (result){
                                        mMvpView.currentStatus(true);
                                        //进行数据库的更新以及UI的更新
                                        User user = loginBean.getResponse().getUser();

                                        LogHelper.i("更新前的头像==>"+user.getUser_head());

                                        UserDao.getInstance(App.getContext())
                                                .updateUser(user);
                                        LogHelper.i("更新后的头像==>"+UserDao.getInstance(App.getContext()).getUserInfo().getUser_head());
                                        mMvpView.updateUserInfo();
                                    }else {
                                        //处于异常状态
                                        mMvpView.authFailed();

                                    }

                                }
                            };


                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            ErrorHandleHelper.handle(e,mMvpView);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }


    }
}
