package com.example.toolslibrary.listener;

import com.example.toolslibrary.exception.ApiException;

/**
 * author: WXC .
 * date:   On 2018/6/5
 */
public abstract class OnNextCallBack {
    /**
     * 回调成功
     */
    public abstract void onNext(String result, String method) throws Exception;

    /**
     * 回调失败
     */
    public abstract void onError(ApiException e, String method);
}
