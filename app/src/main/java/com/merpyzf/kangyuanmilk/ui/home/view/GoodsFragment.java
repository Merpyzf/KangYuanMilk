package com.merpyzf.kangyuanmilk.ui.home.view;


import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseFragment;
import com.merpyzf.kangyuanmilk.common.widget.MyStaggeredGridLayoutManager;
import com.merpyzf.kangyuanmilk.ui.adapter.CategoryAdapter;
import com.merpyzf.kangyuanmilk.ui.home.bean.Meizi;
import com.merpyzf.kangyuanmilk.ui.home.contract.ICategoryContract;
import com.merpyzf.kangyuanmilk.ui.home.presenter.CategoryPresenter;
import com.merpyzf.kangyuanmilk.utils.ui.ItemMarginDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 商品分类
 *
 * @author wangke
 */
public class GoodsFragment extends BaseFragment implements ICategoryContract.ICategoryView, View.OnClickListener {
    @BindView(R.id.recyclerView)
    XRecyclerView mRecyclerView;
    @BindView(R.id.fab_top)
    FloatingActionButton mFabMoveTop;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;

    private ICategoryContract.ICategoryPresenter mPresenter;
    private int page = 1;
    private CategoryAdapter mAdapter;
    private ItemMarginDecoration mDecoration;
    private boolean isFirst = true;
    private MyStaggeredGridLayoutManager mLayoutManager;
    private BottomSheetBehavior<View> mBehavior;
    //存放旧的商品列表
    private List<Meizi.ResultsBean> mOldGoodsList = new ArrayList<>();


    public GoodsFragment() {


    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_goods;
    }

    @Override
    protected void initWidget(View rootview) {


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        mLayoutManager = new MyStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //禁用默认的刷新
        mRecyclerView.setPullRefreshEnabled(false);
        mDecoration = new ItemMarginDecoration(3, 1);
        mRecyclerView.addItemDecoration(mDecoration);

        mSwipeRefresh.setColorSchemeColors(new int[]{getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.pink_a100)});
        mSwipeRefresh.setEnabled(true);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //刷新
                mPresenter.getMeiziData(GoodsFragment.this, "1", true);


            }
        });


    }

    @Override
    protected void initEvent() {


        mFabMoveTop.setOnClickListener(this);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

                //分页加载数据
                page++;
                mPresenter.getMeiziData(GoodsFragment.this, page + "", false);


            }
        });

    }

    @Override
    protected void initData() {


        mPresenter = new CategoryPresenter();
        mPresenter.attachView(this);

        if (page == 1) {
            mPresenter.getMeiziData(this, "1", false);

        }
    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void cancelLoadingDialog() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }

    @Override
    public void getMeiziData(Meizi meizi, boolean isRefresh) {

        if (isRefresh) {

            if (mAdapter != null) {
                mAdapter.clearData();
                mAdapter.addDatas(meizi.getResults());
                mSwipeRefresh.setRefreshing(false);
                page = 1;
                return;
            }
        }

        if (page == 1) {

            if (isFirst) {

                mAdapter = new CategoryAdapter(meizi.getResults(), getActivity(), mRecyclerView);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                isFirst = false;
            }

        } else {

            mRecyclerView.loadMoreComplete();
            mAdapter.addDatas(meizi.getResults());

        }


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

        if (id == 0) {

            mSwipeRefresh.setRefreshing(true);
        } else {

            mSwipeRefresh.setRefreshing(false);
        }

    }


    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        mRecyclerView.removeItemDecoration(mDecoration);
        super.onDestroyView();
    }


}
