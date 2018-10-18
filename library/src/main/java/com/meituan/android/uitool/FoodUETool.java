package com.meituan.android.uitool;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import com.meituan.android.uitool.biz.attr.dialog.provider.impl.PxeDefaultAttrProvider;
import com.meituan.android.uitool.helper.PxeActivityRecorder;
import com.meituan.android.uitool.helper.PxeViewRecorder;
import com.meituan.android.uitool.plugin.PxeMenu;
import com.meituan.android.uitool.utils.ApplicationSingleton;
import com.meituan.android.uitool.utils.PxeActivityUtils;
import com.meituan.android.uitool.utils.PxeCollectionUtils;
import com.meituan.android.uitool.utils.PxePermissionUtils;
import com.meituan.android.uitool.utils.PxeSimpleActivityLifecycleCallbacks;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: shawn
 * Time: 2018/6/19 下午3:25
 * FoodUE工具的开关, 提供了默认的属性获取器
 */
public final class FoodUETool {
    private PxeMenu pxeMenu;
    private boolean isShowMenu;

    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new PxeSimpleActivityLifecycleCallbacks() {

        @Override
        public void onActivityStarted(Activity activity) {
            PxeActivityRecorder.getInstance().recordActivity(activity);
            if (isShowMenu) {
                checkMenu();
                pxeMenu.show();
            }
        }

        @Override
        public void onActivityStopped(Activity activity) {
            PxeActivityRecorder.getInstance().removeActivity(activity);
            if (PxeActivityUtils.getCurrentActivity() == null) {
                if (pxeMenu != null) {
                    pxeMenu.dismiss();
                }
            }
        }
    };

    //------------public------------
    public static FoodUETool getInstance() {
        initContext();
        return Holder.instance;
    }

    public void setOnExitListener(PxeMenu.SubMenuClickEvent exportEvent) {
        checkMenu();
        pxeMenu.setOnExitListener(exportEvent);
    }

    public void open() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(ApplicationSingleton.getApplicationContext())) {
                PxePermissionUtils.requestOverlayPermission(ApplicationSingleton.getApplicationContext());
                Toast.makeText(ApplicationSingleton.getApplicationContext(), "请开启悬浮窗权限", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Application application = ApplicationSingleton.getInstance();
        Activity act = PxeActivityUtils.getCurrentActivity();
        if (application != null && act != null && !PxeActivityUtils.isActivityInvalid(act)) {
            //第一次的时候, 需要额外将activity添加到{@link PxeActivityRecorder}中, 因为当前的act已经走了onStart方法
            PxeActivityRecorder.getInstance().recordActivity(act);
            application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);

            checkMenu();
            pxeMenu.show();
            isShowMenu = true;
        }
    }

    public void exit() {
        isShowMenu = false;

        if (pxeMenu != null) {
            pxeMenu.dismiss();
            pxeMenu = null;
        }
        closeAct();
        release();

        Application application = ApplicationSingleton.getInstance();
        if (application != null) {
            application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
        }
    }

    public void triggerMenuAnim() {
        if (pxeMenu != null) {
            pxeMenu.startAnim();
        }
    }

    //释放资源
    public void release() {
        PxeViewRecorder.getInstance().reset();
        PxeActivityRecorder.getInstance().release();
    }

    public void setAttrProvider(String providerClassName) {
        Holder.attrsProviderSet.add(providerClassName);
    }

    public Set<String> getAttrProviderNames() {
        return Holder.attrsProviderSet;
    }

    //------------private------------
    private FoodUETool() {
        checkMenu();
    }

    private static void initContext() {
        if (ApplicationSingleton.getInstance() != null) {
            return;
        }
        Activity act = PxeActivityUtils.getTopActivity(false);
        act = act != null ? act : PxeActivityUtils.getCurrentActivity();
        if (act != null) {
            ApplicationSingleton.bindInstance(act.getApplication());
        } else {
            ApplicationSingleton.bindInstance(PxeActivityUtils.getApplication());
        }
    }

    private void checkMenu() {
        if (pxeMenu == null) {
            pxeMenu = new PxeMenu(ApplicationSingleton.getInstance().getApplicationContext());
        }
    }

    private static class Holder {
        private static final Set<String> attrsProviderSet = new LinkedHashSet<>();
        private static FoodUETool instance = new FoodUETool();

        static {
            attrsProviderSet.add(PxeDefaultAttrProvider.class.getName());
        }
    }

    //关闭ui工具的activity
    private void closeAct() {
        List<Activity> activities = PxeActivityRecorder.getInstance().getFunctionActivities();
        if (!PxeCollectionUtils.isEmpty(activities)) {
            for (Activity act : activities) {
                act.finish();
            }
        }
    }
}
