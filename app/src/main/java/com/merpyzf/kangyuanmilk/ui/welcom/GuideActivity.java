package com.merpyzf.kangyuanmilk.ui.welcom;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itsronald.widget.ViewPagerIndicator;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.common.widget.ColorShades;
import com.merpyzf.kangyuanmilk.ui.user.view.HomeActivity;
import com.merpyzf.kangyuanmilk.utils.SharedPreHelper;

import butterknife.BindView;

/**
 * Created by wangke on 2017-07-21.
 * 新手引导页
 */

public class GuideActivity extends BaseActivity {


    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.view_pager_indicator)
    ViewPagerIndicator mViewPagerIndicator;
    @BindView(R.id.rootView)
    RelativeLayout mRootView;
    @BindView(R.id.tv_start)
    TextView mTvStart;

    private Context mContext = null;


    public static void startAction(Context context) {

        context.startActivity(new Intent(context, GuideActivity.class));

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initWidget() {

        mContext = this;
        mViewPager.setAdapter(new GuidePagerAdapter(R.array.guide_icos, R.array.guide_titles, R.array.guide_hints));
        mViewPager.setPageTransformer(true, new CustomPageTransformer());

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                int[] landingBg = mContext.getResources().getIntArray(R.array.landing_bg);

                //根据手指滑动修改页面的背景颜色
                ColorShades shades = new ColorShades();
                shades.setFromColor(landingBg[position % landingBg.length])
                        .setToColor(landingBg[(position + 1) % landingBg.length])
                        .setShade(positionOffset);

                //当滑动到倒数第二个页面的时候开始显示 开始体验 按钮
                if (position == landingBg.length - 2) {

                    if (positionOffset == 0) {

                        mTvStart.setVisibility(View.GONE);
                    } else {

                        mTvStart.setVisibility(View.VISIBLE);
                    }

                    mTvStart.setAlpha(positionOffset);

                }

                //设置背景颜色
                mRootView.setBackgroundColor(shades.generate());

            }

        });

    }

    @Override
    public void initEvent() {

        mTvStart.setOnClickListener(view -> {

            SharedPreHelper.saveUseActions();
            HomeActivity.startAction(this);
            finish();


        });

    }

    @Override
    protected void initData() {

    }

    /**
     * 引导页的ViewPager适配器
     */
    class GuidePagerAdapter extends android.support.v4.view.PagerAdapter {
        //ico
        private int guideIcos;
        //大标题
        private String[] guideTitles;
        //小标题
        private String[] guideHints;

        public GuidePagerAdapter(int guideIcos, int guideTitles, int guideHints) {
            this.guideIcos = guideIcos;
            this.guideTitles = mContext.getResources().getStringArray(guideTitles);
            this.guideHints = mContext.getResources().getStringArray(guideHints);
        }

        @Override
        public int getCount() {

            return guideTitles.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.view_pager_guide, container, false);

            ImageView ivGuideIco = view.findViewById(R.id.landing_img_slide);
            TextView tv_title = view.findViewById(R.id.landing_txt_title);
            TextView tv_hint = view.findViewById(R.id.landing_txt_hint);


            Drawable icon = getResources().obtainTypedArray(guideIcos).getDrawable(position);

            if (position != guideTitles.length - 1) {
                ivGuideIco.setImageDrawable(icon);
            }

            tv_title.setText(guideTitles[position]);
            tv_hint.setText(guideHints[position]);
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    class CustomPageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            View imageView = view.findViewById(R.id.landing_img_slide);
            View contentView = view.findViewById(R.id.landing_txt_hint);
            View txt_title = view.findViewById(R.id.landing_txt_title);

            if (position < -1) { // [-Infinity,-1)

            } else if (position <= 0) { // [-1,0]
                view.setTranslationX(pageWidth * -position);
                if (contentView != null) {

                    contentView.setTranslationX(pageWidth * position);
                    txt_title.setTranslationX(pageWidth * position);

                    contentView.setAlpha(1 + position);
                    txt_title.setAlpha(1 + position);
                }

                if (imageView != null) {

                    imageView.setAlpha(1 + position);
                }

            } else if (position <= 1) { // (0,1]
                view.setTranslationX(pageWidth * -position);

                if (contentView != null) {
                    contentView.setTranslationX(pageWidth * position);
                    txt_title.setTranslationX(pageWidth * position);

                    contentView.setAlpha(1 - position);
                    txt_title.setAlpha(1 - position);
                }
                if (imageView != null) {
                    imageView.setAlpha(1 - position);
                }
            }
        }
    }

}
