package com.merpyzf.kangyuanmilk.ui.login.view;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.common.GalleryFragment;
import com.merpyzf.kangyuanmilk.common.widget.GalleryView;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.qiniu.UpLoadHelper;
import com.qiniu.android.http.ResponseInfo;
import com.yalantis.ucrop.UCrop;

import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;

/**
 * Created by Administrator on 2017-07-17.
 *
 */

public class TestActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.btn_picker)
    Button btn_picker;
    @BindView(R.id.btn_photo)
    Button btn_photo;
    private GalleryFragment galleryFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initWidget() {

    }

    @Override
    public void initEvent() {

        btn_picker.setOnClickListener(this);
        btn_photo.setOnClickListener(this);

        // TODO: 2017-07-20 当用户点击顶部的标题栏时候,GalleryView自动滚动到初始位置



    }

    @Override
    protected void initData() {


    }

    @Override
    public void onClick(View view) {


        galleryFragment = new GalleryFragment();
        galleryFragment.show(getSupportFragmentManager(),"1");


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {

            switch (requestCode){
                //GalleryView中拍照
                case GalleryView.GALLERYVIEW_TAKEPHOTO:
                    galleryFragment.onActivityResult(requestCode, resultCode, data);
                    LogHelper.i("wk", "TestActivity" + "==>    requestCode==>" + requestCode + " resuleCode==>" + requestCode);
                    break;

                case UCrop.REQUEST_CROP:

                    final Uri resultUri = UCrop.getOutput(data);

                    LogHelper.i("要进行上传的图片: "+resultUri.getPath());

                    String filename = new File(resultUri.getPath()).getName();

                    UpLoadHelper upLoadHelper = new UpLoadHelper();


                    upLoadHelper.upLoadAveter(resultUri.getPath(), new UpLoadHelper.CompletionListener() {
                        @Override
                        public void onComplete(String key, ResponseInfo info, JSONObject response) {

                            LogHelper.i("上传进度==>"+info.toString());
                            LogHelper.i(response.toString());
                        }
                    });


                    galleryFragment.onDismiss();

                    break;

            }

        } else if (resultCode == UCrop.RESULT_ERROR) {
            Throwable cropError = UCrop.getError(data);
            cropError.printStackTrace();
        }



    }
}
