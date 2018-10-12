package com.meituan.android.uitool.biz.uitest.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.meituan.android.uitool.utils.FoodUEActivityUtils;

/**
 * Created by lusheng on 18/6/27.
 */

public class DataManager {
    private static final String USER_KEY = "user";
    private static final String UET_DENSITY = "density";
    private static final String SP_NAME = "uetool_config";

    public static String getUser() {
        SharedPreferences sp = FoodUEActivityUtils.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(USER_KEY, "");
    }

    public static void saveUser(String user) {
        SharedPreferences sp = FoodUEActivityUtils.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(USER_KEY, user).apply();
    }

    public static int getDensity() {
        SharedPreferences sp = FoodUEActivityUtils.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(UET_DENSITY, 750);
    }

    public static void saveDensity(String density) {
        int intDensity;
        try {
            intDensity = Integer.parseInt(density);
        }catch (Exception e) {
            intDensity = 750;
        }
        SharedPreferences sp = FoodUEActivityUtils.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(UET_DENSITY, intDensity).apply();
    }
}
