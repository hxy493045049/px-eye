package com.meituan.android.uitool.network;

import com.meituan.android.uitool.biz.mock.PxeMockBean;
import com.meituan.android.uitool.biz.uitest.base.UiTestUploadResult;
import com.meituan.android.uitool.network.factory.PxeRetrofitFactory;
import com.meituan.android.uitool.utils.PxeMultiPartUtil;
import com.sankuai.meituan.retrofit2.Call;
import com.sankuai.meituan.retrofit2.MultipartBody;
import com.sankuai.meituan.retrofit2.RequestBody;
import com.sankuai.meituan.retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/12 on 下午2:54
 */
public class PxeRetrofits {
    private Retrofit mockRetrofit;
    private Retrofit imageRetrofit;

    private PxeRetrofits() {
        mockRetrofit = PxeRetrofitFactory.getInstance(PxeApiConfig.PXE_MOCK_BASE);
        imageRetrofit = PxeRetrofitFactory.getInstance(PxeApiConfig.PXE_IMAGE_BASE);
    }

    public Call<PxeMockBean> getMockInfo() {
        return mockRetrofit.create(PxeApiList.PxeMockService.class).getMockInfo();
    }

    public Call<UiTestUploadResult> addImage(final byte[] img, final String user) {
        Map<String, RequestBody> param = new HashMap<>();
        param = PxeMultiPartUtil.convertType(param, "name", "rd");
        param = PxeMultiPartUtil.convertType(param, "user", user);
        MultipartBody.Part data = PxeMultiPartUtil.getRequestBody(img, "rd.png");
        return imageRetrofit.create(PxeApiList.PxeImageService.class).uploadImage(param, data);
    }

    public static PxeRetrofits getInstances() {
        return Holder.instance;
    }

    private static class Holder {
        private static PxeRetrofits instance = new PxeRetrofits();
    }
}
