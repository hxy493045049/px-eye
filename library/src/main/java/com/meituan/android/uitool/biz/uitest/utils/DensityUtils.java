package com.meituan.android.uitool.biz.uitest.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.LinearGradient;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannedString;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.meituan.android.uitool.utils.FoodUEActivityUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static android.view.View.NO_ID;

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
            Activity context = FoodUEActivityUtils.getCurrentActivity();
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
            Activity context = FoodUEActivityUtils.getCurrentActivity();
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
