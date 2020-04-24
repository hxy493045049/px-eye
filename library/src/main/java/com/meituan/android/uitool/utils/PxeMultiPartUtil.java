package com.meituan.android.uitool.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.sankuai.meituan.retrofit2.MultipartBody;
import com.sankuai.meituan.retrofit2.RequestBody;
import com.sankuai.meituan.retrofit2.RequestBodyBuilder;

import java.util.Map;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/12 on 下午3:11
 */
public class PxeMultiPartUtil {
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final String IMAGE_TYPE = "image/jpg";
    private static final String TEXT_TYPE = "text/plain";

    public static MultipartBody.Part getRequestBody(final byte[] img, String fileName) {
        RequestBody body = RequestBodyBuilder.build(img, IMAGE_TYPE);
        return MultipartBody.Part.createFormData("image", fileName, body);
    }

    //由于@Part注解默认的Content-Type类型是“application/json”, 但是服务器需要的是“text/plain”类型,
    //所以需要手动转一下
    public static Map<String, RequestBody> convertType(@NonNull Map<String, RequestBody> params, String key, String value) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
            return params;
        }
        params.put(key, RequestBodyBuilder.build(value.getBytes(), TEXT_TYPE));
        return params;
    }
}
