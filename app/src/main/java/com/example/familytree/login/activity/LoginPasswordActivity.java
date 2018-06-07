package com.example.familytree.login.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.basiclib.HttpPath;
import com.example.basiclib.base.BaseActivity;
import com.example.basiclib.utils.apputils.PhoneNumberUtil;
import com.example.basiclib.utils.retrofitutils.ApiManager;
import com.example.basiclib.utils.retrofitutils.callback.StringCallBack;
import com.example.basiclib.utils.retrofitutils.commonparametersutils.CommonParametersUtils;
import com.example.familytree.R;
import com.example.familytree.login.bean.LoginInfo;
import com.example.toolslibrary.exception.ApiException;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * create by: wxc.
 * date:On 2018/6/5
 * 密码登录
 */
public class LoginPasswordActivity extends BaseActivity {

    private EditText phone_ed, password_ed;
    private String phone, password;
    private Button login_bt;
    private TextView register,loginPhone,forgetPassword;

    @Override
    public void initParms(Bundle params) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_passwordlogin;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        phone_ed = view.findViewById(R.id.login_phonenumber_ed);
        password_ed = view.findViewById(R.id.login_password_ed);
        login_bt = view.findViewById(R.id.loginpassword_login_bt);
        register = view.findViewById(R.id.login_register_tv);
        loginPhone = view.findViewById(R.id.login_change_message);
        forgetPassword = view.findViewById(R.id.login_forget_password);
    }

    @Override
    public void setListener() {
        login_bt.setOnClickListener(this);
        register.setOnClickListener(this);
        loginPhone.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.loginpassword_login_bt:
                phone = phone_ed.getText().toString();
                password = password_ed.getText().toString();
                if (TextUtils.isEmpty(phone) || !PhoneNumberUtil.isPhoneNumber(phone)) {
                    showToast("请输入正确的手机号码");
                } else if (TextUtils.isEmpty(password)) {
                    showToast("请输入密码");
                } else {
                    login();
                }
                break;
            case R.id.login_register_tv:
                startActivity(RegisterActivity.class);
                break;
            case R.id.login_change_message:
                startActivity(LoginPhoneActivity.class);
                break;
            case R.id.login_forget_password:
                startActivity(ForgetPasswordActivity.class);
                break;
        }

    }

    @Override
    public void doBusiness(Context mContext) {
    }


    private void login() {
        Map<String,String> params=new HashMap<>();
        params.put("phone",phone);
        params.put("password",password);
        ApiManager apiManager = new ApiManager(this, new StringCallBack() {
            @Override
            public void onResultNext(String result, String method, int status, String msg) throws Exception {
                LoginInfo loginInfo = new Gson().fromJson(result,LoginInfo.class);
                CommonParametersUtils.savePhone(getApplicationContext(),phone);
                CommonParametersUtils.saveUid(getApplicationContext(),loginInfo.getUid());
                CommonParametersUtils.saveSession(getApplicationContext(),loginInfo.getSession());
                startActivity(PerfectDataActivity.class);
            }

            @Override
            public void onResultError(ApiException e, String method) {

            }
        });
        apiManager.post(HttpPath.LOGIN_PWD,params);
    }
}
