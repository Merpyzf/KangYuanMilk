package com.merpyzf.kangyuanmilk.ui.home.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.AdapterDiffCallback;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.common.bean.PageBean;
import com.merpyzf.kangyuanmilk.common.widget.RecyclerAdapter;
import com.merpyzf.kangyuanmilk.common.widget.StaggeredGridLayoutManagerWrapper;
import com.merpyzf.kangyuanmilk.common.widget.TipView;
import com.merpyzf.kangyuanmilk.common.widget.ViewHolder;
import com.merpyzf.kangyuanmilk.ui.adapter.SearchGoodsAdapter;
import com.merpyzf.kangyuanmilk.ui.adapter.SearchHistoryAdapter;
import com.merpyzf.kangyuanmilk.ui.home.bean.Goods;
import com.merpyzf.kangyuanmilk.ui.home.bean.QueryKey;
import com.merpyzf.kangyuanmilk.ui.home.contract.ISearchContract;
import com.merpyzf.kangyuanmilk.ui.home.model.SearchHistoryBean;
import com.merpyzf.kangyuanmilk.ui.home.presenter.SearchPresenterImpl;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.ui.SearchViewHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity implements ISearchContract.ISearchView, View.OnClickListener,
        RecyclerAdapter.ItemClickListener<SearchHistoryBean> {

    @BindView(R.id.iv_close)
    ImageView mIvClose; //关闭搜索框

    @BindView(R.id.iv_open)
    ImageView mIvOpen; //开启搜索框

    @BindView(R.id.tv_search)
    TextView mTvSearch; //搜索按钮

    @BindView(R.id.edt_search)
    EditText mEdtSearch; //搜索框

    @BindView(R.id.cardView)
    CardView mCardView;

    @BindView(R.id.rv_search_history)
    RecyclerView mRvSearchHistory;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;

    @BindView(R.id.tipView)
    TipView mTipView;

    //显示搜索结果
    @BindView(R.id.xRecycler)
    RecyclerView mGoodsRecyclerView;

    private ISearchContract.ISearchPresenter mPresenter;
    private SearchHistoryAdapter mSearchHistoryAdapter;

    private List<Goods> mOldGoodsList = new ArrayList<>();

    private SearchGoodsAdapter mSearchGoodsAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initWidget() {

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTipView.bindView(mSwipeRefresh);

        mCardView.post(() -> SearchViewHelper.excuteAnimator(mCardView));

        //显示搜索历史的RecyclerView
        mRvSearchHistory.setLayoutManager(new LinearLayoutManager(this));

        //显示搜索结果的RecyclerView
        mGoodsRecyclerView.setLayoutManager(new StaggeredGridLayoutManagerWrapper(2, StaggeredGridLayoutManager.VERTICAL));
        mSearchGoodsAdapter = new SearchGoodsAdapter(mOldGoodsList, this, mGoodsRecyclerView);
        mGoodsRecyclerView.setAdapter(mSearchGoodsAdapter);


    }

    @Override
    protected void initData() {
        super.initData();

        mPresenter = new SearchPresenterImpl(this,mTipView);
        mPresenter.attachView(this);
        //获取历史关键字查询的数据
        mPresenter.getSearchHistoryData();

    }


    @Override
    public void initEvent() {
        mIvClose.setOnClickListener(this);
        mIvOpen.setOnClickListener(this);
        mTvSearch.setOnClickListener(this);


        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                PageBean pageBean = mSearchGoodsAdapter.getPageBean();
                QueryKey key = new QueryKey();

                key.setKey(pageBean.getRemark());
                key.setNum(pageBean.getNum());
                key.setPage(pageBean.getPage());
                mPresenter.searchGoodsKey(key);

            }
        });

        mSearchGoodsAdapter.setOnItemClickListener(new RecyclerAdapter.ItemClickListener<Goods>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, Goods goods, int position) {
                // TODO: 2017-08-22 点击跳转到商品详情页面 +
                LogHelper.i("商品名:"+goods.getId());

            }

            @Override
            public boolean onItemLongClick(ViewHolder viewHolder, Goods goods, int position) {
                return false;
            }
        });
    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.iv_close:

            case R.id.iv_open:
                mEdtSearch.setText("");
                SearchViewHelper.excuteAnimator(mCardView);
                mPresenter.getSearchHistoryData();

                break;

            case R.id.tv_search:

                //获取历史关键字查询的数据
                mPresenter.getSearchHistoryData();

                String key = mEdtSearch.getText().toString().trim();

                if (TextUtils.isEmpty(key)) {

                    App.showToast(getString(R.string.toast_keyword_empty));
                    return;
                }

                if (mOldGoodsList.size() > 0) {
                    mOldGoodsList.clear();
                    mSearchGoodsAdapter.notifyDataSetChanged();
                }

                PageBean pageBean = mSearchGoodsAdapter.getPageBean();
                //每次查询的时候将查询的页置为0
                pageBean.setPage(0);
                int loadPage = pageBean.getLoadPage();
                //搜索的数据比较少，此处不添加分页
                pageBean.setNum(100);
                pageBean.setRemark(key);


                QueryKey queryKey = new QueryKey(key, loadPage, pageBean.getNum());
                //搜索关键字
                mPresenter.searchGoodsKey(queryKey);

                SearchViewHelper.excuteAnimator(mCardView);

                mPresenter.saveSearchData(new SearchHistoryBean(key));

                break;

        }


    }

    /**
     * 显示查询的历史记录
     * @param searchHistoryBeanList
     */
    @Override
    public void showSearchHistory(List<SearchHistoryBean> searchHistoryBeanList) {

        mSearchHistoryAdapter = new SearchHistoryAdapter(mPresenter, searchHistoryBeanList, this, mRvSearchHistory);
        mRvSearchHistory.setAdapter(mSearchHistoryAdapter);
        mSearchHistoryAdapter.setOnItemClickListener(this);

    }

    /**
     * 商品查询结果数据返回
     * @param dataList
     */
    @Override
    public void searchGoodsDataList(List<Goods> dataList) {

        //第一次搜索
        if (mOldGoodsList.size() == 0) {
            mOldGoodsList.addAll(dataList);
            mSearchGoodsAdapter.notifyDataSetChanged();

        } else {

            //处于刷新状态
            AdapterDiffCallback<Goods> diffCallback = new AdapterDiffCallback<>(mOldGoodsList, dataList);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback,true);
            diffResult.dispatchUpdatesTo(mSearchGoodsAdapter);
            mSearchGoodsAdapter.setDatas(dataList);
            mSwipeRefresh.setRefreshing(false);
        }


    }




    @Override
    public void showLoadingDialog() {
        //加载中
        mSwipeRefresh.setRefreshing(true);

    }

    @Override
    public void cancelLoadingDialog() {

        mSwipeRefresh.setRefreshing(false);

    }

    @Override
    public void showErrorMsg(String errorMsg) {

        cancelLoadingDialog();
        App.showToast(errorMsg);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();

                break;

        }

        return true;
    }

    /**
     * 搜索历史RecyclerView 的item的点击事件
     *
     * @param viewHolder
     * @param searchHistoryBean
     * @param position
     */
    @Override
    public void onItemClick(ViewHolder viewHolder, SearchHistoryBean searchHistoryBean, int position) {

        mEdtSearch.setText(searchHistoryBean.getSearchInfo());

    }

    /**
     * 搜索历史RecyclerView 的item的长按事件
     *
     * @param viewHolder
     * @param searchHistoryBean
     * @param position
     * @return
     */
    @Override
    public boolean onItemLongClick(ViewHolder viewHolder, SearchHistoryBean searchHistoryBean, int position) {


        return false;
    }


    @Override
    protected void onDestroy() {

        mPresenter.detachView();
        super.onDestroy();

    }


}
