package com.example.toolslibrary.http.func;

import android.util.Log;

import com.example.toolslibrary.exception.FactoryException;

import rx.Observable;
import rx.functions.Func1;

/**
 * author: WXC .
 * date:   On 2018/6/5
 */
public class ExceptionFunc<String> implements Func1<Throwable, Observable<String>> {
    @Override
    public Observable<String> call(Throwable throwable) {
        Log.e("Tag","-------->"+throwable.getMessage());
        return Observable.error(FactoryException.analysisExcetpion(throwable));
    }
}
