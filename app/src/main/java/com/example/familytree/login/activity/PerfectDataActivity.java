package com.example.familytree.login.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.basiclib.HttpPath;
import com.example.basiclib.base.BaseActivity;
import com.example.basiclib.utils.apputils.ChooseImageUtils;
import com.example.basiclib.utils.apputils.CreateRequestBody;
import com.example.basiclib.utils.apputils.LogUtils;
import com.example.basiclib.utils.apputils.ToastUtils;
import com.example.basiclib.utils.imageUtils.ImageUtils;
import com.example.basiclib.utils.permissionutils.PermissionCall;
import com.example.basiclib.utils.permissionutils.PermissionUtils;
import com.example.basiclib.utils.retrofitutils.ApiManager;
import com.example.basiclib.utils.retrofitutils.callback.StringCallBack;
import com.example.basiclib.utils.retrofitutils.commonparametersutils.CommonParametersUtils;
import com.example.basiclib.view.PickerViewUtils;
import com.example.familytree.R;
import com.example.toolslibrary.exception.ApiException;
import com.tbruyelle.rxpermissions.Permission;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by: wxc.
 * date:On 2018/6/6
 * 完善个人信息
 */
public class PerfectDataActivity extends BaseActivity {

    private ImageView head,birthday_details;
    private TextView title, complete,birthday_tv;
    private EditText name_ed, IDnumber_ed, phone_ed, hometown_ed, duties_ed;
    private RelativeLayout birthday_re, education_re;
    private String name, IDnumber, phone, hometown, duties, birthday, education;
    private static final int REQUEST_CODE_CHOOSE = 23;
    private List<File> list;

    @Override
    public void initParms(Bundle params) {
        list = new ArrayList<File>();
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_perfect_data;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void initView(View view, Bundle savedInstanceState) {
        head = findViewById(R.id.personal_head_img);
        title = findViewById(R.id.titleLayout_title_tv);
        complete = findViewById(R.id.rightIcon_tv);
        title.setText("完善个人信息");
        complete.setText("完成");
        complete.setTextColor(R.color.colorGray);
        name_ed = findViewById(R.id.personal_name_ed);
        IDnumber_ed = findViewById(R.id.personal_IDcard_ed);
        phone_ed = findViewById(R.id.personal_phone_ed);
        hometown_ed = findViewById(R.id.personal_homeplace_ed);
        duties_ed = findViewById(R.id.personal_duties_ed);
        education_re = findViewById(R.id.personal_education_re);
        birthday_re = findViewById(R.id.personal_birthday_re);
        birthday_tv = findViewById(R.id.personal_birthday_tv);
        birthday_details = findViewById(R.id.personal_birthday_details);
        phone = CommonParametersUtils.getPhone(this);
        phone_ed.setText(phone);
    }

    @Override
    public void setListener() {
        head.setOnClickListener(this);
        education_re.setOnClickListener(this);
        birthday_re.setOnClickListener(this);
        complete.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.personal_head_img:
                chooseImage();
                break;
            case R.id.personal_education_re:

                break;
            case R.id.personal_birthday_re:
                PickerViewUtils.initTimePicker(this, new boolean[]{true, true, true, false, false, false}, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        birthday_details.setVisibility(View.GONE);
                        birthday_tv.setText(PickerViewUtils.getdayTime(date));
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("time", date.getTime() + "");
                    }
                });
                break;
            case R.id.rightIcon_tv:
                name= name_ed.getText().toString().trim();
                IDnumber = IDnumber_ed.getText().toString().trim();
                hometown = hometown_ed.getText().toString().trim();
                duties = duties_ed.getText().toString().trim();
                birthday = birthday_tv.getText().toString().trim();
                update();
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    private void update() {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("birthday",birthday);
        params.put("hometown", hometown);
        params.put("post", duties);
        params.put("idnumber",IDnumber);
        params.put("updateuid",CommonParametersUtils.getUid(this));
        params.put("education", "本科");
        params.put("files", String.valueOf(list));
        ApiManager apiManager = new ApiManager(this, new StringCallBack() {
            @Override
            public void onResultNext(String result, String method, int status, String msg) throws Exception {
            }

            @Override
            public void onResultError(ApiException e, String method) {

            }
        });
        if(list == null){
            apiManager.post(HttpPath.UPDATE_PERSONALDATA, params);
        }else {
            apiManager.postFile(HttpPath.UPDATE_PERSONALDATA,params, CreateRequestBody.CreateRequestBody(list));
        }
    }

    /**
     * 选择头像
     */
    private void chooseImage() {
        PermissionUtils.permissonMoreAll(this, new PermissionCall() {
            @Override
            public void success() {
                ChooseImageUtils.chooseImage(PerfectDataActivity.this, REQUEST_CODE_CHOOSE, 1);
            }

            @Override
            public void fail() {
                showToast("缺少必要权限");
            }

            @Override
            public void next(Permission permission) {

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            ImageUtils.showCircleImageAsUri(this, Matisse.obtainResult(data).get(0), head);
            list.clear();
            list.add(new File(Matisse.obtainPathResult(data).get(0)));
        }
    }
}
