package com.example.basiclib.utils.apputils;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * create by: wxc.
 * date:On 2018/6/6
 */
public class CreateRequestBody {
    public static MultipartBody.Part[] CreateRequestBody(List<File> params) {
        MultipartBody.Part[] file = new MultipartBody.Part[params.size()];

        for (int i = 0; i < params.size(); i++) {
            // 创建 RequestBody，用于封装构建RequestBody
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), params.get(i));
            file[i] = MultipartBody.Part.createFormData("images" + i, params.get(i).getName(), requestFile);
        }
        return file;

    }
}
