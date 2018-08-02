package com.meituan.android.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import roboguice.util.Ln;

/**
 * Author: gaojin
 * Time: 2018/6/25 下午6:04
 */

public class FoodDevUtils {
    @TargetApi(Build.VERSION_CODES.M)
    public static void requestPermission(Context context) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
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
            Ln.e(e);
        }
        return null;
    }
}
