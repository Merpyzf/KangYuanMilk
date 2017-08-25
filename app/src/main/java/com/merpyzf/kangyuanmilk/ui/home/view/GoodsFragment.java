package com.merpyzf.kangyuanmilk.ui.home.view;


import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.BaseFragment;
import com.merpyzf.kangyuanmilk.common.bean.PageBean;
import com.merpyzf.kangyuanmilk.common.widget.MyStaggeredGridLayoutManager;
import com.merpyzf.kangyuanmilk.common.widget.TipView;
import com.merpyzf.kangyuanmilk.ui.adapter.GoodsAdapter;
import com.merpyzf.kangyuanmilk.ui.home.bean.Goods;
import com.merpyzf.kangyuanmilk.ui.home.contract.IGoodsContract;
import com.merpyzf.kangyuanmilk.ui.home.presenter.GoodsPresenterImpl;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.SharedPreHelper;
import com.merpyzf.kangyuanmilk.utils.ui.ItemMarginDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 商品分类
 *
 * @author wangke
 */
public class GoodsFragment extends BaseFragment implements IGoodsContract.IGoodsView, View.OnClickListener {
    @BindView(R.id.recyclerView)
    XRecyclerView mRecyclerView;
    @BindView(R.id.fab_top)
    FloatingActionButton mFabMoveTop;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.coordLayout)
    CoordinatorLayout mRootView;

    @BindView(R.id.tipView)
    TipView mTipView;


    private IGoodsContract.IGoodsPresenter mPresenter;
    private int page = 1;
    private GoodsAdapter mAdapter;
    private ItemMarginDecoration mDecoration;
    private boolean isFirst = true;
    private MyStaggeredGridLayoutManager mLayoutManager;
    //存放旧的商品列表
    private List<Goods> mGoodsList = new ArrayList<>();
    private PageBean mPageBean;


    public GoodsFragment() {

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_goods;
    }

    @Override
    protected void initWidget(View rootview) {

        //标记数据是否第一次加载
        isFirst = true;
        mTipView.bindView(mRootView);

        //由于Fragment每次切换的时候数据不会被清楚，因此需要手动清除，避免加载重复数据
        if (mGoodsList.size() > 0) {
            mGoodsList.clear();
        }

        //RecyclerView的初始化
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mLayoutManager = new MyStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setPullRefreshEnabled(false);
        mDecoration = new ItemMarginDecoration(3, 1);
        mRecyclerView.addItemDecoration(mDecoration);

        //设置SwipeRefresh在刷新时候变换的颜色
        mSwipeRefresh.setColorSchemeColors(new int[]{getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.pink_a100)});
        mSwipeRefresh.setEnabled(true);

    }

    @Override
    protected void initEvent() {

        //返回最顶部
        mFabMoveTop.setOnClickListener(this);
        //刷新
        mSwipeRefresh.setOnRefreshListener(() -> {
            //当设置为刷新状态时候此时的page将设置为1
            mPageBean.setRefresh(true);
            mPresenter.getGoodsData(GoodsFragment.this, mTipView, mPageBean.getRemark(), String.valueOf(mPageBean.getLoadPage()), String.valueOf(mPageBean.getNum()));
        });


        //RecyclerView分页加载触发的监听
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

                //分页加载数据
                mPresenter.getGoodsData(GoodsFragment.this, mTipView, mPageBean.getRemark(), String.valueOf(mPageBean.getLoadPage()), String.valueOf(mPageBean.getNum()));

            }
        });

    }

    @Override
    protected void initData() {

        mAdapter = new GoodsAdapter(mGoodsList, getActivity(), mRecyclerView);

        mPageBean = mAdapter.getPageBean();
        //获取上一次选择的商品分类id
        mPageBean.setRemark(String.valueOf(SharedPreHelper.getOldChoiceCategory()));
        mPageBean.setPage(1);

        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new GoodsPresenterImpl();
        mPresenter.attachView(this);
        //首次进入时进行数据的获取
        mPresenter.getGoodsData(this, mTipView, mPageBean.getRemark(), String.valueOf(mPageBean.getPage()), String.valueOf(mPageBean.getNum()));


    }

    @Override
    public void showLoadingDialog() {

        mSwipeRefresh.setRefreshing(true);

    }

    @Override
    public void cancelLoadingDialog() {

        if (mSwipeRefresh.isRefreshing()) {

            mSwipeRefresh.setRefreshing(false);
        }

    }

    @Override
    public void showErrorMsg(String errorMsg) {

        cancelLoadingDialog();
        App.showToast(errorMsg);


    }

    @Override
    public void getGoodsData(List<Goods> goodsList) {
        //下拉刷新时的数据加载
        if (mPageBean.isRefresh()) {

            if (mAdapter != null) {
                mAdapter.clearData();
                mAdapter.addDatas(goodsList);
                //重置刷新的状态
                mPageBean.setRefresh(false);
                mSwipeRefresh.setRefreshing(mPageBean.isRefresh());

                return;
            }
        }

        //如果没有加载出数据
        if (goodsList.size() == 0) {
            mRecyclerView.loadMoreComplete();
            App.showToast("没有更多的数据加载");
            return;
        }


        if (mPageBean.getPage() == 1) {
            if (isFirst) {
                mAdapter.addDatas(goodsList);
                isFirst = false;
            }
        } else {

            mAdapter.addDatas(goodsList);
            mRecyclerView.loadMoreComplete();
        }
    }

    @Override
    public void showEmpty() {

        //表示第一次加载的时候数据就为空，不包括刷新
        if (mAdapter.getPageBean().getPage() == 1) {

            mTipView.setEmptyTip("空空如也");

        }
    }

    @Override
    public void showNetError() {

        mTipView.setErrorTip("网络异常");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.fab_top:


                mRecyclerView.smoothScrollToPosition(0);

                break;
        }
    }

    /**
     * 重置swipeRefresh刷新,解决因为swipeRefresh处于显示状态下无法detachView的问题
     */
    public void resetRefresh() {

        if (mSwipeRefresh != null) {

            if (mSwipeRefresh.isEnabled()) {


                if (mSwipeRefresh.isRefreshing()) {

                    mSwipeRefresh.setRefreshing(false);
                    mSwipeRefresh.setEnabled(false);
                }

            } else {

                mSwipeRefresh.setEnabled(true);
            }
        }


    }

    /**
     * 当前选中的CategoryId由最外面的HomeActivity进行调用
     *
     * @param id 分类id
     */
    public void currentCategoryId(int id) {

        mGoodsList.clear();
        mAdapter.notifyDataSetChanged();

        //这里的remark表示商品分类id
        mPageBean.setRemark(id + "");
        isFirst = true;
        mPageBean.reset();
        mPresenter.getGoodsData(this, mTipView, mPageBean.getRemark(), String.valueOf(mPageBean.getPage()), String.valueOf(mPageBean.getNum()));

    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        mRecyclerView.removeItemDecoration(mDecoration);
        super.onDestroyView();
    }
}
