package com.merpyzf.kangyuanmilk.common;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
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
 * @author wangke
 */
public class ApplyPermissionFragment extends BottomSheetDialogFragment implements EasyPermissions.PermissionCallbacks {

    private static final int RC_CAMERA_AND_LOCATION = 1;
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

    private String[] perms_camera;
    private String[] perms_storage;
    private String[] perms_sms;
    private String[] perms_net;



    /**
     * 调用检查是否已授予了所有的权限,如果没有则显示进行权限授予
     */
    public void havaAll(FragmentManager fragmentManager){

        new ApplyPermissionFragment().show(fragmentManager,"tag");

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

        //拍照权限
        perms_camera = new String[]{Manifest.permission.CAMERA};

        mPermsList.add(perms_camera);
        //读取sdk权限
        perms_storage = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        mPermsList.add(perms_storage);
        //读取短信的权限
        perms_sms = new String[]{Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS};
        mPermsList.add(perms_sms);
        //获取网络状态的权限
        perms_net = new String[]{Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.CHANGE_NETWORK_STATE};
        mPermsList.add(perms_net);





        checkAndRequest(true);







        return view;
    }

    /**
     * 检查和申请权限
     * @param isRequest true: 检查的同时如果没有权限就去申请
     *                  false： 只检查不申请
     */
    private void checkAndRequest(boolean isRequest) {

        for (int i = 0; i< mPermsList.size();i++) {

            //检查拍照相关的权限
            if (EasyPermissions.hasPermissions(mActivity, mPermsList.get(i))) {

                LogHelper.i("已有权限");
                iv_perms_camera.setVisibility(View.VISIBLE);


            } else {

                iv_perms_camera.setVisibility(View.INVISIBLE);

                if (isRequest) {
                    //申请权限
                    EasyPermissions.requestPermissions(this, "拍照需要的权限"
                            , i, perms_camera);

                }
            }

        }


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        return new TransStatusBottomSheetDialog(getActivity());
    }

    @Override
    public void onDestroy() {

        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.btn_award)
    public void awardPremission(Button view){

        //点击进行权限的授予



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
        LogHelper.i("权限通过");

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //申请被拒绝
        LogHelper.i("权限被拒绝");
    }
}
