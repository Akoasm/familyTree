package com.example.familytree.login.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.basiclib.HttpPath;
import com.example.basiclib.base.BaseActivity;
import com.example.basiclib.utils.apputils.PhoneNumberUtil;
import com.example.basiclib.utils.retrofitutils.ApiManager;
import com.example.basiclib.utils.retrofitutils.callback.StringCallBack;
import com.example.basiclib.utils.retrofitutils.commonparametersutils.CommonParametersUtils;
import com.example.familytree.R;
import com.example.familytree.login.bean.LoginInfo;
import com.example.familytree.login.utils.GetCodeUtils;
import com.example.toolslibrary.exception.ApiException;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * create by: wxc.
 * date:On 2018/6/6
 * 手机登录
 */
public class LoginPhoneActivity extends BaseActivity {
    private EditText phone_ed,code_ed;
    private Button getCode_bt,login_bt;
    private TextView password_login_tv;
    private ImageView close;
    private String phone,code;

    @Override
    public void initParms(Bundle params) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_phonelogin;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        phone_ed = findViewById(R.id.phonelogin_phonenumber_ed);
        code_ed = findViewById(R.id.phonelogin_code_ed);
        getCode_bt = findViewById(R.id.phonelogin_getcode_bt);
        login_bt = findViewById(R.id.phonelogin_login);
        password_login_tv = findViewById(R.id.phonelogin_passwordlogin);
        close = findViewById(R.id.phonelogin_close);
    }

    @Override
    public void setListener() {
        getCode_bt.setOnClickListener(this);
        login_bt.setOnClickListener(this);
        password_login_tv.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.phonelogin_getcode_bt:
                phone = phone_ed.getText().toString().trim();
                if(!PhoneNumberUtil.isPhoneNumber(phone) || TextUtils.isEmpty(phone)){
                    showToast("请输入正确的手机号码");
                }else {
                    GetCodeUtils.getCode(phone,getCode_bt,this);
                }
                break;
            case R.id.phonelogin_login:
                phone = phone_ed.getText().toString().trim();
                code = code_ed.getText().toString().trim();
                if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(code)){
                    login();
                }else{
                    showToast("请填写完整信息");
                }
                break;
            case R.id.phonelogin_close:
                finish();
                break;
            case R.id.phonelogin_passwordlogin:
                startActivity(LoginPasswordActivity.class);
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }
    private void login(){
        Map<String,String> params=new HashMap<>();
        params.put("phone",phone);
        params.put("code",code);
        ApiManager apiManager = new ApiManager(this, new StringCallBack() {
            @Override
            public void onResultNext(String result, String method, int status, String msg) throws Exception {
                LoginInfo loginInfo = new Gson().fromJson(result,LoginInfo.class);
                CommonParametersUtils.savePhone(getApplicationContext(),phone);
                CommonParametersUtils.saveUid(getApplicationContext(),loginInfo.getUid());
                startActivity(PerfectDataActivity.class);
            }

            @Override
            public void onResultError(ApiException e, String method) {

            }
        });
        apiManager.post(HttpPath.LOGIN_CODE,params);
    }
}
