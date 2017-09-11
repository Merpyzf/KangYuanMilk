package com.merpyzf.kangyuanmilk.ui.home.model;

import com.merpyzf.kangyuanmilk.ui.home.bean.ContentBean;

import io.reactivex.Observable;

/**
 * Created by 春水碧于天 on 2017/9/11.
 */

public interface IContentModel {


    Observable<ContentBean> getActivityContentById(int activityId);


}
