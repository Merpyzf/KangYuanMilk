package com.merpyzf.kangyuanmilk.common;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.widget.GalleryView;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.ui.TransStatusBottomSheetDialog;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 图片选择器
 *
 * @author wangke
 */
public class GalleryFragment extends BottomSheetDialogFragment implements GalleryView.ImageSelectedChangedListener {

    @BindView(R.id.galleryView)
    GalleryView galleryView;
    @BindView(R.id.fab_croup)
    FloatingActionButton fab_croup;

    private LoaderManager loaderManager;
    private Unbinder unbinder;
    private Context mContext;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getContext();
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
        galleryView.updatePhoto();

    }

    @Override
    public void onSelectedChange(final List<GalleryView.Image> imageList, int count) {

        if (count > 0) {
            //显示fab确定选择按钮
            showFab(fab_croup);
            LogHelper.i("wk", "显示");
            //当点击Fab浮动按钮的时候进行图片的剪切
            fab_croup.setOnClickListener((view) -> {


                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {


                            File cacheDir = new File(mContext.getExternalCacheDir(), "temp");
                            if (!cacheDir.exists()) {
                                cacheDir.mkdirs();
                            }
                            //用作头像缓存
                            File tempFile = new File(cacheDir, "temp.png");
                            //用于裁切保存的文件路径对应的uri
                            Uri destUri = Uri.fromFile(tempFile);
                            //选择图的源文件
                            Uri sourceUri = Uri.fromFile(new File(imageList.get(0).getPath()));

                            UCrop.Options options = new UCrop.Options();
                            //设置裁切后生成的图片格式
                            options.setCompressionFormat(Bitmap.CompressFormat.PNG);
                            //设置裁切后的尺寸
                            options.withMaxResultSize(500, 500);
                            //设置裁切图的质量
                            options.setCompressionQuality(90);
                            //设置按钮等组件的颜色为主题色
                            options.setActiveWidgetColor(getResources().getColor(R.color.colorPrimary));
                            //设置裁切时的边框的颜色
                            options.setCropFrameColor(getResources().getColor(R.color.colorPrimary));
                            //设置toolbar的颜色
                            options.setToolbarColor(getResources().getColor(R.color.colorPrimary));


                            //跳转到uCrop的裁切页面
                            UCrop.of(sourceUri, destUri)
                                    .withAspectRatio(1, 1)
                                    .withOptions(options)
                                    .start(getActivity());

                        } else {

                            App.showToast("没有找到存储设备,请检查");

                        }
                    }
            );
        } else {
            //隐藏
            hideFab(fab_croup);
            LogHelper.i("wk", "隐藏");
        }

    }

    private void hideFab(View view) {

        Animator animator = AnimatorInflater.loadAnimator(getContext(), R.animator.anim_fab_hide);
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


        Animator animator = AnimatorInflater.loadAnimator(getContext(), R.animator.anim_fab_show);
        animator.setTarget(view);
        animator.setInterpolator(new AnticipateOvershootInterpolator());


        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

                LogHelper.i("wk", "动画开始显示动画");
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

    /**
     * 将自己隐藏
     */
    public void onDismiss() {

        dismiss();
    }


    @Override
    public void onDestroyView() {

        //销毁LoadManager
        galleryView.destory();
        //取消绑定
        unbinder.unbind();
        super.onDestroyView();

    }
}
