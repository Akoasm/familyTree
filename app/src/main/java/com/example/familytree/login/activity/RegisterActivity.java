package com.example.familytree.login.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.basiclib.HttpPath;
import com.example.basiclib.utils.apputils.CountDownUtils;
import com.example.basiclib.utils.apputils.PhoneNumberUtil;
import com.example.basiclib.utils.retrofitutils.ApiManager;
import com.example.basiclib.utils.retrofitutils.callback.StringCallBack;
import com.example.familytree.R;
import com.example.basiclib.base.BaseActivity;
import com.example.familytree.login.utils.GetCodeUtils;
import com.example.toolslibrary.exception.ApiException;

import java.util.HashMap;
import java.util.Map;

/**
 * create by: wxc.
 * date:On 2018/6/5
 * 注册
 */
public class RegisterActivity extends BaseActivity {
    private EditText phone_ed,code_ed,password_ed;
    private Button getCode_bt,register_bt;
    private TextView goLogin_tv;
    private String phone,code,password;

    @Override
    public void initParms(Bundle params) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        phone_ed = findViewById(R.id.register_phonenumber_ed);
        code_ed = findViewById(R.id.register_code_ed);
        password_ed = findViewById(R.id.register_password_ed);
        getCode_bt = findViewById(R.id.register_getcode_bt);
        register_bt = findViewById(R.id.register_register_bt);
        goLogin_tv = findViewById(R.id.register_gologin);

    }

    @Override
    public void setListener() {
        getCode_bt.setOnClickListener(this);
        register_bt.setOnClickListener(this);
        goLogin_tv.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.register_getcode_bt:
                phone = phone_ed.getText().toString();
                if(TextUtils.isEmpty(phone) || !PhoneNumberUtil.isPhoneNumber(phone)){
                    showToast("请输入正确的手机号码");
                }else{
//                    getCode();
                    GetCodeUtils.getCode(phone,getCode_bt,this);
                }
                break;
            case R.id.register_register_bt:
                phone = phone_ed.getText().toString();
                password = password_ed.getText().toString();
                code = code_ed.getText().toString();
                if(TextUtils.isEmpty(phone)||TextUtils.isEmpty(password)|| TextUtils.isEmpty(code)){
                    showToast("请输入完整信息");
                }else{
                    register();
                }
                break;
            case R.id.register_gologin:
                startActivity(LoginPasswordActivity.class);
                break;
        }

    }

    @Override
    public void doBusiness(Context mContext) {

    }
    private void register(){
        Map<String,String> params=new HashMap<>();
        params.put("phone",phone);
        params.put("code",code);
        params.put("password",password);
        ApiManager apiManager = new ApiManager(this, new StringCallBack() {
            @Override
            public void onResultNext(String result, String method, int status, String msg) throws Exception {
                startActivity(LoginPasswordActivity.class);
            }

            @Override
            public void onResultError(ApiException e, String method) {

            }
        });
        apiManager.post(HttpPath.REGISTER,params);
    }

//    private void getCode(){
//        Map<String,String> params=new HashMap<>();
//        params.put("phone",phone);
//        ApiManager manager = new ApiManager(this, new StringCallBack() {
//            @Override
//            public void onResultNext(String result, String method, int code, String msg) throws Exception {
//
//                if(code == 100){
//                    CountDownUtils.countDown(getCode_bt,60);
//                }else {
//                    showToast(msg);
//                }
//            }
//
//            @Override
//            public void onResultError(ApiException e, String method) {
//
//            }
//        });
//        manager.post(HttpPath.GET_CODE,params);
//    }

}
