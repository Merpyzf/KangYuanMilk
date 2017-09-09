package com.merpyzf.kangyuanmilk.common.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.utils.LogHelper;
import com.merpyzf.kangyuanmilk.utils.ui.TransStatusBottomSheetDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wangke on 2017/9/9.
 * 商品参数的选择
 */

public class GoodsParamsPickerFragment extends BottomSheetDialogFragment implements View.OnClickListener {


    private String mGoodsImage;
    private String mGoodsName;
    private int mGoodsPrice;
    private String mGoodsSpec;
    private int mGoodsNum; //商品的数量

    private Unbinder mUnbinder;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.iv_goods)
    ImageView mIvGoods; //商品图片
    @BindView(R.id.tv_goods_name)
    TextView mTvGoodsName; //商品名字
    @BindView(R.id.tv_goods_price)
    TextView mTvGoodsPrice; //商品的价格
    @BindView(R.id.iv_goods_thumbnail)
    ImageView mIvGoodsThumb; //商品图片的缩略图
    @BindView(R.id.tv_spec)
    TextView mTvSpec; //规格
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.tv_goods_num)
    TextView mTvGoodsNum;
    @BindView(R.id.tv_reduce)
    TextView mTvReduce;
    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;

    private int mTimeChoiceState = 0;


    public GoodsParamsPickerFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_goods_params_picker, container, false);


        mUnbinder = ButterKnife.bind(this, view);
        initWidget();
        initEvent();


        return view;
    }

    private void initEvent() {

        mBtnNext.setOnClickListener(this);

        mTvAdd.setOnClickListener(this);
        mTvReduce.setOnClickListener(this);

        mRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {

            switch (i){


                case R.id.rb_picker_calendar:

                    LogHelper.i("rb_picker_calendar");
                    mTimeChoiceState = 0;
                    mBtnNext.setText("下一步");

                    break;

                case R.id.rb_one_month:
                    LogHelper.i("rb_one_month");
                    mTimeChoiceState = 1;
                    mBtnNext.setText("确定");

                    break;
                case R.id.rb_two_month:
                    LogHelper.i("rb_two_month");
                    mTimeChoiceState = 2;
                    mBtnNext.setText("确定");
                    break;
                case R.id.rb_three_month:
                    LogHelper.i("rb_three_month");
                    mTimeChoiceState = 3;
                    mBtnNext.setText("确定");
                    break;

            }


        });


    }

    private void initWidget() {

        Bundle arguments = getArguments();

        mGoodsName = (String) arguments.get("goods_name");
        mGoodsImage = (String) arguments.get("goods_image");
        mGoodsPrice = (int) arguments.get("goods_price");
        mGoodsSpec = (String) arguments.get("goods_spec");

        mTvGoodsName.setText(mGoodsName);
        mTvGoodsPrice.setText("价格: " + mTvGoodsPrice);
        mTvGoodsPrice.setText("规格: " + mGoodsSpec);

        loadImage(mIvGoods, mGoodsImage);
        loadImage(mIvGoodsThumb, mGoodsImage);

    }

    private void loadImage(ImageView imageView, String mGoodsImage) {


        Glide.with(getContext())
                .load(mGoodsImage)
                .into(imageView);


    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TransStatusBottomSheetDialog(getContext());
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_next:


                dismiss();

                if(mTimeChoiceState == 0) {

                    CalendarPickerFragment calendarPickerFragment = new CalendarPickerFragment();
                    calendarPickerFragment.show(getFragmentManager(), "tag");

                }








                // TODO: 2017/9/9 将已经选择的商品参数回调给商品详情的activity

                break;


            case R.id.tv_add:

                mGoodsNum++;

                mTvGoodsNum.setText(mGoodsNum+"");


                break;


            case R.id.tv_reduce:


                if (mGoodsNum > 1) {

                    mGoodsNum--;
                    mTvGoodsNum.setText(mGoodsNum+"");
                }



                break;

        }


    }





    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
