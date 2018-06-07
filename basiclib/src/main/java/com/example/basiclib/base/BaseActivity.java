package com.example.basiclib.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.example.basiclib.utils.apputils.LogUtils;
import com.example.basiclib.utils.apputils.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;


public abstract class BaseActivity extends RxAppCompatActivity implements View.OnClickListener{


    /** 是否禁止旋转屏幕 **/
    private boolean isAllowScreenRoate = true;
    /** 是否沉浸状态栏 **/
    private boolean isSetStatusBar = false;
    /** 当前Activity渲染的视图View **/
    private View mContextView = null;
    /** 日志输出标志 **/
    protected final String TAG = this.getClass().getSimpleName();
    private ImmersionBar mImmersionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        initParms(bundle);
        View mView = bindView();
        if (null == mView) {
            mContextView = LayoutInflater.from(this).inflate(bindLayout(),null);
        } else{
            mContextView = mView;
        }

        setContentView(mContextView);
//        if (isSetStatusBar) {
            steepStatusBar();
//        }
        if (isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        initView(mContextView,savedInstanceState);
        setListener();
        doBusiness(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * [初始化参数]
     *
     * @param params
     */
    public abstract void initParms(Bundle params);

    /**
     * [沉浸状态栏]
     */
    public void steepStatusBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(true);
        mImmersionBar.init();   //所有子类都将继承这些相同的属性
    }

    /**
     * 改变View透明度
     * @param alpha
     */
    public void backgroundAlpha(Activity activity, float alpha){
        WindowManager.LayoutParams lp =activity.getWindow().getAttributes();
        lp.alpha =alpha;
        getWindow().setAttributes(lp);
    }

    /**
     * string转double
     * @param str
     * @return
     */
    public double parseDouble(String str){
        double d = 0;
        if (str!=null);
        d = Double.parseDouble(str);
        return d;
    }

    /**
     * [绑定视图]
     *
     * @return
     */
    public abstract View bindView();

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * [初始化控件]
     *
     * @param view
     */
    public abstract void initView(final View view,Bundle savedInstanceState);


    /**
     * [设置监听]
     */
    public abstract void setListener();

    /**
     * [点击监听操作]
     */
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    /**
     * [业务操作]
     *
     * @param mContext
     */
    public abstract void doBusiness(Context mContext);



    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this,clz));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
    /**
     * 打印log
     * @param msg
     */
    public void showLog(String msg){
        LogUtils.e(TAG,msg);
    }


    /**
     * 弹出toast
     * @param msg
     */
    public void showToast(String msg){
        ToastUtils.showShort(msg);
    }
    public boolean isAllowScreenRoate() {
        return isAllowScreenRoate;
    }

    public void setAllowScreenRoate(boolean allowScreenRoate) {
        isAllowScreenRoate = allowScreenRoate;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
