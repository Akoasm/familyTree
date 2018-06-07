package com.example.basiclib.utils.apputils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.example.basiclib.base.BaseActivity;
import com.example.basiclib.utils.retrofitutils.commonparametersutils.CommonParametersUtils;

/**
 * create by: wxc.
 * date:On 2018/6/5
 */
public class AppUtils {
    /**
     * 获取应用包名
     * @param context
     * @return
     */
    public static String getPackageName(Context context){
        return context.getPackageName();
    }

    /**
     * 获取应用程序名称
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            //这种方式是可以的
            int labelRes = applicationInfo.labelRes;
            String name = context.getResources().getString(labelRes);
            return name;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取应用程序版本名称
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            String versionName = packageInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断是否登录
     * @return
     */
    public static boolean isLogin(BaseActivity baseActivity) {
        if (!TextUtils.isEmpty(CommonParametersUtils.getUid(baseActivity))) {
            return true;
        } else {
            ToastUtils.showShort("请先登录");
            return false;
        }
    }

}
