package com.merpyzf.kangyuanmilk.ui.user;

import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.AddressPickerFragment;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.ui.user.bean.Address;
import com.merpyzf.kangyuanmilk.ui.user.contract.IModifyAddressContract;
import com.merpyzf.kangyuanmilk.ui.user.presenter.ModifyAddressPresenterImpl;
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
    Toolbar toolbar;
    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_phone)
    EditText edt_phone;
    @BindView(R.id.tv_choice_address)
    TextView tv_choice_address;
    @BindView(R.id.ll_choice_address)
    LinearLayout ll_choice_address;
    @BindView(R.id.edt_address_detail)
    EditText edt_address_detail;
    @BindView(R.id.btn_save_address)
    Button btn_save_address;


    private IModifyAddressContract.IModefyAddressPresenter mPresenter = null;
    private AddressPickerFragment mPickerFragment;
    private Address mAddress = new Address();
    private MaterialDialog mMaterialDialog;


    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_address;
    }

    @Override
    public void initWidget() {

        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("添加收货物地址");
        bar.setDisplayHomeAsUpEnabled(true);
        mPickerFragment = new AddressPickerFragment();


    }

    @Override
    protected void initData() {
        mPresenter = new ModifyAddressPresenterImpl();
        mPresenter.attachView(this);

    }

    @Override
    public void initEvent() {
        ll_choice_address.setOnClickListener(this);
        btn_save_address.setOnClickListener(this);
        mPickerFragment.setOnSelectorComplete(this);
    }

    @Override
    public void showLoadingDialog() {
        mMaterialDialog = new MaterialDialog.Builder(this)
                .title("保存中")
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

    }

    @Override
    public void showErrorMsg(String errorMsg) {
        cancelLoadingDialog();
        App.showToast(errorMsg);

    }

    @Override
    public Address getAddressInfo() {


        String name = edt_name.getText().toString().trim();
        String phone = edt_phone.getText().toString().trim();
        //所在地址区域
        String address = tv_choice_address.getText().toString();
        //详细地址
        String addressDetail = edt_address_detail.getText().toString().trim();


        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address) || TextUtils.isEmpty(addressDetail)) {

            App.showToast("请检查输入是否有为空的项");


            return null;
        }

        if (!RegexHelper.regexPhoneNum(phone)) {

            App.showToast("请输入正确的电话号码");

            return null;
        }


        //设置收货人姓名
        mAddress.setConsignee(name);
        //设置收货人电话
        mAddress.setConsignee_tel(phone);
        //设置收获地址
        mAddress.setAddress_content(address + addressDetail);
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

                if (addressInfo != null) {

                    mPresenter.addAddress(addressInfo);
                }
                break;

            case R.id.ll_choice_address:


                mPickerFragment.show(getSupportFragmentManager());

                break;

        }


    }

    /**
     * 地址选择完成后回调
     *
     * @param address 地址
     * @param id      地址id
     */
    @Override
    public void selectedComplete(String address, int id) {
        tv_choice_address.setText(address);
        mAddress.setAds_id(id);
        mPickerFragment.dismiss();
    }
}
