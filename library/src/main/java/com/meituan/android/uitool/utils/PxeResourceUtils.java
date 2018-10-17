package com.meituan.android.uitool.utils;

import android.content.res.Resources;
import android.view.View;

import static android.view.View.NO_ID;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午4:09
 */
public class PxeResourceUtils {
    public static Resources getResource() {
        return ApplicationSingleton.getApplicationContext().getResources();
    }

    public static String getResourceName(int id) {
        Resources resources = getResource();
        try {
            if (id == NO_ID || id == 0) {
                return "";
            } else {
                return resources.getResourceEntryName(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getResId(View view) {
        try {
            int id = view.getId();
            if (id == NO_ID) {
                return "";
            } else {
                return "0x" + Integer.toHexString(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String intToHexColor(int color) {
        return "#" + Integer.toHexString(color).toUpperCase();
    }
}
