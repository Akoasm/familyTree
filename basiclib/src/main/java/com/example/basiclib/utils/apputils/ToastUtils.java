package com.example.basiclib.utils.apputils;

import android.widget.Toast;

import com.example.basiclib.MyApplication;

/**
 * create by: wxc.
 * date:On 2018/6/5
 */
public class ToastUtils {
    public static Toast toast;

    /**
     * 短时间显示toast
     * @param msg
     */
    public static void showShort(CharSequence msg){
        if (toast==null){
            toast= Toast.makeText(MyApplication.getContext(),msg, Toast.LENGTH_SHORT);
        }else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 长时间显示toast
     * @param msg
     */
    public static void showLong(CharSequence msg){
        if (toast==null){
            toast= Toast.makeText(MyApplication.getContext(),msg, Toast.LENGTH_LONG);
        }else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 隐藏toast
     */
    public static void hideToast(){
        if (toast!=null){
            toast.cancel();
        }
    }

}
