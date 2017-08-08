package com.merpyzf.kangyuanmilk.ui.user;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.common.widget.TipView;
import com.merpyzf.kangyuanmilk.ui.adapter.UserAddressAdapter;
import com.merpyzf.kangyuanmilk.ui.user.bean.Address;
import com.merpyzf.kangyuanmilk.ui.user.contract.IUserAddressContract;
import com.merpyzf.kangyuanmilk.ui.user.presenter.UserAddressPresenterImpl;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.ui.ItemMarginDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * 管理用户收货地址 2017-8-7
 *
 * @author wangke
 */
public class UserAddressActivity extends BaseActivity implements IUserAddressContract.IUserAddressView, UserAddressAdapter.onItemWidgetClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refresh_layout;
    @BindView(R.id.tv_add_address)
    TextView tv_add_address;
    @BindView(R.id.tipView)
    TipView tipView;
    @BindView(R.id.ll_container)
    LinearLayout ll_container;

    private IUserAddressContract.IUserAddressPresenter mPresenter = null;
    private UserAddressAdapter mAdapter = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_address;
    }

    @Override
    public void initWidget() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new ItemMarginDecoration());
        tipView.bindView(ll_container);

        //设置RecyclerView中item中widget点击事件的监听回调

    }

    @Override
    protected void initData() {
        mPresenter = new UserAddressPresenterImpl();
        mPresenter.attachView(this);
        mPresenter.getUserAds(this);


    }

    /**
     * 成功时的提示
     *
     * @param msg 提示信息
     */
    @Override
    public void showSuccess(String msg) {

    }

    /**
     * 错误提示
     *
     * @param errorMsg
     */
    @Override
    public void showErrorMsg(String errorMsg) {

    }

    /**
     * 网路错误无法加载数据时的提示
     *
     * @param msg
     */
    @Override
    public void showNetErrorTip(String msg) {

        tipView.setNetErrorTip(msg);

    }

    /**
     * 加载数据为空的时候的提示
     *
     * @param msg
     */
    @Override
    public void showEmptyTip(String msg) {


        tipView.setEmptyTip(msg);

    }

    /**
     * 显示用户的所有地址
     *
     * @param addressList 获取到的地址列表集合
     */
    @Override
    public void showUserAddress(List<Address> addressList) {

        mAdapter = new UserAddressAdapter(addressList, this, recyclerView);
        mAdapter.setmOnItemWidgetClickListener(this);

        recyclerView.setAdapter(mAdapter);

        addressList.forEach(address -> {

            LogHelper.i("用户的地址==>" + address.getAddress_content());

        });


    }

    /**
     * 给用户添加一个地址
     *
     * @param address
     */
    @Override
    public void addUserAddress(Address address) {

    }


    /**
     * 加载时的dialog
     */
    @Override
    public void showLoadingDialog() {

    }

    /**
     * 关闭dialog
     */
    @Override
    public void cancelLoadingDialog() {

    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    /**
     * checkbox被i选中的时候设为默认地址
     *
     * @param checked 当前cb的状态
     * @param address 要设置成默认地址
     */
    @Override
    public void onCheckedChanged(Address address) {

        LogHelper.i("被选中的地址==>" + address.getAddress_content());

    }

    /**
     * 跳转到地址编辑的Activity
     */
    @Override
    public void onEditClick(Address address) {

        LogHelper.i("待编辑的地址信息==>" + address.getAddress_content());

    }

    /**
     * 删除一条地址信息
     *
     * @param address
     */
    @Override
    public void onRemoveClick(Address address) {

        LogHelper.i("待删除的地址信息==>" + address.getAddress_content());

    }
}
