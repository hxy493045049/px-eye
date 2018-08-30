package com.meituan.android.uitool;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.meituan.android.uitool.biz.element.provider.impl.FoodUEDefaultAttrProvider;
import com.meituan.android.uitool.plugin.FoodUEMenu;
import com.meituan.android.uitool.utils.FoodUEActivityUtils;
import com.meituan.android.uitool.utils.FoodUEPermissionUtils;

import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Author: gaojin
 * Time: 2018/6/19 下午3:25
 * FoodUE工具的开关, 提供了默认的属性获取器
 */
public final class FoodUETool {
    private static WeakReference<Context> APPLICATION_CONTEXT_REF;
    private WeakReference<Activity> targetActivityRef;
    private FoodUEMenu ueMenu;
    //这里放string是为了单例
    private Set<String> attrsProviderSet = new LinkedHashSet<String>() {
        {
            add(FoodUEDefaultAttrProvider.class.getName());
        }
    };

    private FoodUETool() {
        initMenu();
    }

    //------------public------------
    public static FoodUETool getInstance(Context applicationContext) {
        if (FoodUETool.APPLICATION_CONTEXT_REF == null || FoodUETool.APPLICATION_CONTEXT_REF.get() == null) {
            if (applicationContext == null) {
                throw new IllegalArgumentException("初始化的context不能为空");
            } else {
                APPLICATION_CONTEXT_REF = new WeakReference<>(applicationContext.getApplicationContext());
            }
        }
        return Holder.instance;
    }

    @NonNull
    public static Context getApplicationContext() {
        if (APPLICATION_CONTEXT_REF != null) {
            return APPLICATION_CONTEXT_REF.get();
        } else {
            throw new IllegalStateException("context为空,必须先执行初始化");
        }
    }

    @NonNull
    public static Resources getResource() {
        if (APPLICATION_CONTEXT_REF != null) {
            return APPLICATION_CONTEXT_REF.get().getResources();
        } else {
            throw new IllegalStateException("context为空,必须先执行初始化");
        }
    }

    @Nullable
    public Activity getTargetActivity() {
        if (targetActivityRef.get() != null) {
            return targetActivityRef.get();
        }
        return null;
    }

    public void setTargetActivity(Activity targetActivityRef) {
        this.targetActivityRef = new WeakReference<>(targetActivityRef);
    }

    public void setOnExitListener(FoodUEMenu.SubMenuClickEvent exportEvent) {
        initMenu();
        ueMenu.setOnExitListener(exportEvent);
    }

    public void open() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(FoodUETool.APPLICATION_CONTEXT_REF.get())) {
                FoodUEPermissionUtils.requestOverlayPermission(FoodUETool.APPLICATION_CONTEXT_REF.get());
                Toast.makeText(FoodUETool.APPLICATION_CONTEXT_REF.get(), "请开启悬浮窗权限", Toast.LENGTH_SHORT).show();
            }
        }

        initMenu();
        ueMenu.show();
    }

    public void exit() {
        if (ueMenu != null) {
            ueMenu.dismiss();
        }
        ueMenu = null;
        closeAct();
    }

    //释放资源
    public void release() {
        if (targetActivityRef != null) {
            targetActivityRef.clear();
        }
        targetActivityRef = null;
    }

    public void setAttrProvider(String providerClassName) {
        attrsProviderSet.add(providerClassName);
    }

    public Set<String> getAttrProviderNames() {
        return attrsProviderSet;
    }

    //------------private------------
    private void initMenu() {
        if (ueMenu == null) {
            ueMenu = new FoodUEMenu(FoodUETool.APPLICATION_CONTEXT_REF.get());
        }
    }

    private static class Holder {
        private static FoodUETool instance = new FoodUETool();
    }

    //重置
    private void closeAct() {
        Activity act = FoodUEActivityUtils.getCurrentActivity();
        if (act instanceof FoodUEToolsActivity) {
            act.finish();
        }
    }
}
