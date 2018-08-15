package com.meituan.android.utils;

import android.app.Application;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;

import com.meituan.android.singleton.ApplicationSingleton;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/10 on 上午10:59
 */
public class FoodUEDimensionUtils {
    private static final Application APPLICATION = ApplicationSingleton.getInstance();

    private FoodUEDimensionUtils() {
    }

    public static String px2dip(float pxValue) {
        return px2dip(pxValue, false);
    }

    public static String px2dip(float pxValue, boolean withUnit) {
        float scale = APPLICATION.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5F) + (withUnit ? "dp" : "");
    }

    public static int dip2px(float dpValue) {
        float scale = APPLICATION.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }

    public static int sp2px(float sp) {
        return (int) TypedValue.applyDimension(2, sp, APPLICATION.getResources().getDisplayMetrics());
    }

    public static String px2sp(float pxValue) {
        final float fontScale = APPLICATION.getResources().getDisplayMetrics().scaledDensity;
        return String.valueOf((int) (pxValue / fontScale + 0.5f));
    }

    public static int getScreenWidth() {
        return APPLICATION.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return APPLICATION.getResources().getDisplayMetrics().heightPixels;
    }

    public static float getTextHeight(String text, Paint textPaint) {
        Rect rect = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    public static float getTextWidth(String text, Paint textPaint) {
        return textPaint.measureText(text);
    }
}
