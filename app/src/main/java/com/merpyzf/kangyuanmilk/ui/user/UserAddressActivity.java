package com.merpyzf.kangyuanmilk.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.AdapterDiffCallback;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.common.observer.Observer;
import com.merpyzf.kangyuanmilk.common.observer.UserInfoSubject;
import com.merpyzf.kangyuanmilk.common.widget.TipView;
import com.merpyzf.kangyuanmilk.ui.adapter.IUserAddressCallBack;
import com.merpyzf.kangyuanmilk.ui.adapter.UserAddressAdapter;
import com.merpyzf.kangyuanmilk.ui.user.bean.Address;
import com.merpyzf.kangyuanmilk.ui.user.contract.IUserAddressContract;
import com.merpyzf.kangyuanmilk.ui.user.presenter.UserAddressPresenterImpl;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.db.dao.UserDao;
import com.merpyzf.kangyuanmilk.utils.ui.ItemMarginDecoration;

import net.qiujuer.genius.res.Resource;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * 管理用户收货地址 2017-8-7
 *
 * @author wangke
 */
public class UserAddressActivity extends BaseActivity implements IUserAddressContract.IUserAddressView, IUserAddressCallBack.onItemWidgetClickListener, Observer {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_add_address)
    TextView mTvAddAddress;
    @BindView(R.id.tipView)
    TipView mTipView;
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;

    private List<Address> mOldList = null;
    private IUserAddressContract.IUserAddressPresenter mPresenter = null;
    private UserAddressAdapter mAdapter = null;

    public static void showAction(Context context) {

        context.startActivity(new Intent(context, UserAddressActivity.class));

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_address;
    }

    @Override
    public void initWidget() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new ItemMarginDecoration());
        mTipView.bindView(mLlContainer);
        //设置RecyclerView中item中widget点击事件的监听回调
        mTvAddAddress.setOnClickListener(view -> ModifyAddressActivity.showAction(this, new Bundle()));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.title_useraddress_activity_text);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRefreshLayout.setColorSchemeColors(new int[]{Resource.Color.GREEN, Resource.Color.PINK});


    }

    @Override
    protected void initData() {
        mPresenter = new UserAddressPresenterImpl();
        mPresenter.attachView(this);
        mPresenter.getUserAds(this, false);

        UserInfoSubject.getInstance().attach(UserAddressActivity.class.getName(), this);


    }

    @Override
    public void initEvent() {
        super.initEvent();

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                LogHelper.i("刷新中……");
                mPresenter.getUserAds(UserAddressActivity.this, true);


            }
        });

    }

    /**
     * 网路错误无法加载数据时的提示
     *
     * @param msg
     */
    @Override
    public void showNetErrorTip(String msg) {

        mTipView.setErrorTip(msg);

    }

    /**
     * 成功时的提示
     *
     * @param msg 提示信息
     */
    @Override
    public void showSuccess(String msg) {

        App.showToast(msg);
    }

    /**
     * 错误提示
     *
     * @param errorMsg
     */
    @Override
    public void showErrorMsg(String errorMsg) {

        App.showToast(errorMsg);
        mTipView.setErrorTip(errorMsg);

    }


    /**
     * 加载数据为空的时候的提示
     *
     * @param msg
     */
    @Override
    public void showEmptyTip(String msg) {


        mTipView.setEmptyTip(msg);

    }

    /**
     * 显示用户的所有地址
     *
     * @param addressList 获取到的地址列表集合
     */
    @Override
    public void showUserAddress(List<Address> addressList) {

        Observable.just(addressList)
                .filter(addresses -> {

                    mTipView.reset();
                    mAdapter = new UserAddressAdapter(addresses, UserAddressActivity.this, mRecyclerView);
                    mRecyclerView.setAdapter(mAdapter);

                    return mOldList != null;
                })
                .subscribe(addresses -> {

                    AdapterDiffCallback<Address> diffCallback = new AdapterDiffCallback<>(mOldList, addresses);

                    //如果List的集合的数据量较大的时候，需要放入线程中进行计算,然后在主线程中进行UI的更新
                    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback, true);
                    diffResult.dispatchUpdatesTo(mAdapter);
                    mAdapter.setDatas(addresses);
                    //刷新完毕
                    mRefreshLayout.setRefreshing(false);


                });

        mAdapter.setmOnItemWidgetClickListener(this);
        mOldList = addressList;


    }


    @Override
    public void setAdsDefaultSuccess(Address address) {

        mAdapter.updateItem(address);
        UserDao.getInstance().setUserDefaultAds(address);
    }


    /**
     * 加载时的dialog
     */
    @Override
    public void showLoadingDialog() {

        mTipView.setLoading();

    }

    /**
     * 关闭dialog
     */
    @Override
    public void cancelLoadingDialog() {

    }


    /**
     * checkbox被i选中的时候设为默认地址
     *
     * @param address 要设置成默认地址
     */
    @Override
    public void onCheckedChanged(Address address) {

        LogHelper.i("被选中的地址==>" + address.getAddress_content());
        //将选中地址设置为默认
        mPresenter.setAdsAsDefault(this, address);


    }

    /**
     * 跳转到地址编辑的Activity
     */
    @Override
    public void onEditClick(Address address) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("address", address);
        ModifyAddressActivity.showAction(this, bundle);
    }

    /**
     * 删除一条地址信息
     *
     * @param address
     */
    @Override
    public void onRemoveClick(Address address) {

        LogHelper.i("待删除的地址信息==>" + address.getAddress_content());
        mPresenter.deleteAds(this, address);


    }

    /**
     * 删除地址信息成功的回调
     *
     * @param address
     */
    @Override
    public void removeAdsSuccess(Address address) {

        mAdapter.remove(address);

        int itemCount = mAdapter.getItemCount();

        if (itemCount == 0) showEmptyTip(getString(R.string.user_address_empty));

    }

    @Override
    public void update() {

        if (mPresenter != null) {
            mPresenter.getUserAds(this, true);
        }
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
    protected void onDestroy() {
        mPresenter.detachView();
        UserInfoSubject.getInstance().detach(UserAddressActivity.class.getName());
        super.onDestroy();
    }

}
