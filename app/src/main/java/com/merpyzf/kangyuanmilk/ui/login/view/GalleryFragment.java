package com.merpyzf.kangyuanmilk.ui.login.view;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.widget.GalleryView;
import com.merpyzf.kangyuanmilk.utils.ui.TransStatusBottomSheetDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 图片选择器显示的BottomSheetDialogFragment
 *
 * @author wangke
 */
public class GalleryFragment extends BottomSheetDialogFragment implements GalleryView.ImageSelectedChangedListener{

    @BindView(R.id.galleryView)
    GalleryView galleryView;
    @BindView(R.id.fab_croup)
    FloatingActionButton fab_croup;

    private LoaderManager loaderManager;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        unbinder = ButterKnife.bind(this, view);

        loaderManager = getActivity().getLoaderManager();
        //需要从外部传入一个loaderManager对象进去，用于从数据库中读取图片,注意使用完之后一定要进行销毁
        galleryView.init(getContext(), loaderManager);
        galleryView.setMaxSelected(1);
        //设置选择图片的监听
        galleryView.setOnImageSelectedChangedListener(this);




        return view;


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //使用自己定义的dialog样式,避免顶部状态栏出现被dialog覆盖而变成黑色
        return new TransStatusBottomSheetDialog(getContext());
    }

    @Override
    public void onDestroyView() {

        //销毁LoadManager
        galleryView.destory();
        //取消绑定
        unbinder.unbind();
        super.onDestroyView();

    }

    /**
     * 拍照结束时的一个回调,真实的调用发生在开启这个Fragment的Activity,需要在Activity中手动
     * 调用onActivityResult方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //刷新GalleryView将拍摄的图片加载进去
       // galleryView.updatePhoto();

    }

    @Override
    public void onSelectedChange(List<GalleryView.Image> imageList, int count) {


        if(count>0){
            //显示fab确定选择按钮

            showFab(fab_croup);
            Log.i("wk","显示");



        }else {

            //隐藏

            hideFab(fab_croup);
            Log.i("wk","隐藏");
        }





     /*   if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {


            File ParentFile = new File(Environment.getExternalStorageDirectory(), "/Avatar");

            //如果文件夹不存在，就进行创建
            if (!ParentFile.exists()) {

                boolean mkdirs = ParentFile.mkdir();
                Log.i("wk", "创建结果:" + mkdirs);

            }
            // TODO: 2017-07-19
            File AvatarFile = new File(ParentFile, "temp" + ".png");
            AvatarFile.setWritable(true);


            Uri sourceUri = Uri.fromFile(new File(imageList.get(0).getPath()));

            Uri destinationUri = Uri.fromFile(new File(AvatarFile.getPath()));

            UCrop.Options options = new UCrop.Options();

            options.setCompressionFormat(Bitmap.CompressFormat.PNG);

            Log.i("wk","要裁切的图片："+imageList.get(0).getPath());

          *//*  UCrop.of(sourceUri, destinationUri)
                    .withAspectRatio(8, 8)
                    .withOptions(options)
                    .withMaxResultSize(maxWidth, maxHeight)
                    .start(getActivity());*//*

        } else {

            Toast.makeText(getContext(), "没有找到可用的存储位置", Toast.LENGTH_SHORT).show();

        }
*/

    }



    private void hideFab(View view) {

        Animator animator = AnimatorInflater.loadAnimator(getContext(),R.animator.anim_fab_hide);
        animator.setTarget(view);
        animator.setInterpolator(new AnticipateOvershootInterpolator());



        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                fab_croup.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();

    }

    private void showFab(View view) {


        Animator animator = AnimatorInflater.loadAnimator(getContext(),R.animator.anim_fab_show);
        animator.setTarget(view);
        animator.setInterpolator(new AnticipateOvershootInterpolator());


        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                Log.i("wk","动画开始显示动画");
                fab_croup.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animator animator) {



            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animator.start();



    }
}
