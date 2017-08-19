package com.merpyzf.kangyuanmilk.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.widget.AddressPickerFragment;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.common.observer.UserInfoSubject;
import com.merpyzf.kangyuanmilk.ui.user.bean.Address;
import com.merpyzf.kangyuanmilk.ui.user.contract.IModifyAddressContract;
import com.merpyzf.kangyuanmilk.ui.user.presenter.ModifyAddressPresenterImpl;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.RegexHelper;
import com.merpyzf.kangyuanmilk.utils.db.dao.UserDao;

import butterknife.BindView;

/**
 * 添加地址
 *
 * @author wangke
 */
public class ModifyAddressActivity extends BaseActivity implements IModifyAddressContract.IModifyAddressView, View.OnClickListener, AddressPickerFragment.OnSelectorComplete {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_name)
    EditText mEdtName;
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.tv_choice_address)
    TextView mTvChoiceAddress;
    @BindView(R.id.linearlayout_choice_address)
    LinearLayout mLlChoiceAddress;
    @BindView(R.id.edt_address_detail)
    EditText mEdtAddressDetail;
    @BindView(R.id.btn_save_address)
    Button mBtnSaveAddress;


    private IModifyAddressContract.IModefyAddressPresenter mPresenter = null;
    private AddressPickerFragment mPickerFragment;
    private MaterialDialog mMaterialDialog;
    private Address mAddress = new Address();
    private Address mAds;

    /**
     * 开启当前的Activity
     *
     * @param context 上下文
     * @param bundle  Bundle参数传递
     */
    public static void showAction(Context context, Bundle bundle) {

        Intent intent = new Intent(context, ModifyAddressActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle bundle) {


        mAds = (Address) bundle.getSerializable("address");
        //如果没有更改所在地址的时候就使用之前的地址id

        if (mAds == null)
            return true;

        mAddress.setAds_id(mAds.getAds_id());
        mAddress.setAddress_id(mAds.getAddress_id());
        setSupportActionBar(mToolbar);

        LogHelper.i("待编辑的地址==> " + mAds.getAddress_content());


        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_address;
    }


    @Override
    public void initWidget() {

        setSupportActionBar(mToolbar);
        ActionBar bar = getSupportActionBar();
        bar.setTitle(R.string.title_modifyaddress_activity_text);
        bar.setDisplayHomeAsUpEnabled(true);
        mPickerFragment = new AddressPickerFragment();

        if (mAds != null) {

            mEdtName.setText(mAds.getConsignee());
            mEdtPhone.setText(mAds.getConsignee_tel());
            mTvChoiceAddress.setText(mAds.getAddress_all());
            mEdtAddressDetail.setText(" " + mAds.getAddress_content());
            getSupportActionBar().setTitle(R.string.title_modifyaddress_activity_modifytext);
        } else {

            getSupportActionBar().setTitle(R.string.title_modifyaddress_activity_text);
        }


    }

    @Override
    protected void initData() {
        mPresenter = new ModifyAddressPresenterImpl();
        mPresenter.attachView(this);

    }

    @Override
    public void initEvent() {
        mLlChoiceAddress.setOnClickListener(this);
        mBtnSaveAddress.setOnClickListener(this);
        mPickerFragment.setOnSelectorComplete(this);
    }

    @Override
    public void showLoadingDialog() {
        mMaterialDialog = new MaterialDialog.Builder(this)
                .title(R.string.title_dialog_modifyaddress_save)
                .content(R.string.please_wait_save)
                .progress(true, 0)
                .canceledOnTouchOutside(false)
                .show();

    }

    @Override
    public void cancelLoadingDialog() {
        if (mMaterialDialog != null) {
            mMaterialDialog.dismiss();
        }
    }

    @Override
    public void saveSuccess(String msg) {

        cancelLoadingDialog();
        App.showToast(msg);
        UserInfoSubject.getInstance().notifyChange(UserAddressActivity.class.getName());

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        cancelLoadingDialog();
        App.showToast(errorMsg);

    }

    @Override
    public Address getAddressInfo() {


        String name = mEdtName.getText().toString().trim();
        String phone = mEdtPhone.getText().toString().trim();
        //所在地址区域
        String address = mTvChoiceAddress.getText().toString();
        //详细地址
        String addressDetail = mEdtAddressDetail.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address) || TextUtils.isEmpty(addressDetail)) {

            App.showToast(getString(R.string.modify_activity_check_isempty));


            return null;
        }

        if (!RegexHelper.regexPhoneNum(phone)) {

            App.showToast(getString(R.string.modify_activity_check_iscorrect_phone));

            return null;
        }


        //设置收货人姓名
        mAddress.setConsignee(name);
        //设置收货人电话
        mAddress.setConsignee_tel(phone);
        //设置收获地址
        mAddress.setAddress_content(addressDetail);
        //设置userId
        mAddress.setUser_id(UserDao.getInstance().getUserInfo().getUser_id());

        return mAddress;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            //保存地址
            case R.id.btn_save_address:
                Address addressInfo = getAddressInfo();

                //新增一条地址
                if (mAds == null) {
                    if (addressInfo != null) {

                        mPresenter.addAddress(ModifyAddressActivity.this, addressInfo);
                    }

                } else {

                    //更新一条地址
                    if (addressInfo != null) {

                        mPresenter.updateAddress(ModifyAddressActivity.this, addressInfo);
                    }

                }


                break;

            case R.id.linearlayout_choice_address:


                mPickerFragment.show(getSupportFragmentManager());

                break;

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


    /**
     * 地址选择完成后回调
     *
     * @param address 地址
     * @param id      地址id
     */
    @Override
    public void selectedComplete(String address, int id) {
        mTvChoiceAddress.setText(address);
        mAddress.setAds_id(id);
        mPickerFragment.dismiss();
    }

    @Override
    protected void onDestroy() {

        mPresenter.detachView();
        super.onDestroy();
    }
}
