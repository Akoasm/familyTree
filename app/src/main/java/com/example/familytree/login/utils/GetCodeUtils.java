package com.example.familytree.login.utils;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;


import com.example.basiclib.HttpPath;
import com.example.basiclib.base.BaseActivity;
import com.example.basiclib.utils.apputils.CountDownUtils;
import com.example.basiclib.utils.retrofitutils.ApiManager;
import com.example.basiclib.utils.retrofitutils.callback.StringCallBack;
import com.example.toolslibrary.exception.ApiException;

import java.util.HashMap;
import java.util.Map;

/**
 * create by: wxc.
 * date:On 2018/6/6
 */
public class GetCodeUtils {

    public static void getCode(String phone,final View view,final BaseActivity activity){
        Map<String,String> params=new HashMap<>();
        params.put("phone",phone);
        ApiManager manager = new ApiManager(activity, new StringCallBack() {
            @Override
            public void onResultNext(String result, String method, int status, String msg) throws Exception {

                if(status == 100){
                    CountDownUtils.countDown((TextView) view,60);
                }else {
                    activity.showToast(msg);
                }
            }

            @Override
            public void onResultError(ApiException e, String method) {

            }
        });
        manager.post(HttpPath.GET_CODE,params);
    }
}
