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
import com.example.familytree.R;
import com.example.familytree.login.utils.GetCodeUtils;
import com.example.toolslibrary.exception.ApiException;

import java.util.HashMap;
import java.util.Map;

/**
 * create by: wxc.
 * date:On 2018/6/5
 * 忘记密码
 */
public class ForgetPasswordActivity extends BaseActivity {
    private EditText code_ed,password_ed,again_password_ed,phone_ed;
    private Button getCode_bt,commit_bt;
    private String code,password,again_password,phone;
    private ImageView back;
    private TextView title;

    @Override
    public void initParms(Bundle params) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        back = findViewById(R.id.back);
        title = findViewById(R.id.titleLayout_title_tv);
        title.setText("忘记密码");
        code_ed = findViewById(R.id.forget_code_ed);
        password_ed = findViewById(R.id.forget_password_ed);
        again_password_ed = findViewById(R.id.forget_again_password_ed);
        phone_ed = findViewById(R.id.forget_phone_ed);
        getCode_bt = findViewById(R.id.forget_getcode_bt);
        commit_bt = findViewById(R.id.forget_commit_bt);
    }

    @Override
    public void setListener() {
        back.setOnClickListener(this);
        getCode_bt.setOnClickListener(this);
        commit_bt.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.forget_getcode_bt:
                phone = phone_ed.getText().toString();
                if(TextUtils.isEmpty(phone) || !PhoneNumberUtil.isPhoneNumber(phone)){
                    showToast("请输入正确的手机号码");
                }else{
                    GetCodeUtils.getCode(phone,getCode_bt,this);
                }
                break;
            case R.id.forget_commit_bt:
                phone = phone_ed.getText().toString();
                code = code_ed.getText().toString();
                password = password_ed.getText().toString();
                again_password = again_password_ed.getText().toString();
                if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(code) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(again_password)){
                if(!password.equals(again_password)){
                    showToast("两次密码输入不一致");
                }else{
                    commit();
                }
                }else{
                    showToast("请填写完整信息");
                }
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    private void commit(){
        Map<String,String> params=new HashMap<>();
        params.put("phone",phone);
        params.put("code",code);
        params.put("password",password);
        ApiManager apiManager = new ApiManager(this, new StringCallBack() {
            @Override
            public void onResultNext(String result, String method, int status, String msg) throws Exception {

            }

            @Override
            public void onResultError(ApiException e, String method) {

            }
        });
        apiManager.post(HttpPath.FORGET_PASSWORD,params);
    }

}
