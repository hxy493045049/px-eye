package com.meituan.android.uitool.utils;

import android.app.Application;
import android.content.Context;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/10/17 on 下午6:07
 */
public class ApplicationSingleton {
    private static volatile Application instance;

    public ApplicationSingleton() {
    }

    public static void bindInstance(Application application) {
        instance = application;
    }

    public static Context getApplicationContext() {
        if (instance != null) {
            return instance.getApplicationContext();
        } else {
            throw new IllegalStateException("没有初始化");
        }
    }

    public static Application getInstance() {
        return instance;
    }
}
