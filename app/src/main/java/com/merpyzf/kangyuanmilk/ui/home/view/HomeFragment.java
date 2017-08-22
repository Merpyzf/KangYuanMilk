package com.merpyzf.kangyuanmilk.ui.home.view;


import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.AdapterDiffCallback;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.BaseFragment;
import com.merpyzf.kangyuanmilk.ui.adapter.HomeAdapter;
import com.merpyzf.kangyuanmilk.ui.home.bean.HomeBean;
import com.merpyzf.kangyuanmilk.ui.home.contract.IHomeCantract;
import com.merpyzf.kangyuanmilk.ui.home.presenter.HomePresenterImpl;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页
 *
 * @author wangke
 */
public class HomeFragment extends BaseFragment implements IHomeCantract.IHomeView {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.pull_to_refresh)
    PullToRefreshView mPullToRefresh;
    private List<HomeBean.ResponseBean.ResultListBean> oldDataList = new ArrayList<>();


    private LinearLayoutManager mLayoutManager;
    private IHomeCantract.IHomePresenter mPresenter;
    private HomeAdapter mHomeAdapter;
    private MaterialDialog mLoginDialog;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initWidget(View rootview) {

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mHomeAdapter = new HomeAdapter(oldDataList, getContext(), mRecyclerView);
        mRecyclerView.setAdapter(mHomeAdapter);

    }


    @Override
    protected void initData() {

        mPresenter = new HomePresenterImpl(this);
        mPresenter.attachView(this);
        mPresenter.getHomePageData(false);

    }

    @Override
    protected void initEvent() {
        //刷新事件
        mPullToRefresh.setOnRefreshListener(() -> {

            mPresenter.getHomePageData(true);

        });
    }


    /**
     * 等待的dialog弹窗
     */
    @Override
    public void showLoadingDialog() {

        mLoginDialog = new MaterialDialog.Builder(getActivity())
                .title("加载中")
                .content("Please wait...")
                .progress(true, 0)
                .canceledOnTouchOutside(false)
                .show();


    }

    /**
     * 隐藏dialog
     */
    @Override
    public void cancelLoadingDialog() {

        if (mLoginDialog != null) {
            mLoginDialog.dismiss();
        }
    }

    /**
     * 显示错误信息
     *
     * @param errorMsg
     */
    @Override
    public void showErrorMsg(String errorMsg) {

        cancelLoadingDialog();
        App.showToast(errorMsg);

    }

    /**
     * 显示主页面
     *
     * @param response
     */
    @Override
    public void showHomePage(HomeBean.ResponseBean response) {

        //初次加载
        if (oldDataList.size() == 0) {
            oldDataList.addAll(response.getResultList());
            mLayoutManager.scrollToPosition(0);
            mHomeAdapter.notifyDataSetChanged();
        } else {
            //刷新

            AdapterDiffCallback<HomeBean.ResponseBean.ResultListBean> diffCallback = new AdapterDiffCallback<>(oldDataList, response.getResultList());
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback,true);
            mHomeAdapter.setDatas(response.getResultList());
            diffResult.dispatchUpdatesTo(mHomeAdapter);
            mPullToRefresh.setRefreshing(false);

        }

    }

    /**
     * Fragemnt的View销毁时回调
     */
    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

}
