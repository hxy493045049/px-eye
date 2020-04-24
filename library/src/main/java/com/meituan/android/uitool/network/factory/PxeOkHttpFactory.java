package com.meituan.android.uitool.network.factory;

import com.sankuai.meituan.retrofit2.callfactory.okhttp.OkHttpCallFactory;
import com.sankuai.meituan.retrofit2.raw.RawCall;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/9 on 下午3:34
 * 目前只支持okhttp, nvGlobal没有配置
 * 相关文档详见: https://km.sankuai.com/page/28302097
 */
public class PxeOkHttpFactory {
    private static final long TIME_OUT_LIMIT = 60;
    private static final long READ_OUT_LIMIT = 60;
    private static final long WRITE_OUT_LIMIT = 60;

    private PxeOkHttpFactory() {
    }

    public static RawCall.Factory getInstance() {
        return Holder.instance;
    }

    private static RawCall.Factory init() {
        OkHttpClient client = createOkHttpClientInstance();
        return OkHttpCallFactory.create(client);
    }

    private static OkHttpClient createOkHttpClientInstance() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(TIME_OUT_LIMIT, TimeUnit.SECONDS);
        client.setReadTimeout(READ_OUT_LIMIT, TimeUnit.SECONDS);
        client.setWriteTimeout(WRITE_OUT_LIMIT, TimeUnit.SECONDS);
        return client;
    }

    private static class Holder {
        private static RawCall.Factory instance = PxeOkHttpFactory.init();
    }
}
