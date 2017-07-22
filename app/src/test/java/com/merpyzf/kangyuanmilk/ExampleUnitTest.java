package com.merpyzf.kangyuanmilk;

import com.merpyzf.kangyuanmilk.utils.qiniu.Auth;

import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void myTest() {

        Auth auth = Auth.create("KXLzuFCOxgNar5whqU3-0bmrH6rTHOqaidcohRes", "aiw1aAh-dwA9k6nkWAQLExy2Taz9cE1nFYA_01WY");
        String uploadToken = auth.uploadToken("kangyuanmilk", "/avater");
        System.out.println(uploadToken);

        class user {
            String name;
            int age;

            public user(String name, int age) {
                this.name = name;
                this.age = age;
            }


        }


        user[] users = new user[]{new user("wangke", 1), new user("xiaoming", 2)};

        Arrays.sort(users, (user, t1) -> Integer.compare(user.age, t1.age));


        this.myTest1(10);

    }

    public void myTest1(int times) {

        Runnable runable = () -> {


            for (int i = 0; i < times; i++) {

                System.out.println("测试打印==》" + i);
            }


        };

        new Thread(runable).start();

        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return false;
            }
        };

        FileFilter filter = (file)->file.getPath().endsWith(".java");


    }

    @Test
    public void myTest2(){


        ArrayList<String> mList = new ArrayList<>();
        mList.add("1");
        mList.add("2");
        mList.add("3");

        mList.forEach(System.out::print);

    }

    @Test
    public void myTest3(){

        List<String> names = new ArrayList<>();
        names.add("TaoBao");
        names.add("ZhiFuBao");
/*
        List<String> collect = names.stream().map((name) -> name.toLowerCase()).collect(Collectors.toList());

        collect.forEach(System.out::println);
        */

        //filter过滤操作符号
        names.stream().filter((name)->name.startsWith("T")).forEach(System.out::println);


    }

    @Test
    public void myTest4(){





    }

}