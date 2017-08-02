package com.merpyzf.kangyuanmilk.ui.user.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.user.UserInfoActivity;
import com.merpyzf.kangyuanmilk.ui.user.bean.MessageBean;
import com.merpyzf.kangyuanmilk.ui.user.contract.IUserInfoContract;
import com.merpyzf.kangyuanmilk.ui.user.model.IUserInfoModel;
import com.merpyzf.kangyuanmilk.ui.user.model.UserInfoModelImpl;
import com.merpyzf.kangyuanmilk.utils.ErrorHandle;
import com.merpyzf.kangyuanmilk.utils.ErrorHandleHelper;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.qiniu.UpLoadHelper;
import com.qiniu.android.http.ResponseInfo;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by wangke on 2017-07-27.
 */

public class UserInfoPresenterImpl extends BasePresenter<IUserInfoContract.IUserInfoView> implements IUserInfoContract.IUserInfoPresenter {


    private IUserInfoModel mModel;

    public UserInfoPresenterImpl() {

        mModel = new UserInfoModelImpl();

    }

    /**
     * 上传头像
     *
     * @param imagePath 要上传的文件的路径
     */
    @Override
    public void upLoadAvater(UserInfoActivity context, String imagePath) {

        UpLoadHelper upLoadHelper = new UpLoadHelper();

        //上传结果的回调
        upLoadHelper.upLoadAveter(imagePath, new UpLoadHelper.CompletionListener() {
            @Override
            public void onComplete(String key, ResponseInfo info, JSONObject response) {
                LogHelper.i("onComplete==>所在线程==>" + Thread.currentThread().getName());
                LogHelper.i("key==>" + key);
                LogHelper.i("上传结果==>" + info.toString());
                if (!info.isOK()) {
                    //上传失败
                    mMvpView.uploadFailed();

                } else {

                    //上传成功
                    saveAvater(context, key);
                }
            }

            //监听上传进度
            @Override
            public void uploadProgress(double percent) {
                if (mMvpView == null) return;
                mMvpView.currentUpLoadImageProgress((float) percent);
                LogHelper.i("uploadProgress==>所在线程==>" + Thread.currentThread().getName());
            }
        });
    }


    /**
     * 保存头像
     *
     * @param fileName 文件名 /avater/finemame
     */
    @Override
    public void saveAvater(UserInfoActivity context, String fileName) {

        //先上传到服务器，服务器上传成功之后然后再保存到本地数据库
        User user = mModel.getUserInfo();
        user.setUser_head(fileName);

        mModel.uploadAvater(user)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<MessageBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MessageBean uploadAvaterBean) {

                        //根据返回的状态码进行异常的处理
                        new ErrorHandle(this, uploadAvaterBean.getStatus()) {
                            //状态码 == 200
                            @Override
                            protected void deal() {

                                //获取七牛云的图片外链
                                String outChain = uploadAvaterBean.getResponse().getMessage();

                                LogHelper.i("outChain==>" + outChain + fileName);
                                //更新数据库中的用户信息
                                user.setUser_head(outChain + fileName);


                                mModel.updateUserInfo(user);

                                //表示上传成功

                                if (mMvpView == null) return;

                                mMvpView.upLoadSuccess(fileName);


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

    @Override
    public void getUserInfo() {

    }
}