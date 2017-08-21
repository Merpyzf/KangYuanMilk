package com.merpyzf.kangyuanmilk.ui.home.view;

import android.support.v4.widget.SwipeRefreshLayout;
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
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.common.bean.PageBean;
import com.merpyzf.kangyuanmilk.common.widget.RecyclerAdapter;
import com.merpyzf.kangyuanmilk.common.widget.ViewHolder;
import com.merpyzf.kangyuanmilk.ui.adapter.SearchGoodsAdapter;
import com.merpyzf.kangyuanmilk.ui.adapter.SearchHistoryAdapter;
import com.merpyzf.kangyuanmilk.ui.home.bean.Goods;
import com.merpyzf.kangyuanmilk.ui.home.bean.QueryKey;
import com.merpyzf.kangyuanmilk.ui.home.contract.ISearchContract;
import com.merpyzf.kangyuanmilk.ui.home.model.SearchHistoryBean;
import com.merpyzf.kangyuanmilk.ui.home.presenter.SearchPresenterImpl;
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

/*    @BindView(R.id.tipView)
    TipView mTipView;*/

    //显示搜索结果
    @BindView(R.id.xRecycler)
    RecyclerView mXRecyclerView;

    private ISearchContract.ISearchPresenter mPresenter;
    private SearchHistoryAdapter mSearchHistoryAdapter;

    private List<Goods> mGoodsList = new ArrayList<>();
    private SearchGoodsAdapter mSearchGoodsAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initWidget() {

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        mTipView.bindView(mSwipeRefresh);

        mCardView.post(() -> SearchViewHelper.excuteAnimator(mCardView));

        //显示搜索历史的RecyclerView
        mRvSearchHistory.setLayoutManager(new LinearLayoutManager(this));


        //显示搜索结果的RecyclerView
        mXRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mSearchGoodsAdapter = new SearchGoodsAdapter(mGoodsList, this, mXRecyclerView);
//        mXRecyclerView.setPullRefreshEnabled(true);
        mXRecyclerView.setAdapter(mSearchGoodsAdapter);


    }

    @Override
    protected void initData() {
        super.initData();

        mPresenter = new SearchPresenterImpl(this);
        mPresenter.attachView(this);
        //获取历史关键字查询的数据
        mPresenter.getSearchHistoryData();

    }


    @Override
    public void initEvent() {
        mIvClose.setOnClickListener(this);
        mIvOpen.setOnClickListener(this);
        mTvSearch.setOnClickListener(this);

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


                PageBean pageBean = mSearchGoodsAdapter.getPageBean();
                //每次查询的时候将查询的页置为0
                pageBean.setPage(0);
                int loadPage = pageBean.getLoadPage();
                QueryKey queryKey = new QueryKey(key, loadPage, pageBean.getNum());
                //搜索关键字
                mPresenter.searchGoodsKey(queryKey);


                SearchViewHelper.excuteAnimator(mCardView);

                mPresenter.saveSearchData(new SearchHistoryBean(key));


                break;

        }


    }


    @Override
    public void showSearchHistory(List<SearchHistoryBean> searchHistoryBeanList) {

        mSearchHistoryAdapter = new SearchHistoryAdapter(mPresenter, searchHistoryBeanList, this, mRvSearchHistory);
        mRvSearchHistory.setAdapter(mSearchHistoryAdapter);
        mSearchHistoryAdapter.setOnItemClickListener(this);


    }

    @Override
    public void searchGoodsDataList(List<Goods> dataList) {

        mSearchGoodsAdapter.addDatas(dataList);
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();

                break;

        }

        return true;
    }

    @Override
    public void onItemClick(ViewHolder viewHolder, SearchHistoryBean searchHistoryBean, int position) {

        mEdtSearch.setText(searchHistoryBean.getSearchInfo());

    }

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
