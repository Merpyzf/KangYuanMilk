package com.merpyzf.kangyuanmilk.common.widget;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.utils.image.GlideImageLoader;
import com.merpyzf.kangyuanmilk.utils.image.ImageLoaderOptions;

import net.qiujuer.genius.ui.Ui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangke on 2017-07-18.
 * 图片选择器
 * 计划实现功能: 1. cell中的第一项默认为拍照,拍照成功后返回当前图片选择的页面
 * 2.滑动多选:
 * 列值等于x轴移动的距离／每个item 的宽度
 * 行值等于（RecyclerView滑动的距离＋手指滑动的距离－RecylerView距离顶部偏移）（一般不设置magin就是0，兼容多种情况）
 */

public class GalleryView extends RecyclerView implements android.app.LoaderManager.LoaderCallbacks<Cursor>, RecyclerAdapter.ItemClickListener<GalleryView.Image> {

    private static final int ITEM_TYPE_COMMON = 1;
    private static final int ITEM_TYPE_CAMERA = 0;
    private static final int LOAD_IMAGE = 1;
    private static final int MAX_SELECTED = 3;
    private static final String TAG = GalleryView.class.getSimpleName() ;
    private Context mContext;
    private List<Image> mImages = null;
    private GalleryAdapter mGalleryAdapter;
    private List<Image> mPaths = null;
    private Loader<Cursor> mLoader;
    private ImageSelectedChangedListener mImageSelectedChangedListener = null;
    private int mItemWidth = 0;
    private int mItemHeight = 0;
    private int mScrollerTotalHeight = 0;


    public GalleryView(Context context) {
        super(context);
        init(context);
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);

    }

    private void init(Context context) {

        mContext = context;

        mImages = mImages == null ? new ArrayList<Image>() : mImages;
        mPaths = mPaths == null ? new ArrayList<Image>() : mPaths;

        mLoader = ((Activity) context).getLoaderManager().initLoader(LOAD_IMAGE, null, this);

        setLayoutManager(new GridLayoutManager(mContext, 3));

        mGalleryAdapter = new GalleryAdapter(mImages, mContext, this);

        setAdapter(mGalleryAdapter);

        mGalleryAdapter.setOnItemClickListener(this);

        // TODO: 2017-07-19 此处耦合太高，item的高度应想办法从RecyclerView中获取
        mItemWidth = mItemHeight = (int) Ui.dipToPx(getResources(),120);




        addOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mScrollerTotalHeight+=dy;
                Log.i("wk","滑动高度==>"+mScrollerTotalHeight+"滑动到第几列==>"+mScrollerTotalHeight/mItemHeight);

            }
        });


    }


    @Override
    public android.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        Log.i("wk", "i==>" + i);

        if (i == LOAD_IMAGE) {

            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED};
            return new CursorLoader(mContext, uri, projection, null, null, MediaStore.Images.Media.DATE_ADDED + " DESC");
        }
        return null;
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {

        int count = 0;
        while (cursor.moveToNext()) {

            String id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            String add_date = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED));

            File file = new File(path);

            if (file != null && file.length() > 2000) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

                Date date = new Date(Long.valueOf(add_date));
                String sDate = dateFormat.format(date);

                Image image = new Image(id, path, sDate);
                mImages.add(image);
            }
        }

        //刷新适配器

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

        Log.i("wk", "当前选中的图片:" + image.getPath());


        if (position > 0) {

            //如果是选中状态就取消勾选
            if (image.isSelected) {

                image.setSelected(false);
                //从集合中将移除选中的图片
                mPaths.remove(image);


            } else {

                if (mPaths.size() >= MAX_SELECTED) {

                    Toast.makeText(mContext, "最大只能选择" + MAX_SELECTED + "张图片", Toast.LENGTH_SHORT).show();
                    return;
                }

                //进行选中图片
                image.setSelected(true);
                //将选中的图片添加到集合中去
                mPaths.add(image);
                //将选中的图片回调给外界调用的地方

                if(mImageSelectedChangedListener!=null){
                    mImageSelectedChangedListener.onSelectedChange(mImages,mImages.size());
                }

            }

            //刷新当前选中的条目
            mGalleryAdapter.updataItem(position, image);


        } else {

            Toast.makeText(mContext, "跳转到拍照页面", Toast.LENGTH_SHORT).show();









        }
    }

    @Override
    public boolean onItemLongClick(com.merpyzf.kangyuanmilk.common.widget.ViewHolder viewHolder, Image image, int position) {

        clearAllSelected();
        Toast.makeText(mContext,"清除所有",Toast.LENGTH_SHORT).show();

        return true;
    }

    /**
     * 清除所有选择
     */
    public void clearAllSelected(){

        List<Image> datas = mGalleryAdapter.getDatas();

        for (Image image : datas) {

            if(image.isSelected){
                //如果是选中状态,就将其设置为未选中状态
                image.isSelected = false;
                //刷新当前item
                mGalleryAdapter.updateItem(image);
            }

        }

        mPaths.clear();

    }



    /**
     * 使用结束的时候调用此方法销销毁Loader对象
     */
    public void destory() {

        mLoader.abandon();
    }

    /**
     * 封装Image类的信息
     */
    static class Image {

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

        public Image(String id, String path, String add_date) {
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



                    GalleryHolder galleryHolder  = new GalleryHolder(cellView);




                    return galleryHolder;

                case ITEM_TYPE_CAMERA:

                    View cellCameraView = LayoutInflater.from(mContext)
                            .inflate(R.layout.item_camera_gallery, parent, false);

                    GalleryCameraHolder galleryCameraHolder = new GalleryCameraHolder(cellCameraView);

                    return galleryCameraHolder;

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

            if (image.isSelected) {

                view_cover.setVisibility(VISIBLE);

            } else {

                view_cover.setVisibility(INVISIBLE);
            }


        }
    }

    class GalleryCameraHolder extends com.merpyzf.kangyuanmilk.common.widget.ViewHolder {

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
     * 手指触摸事件的监听，实现对图片的滑动多选
     * @param e
     * @return
     */



    private int lastPosition = -1;
    @Override
    public boolean onTouchEvent(MotionEvent e) {

        switch (e.getAction()){


            case MotionEvent.ACTION_MOVE:



                int rowPosition = (int) (e.getX()/mItemWidth);

                if(lastPosition != rowPosition){

                    Log.i("wwk","所选列所在位置的下标:"+lastPosition);

                }

                lastPosition = rowPosition;

                break;

            case MotionEvent.ACTION_UP:



                break;

        }



        return super.onTouchEvent(e);

    }




}
