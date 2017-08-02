package com.merpyzf.kangyuanmilk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangke on 2017-08-02.
 * //时间转换相关的工具类
 *
 */

public class TimeHelper {

    /**
     * 返回日期时间
     * @param time 秒数
     * @return String类型的时间
     */
    public static String getDateTime(long time){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDateTime = format.format(new Date(time));
        return strDateTime;

    }

}
