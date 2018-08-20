package com.meituan.android.plugin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.meituan.android.base.BaseConfig;
import com.meituan.android.singleton.ApplicationSingleton;
import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.FoodUEToolsActivity;
import com.meituan.android.uitool.library.R;
import com.meituan.android.utils.FoodUEActivityUtils;

import java.util.ArrayList;
import java.util.List;

import roboguice.util.Ln;

/**
 * Author: gaojin
 * Time: 2018/6/19 下午3:34
 */
public class FoodUEMenu extends LinearLayout {
    private View vMenu;
    private ViewGroup vSubMenuContainer;
    private ValueAnimator animator;
    private Interpolator defaultInterpolator = new AccelerateDecelerateInterpolator();

    private WindowManager windowManager;
    private WindowManager.LayoutParams params = new WindowManager.LayoutParams();
    private int touchSlop;

    private SubMenuClickEvent exitListener;

    public FoodUEMenu(final Context context) {
        super(context);
        inflate(context, R.layout.food_ue_menu_layout, this);
        setGravity(Gravity.CENTER_VERTICAL);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        windowManager = (WindowManager) ApplicationSingleton.getInstance().getSystemService(Context.WINDOW_SERVICE);

        vMenu = findViewById(R.id.menu);
        vSubMenuContainer = findViewById(R.id.sub_menu_container);

        List<FoodUESubMenu.MenuModel> menuModels = initMenuData();
        initMenuView(menuModels);
        vMenu.setOnClickListener(v -> startAnim());
        vMenu.setOnTouchListener(new OnTouchListener() {
            private float downX, downY;
            private float lastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getRawX();
                        downY = event.getRawY();
                        lastY = downY;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        params.y += event.getRawY() - lastY;
                        params.y = Math.max(0, params.y);
                        windowManager.updateViewLayout(FoodUEMenu.this, params);
                        lastY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(event.getRawX() - downX) < touchSlop && Math.abs(event.getRawY() - downY) < touchSlop) {
                            vMenu.performClick();
                            return false;
                        }
                        break;
                }
                return true;
            }
        });
    }

    //------------- public -------------
    public void show() {
        try {
            windowManager.addView(this, getWindowLayoutParams());
        } catch (Exception e) {
            Ln.e(e);
        }
    }

    public void dismiss() {
        try {
            //取消ui工具后应该还原到最初始的配置
            windowManager.removeView(this);
            vSubMenuContainer.setTranslationX(-vSubMenuContainer.getWidth());
        } catch (Exception e) {
            Ln.e(e);
        }
    }

    public void setOnExitListener(SubMenuClickEvent exportEvent) {
        this.exitListener = exportEvent;
    }

    //------------- private -------------
    private List<FoodUESubMenu.MenuModel> initMenuData() {
        List<FoodUESubMenu.MenuModel> menuModels = new ArrayList<>();
        menuModels.add(new FoodUESubMenu.MenuModel("测量条", R.drawable.food_ue_show_gridding,
                (v) -> triggerOpen(FoodUEToolsActivity.Type.TYPE_MEASURE)));

        menuModels.add(new FoodUESubMenu.MenuModel("属性", R.drawable.food_ue_show_gridding,
                (v) -> triggerOpen(FoodUEToolsActivity.Type.TYPE_EDIT_ATTR)));

        menuModels.add(new FoodUESubMenu.MenuModel("关闭", R.drawable.ui_close, (v) -> {
            FoodUETool.getInstance().exit();
            if (exitListener != null) {
                exitListener.onClick(getContext());
            }
        }));
        return menuModels;
    }

    private void initMenuView(List<FoodUESubMenu.MenuModel> menuModels) {
        LayoutParams subMenuParams = new LayoutParams(BaseConfig.dp2px(50), BaseConfig.dp2px(50));
        subMenuParams.leftMargin = BaseConfig.dp2px(10);
        for (FoodUESubMenu.MenuModel model : menuModels) {
            FoodUESubMenu subMenu = new FoodUESubMenu(getContext());
            subMenu.update(model);
            vSubMenuContainer.addView(subMenu, subMenuParams);
        }
    }

    private void startAnim() {
        final boolean isOpen = vSubMenuContainer.getTranslationX() <= -vSubMenuContainer.getWidth();
        ensureAnim();
        animator.setInterpolator(isOpen ? defaultInterpolator : new ReverseInterpolator(defaultInterpolator));
        animator.removeAllListeners();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                vSubMenuContainer.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isOpen) {
                    vSubMenuContainer.setVisibility(GONE);
                }
            }
        });
        animator.start();
    }

    private void ensureAnim() {
        if (animator == null) {
            animator = ValueAnimator.ofInt(-vSubMenuContainer.getWidth(), 0);
            animator.addUpdateListener(animation -> vSubMenuContainer.setTranslationX((int) animation.getAnimatedValue()));
            animator.setDuration(400);
        }
    }

    private void triggerOpen(@FoodUEToolsActivity.Type int type) {
        Activity currentTopActivity = FoodUEActivityUtils.getCurrentActivity();
        if (currentTopActivity == null) {
            return;
        } else if (currentTopActivity.getClass() == FoodUEToolsActivity.class) {
            currentTopActivity.finish();
            return;
        }
        Intent intent = new Intent(currentTopActivity, FoodUEToolsActivity.class);
        intent.putExtra(FoodUEToolsActivity.EXTRA_TYPE, type);
        currentTopActivity.startActivity(intent);
        currentTopActivity.overridePendingTransition(0, 0);
        FoodUETool.getInstance().setTargetActivity(currentTopActivity);
    }

    private WindowManager.LayoutParams getWindowLayoutParams() {
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.TRANSLUCENT;
        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 10;
        params.y = 10;
        return params;
    }

    private static class ReverseInterpolator implements TimeInterpolator {

        private TimeInterpolator mWrappedInterpolator;

        ReverseInterpolator(TimeInterpolator interpolator) {
            mWrappedInterpolator = interpolator;
        }

        @Override
        public float getInterpolation(float input) {
            return mWrappedInterpolator.getInterpolation(Math.abs(input - 1f));
        }
    }

    public interface SubMenuClickEvent {
        void onClick(Context context);
    }
}
