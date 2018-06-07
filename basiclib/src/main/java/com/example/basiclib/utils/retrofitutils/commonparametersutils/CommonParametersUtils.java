package com.example.basiclib.utils.retrofitutils.commonparametersutils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * create by: wxc.
 * date:On 2018/6/5
 */
public class CommonParametersUtils {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static final String SHARENAME="commonParameters";

    /**
     * 保存phone
     * @param context
     * @param phone
     */
    public static void savePhone(Context context,String phone){
        editor=context.getSharedPreferences(SHARENAME,Context.MODE_PRIVATE).edit();
        editor.putString("phone",phone);
        editor.commit();
    }
    /**
     * 获取phone
     * @param context
     * @return
     */
    public static String getPhone(Context context){
        preferences=context.getSharedPreferences(SHARENAME,Context.MODE_PRIVATE);
        return preferences.getString("phone","");
    }

    /**
     * 获取uid
     * @param context
     * @return
     */
    public static String getUid(Context context){
        preferences =context.getSharedPreferences(SHARENAME,Context.MODE_PRIVATE);
        return preferences.getString("uid","");
    }

    /**
     * 保存uid
     * @param context
     * @param uid
     */
    public static void saveUid(Context context,String uid){
        editor=context.getSharedPreferences(SHARENAME,Context.MODE_PRIVATE).edit();
        editor.putString("uid",uid);
        editor.commit();
    }

    /**
     * session
     * @param context
     * @return
     */
    public static String getSession(Context context){
        preferences=context.getSharedPreferences(SHARENAME,Context.MODE_PRIVATE);
        return preferences.getString("session","");
    }
    /**
     * session
     * @param context
     * @param session
     */
    public static void saveSession(Context context,String session){
        editor=context.getSharedPreferences(SHARENAME,Context.MODE_PRIVATE).edit();
        editor.putString("session",session);
        editor.commit();
    }
    /**
     * 清空登陆数据
     * @param context
     */
    public static void clearParams(Context context){
        editor=context.getSharedPreferences(SHARENAME,Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }
}
