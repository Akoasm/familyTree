package com.example.toolslibrary.http;

import android.app.Dialog;

import com.example.toolslibrary.api.BaseApi;
import com.example.toolslibrary.exception.RetryWhenNetworkException;
import com.example.toolslibrary.http.func.ExceptionFunc;
import com.example.toolslibrary.http.func.ResultFunc;
import com.example.toolslibrary.interceptor.LoggingInterceptor;
import com.example.toolslibrary.listener.OnNextCallBack;
import com.example.toolslibrary.subscribers.RetrofitUtilSubscribers;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApiUtils {
    //使用软引用对象
    private OnNextCallBack onNextCallBack;
    private SoftReference<RxAppCompatActivity> appCompatActivity;
    private Dialog dialog;
    private OkHttpClient.Builder builder;
    public ApiUtils(RxAppCompatActivity appCompatActivity,OnNextCallBack onNextCallBack) {
        this.onNextCallBack = onNextCallBack;
        this.appCompatActivity = new SoftReference<>(appCompatActivity);
    }

    public OkHttpClient.Builder getBuilder() {
        return builder;
    }

    public void setBuilder(OkHttpClient.Builder builder) {
        this.builder = builder;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    /**
     * 获取retrofit实例
     * @return
     */
    public Retrofit getRetrofit(BaseApi api){
        if (builder==null){
            builder = new OkHttpClient.Builder();
        }
        //设置超时时间缓存等设置
        builder.connectTimeout(api.getConnectionTime(), TimeUnit.SECONDS);
        builder.addInterceptor(new LoggingInterceptor());
        /*创建retrofit对象*/
        final Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(api.getBaseUrl())
                .build();
        return retrofit;
    }
    public void httpDeal(Observable observable, BaseApi api){
        observable=observable
                /*请求失败后retry的设置*/
                .retryWhen(new RetryWhenNetworkException(api.getRetryCount(),api.getRetryDelay(),api.getRetryIncreaseDelay()))
                /*异常处理*/
                .onErrorResumeNext(new ExceptionFunc())
                /*返回数据统一判断*/
                .map(new ResultFunc())
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*生命周期管理*/
                .compose(appCompatActivity.get().bindToLifecycle())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread());
        //回调结果（弹出加载框）
        if (onNextCallBack!=null){
            RetrofitUtilSubscribers retrofitUtilSubscribers=new RetrofitUtilSubscribers(dialog,api,appCompatActivity,onNextCallBack);
            observable.subscribe(retrofitUtilSubscribers);
        }
    }
}
