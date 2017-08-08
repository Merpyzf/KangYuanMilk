package com.merpyzf.kangyuanmilk.ui.user;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.ui.user.contract.IUserAddressContract;
import com.merpyzf.kangyuanmilk.ui.user.presenter.UserAddressPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 管理用户收货地址 2017-8-7
 *
 * @author wangke
 */
public class UserAddressActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refresh_layout;
    @BindView(R.id.tv_add_address)
    TextView tv_add_address;
    private IUserAddressContract.IUserAddressPresenter mPresenter = null;


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_address;
    }

    @Override
    public void initWidget() {

    }

    @Override
    protected void initData() {
        mPresenter = new UserAddressPresenterImpl();
        mPresenter.getUserAddress();

    }
}
