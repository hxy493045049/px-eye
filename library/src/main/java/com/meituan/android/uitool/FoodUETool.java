package com.meituan.android.uitool;

import android.app.Activity;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.meituan.android.biz.element.provider.impl.FoodUEDefaultAttrProvider;
import com.meituan.android.plugin.FoodUEMenu;
import com.meituan.android.singleton.ApplicationSingleton;
import com.meituan.android.utils.FoodUEActivityUtils;
import com.meituan.android.utils.FoodUEPermissionUtils;

import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Author: gaojin
 * Time: 2018/6/19 下午3:25
 * FoodUE工具的开关, 提供了默认的属性获取器
 */
public final class FoodUETool {
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
    public static FoodUETool getInstance() {
        return Holder.instance;
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
        ueMenu.setOnExitListener(exportEvent);
    }

    public void open() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(ApplicationSingleton.getInstance())) {
                FoodUEPermissionUtils.requestOverlayPermission(ApplicationSingleton.getInstance());
                Toast.makeText(ApplicationSingleton.getInstance(), "请开启悬浮窗权限", Toast.LENGTH_SHORT).show();
            }
        }
        ueMenu.show();
    }

    public void exit() {
        ueMenu.dismiss();
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
            ueMenu = new FoodUEMenu(ApplicationSingleton.getInstance());
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
