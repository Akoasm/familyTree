package com.example.basiclib.utils.apputils;

import android.content.pm.ActivityInfo;

import com.example.basiclib.R;
import com.example.basiclib.base.BaseActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

/**
 * create by: wxc.
 * date:On 2018/6/6
 */
public class ChooseImageUtils {
    /**
     *
     * @param activity
     * @param code
     * @param number
     */
    public static void chooseImage(final BaseActivity activity, final int code, int number) {
        Matisse.from(activity)
                .choose(MimeType.ofImage())
                .theme(R.style.Matisse_Dracula)
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.example.familytree.fileprovider"))
                .maxSelectable(number)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new MyGlideEngine())
                .forResult(code);
    }
}
