package com.meituan.android.uitool.network.factory;

import com.google.gson.Gson;
import com.sankuai.meituan.retrofit2.Retrofit;
import com.sankuai.meituan.retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/9 on 下午3:13
 */
public class PxeRetrofitFactory {

    private PxeRetrofitFactory() {
    }


    public static Retrofit getInstance(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .callFactory(PxeOkHttpFactory.getInstance())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }
}
