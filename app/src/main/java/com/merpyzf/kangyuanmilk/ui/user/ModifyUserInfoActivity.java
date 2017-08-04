package com.merpyzf.kangyuanmilk.ui.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.AddressPickerFragment;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.common.observer.UserInfoSubject;
import com.merpyzf.kangyuanmilk.ui.base.User;
import com.merpyzf.kangyuanmilk.ui.user.contract.IModifyInfoContract;
import com.merpyzf.kangyuanmilk.ui.user.presenter.ModifyInfoPresenterImpl;
import com.merpyzf.kangyuanmilk.utils.RegexHelper;
import com.merpyzf.kangyuanmilk.utils.db.dao.UserDao;

import butterknife.BindView;

public class ModifyUserInfoActivity extends BaseActivity implements IModifyInfoContract.IModifyInfoView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_gender)
    TextView tv_gender;
    //手机号码可以被修改
    @BindView(R.id.edt_tel)
    EditText edt_tel;
    //默认配送地址可以被修改
    @BindView(R.id.tv_defalut_address)
    TextView tv_default_address;
    //身份证号码可以被修改
    @BindView(R.id.edt_identity)
    EditText edt_identity;
    @BindView(R.id.tv_reg_date)
    TextView tv_reg_date;
    private AddressPickerFragment mPickerFragment;
    private IModifyInfoContract.IModifyInfoPresenter mPresenter = null;
    private MaterialDialog mMaterialDialog;

    public static void showAction(Context context, Bundle bundle) {

        Intent intent = new Intent(context, ModifyUserInfoActivity.class);
        if (bundle != null) {

            intent.putExtras(bundle);

        }
        context.startActivity(intent);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_user_info;
    }

    @Override
    public void initWidget() {

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        //给toolbar设置标题
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("个人资料修改");
        }
        mPickerFragment = new AddressPickerFragment();

        tv_default_address.setOnClickListener(view -> mPickerFragment.show(getSupportFragmentManager()));
    }

    @Override
    public void initEvent() {

        tv_gender.setOnClickListener(view -> new MaterialDialog.Builder(this)
                .title(R.string.diaolog_gender)
                .items(R.array.dialog_items_gender)
                .itemsCallbackSingleChoice(-1, (dialog, view1, which, text) -> {

                    switch (which) {

                        case 0:
                            tv_gender.setText("男");
                            break;
                        case 1:
                            tv_gender.setText("女");
                            break;
                    }
                    return true;
                })
                .negativeText(R.string.dialog_gender_btn_negative)
                .positiveText(R.string.dialog_gender_btn_positive)
                .show());


        toolbar.setOnMenuItemClickListener(item -> {

            switch (item.getItemId()) {

                case R.id.action_done:

                    User user = UserDao.getInstance().getUserInfo();

                    String userTel = edt_tel.getText().toString().trim();

                    if (userTel.equals("")) {
                        return true;
                    }

                    if (!RegexHelper.regexPhoneNum(userTel)) {

                        App.showToast("手机号格式不正确");
                        return true;
                    }

                    String userIdCard = edt_identity.getText().toString();
                    if (userIdCard.equals("")) {
                        App.showToast("身份证号不能为空");
                        return true;
                    }

                    if (!RegexHelper.regexIdCard(userIdCard)) {

                        App.showToast("身份证输入不合法");

                        return true;
                    }

                    user.setUser_tel(userTel);
                    user.setUser_idcard(userIdCard);
                    user.setUser_sex(tv_gender.getText().toString().equals("男"));
                    mPresenter.updateUserInfo(user);

                    break;

                case android.R.id.home:

                    onBackPressed();

                    break;


            }


            return true;

        });

    }


    @Override
    protected void initData() {
        super.initData();
        mPresenter = new ModifyInfoPresenterImpl(this);
        mPresenter.attachView(this);
        //从数据库中读取当前的用户信息
        mPresenter.getUserInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_modify, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 显示用户信息
     * @param user 用户信息
     */
    @Override
    public void showUserInfo(User user) {

        tv_username.setText(user.getUser_name());
        tv_gender.setText(user.isUser_sex() ? "男" : "女");
        tv_default_address.setText(user.getAddress_content());
        edt_identity.setText(user.getUser_idcard());
        edt_tel.setText(user.getUser_tel());


    }

    @Override
    public void showLoadingDialog() {

        mMaterialDialog = new MaterialDialog.Builder(this)
                .title(R.string.progress_dialog_save)
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
    public void showErrorMsg(String errorMsg) {

        App.showToast(errorMsg);

    }

    @Override
    public void updateSuccess(String msg) {

        App.showToast(msg);
        //通知观察者更新
        UserInfoSubject.getInstance().notifyChange();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
