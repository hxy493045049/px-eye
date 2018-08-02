package com.meituan.android.uitool;

import android.app.Application;

import com.meituan.android.base.BaseConfig;
import com.meituan.android.singleton.ApplicationSingleton;

/**
 * Author: gaojin
 * Time: 2018/8/2 上午11:14
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationSingleton.bindInstance(this);
        BaseConfig.init(this, "", "");
    }
}
