package com.merpyzf.kangyuanmilk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.y;

/**
 * Created by wangke on 2017/9/6.
 * 日期相关的工具类
 */

public class CalendarUtils {


    /**
     * 获取当前系统时间的年份
     *
     * @return
     */
    public static int getCurrentYear() {

        Date date = new Date(System.currentTimeMillis());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");

        String format = dateFormat.format(date);

        return Integer.valueOf(format);
    }


    /**
     * 获取当前所在的月份
     * @return
     */
    public static int getCurrentMonth(){
        Date date = new Date(System.currentTimeMillis());

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");

        String format = dateFormat.format(date);

        return Integer.valueOf(format);
    }



    /**
     * 获取当前日期所在每周的第几天
     *
     * @param date
     * @return
     */
    public static int getDayOfWeek(String date) {

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(parseStrDate(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar.get(Calendar.DAY_OF_WEEK) - 1;

    }


    /**
     * 获取当前日期为每月的第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfMonth(String date) {


        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(parseStrDate(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar.get(Calendar.WEEK_OF_MONTH) - 1;
    }


    /**
     * 根据传入的月份获取当前月的天数
     *
     * @param month
     * @return
     */
    public static int getMonthDay(int month) {

        int months[] = new int[]{31, 30, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        //判断是否闰年

        if ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) {
            months[1] = 29;
        } else {
            months[1] = 28;
        }

        return months[month - 1];
    }


    /**
     * 将字符串类型的date转换成Date类型
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseStrDate(String date) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(date);

    }


    public static Date getCurrentDate() {
        Date date = new Date(System.currentTimeMillis());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String tempDate = dateFormat.format(date);

        Date result = null;
        try {
            result = dateFormat.parse(tempDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return result;
    }

    /**
     * 根据传入的毫秒数返回当前的时间
     *
     * @param time 毫秒数
     * @return String类型的时间
     */
    public static String getDateTime(long time) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDateTime = format.format(new Date(time));
        return strDateTime;

    }

    /**
     * 根据传入的Date类型的日期返回Str类型的 yyyy-MM-dd 时间
     *
     * @param date
     * @return
     */
    public static String getDate(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(date);
        return strDate;
    }

    /**
     * 计算两个日期间的天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getIntervalDay(Calendar startDate, Calendar endDate) {

        int day = (int) ((endDate.getTimeInMillis() - startDate.getTimeInMillis()) / (1000 * 3600 * 24));

        LogHelper.i("日期间的间隔天数==>"+day);

        return day;

    }




}
