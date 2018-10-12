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
import com.meituan.android.uitool.FoodUEToolsActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Author: gaojin
 * Time: 2018/6/25 下午6:04
 */
public class PxeActivityUtils {

    private PxeActivityUtils() {
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
     * 通过反射获取最上层的activity, activity必须执行完oncreate才能被获取
     *
     * @return 最上层的activity
     */
    @SuppressLint("PrivateApi")
    public static Activity getCurrentTopActivity() {
        return getTargetActivity(true);
    }

    /**
     * 获取任务栈顶部的act, 当FoodUEToolsActivity在栈顶时,isContainFunctionAct=false将会忽略FoodUEToolsActivity获取下一个
     *
     * @param isContainFunctionAct 是否包含{@link FoodUEToolsActivity}
     * @return 获取任务栈顶部的act
     */
    public static Activity getTargetActivity(boolean isContainFunctionAct) {
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
                if (isContainFunctionAct) {
                    return (Activity) activityField.get(record);
                } else if (!(activityField.get(record) instanceof FoodUEToolsActivity)) {
                    return (Activity) activityField.get(record);
                }
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
