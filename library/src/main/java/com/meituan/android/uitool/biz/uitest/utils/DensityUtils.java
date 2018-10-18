package com.meituan.android.uitool.biz.uitest.utils;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.meituan.android.uitool.utils.ApplicationSingleton;
import com.meituan.android.uitool.utils.PxeActivityUtils;
import com.meituan.android.uitool.utils.PxeResourceUtils;
import com.meituan.android.uitool.utils.PxeSimpleActivityLifecycleCallbacks;

public class DensityUtils {
    private static int origin_density_dpi = 0;

    private static android.app.Application.ActivityLifecycleCallbacks LIEFCYCLE_CALLBACKS = new PxeSimpleActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Resources resources = activity.getApplication().getResources();
            resources.getDisplayMetrics().scaledDensity = resources.getDisplayMetrics().density;
        }
    };

    public static void changeAppDensity() {
        try {
            Activity context = PxeActivityUtils.getTopActivity(true);
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
            if (origin_density_dpi == 0) {
                return;
            }
            Resources resources = PxeResourceUtils.getResource();
            Configuration configuration = resources.getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.densityDpi = origin_density_dpi;
            }
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
            ApplicationSingleton.getInstance().unregisterActivityLifecycleCallbacks(LIEFCYCLE_CALLBACKS);
        } catch (Throwable e) {
        }
    }
}
