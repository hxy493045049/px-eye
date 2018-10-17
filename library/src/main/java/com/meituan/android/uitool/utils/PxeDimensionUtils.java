package com.meituan.android.uitool.utils;

import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;

import com.meituan.android.uitool.FoodUETool;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/10 on 上午10:59
 */
public class PxeDimensionUtils {
    public static Rect screenRect = new Rect(0, 0, getScreenWidth(), getScreenHeight());

    private PxeDimensionUtils() {
    }

    public static String px2dip(float pxValue) {
        return px2dip(pxValue, false);
    }

    public static String px2dip(float pxValue, boolean withUnit) {
        float scale = PxeResourceUtils.getResource().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5F) + (withUnit ? "dp" : "");
    }

    public static int dip2px(float dpValue) {
        float scale = PxeResourceUtils.getResource().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }

    public static int sp2px(float sp) {
        return (int) TypedValue.applyDimension(2, sp, PxeResourceUtils.getResource().getDisplayMetrics());
    }

    public static String px2sp(float pxValue) {
        final float fontScale = PxeResourceUtils.getResource().getDisplayMetrics().scaledDensity;
        return String.valueOf((int) (pxValue / fontScale + 0.5f));
    }

    public static int getScreenWidth() {
        return PxeResourceUtils.getResource().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return PxeResourceUtils.getResource().getDisplayMetrics().heightPixels;
    }

    /**
     * 判断矩形是否在屏幕内
     *
     * @param r 目标矩形
     * @return 判断矩形是否在屏幕内, 部分交互或者被包含返回true, 完全不交互返回false
     */
    public static boolean isRectInScreen(Rect r) {
        if (r == null) {
            return false;
        }
        if (r.left >= r.right || r.top >= r.bottom) {
            return false;
        }
        boolean result = false;
        //如果两个矩形相交
        if (screenRect.left < r.right && r.left < screenRect.right
                && screenRect.top < r.bottom && r.top < screenRect.bottom) {
            result = true;
        }
        return result || screenRect.contains(r);
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
