package com.merpyzf.kangyuanmilk.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.utils.LogHelper;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);


//        backpressure1();
//        backpressure2();

        testThread();

    }

    /**
     * 不支持背压
     */
    private void backpressure1() {
        Observable.
                create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {

                        for (int i = 0; i < 10000000; i++) {

                            e.onNext(i);

                        }

                        e.onComplete();


                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                        try {
                            Thread.sleep(50);

                            LogHelper.i("onNext==>" + integer);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * 支持背压
     */
    private void backpressure2() {

        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {

                for (int i = 0; i < 10000000; i++) {

                    e.onNext(i);
                }

                e.onComplete();

            }
        }, BackpressureStrategy.BUFFER)
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<Integer>() {

                    Subscription sub = null;

                    @Override
                    public void onSubscribe(Subscription s) {

                        sub = s;
                        s.request(1);

                        LogHelper.i("==>onSubscribe()");

                    }

                    @Override
                    public void onNext(Integer integer) {

                        try {
                            Thread.sleep(50);
                            LogHelper.i("==>onNext:" + integer);
                            sub.request(1);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    /**
     * 测试线程调度的两个方法
     */
    private void testThread() {


        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                LogHelper.i("subscribe==>" + Thread.currentThread().getName());
                e.onNext(1);

            }
        }).subscribeOn(Schedulers.computation())

                .map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                LogHelper.i("apply==>" + Thread.currentThread().getName() + " : " + integer);
                return integer + 1;
            }

        }).flatMap(new Function<Integer, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Integer integer) throws Exception {

                LogHelper.i("flatMap==>" + Thread.currentThread().getName() + " : " + integer);

                return Observable.just(integer+1);
            }
        })
                .subscribeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(integer -> LogHelper.i("accept==>" + Thread.currentThread().getName() + " : " + integer));


    }

}
