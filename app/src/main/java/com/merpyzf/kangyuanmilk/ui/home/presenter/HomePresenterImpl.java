package com.merpyzf.kangyuanmilk.ui.home.presenter;

import com.google.gson.Gson;
import com.merpyzf.kangyuanmilk.ui.base.BasePresenter;
import com.merpyzf.kangyuanmilk.ui.home.bean.HomeBean;
import com.merpyzf.kangyuanmilk.ui.home.contract.IHomeCantract;
import com.merpyzf.kangyuanmilk.ui.home.model.HomeModelImpl;
import com.merpyzf.kangyuanmilk.ui.home.model.IHomeModel;
import com.merpyzf.kangyuanmilk.ui.home.view.HomeFragment;
import com.merpyzf.kangyuanmilk.utils.CacheHelper;
import com.merpyzf.kangyuanmilk.utils.ErrorHandle;
import com.merpyzf.kangyuanmilk.utils.ErrorHandleHelper;
import com.merpyzf.kangyuanmilk.utils.NetworkHelper;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangke on 2017-08-22.
 */

public class HomePresenterImpl extends BasePresenter<IHomeCantract.IHomeView> implements IHomeCantract.IHomePresenter {

    private IHomeModel mModel;
    private HomeFragment context;


    public HomePresenterImpl(HomeFragment context) {
        mModel = new HomeModelImpl();
        this.context = context;
    }


    @Override
    public void getHomePageData(boolean isRefresh) {


        //网络通畅时正常加载
        if (NetworkHelper.isAvailableByPing()) {

            //只有页面加载的时候才显示加载的dialog
            if (!isRefresh) {
                mMvpView.showLoadingDialog();
            }

            mModel.getHomePageData()
                    .compose(context.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<HomeBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(HomeBean homeBean) {

                            new ErrorHandle(this, homeBean.getStatus()) {

                                @Override
                                protected void deal() {

                                    mMvpView.showHomePage(homeBean.getResponse());
                                    CacheHelper.cacheHomePage(new Gson().toJson(homeBean));


                                }
                            };


                        }

                        @Override
                        public void onError(Throwable e) {

                            ErrorHandleHelper.handle(e, mMvpView);

                        }

                        @Override
                        public void onComplete() {

                            if (!isRefresh) {
                                mMvpView.cancelLoadingDialog();
                            }

                        }
                    });


        } else {
            //从缓存中进行加载

            String homePageCache = CacheHelper.getHomePageCache();

            if (!homePageCache.equals("no_cache")) {
                //有缓存时则加载本地缓存
                Gson gson = new Gson();
                HomeBean homeBean = gson.fromJson(homePageCache, HomeBean.class);
                mMvpView.showHomePage(homeBean.getResponse());

            }


        }


    }
}
