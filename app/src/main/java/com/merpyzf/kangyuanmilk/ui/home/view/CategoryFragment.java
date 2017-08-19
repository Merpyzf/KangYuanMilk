package com.merpyzf.kangyuanmilk.ui.home.view;


import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.ui.ItemMarginDecoration;

import butterknife.BindView;

/**
 * 商品分类
 *
 * @author wangke
 */
public class CategoryFragment extends BaseFragment implements ICategoryContract.ICategoryView, View.OnClickListener {
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

    public CategoryFragment() {


    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_category;
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

                LogHelper.i("刷新到 ==>" + (++page));
                mPresenter.getMeiziData(CategoryFragment.this, page + "");

            }
        });

    }

    @Override
    protected void initData() {


        mPresenter = new CategoryPresenter();
        mPresenter.attachView(this);

        if (page == 1) {
            mPresenter.getMeiziData(this, "1");

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
    public void getMeiziData(Meizi meizi) {

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
     * 将RecyclerView中的item移动到指定的位置
     *
     * @param position     要移动的位置
     * @param recyclerView recyclerview
     */
    private void moveToPosition(int position, RecyclerView recyclerView) {

        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();

        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int[] firstItem = manager.findFirstVisibleItemPositions(new int[]{0, 2});
        int[] lastItem = manager.findLastVisibleItemPositions(new int[]{mAdapter.getItemCount() - 2, mAdapter.getItemCount() - 1});
        //然后区分情况
        if (position <= firstItem[0]) {

            //当要置顶的项在当前显示的第一个项的前面时
            recyclerView.scrollToPosition(position);
        } else if (position <= lastItem[0]) {

            //当要置顶的项已经在屏幕上显示时
            int top = recyclerView.getChildAt(position - firstItem[0]).getTop();
            recyclerView.scrollBy(0, top);
        } else {

            //当要置顶的项在当前显示的最后一项的后面时
            recyclerView.scrollToPosition(position);
            //这里这个变量是用在RecyclerView滚动监听里面的
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
