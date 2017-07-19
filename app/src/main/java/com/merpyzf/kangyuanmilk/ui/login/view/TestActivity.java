package com.merpyzf.kangyuanmilk.ui.login.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.utils.image.GlideImageLoader;
import com.merpyzf.kangyuanmilk.utils.image.ImageLoaderOptions;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2017-07-17.
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
        galleryFragment.onActivityResult(requestCode,resultCode,data);
        Log.i("wk","TestActivity"+"==>    requestCode==>"+requestCode+" resuleCode==>"+requestCode);

    }
}
