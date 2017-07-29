package com.merpyzf.kangyuanmilk.common.widget;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.App;
import com.merpyzf.kangyuanmilk.utils.image.GlideImageLoader;
import com.merpyzf.kangyuanmilk.utils.image.ImageLoaderOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangke on 2017-07-18.
 * 图片选择器
 * 计划实现功能: 1. item中的第一项默认为拍照,拍照成功后返回当前图片选择的页面
 * 2.滑动多选:
 * 列值等于x轴移动的距离／每个item 的宽度
 * 行值等于（RecyclerView滑动的距离＋手指滑动的距离－RecylerView距离顶部偏移）（一般不设置magin就是0，兼容多种情况）
 */

public class GalleryView extends RecyclerView implements android.app.LoaderManager.LoaderCallbacks<Cursor>, RecyclerAdapter.ItemClickListener<GalleryView.Image> {

    private static final int ITEM_TYPE_COMMON = 1;
    private static final int ITEM_TYPE_CAMERA = 0;
    private static final int LOAD_IMAGE = 1;
    public static final int GALLERYVIEW_TAKEPHOTO = 0x00001;
    private static int MAX_SELECTED = 3;
    private static final String TAG = GalleryView.class.getSimpleName();
    private Context mContext;
    private List<Image> mImages = null;
    private GalleryAdapter mGalleryAdapter;
    private List<Image> mPaths = null;
    private Loader<Cursor> mLoader;
    private ImageSelectedChangedListener mImageSelectedChangedListener = null;
    private LoaderManager mLoadManager = null;
    private String mAvatarFilePath;

    public GalleryView(Context context) {
        super(context);
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void init(Context context, LoaderManager loaderManager) {

        mContext = context;

        mImages = mImages == null ? new ArrayList<Image>() : mImages;
        mPaths = mPaths == null ? new ArrayList<Image>() : mPaths;

        //作为全局变量保存,用于销毁(一定要销毁，不然会报错)

        mLoadManager = loaderManager;

        mLoader = loaderManager.initLoader(LOAD_IMAGE, null, this);


        setLayoutManager(new GridLayoutManager(mContext, 3));

        mGalleryAdapter = new GalleryAdapter(mImages, mContext, this);

        setAdapter(mGalleryAdapter);

        mGalleryAdapter.setOnItemClickListener(this);

    }


    /**
     * 设置最大可选择图片的数量
     *
     * @param num 可选择的图片的数量
     */
    public void setMaxSelected(int num) {

        MAX_SELECTED = num;

    }


    @Override
    public android.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {


        if (i == LOAD_IMAGE) {

            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED};
            return new CursorLoader(mContext, uri, projection, null, null, MediaStore.Images.Media.DATE_ADDED + " DESC");
        }
        return null;
    }

    /**
     * 获取加载的数据，并刷新适配器进行UI的更新
     *
     * @param loader
     * @param cursor
     */
    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {

        //填充一个空的item作为拍照
        mImages.add(new Image("","",""));

        while (cursor.moveToNext()) {

            String id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            String add_date = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED));

            File file = new File(path);

            //对图片的大小进行筛选
            if (file.length() > 2000) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

                Date date = new Date(Long.valueOf(add_date));
                String sDate = dateFormat.format(date);

