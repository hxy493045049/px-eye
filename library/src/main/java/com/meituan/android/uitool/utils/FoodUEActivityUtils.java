package com.meituan.android.uitool.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.meituan.android.uitool.FoodUETool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Author: gaojin
 * Time: 2018/6/25 下午6:04
 */
public class FoodUEActivityUtils {

    private FoodUEActivityUtils() {
    }

    public static int getStatusBarHeight() {
        Resources resources = FoodUETool.getApplicationContext().getResources();
        int resId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resId > 0 ? resources.getDimensionPixelSize(resId) : 0;
    }

    public static void enableFullscreen(@NonNull Window window) {
        if (Build.VERSION.SDK_INT >= 21) {
            View view = window.getDecorView();
            if (view != null) {
                view.setSystemUiVisibility(view.getSystemUiVisibility() | 1280);
            }
        }
    }

    public static void setStatusBarColor(@NonNull Window window, int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(color);
        }
    }

    /**
     * 通过反射获取最上层的activity, activity必须执行完oncreate才能被获取
     *
     * @return 最上层的activity
     */
    @SuppressLint("PrivateApi")
    public static Activity getCurrentActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getMethod("currentActivityThread");
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);
            Field mActivitiesField = activityThreadClass.getDeclaredField("mActivities");
            mActivitiesField.setAccessible(true);
            Map activities = (Map) mActivitiesField.get(currentActivityThread);
            for (Object record : activities.values()) {
                Class recordClass = record.getClass();
                Field pausedField = recordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!(boolean) pausedField.get(record)) {
                    Field activityField = recordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(record);
                }
            }
        } catch (Exception e) {
            Log.e("FoodUEActivityUtils", "getCurrentActivity", e);
        }
        return null;
    }
}
