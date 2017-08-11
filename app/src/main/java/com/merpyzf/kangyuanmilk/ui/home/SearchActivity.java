package com.merpyzf.kangyuanmilk.ui.home;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.merpyzf.kangyuanmilk.common.widget.RecyclerAdapter;
import com.merpyzf.kangyuanmilk.common.widget.ViewHolder;
import com.merpyzf.kangyuanmilk.ui.adapter.SearchHistoryAdapter;
import com.merpyzf.kangyuanmilk.ui.home.contract.ISearchContract;
import com.merpyzf.kangyuanmilk.ui.home.model.SearchBean;
import com.merpyzf.kangyuanmilk.ui.home.presenter.SearchPresenterImpl;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.ui.SearchViewHelper;

import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity implements ISearchContract.ISearchView, View.OnClickListener,
        RecyclerAdapter.ItemClickListener<SearchBean> {

    @BindView(R.id.iv_close)
    ImageView iv_close; //关闭搜索框

    @BindView(R.id.iv_open)
    ImageView iv_open; //开启搜索框


    @BindView(R.id.tv_search)
    TextView tv_search; //搜索按钮

    @BindView(R.id.edt_search)
    EditText edt_search; //搜索框

    @BindView(R.id.cardView)
    CardView cardView;

    @BindView(R.id.rv_search_history)
    RecyclerView rv_search_history;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private ISearchContract.ISearchPresenter mPresenter;
    private SearchHistoryAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initWidget() {

        cardView.post(() -> SearchViewHelper.excuteAnimator(cardView));
        rv_search_history.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void initData() {
        super.initData();

        mPresenter = new SearchPresenterImpl();
        mPresenter.attachView(this);
        //获取历史关键字查询的数据
        mPresenter.getSearchHistoryData();

    }


    @Override
    public void initEvent() {
        iv_close.setOnClickListener(this);
        iv_open.setOnClickListener(this);
        tv_search.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.iv_close:

            case R.id.iv_open:
                edt_search.setText("");
                SearchViewHelper.excuteAnimator(cardView);
                mPresenter.getSearchHistoryData();

                break;

            case R.id.tv_search:

                //获取历史关键字查询的数据
                mPresenter.getSearchHistoryData();

                String info = edt_search.getText().toString().trim();

                if (TextUtils.isEmpty(info)) {

                    App.showToast("关键字不能为空");
                    return;
                }

                //搜索关键字
                mPresenter.searchMilkListData(info);

                SearchViewHelper.excuteAnimator(cardView);

                mPresenter.saveSearchData(new SearchBean(info));


                break;

        }


    }


    @Override
    public void showSearchHistory(List<SearchBean> searchBeanList) {

        LogHelper.i("changdu ==>" + searchBeanList.size());

        mAdapter = new SearchHistoryAdapter(mPresenter, searchBeanList, this, rv_search_history);
        rv_search_history.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);


    }

    @Override
    public void showMilkDataList() {

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
    protected void onDestroy() {

        mPresenter.detachView();
        super.onDestroy();

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
    public void onItemClick(ViewHolder viewHolder, SearchBean searchBean, int position) {

        LogHelper.i("====>" + searchBean.getSearchInfo());
        edt_search.setText(searchBean.getSearchInfo());

    }

    @Override
    public boolean onItemLongClick(ViewHolder viewHolder, SearchBean searchBean, int position) {


        LogHelper.i("====>onItemLongClick");
        return false;
    }
}
