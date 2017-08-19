package com.merpyzf.kangyuanmilk.common.widget;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.ui.TransStatusBottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 权限申请
 *
 * @author wangke
 */
public class ApplyPermissionFragment extends BottomSheetDialogFragment implements EasyPermissions.PermissionCallbacks {

    private static final int RC_CAMERA_AND_LOCATION = 1;
    private onApplyPermissionCompleted mOnApplyPermissionCompleted;
    private Activity mActivity = null;
    private Unbinder unbinder;

    @BindView(R.id.iv_perms_camera)
    ImageView iv_perms_camera;

    @BindView(R.id.iv_perms_storage)
    ImageView iv_perms_storage;

    @BindView(R.id.iv_perms_sms)
    ImageView iv_perms_sms;

    @BindView(R.id.iv_perms_net)
    ImageView iv_perms_net;

    private List<String[]> mPermsList = new ArrayList<>();
    private List<ImageView> mImageViewList = new ArrayList<>();

    private String[] tips = null;

    /**
     * 调用检查是否已授予了所有的权限,如果没有则显示进行权限授予
     */
    public void haveAll(FragmentManager fragmentManager, Context context) {

        if (!checkHaveAllPermis(context)) {

            tips = context.getResources().getStringArray(R.array.apply_permissions);
            show(fragmentManager, "tag");

        }
    }

    public ApplyPermissionFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = LayoutInflater.from(mActivity)
                .inflate(R.layout.fragment_apply_permission, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        //刚进入页面时先检查权限但不申请权限
        checkAndRequest(false);


        return view;
    }

    /**
     * 初始化权限相关的数据
     */
    private void initData() {

        mImageViewList.clear();
        mPermsList.clear();

        mImageViewList.add(iv_perms_camera);
        mImageViewList.add(iv_perms_storage);
        mImageViewList.add(iv_perms_sms);
        mImageViewList.add(iv_perms_net);
        //拍照权限
        mPermsList.add(new String[]{Manifest.permission.CAMERA});
        //读取sdk权限
        mPermsList.add(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});
        //读取短信的权限
        mPermsList.add(new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS});


    }

    /**
     * 检查和申请权限
     *
     * @param isRequest true: 检查的同时如果没有权限就去申请
     *                  false： 只检查不申请
     */
    private void checkAndRequest(boolean isRequest) {

        for (int i = 0; i < mPermsList.size(); i++) {

            //检查拍照相关的权限
            if (EasyPermissions.hasPermissions(mActivity, mPermsList.get(i))) {

                LogHelper.i("已有权限");
                mImageViewList.get(i).setVisibility(View.VISIBLE);

            } else {

                mImageViewList.get(i).setVisibility(View.INVISIBLE);

                if (isRequest) {
                    //申请权限
                    EasyPermissions.requestPermissions(this, tips[i]
                            , i, mPermsList.get(i));
                }
            }
        }
    }

    /**
     * 自定义自己的dialog,避免顶部的statusbar被全屏的dialog覆盖而变成黑色
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new TransStatusBottomSheetDialog(getActivity());
    }


    /**
     * 权限申请按钮的点击事件
     *
     * @param view
     */
    @OnClick(R.id.btn_award)
    public void awardPremission(Button view) {

        //点击进行权限的授予
        checkAndRequest(true);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //在本身的权限回调方法中讲处理结果交给EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    //权限申请结果的回调
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //通过申请
        LogHelper.i(requestCode + "权限通过");
        refreshStatus(requestCode, true);


        if (checkHaveAllPermis(mActivity)) {
            dismiss();

            //当权限全部授予之后通知外界
            if (mOnApplyPermissionCompleted != null) {

                mOnApplyPermissionCompleted.onCompleted();

            }


        }

    }


    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //申请被拒绝
        LogHelper.i(requestCode + "权限被拒绝");
    }


    //检查所有的权限是否都已经授予
    private Boolean checkHaveAllPermis(Context context) {

        initData();

        for (int i = 0; i < mPermsList.size(); i++) {
            if (!EasyPermissions.hasPermissions(context, mPermsList.get(i))) {

                return false;
            }

        }


        return true;
    }

    /**
     * 刷新界面权限的状态
     *
     * @param requestCode 权限申请时的请求码
     * @param isGranted   权限是否授予
     */
    private void refreshStatus(int requestCode, boolean isGranted) {


        switch (requestCode) {

            case 0:

                if (isGranted) {

                    if (mImageViewList.get(0).getVisibility() == View.INVISIBLE) {

                        mImageViewList.get(0).setVisibility(View.VISIBLE);
                    }

                }


                break;
            case 1:

                if (isGranted) {

                    if (mImageViewList.get(1).getVisibility() == View.INVISIBLE) {

                        mImageViewList.get(1).setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 2:

                if (isGranted) {

                    if (mImageViewList.get(2).getVisibility() == View.INVISIBLE) {

                        mImageViewList.get(2).setVisibility(View.VISIBLE);
                    }
                }
                break;


            default:
                break;
        }

    }


    /**
     * 监听权限申请是否完成
     */
    public interface onApplyPermissionCompleted {
        /**
         * 权限申请完成后的回调
         */
        void onCompleted();
    }

    public void setmOnApplyPermissionCompleted(onApplyPermissionCompleted mOnApplyPermissionCompleted) {
        this.mOnApplyPermissionCompleted = mOnApplyPermissionCompleted;
    }


    /**
     * 当前Fragment销毁时进行资源的释放
     */
    @Override
    public void onDestroy() {

        unbinder.unbind();
        super.onDestroy();
    }

}