                Image image = new Image(id, path, sDate);
                mImages.add(image);
            }
        }
        mGalleryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {


    }

    /**
     * 图片选择器点击事件的回调方法
     *
     * @param viewHolder 当前item对应着的viewHodler
     * @param image      选中的图片
     * @param position   对应的下标记
     */
    @Override
    public void onItemClick(com.merpyzf.kangyuanmilk.common.widget.ViewHolder viewHolder, Image image, int position) {

        if (position > 0) {

            //如果是选中状态就取消勾选
            if (image.isSelected) {

                image.setSelected(false);
                //从集合中将移除选中的图片
                mPaths.remove(image);


            } else {
                if (mPaths.size() >= MAX_SELECTED) {
                    return;
                }
                //进行选中图片
                image.setSelected(true);
                //将选中的图片添加到集合中去
                mPaths.add(image);
                //将选中的图片回调给外界调用的地方
            }

            //刷新当前选中的条目
            mGalleryAdapter.updateItem(position, image);

            if (mImageSelectedChangedListener != null) {
                mImageSelectedChangedListener.onSelectedChange(mPaths, mPaths.size());
            }


        } else {

            Toast.makeText(mContext, "跳转到拍照页面", Toast.LENGTH_SHORT).show();


            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {


                File ParentFile = new File(Environment.getExternalStorageDirectory(), "/Header");

                //如果文件夹不存在，就进行创建
                if (!ParentFile.exists()) {

                    boolean mkdirs = ParentFile.mkdir();
                    Log.i("wk", "创建结果:" + mkdirs);
                    if (!mkdirs) {
                        return;
                    }

                }
                // TODO: 2017-07-19
                //因为Glide加载图片的缓存问题,使用相同的文件名会导致不能及时显示当前最新的图片，而是会去寻找缓存
                //那个文件名所对应的缓存,采取方案：在刚进入App的时候进行清理如果>20张的时候
                File AvatarFile = new File(ParentFile, System.currentTimeMillis() + ".jpg");
                AvatarFile.setWritable(true);

                mAvatarFilePath = AvatarFile.getPath();
                // TODO: 2017-07-19 调用相机拍照 需要针对Android N以上的设备做适配
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, AvatarFile.getPath());
                } else {

                    //设置保存拍摄图片的所在目录
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(AvatarFile));
                }
                ((Activity) mContext).startActivityForResult(intent, GALLERYVIEW_TAKEPHOTO);

            } else {
                App.showToast("没有找到可用的存储位置");

            }

        }
    }

    @Override
    public boolean onItemLongClick(com.merpyzf.kangyuanmilk.common.widget.ViewHolder viewHolder, Image image, int position) {

        if(position>0) {
            clearAllSelected();
            App.showToast("清除所有已选择的图片");
        }
        return true;
    }

    /**
     * 清除所有选择
     */
    public void clearAllSelected() {

        List<Image> datas = mGalleryAdapter.getDatas();

        for (Image image : datas) {

            if (image.isSelected) {
                //如果是选中状态,就将其设置为未选中状态
                image.isSelected = false;
                //刷新当前item
                mGalleryAdapter.updateItem(image);
            }

        }
        mPaths.clear();
        if (mImageSelectedChangedListener != null) {
            mImageSelectedChangedListener.onSelectedChange(mPaths, mPaths.size());
        }
    }


    /**
     * 更新拍摄的照片,使显示在GalleryView上面去
     */
    public void updatePhoto() {
        Image image = new Image("", mAvatarFilePath, "");
        mGalleryAdapter.insertItem(1, image);
    }

    /**
     * RecyclerView的适配器
     */
    class GalleryAdapter extends RecyclerAdapter<Image> {


        public GalleryAdapter(List<Image> mDatas, Context mContext, RecyclerView mRecyclerView) {
            super(mDatas, mContext, mRecyclerView);
        }

        @Override
        public com.merpyzf.kangyuanmilk.common.widget.ViewHolder createHolder(ViewGroup parent, int viewType) {


            switch (viewType) {

                case ITEM_TYPE_COMMON:

                    View cellView = LayoutInflater.from(mContext)
                            .inflate(R.layout.item_gallery, parent, false);


                    return new GalleryHolder(cellView);

                case ITEM_TYPE_CAMERA:

                    View cellCameraView = LayoutInflater.from(mContext)
                            .inflate(R.layout.item_camera_gallery, parent, false);
                    return new GalleryCameraHolder(cellCameraView);


            }

            return null;
        }

        @Override
        public int getItemViewType(int position) {


            if (position == 0) {

                return ITEM_TYPE_CAMERA;

            } else {

                return ITEM_TYPE_COMMON;
            }
        }
    }

    /**
     * 两种不同布局的ViewHolder
     */
    class GalleryHolder extends com.merpyzf.kangyuanmilk.common.widget.ViewHolder<Image> {

        @BindView(R.id.iv_gallery)
        ImageView iv_gallery;
        @BindView(R.id.view_cover)
        View view_cover;
        @BindView(R.id.cb_gallery)
        CheckBox cb_gallery;


        public GalleryHolder(View itemView) {
            super(itemView);
            iv_gallery = (ImageView) itemView.findViewById(R.id.iv_gallery);
            view_cover = (View) itemView.findViewById(R.id.view_cover);
            cb_gallery = (CheckBox) itemView.findViewById(R.id.cb_gallery);
        }

        @Override
        protected void onBindWidget(Image image) {


            ImageLoaderOptions.Bulider bulider = new ImageLoaderOptions.Bulider();


            ImageLoaderOptions options = bulider.isCenterCrop(true)
                    .errorDrawable(R.drawable.ic_default)
                    .isCrossFade(true)
                    .isSkipMemoryCache(true)
                    .size(new ImageLoaderOptions.ImageReSize(400, 400))
                    .build();
            GlideImageLoader.showImage(iv_gallery, new File(image.getPath()), options);

            cb_gallery.setChecked(image.isSelected());

            if (image.isSelected) view_cover.setVisibility(VISIBLE);
            else {
                view_cover.setVisibility(INVISIBLE);
            }


        }
    }

    private class GalleryCameraHolder extends com.merpyzf.kangyuanmilk.common.widget.ViewHolder {

        public GalleryCameraHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBindWidget(Object o) {

        }
    }

    /**
     * 图片选择事件的回调接口
     */
    public interface ImageSelectedChangedListener {
        void onSelectedChange(List<Image> imageList, int count);
    }

    public void setOnImageSelectedChangedListener(ImageSelectedChangedListener ImageSelectedChangedListener) {
        this.mImageSelectedChangedListener = ImageSelectedChangedListener;
    }

    /**
     * 封装Image类的信息
     */
    public static class Image {

        private String id;
        //路径
        private String path;
        //添加时间
        private String add_date;
        //当前image是否被选中
        private boolean isSelected = false;

        public Image() {
        }

        public String getAdd_date() {
            return add_date;
        }

        public void setAdd_date(String add_date) {
            this.add_date = add_date;
        }

        Image(String id, String path, String add_date) {
            this.id = id;
            this.path = path;
            this.add_date = add_date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    /**
     * 使用结束的时候调用此方法销销毁LoadManager对象,和容器的生命周期进行绑定
     */
    public void destory() {

        mLoadManager.destroyLoader(LOAD_IMAGE);
    }

}
