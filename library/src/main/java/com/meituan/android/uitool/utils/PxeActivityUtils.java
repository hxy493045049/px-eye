package com.meituan.android.uitool.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
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
@SuppressLint("PrivateApi")
public class PxeActivityUtils {

    private PxeActivityUtils() {
    }

    public static int getStatusBarHeight() {
        Resources resources = PxeResourceUtils.getResource();
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

    public static boolean isActivityInvalid(Activity act) {
        if (Build.VERSION.SDK_INT >= 17) {
            return act == null || act.isDestroyed() || act.isFinishing();
        } else {
            return act == null || act.isFinishing();
        }
    }

    public static void setStatusBarColor(@NonNull Window window, int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(color);
        }
    }

    /**
     * 获取进程中最上层的activity, 这个activity的生命周期必须在{@link Activity#onPause()}之前
     */
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

    /**
     * 获取进程最上层的activity,无论出于何种生命周期状态下
     */
    @SuppressLint("PrivateApi")
    public static Activity getTopActivity(boolean isContainFunctionAct) {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getMethod("currentActivityThread");
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);
            Field mActivitiesField = activityThreadClass.getDeclaredField("mActivities");
            mActivitiesField.setAccessible(true);
            Map activities = (Map) mActivitiesField.get(currentActivityThread);
            for (Object record : activities.values()) {
                Class recordClass = record.getClass();
                Field activityField = recordClass.getDeclaredField("activity");
                activityField.setAccessible(true);
            }
        } catch (Exception e) {
            Log.e("PxeActivityUtils", "getFunctionActivity", e);
        }
        return null;
    }


    public static Application getApplication() {
        Application application = null;
        try {
            Class activityThreadClazz = Class.forName("android.app.ActivityThread");
            Method method = activityThreadClazz.getMethod("currentActivityThread");
            Object activityThreadObj = method.invoke(null);
            Class activityThreadCls = activityThreadObj.getClass();
            Field field = activityThreadCls.getDeclaredField("mInitialApplication");
            field.setAccessible(true);
            application = (Application) field.get(activityThreadObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return application;
    }
}
