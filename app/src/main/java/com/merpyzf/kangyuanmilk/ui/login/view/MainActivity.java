package com.merpyzf.kangyuanmilk.ui.login.view;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.merpyzf.kangyuanmilk.R;
import com.merpyzf.kangyuanmilk.common.BaseActivity;
import com.merpyzf.kangyuanmilk.common.api.KangYuanApi;
import com.merpyzf.kangyuanmilk.common.widget.TipView;
import com.merpyzf.kangyuanmilk.ui.login.model.User;
import com.merpyzf.kangyuanmilk.ui.login.presenter.LoginPresenterImpl;
import com.merpyzf.kangyuanmilk.utils.http.RetrofitFactory;

import java.io.IOException;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity implements ILogin, View.OnClickListener {

    @BindView(R.id.edt_user)
    EditText edt_user;
    @BindView(R.id.edt_pwd)
    EditText edt_pwd;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.btn_clear)
    Button btn_clear;
    @BindView(R.id.tipView)
    TipView tipView;


    private LoginPresenterImpl loginPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initWidget() {

        loginPresenter = new LoginPresenterImpl(this);


    }

    @Override
    protected void initData() {
//


        tipView.netErrorTip("没网啦");
//        tipView.emptyTip("null");
//       tipView.loading();
    }

    @Override
    public void initEvent() {

        btn_login.setOnClickListener(this);
        btn_clear.setOnClickListener(this);


        KangYuanApi kangYuanApi = RetrofitFactory.getServiceInstance();

        kangYuanApi.getJson(new com.merpyzf.kangyuanmilk.common.bean.User("wangke","wangke"))
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody value) {

                        try {
                            Log.i("wk","返回过来的数据:"+value.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.i("wk","出错了");
                    }

                    @Override
                    public void onComplete() {

                    }
                });




        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

                e.onNext("hahaha");

            }
        }).flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {

                Log.i("wk","flatmap===> "+s);
                //将Observer发送过来的事件处理之后,又重新创建了一个Observable来发送事件
                return Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onNext("flatmap");
                    }
                });
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

                Log.i("wk","Observer==>"+s);
            }
        });


    }


    @Override
    public void onLoginResult(boolean result, String code) {


        if (result) {

            Toast.makeText(this, "登录成功： " + code, Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "登录失败： " + code, Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void clearText() {

        edt_user.setText("");
        edt_pwd.setText("");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_login:

                String userName = edt_user.getText().toString();
                String pwd = edt_pwd.getText().toString().trim();


                loginPresenter.doLogin(new User(userName, pwd));

                break;

            case R.id.btn_clear:

                loginPresenter.clear();

                break;

        }

    }


    public Retrofit UserRetrofit() {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, java.util.concurrent.TimeUnit.SECONDS);
        builder.connectTimeout(9, java.util.concurrent.TimeUnit.SECONDS);
        //添加日志调试功能
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);


        return new Retrofit.Builder().baseUrl("http://www.baidu.com")
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }


}
