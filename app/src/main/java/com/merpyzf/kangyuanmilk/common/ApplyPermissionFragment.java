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

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.utils.ui.TransStatusBottomSheetDialog;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 权限申请
 * @author wangke
 */
public class ApplyPermissionFragment extends BottomSheetDialogFragment implements EasyPermissions.PermissionCallbacks {

    private Activity mActivity = null;
    private Unbinder unbinder;


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

        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};

        //判断权限是否授予
        if(EasyPermissions.hasPermissions(mActivity,perms)){




        }else{

            // 没有权限就去申请权限
           /* EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    RC_CAMERA_AND_LOCATION, perms);*/


        }








        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        return new TransStatusBottomSheetDialog(getContext());
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

        //进行权限的授予
//        EasyPermissions.onRequestPermissionsResult();


    }

    //权限申请结果的回调
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //通过申请


    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //申请被拒绝
    }
}
