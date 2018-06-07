package com.example.basiclib.utils.retrofitutils.callback;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.basiclib.MyApplication;
import com.example.toolslibrary.exception.ApiException;
import com.example.toolslibrary.listener.OnNextCallBack;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * create by: wxc.
 * date:On 2018/6/5
 */
public abstract class StringCallBack extends OnNextCallBack {
    public Context mC;

    public StringCallBack() {
    }

    public StringCallBack(Context context) {
        this.mC = context;
    }

    @Override
    public void onNext(String result, String method) throws Exception {
        JSONObject jsonObject = null;
        String data = null;
        jsonObject = new JSONObject(result);
        int status = jsonObject.optInt("status");
        String msg = jsonObject.optString("msg");
//        JSONObject pageinfo=jsonObject.optJSONObject("pageinfo");
//        PageInfo info=null;
//        if (pageinfo!=null){
//            info=new Gson().fromJson(pageinfo.toString(),PageInfo.class);
//        }

        switch (status) {
            case 100:
                data = jsonObject.get("data").toString();
                onResultNext(data, method, status, msg);
                break;
            case 103:
                Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
                break;
            case 110:
                Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
                break;
            default:
                if (!msg.equals("session解析错误")) {
                    Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        Log.e("StringCallBack","e="+e.getDisplayMessage());
        Toast.makeText(MyApplication.getContext(),e.getDisplayMessage(),Toast.LENGTH_SHORT).show();
        onResultError(e,method);
    }

    public abstract void onResultNext(String result, String method, int status, String msg) throws Exception;

    public abstract void onResultError(ApiException e, String method);
}
