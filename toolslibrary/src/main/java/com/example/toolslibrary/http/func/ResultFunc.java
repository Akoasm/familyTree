package com.example.toolslibrary.http.func;

import com.example.toolslibrary.exception.HttpTimeException;

import rx.functions.Func1;

/**
 * author: WXC .
 * date:   On 2018/6/5
 */
public class ResultFunc implements Func1<Object,Object> {
    @Override
    public Object call(Object o) {
        if (o == null || "".equals(o.toString())) {
            throw new HttpTimeException("数据错误");
        }
        return o;
    }
}
