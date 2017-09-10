package com.merpyzf.kangyuanmilk;

import com.merpyzf.kangyuanmilk.ui.home.view.GoodsDetailActivity;
import com.merpyzf.kangyuanmilk.utils.CalendarUtils;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.Subject;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void myTest() {


        String path = "http://otdmrup4y.bkt.clouddn.com/avatar/52f3187f518f7106a82b5578424ca6cf";

        String[] split = path.split("/");

        String avater = split[split.length - 2] + "/" + split[split.length - 1];


        List<String> mNewsDataList = new ArrayList<>();

        List<String> mLastDataList = new ArrayList<>();


        for (int i = 0; i < mNewsDataList.size(); i++) {

            mNewsDataList.add("new==>" + i);


        }


    }

    @Test
    public void myTest1() {


        GoodsDetailActivity goodsDetailActivity = new GoodsDetailActivity();

        System.out.println(goodsDetailActivity.initGoodParams(2));


    }


    /**
     * 泛型方法
     *
     * @param t
     * @param <T>
     */
    public <T extends Integer> void myTest2(T t) {

        System.out.println(t);

    }

    @Test
    public void testGenericity() {


        class Father<T1, T2> {


            T1 age;
            T2 name;

            public void getAge() {
                System.out.println(age);
            }

            public void getName() {
                System.out.println(name);
            }
        }
        Father<String, Integer> father1 = new Father<>();
        Father<Integer, String> father2 = new Father<>();

        System.out.println(father1.getClass() == father2.getClass());


    }

    public <T> void countGreaterThan(List<T> array, T max) {

        int count = 0;
        for (int i = 0; i < array.size(); i++) {

         /*   if(array.get(i)>max){

                count++;

            }*/

        }


    }

    @Test
    public void testCalender() {
        try {
            String dateString = "2017-09-9";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int week = calendar.get(Calendar.WEEK_OF_MONTH);
           System.out.println("第"+week+"周的第"+(calendar.get(Calendar.DAY_OF_WEEK)) +"天");

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testRxJava1() {

        Subject.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {

                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {


                System.out.println("accept==>" + integer);

            }
        });


    }

    @Test
    public void test(){


        //获取当前时间的下一个月的开始与结束时间

        //获取当前时候的下两个月的开始与结束时间

        //获取当前时间的下三个月的开始月结束时间

        getNextMonthStartAndEnd(0);


    }


    public void getNextMonthStartAndEnd(int next){

        //当前的系统时间
        Calendar curralendar = Calendar.getInstance();
        //设置为当前系统的时间
        curralendar.setTime(new Date(System.currentTimeMillis()));

        curralendar.set(Calendar.MONTH,curralendar.get(Calendar.MONTH)+1);
        curralendar.set(Calendar.DAY_OF_MONTH,1);

        Date startDate = curralendar.getTime();
        String start = CalendarUtils.getDate(startDate);

        System.out.println("开始日期: "+start);


        Calendar cloneCalendar = (Calendar) curralendar.clone();

        //起始的月份
        int startMonth  = cloneCalendar.get(Calendar.MONTH);

        int leftMonth = 11-startMonth;

        System.out.println("剩下的月份："+leftMonth);

        if(next>leftMonth){

            int numYear = (int) Math.ceil((next-leftMonth)/(12*1.0f));


            System.out.println("几年 == 》"+numYear);

            int numMonth = (next - leftMonth)%12;
            System.out.println("几月 == 》"+numMonth);

            cloneCalendar.set(Calendar.YEAR, cloneCalendar.get(Calendar.YEAR)+numYear);
            cloneCalendar.set(Calendar.MONTH,numMonth-1);
            cloneCalendar.set(Calendar.DAY_OF_MONTH,CalendarUtils.getMonthDay(cloneCalendar.get(Calendar.YEAR), cloneCalendar.get(Calendar.MONTH)+1));


        }else {

            //表示仍然在当前的年份当中
            cloneCalendar.set(Calendar.MONTH,cloneCalendar.get(Calendar.MONTH)+next);
            //设置月份为当前月的最后一天
            cloneCalendar.set(Calendar.DAY_OF_MONTH,CalendarUtils.getMonthDay(cloneCalendar.get(Calendar.YEAR), cloneCalendar.get(Calendar.MONTH)+1));

        }

        //年
        int year = cloneCalendar.get(Calendar.YEAR);
        //月
        int month = cloneCalendar.get(Calendar.MONTH)+1;
        //日
        int day = cloneCalendar.get(Calendar.DAY_OF_MONTH);

        System.out.println(CalendarUtils.getDate(cloneCalendar.getTime()));










    }


}