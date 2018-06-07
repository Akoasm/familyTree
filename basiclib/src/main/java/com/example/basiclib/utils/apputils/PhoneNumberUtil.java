package com.example.basiclib.utils.apputils;

import android.text.TextUtils;

/**
 * create by: wxc.
 * date:On 2018/6/5
 */
public class PhoneNumberUtil {
    /**
     * 是否为电话号码
     */
    public static boolean isPhoneNumber(String phonenumber) {
        String telRegex = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(phonenumber))
            return false;
        else return phonenumber.matches(telRegex);
    }
}
