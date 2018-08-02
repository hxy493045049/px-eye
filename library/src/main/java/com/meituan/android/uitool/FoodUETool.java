package com.meituan.android.uitool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import com.meituan.android.singleton.ApplicationSingleton;
import com.meituan.android.utils.FoodDevUtils;

/**
 * Author: gaojin
 * Time: 2018/6/19 下午3:25
 */

public final class FoodUETool {

    @SuppressLint("StaticFieldLeak")
    private static volatile FoodUETool instance;

    private Activity targetActivity;
    private FoodUEMenu ueMenu;

    private FoodUETool() {
    }

    public static FoodUETool getInstance() {
        if (instance == null) {
            synchronized (FoodUETool.class) {
                if (instance == null) {
                    instance = new FoodUETool();
                }
            }
        }
        return instance;
    }

    public static boolean showUETMenu() {
        return getInstance().showMenu();
    }

    public static void dismissUETMenu() {
        getInstance().dismissMenu();
    }

    void release() {
        targetActivity = null;
    }

    public Activity getTargetActivity() {
        return targetActivity;
    }

    public void setTargetActivity(Activity targetActivity) {
        this.targetActivity = targetActivity;
    }

    public void setExportEvent(SubMenuClickEvent exportEvent) {
        checkUeMenu();
        ueMenu.setExportEvent(exportEvent);
    }

    private boolean showMenu() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(ApplicationSingleton.getInstance())) {
                FoodDevUtils.requestPermission(ApplicationSingleton.getInstance());
                Toast.makeText(ApplicationSingleton.getInstance(), "请开启悬浮窗权限", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        checkUeMenu();
        ueMenu.show();
        return true;
    }

    private void checkUeMenu() {
        if (ueMenu == null) {
            ueMenu = new FoodUEMenu(ApplicationSingleton.getInstance());
        }
    }

    private void dismissMenu() {
        if (ueMenu != null) {
            ueMenu.dismiss();
            ueMenu = null;
        }
    }
}
