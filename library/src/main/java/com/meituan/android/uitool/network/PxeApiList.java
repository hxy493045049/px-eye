package com.meituan.android.uitool.network;

import com.meituan.android.uitool.biz.mock.PxeMockBean;
import com.meituan.android.uitool.biz.uitest.base.UiTestUploadResult;
import com.sankuai.meituan.retrofit2.Call;
import com.sankuai.meituan.retrofit2.MultipartBody;
import com.sankuai.meituan.retrofit2.RequestBody;
import com.sankuai.meituan.retrofit2.http.GET;
import com.sankuai.meituan.retrofit2.http.Multipart;
import com.sankuai.meituan.retrofit2.http.POST;
import com.sankuai.meituan.retrofit2.http.Part;
import com.sankuai.meituan.retrofit2.http.PartMap;

import java.util.Map;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/12 on 上午11:09
 */
public class PxeApiList {
    public interface PxeMockService {
        @GET("food/mocks")
        Call<PxeMockBean> getMockInfo();
    }

    public interface PxeImageService {
        @Multipart
        @POST("api/uicheck/addImage")
        Call<UiTestUploadResult> uploadImage(@PartMap(encoding = "8bit") Map<String, RequestBody> paramMap, @Part MultipartBody.Part multipartBody);
    }
}
