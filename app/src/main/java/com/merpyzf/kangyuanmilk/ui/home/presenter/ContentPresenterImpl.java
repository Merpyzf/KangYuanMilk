package com.merpyzf.kangyuanmilk.ui.home.presenter;

import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.home.bean.Content;
import com.merpyzf.kangyuanmilk.ui.home.bean.ContentBean;
import com.merpyzf.kangyuanmilk.ui.home.contract.IContentContract;
import com.merpyzf.kangyuanmilk.ui.home.model.ContentModelImpl;
import com.merpyzf.kangyuanmilk.ui.home.model.IContentModel;
import com.merpyzf.kangyuanmilk.ui.home.view.ContentActivity;
import com.merpyzf.kangyuanmilk.utils.ErrorHandle;
import com.merpyzf.kangyuanmilk.utils.ErrorHandleHelper;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by 春水碧于天 on 2017/9/11.
 */

public class ContentPresenterImpl extends BasePresenter<IContentContract.IContentView> implements IContentContract.IContentPresenter {

    private ContentActivity mContext;

    private IContentModel mModel;


    public ContentPresenterImpl(ContentActivity mContext) {
        this.mContext = mContext;
        mModel = new ContentModelImpl();
    }

    @Override
    public void getActivityContent(int activityId) {


        mModel.getActivityContentById(activityId)
                .compose(mContext.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<ContentBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ContentBean activityBean) {


                        new ErrorHandle(this, activityBean.getStatus()) {
                            @Override
                            protected void deal() {


                                LogHelper.i("");

                                Content content = activityBean.getResponse().getActivity();

                                mMvpView.showAppbarContent(content.getActivity_title(), content.getActivity_image());

                                mMvpView.showWebContent(content.getActivity_content());





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
}
