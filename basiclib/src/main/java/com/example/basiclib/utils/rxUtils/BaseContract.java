package com.example.basiclib.utils.rxUtils;

/**
 * create by: wxc.
 * date:On 2018/6/5
 */
public interface BaseContract {
    interface BasePresenter<T> {

        void attachView(T view);

        void detachView();
    }

    interface BaseView {

    }
}
