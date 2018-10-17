package com.meituan.android.uitool.biz.uitest.utils;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.meituan.android.uitool.utils.PxeActivityUtils;

public class DensityUtils {


    private static android.app.Application.ActivityLifecycleCallbacks LIEFCYCLE_CALLBACKS = new android.app.Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Resources resources = activity.getApplication().getResources();
            resources.getDisplayMetrics().scaledDensity = resources.getDisplayMetrics().density;
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    };
    private static int origin_density_dpi = 0;

    public static void changeAppDensity() {
        try {
            Activity context = PxeActivityUtils.getCurrentTopActivity();
            if (context == null) {
                return;
            }
            Resources resources = context.getApplication().getResources();

            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            Configuration configuration = resources.getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (origin_density_dpi == 0) {
                    origin_density_dpi = configuration.densityDpi;
                }
                int uiSize = DataManager.getDensity();
                configuration.densityDpi = (int) (320.0 * displayMetrics.widthPixels / uiSize + 0.5);
            }
            resources.updateConfiguration(configuration, displayMetrics);
            // 先反注册一下然后再注册，这样保证只注册了一次
            context.getApplication().unregisterActivityLifecycleCallbacks(LIEFCYCLE_CALLBACKS);
            context.getApplication().registerActivityLifecycleCallbacks(LIEFCYCLE_CALLBACKS);
        } catch (Throwable e) {
            //ignore
        }
    }

    public static void resetAppDensity() {
        try {
            Activity context = PxeActivityUtils.getCurrentTopActivity();
            if (context == null || origin_density_dpi == 0) {
                return;
            }
            Resources resources = context.getApplication().getResources();
            Configuration configuration = resources.getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.densityDpi = origin_density_dpi;
            }
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
            context.getApplication().unregisterActivityLifecycleCallbacks(LIEFCYCLE_CALLBACKS);
        } catch (Throwable e) {
        }
    }
}
