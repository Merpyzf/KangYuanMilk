package com.merpyzf.kangyuanmilk;

import org.junit.Test;

import java.util.ArrayList;
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

    public <T>void countGreaterThan(List<T> array, T max){

        int count =0;
        for(int i=0;i<array.size();i++){

         /*   if(array.get(i)>max){

                count++;

            }*/

        }



    }
    @Test

    public void testGenericity1() {


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

}