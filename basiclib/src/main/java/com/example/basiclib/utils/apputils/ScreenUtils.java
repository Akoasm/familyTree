package com.example.basiclib.utils.apputils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by HRR on 2017/10/11.
 * 屏幕工具类，涉及到屏幕宽度、高度、密度比、(像素、dp、sp)之间的转换等。
 */

public class ScreenUtils {

    private ScreenUtils(){}

    /**
     * 获取屏幕宽度，单位为px
     * @param context   应用程序上下文
     * @return 屏幕宽度，单位px
     */
    public static int getScreenWidth(Context context){
        return getDisplayMetrics(context).widthPixels;
    }

    /**
     * 获取屏幕高度，单位为px
     * @param context   应用程序上下文
     * @return 屏幕高度，单位px
     */
    public static int getScreenHeight(Context context){
        return getDisplayMetrics(context).heightPixels;
    }

    /**
     * 获取系统dp尺寸密度值
     * @param context   应用程序上下文
     * @return
     */
    public static float getDensity(Context context){
        return getDisplayMetrics(context).density;
    }

    /**
     * 获取系统字体sp密度值
     * @param context   应用程序上下文
     * @return
     */
    public static float getScaledDensity(Context context){
        return getDisplayMetrics(context).scaledDensity;
    }

    /**
     * dip转换为px大小
     * @param context   应用程序上下文
     * @param dpValue   dp值
     * @return  转换后的px值
     */
    public static int dp2px(Context context, int dpValue){
        return (int) (dpValue * getDensity(context) + 0.5f);
    }

    /**
     * px转换为dp值
     * @param context   应用程序上下文
     * @param pxValue   px值
     * @return  转换后的dp值
     */
    public static int px2dp(Context context, int pxValue){
        return (int) (pxValue / getDensity(context) + 0.5f);
    }

    /**
     * sp转换为px
     * @param context   应用程序上下文
     * @param spValue   sp值
     * @return      转换后的px值
     */
    public static int sp2px(Context context, int spValue){
        return (int) (spValue * getScaledDensity(context) + 0.5f);
    }

    /**
     * px转换为sp
     * @param context   应用程序上下文
     * @param pxValue   px值
     * @return  转换后的sp值
     */
    public static int px2sp(Context context, int pxValue){
        return (int) (pxValue / getScaledDensity(context) + 0.5f);
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context){
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }else {
            resourceId=72;
        }
        return statusBarHeight;
    }

    /**
     * 获取DisplayMetrics对象
     * @param context   应用程序上下文
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context){
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }
}
