package com.meituan.android.uitool.plugin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.FoodUEToolsActivity;
import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.plugin.adapter.FoodUESubMenuAdapter;
import com.meituan.android.uitool.plugin.model.MenuModel;
import com.meituan.android.uitool.utils.FoodUEActivityUtils;
import com.meituan.android.uitool.utils.FoodUEDimensionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: gaojin
 * Time: 2018/6/19 下午3:34
 */
public class FoodUEMenu extends LinearLayout implements View.OnTouchListener, FoodUESubMenuAdapter.SubMenuClickListener {
    private View vMenu;
    private RecyclerView vSubMenuContainer;
    private ValueAnimator animator;
    private Interpolator defaultInterpolator = new AccelerateDecelerateInterpolator();

    private WindowManager windowManager;
    private WindowManager.LayoutParams params = new WindowManager.LayoutParams();
    private int touchSlop;

    private int SCREEN_HEIGHT = FoodUEDimensionUtils.getScreenHeight();
    private int SCREEN_WIDTH = FoodUEDimensionUtils.getScreenWidth();

    private SubMenuClickEvent exitListener;
    private FoodUESubMenuAdapter subMenuAdapter;

    private float downX, downY;
    private float lastY, lastX;

    public FoodUEMenu(final Context context) {
        super(context);
        inflate(context, R.layout.food_ue_menu_layout, this);
        setOrientation(VERTICAL);
        setGravity(Gravity.END | Gravity.CENTER_VERTICAL);

        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        windowManager = (WindowManager) FoodUETool.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        vMenu = findViewById(R.id.menu);
        vSubMenuContainer = findViewById(R.id.sub_menu_container);
        vSubMenuContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        vSubMenuContainer.setHasFixedSize(true);
        subMenuAdapter = new FoodUESubMenuAdapter(initMenuData());
        subMenuAdapter.setOnSubMenuClickListener(this);
        vSubMenuContainer.setAdapter(subMenuAdapter);

        vMenu.setOnClickListener(v -> startAnim());
        vMenu.setOnTouchListener(this);
    }

    //------------- public -------------
    public void show() {
        try {
            windowManager.addView(this, getWindowLayoutParams());
        } catch (Exception e) {
            Log.e("FoodUEMenu", "show", e);
        }
    }

    public void dismiss() {
        try {
            //取消ui工具后应该还原到最初始的配置
            windowManager.removeView(this);
            vSubMenuContainer.setTranslationX(-vSubMenuContainer.getWidth());
        } catch (Exception e) {
            Log.e("FoodUEMenu", "dismiss", e);
        }
    }

    public void setOnExitListener(SubMenuClickEvent exportEvent) {
        this.exitListener = exportEvent;
    }

    //------------- private -------------
    private List<MenuModel> initMenuData() {
        List<MenuModel> menuModels = new ArrayList<>();
        menuModels.add(new MenuModel("测量条", R.drawable.ui_close, FoodUEToolsActivity.Type.TYPE_MEASURE));
        menuModels.add(new MenuModel("属性", R.drawable.ui_close, FoodUEToolsActivity.Type.TYPE_EDIT_ATTR));
        menuModels.add(new MenuModel("相对位置", R.drawable.ui_close, FoodUEToolsActivity.Type.TYPE_RELATIVE_POSITION));
        menuModels.add(new MenuModel("关闭", R.drawable.ui_close, FoodUEToolsActivity.Type.TYPE_EXIT));
        return menuModels;
    }

    private void startAnim() {
        final boolean isOpen = vSubMenuContainer.getTranslationY() <= -vSubMenuContainer.getHeight();
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
            animator = ValueAnimator.ofInt(-vSubMenuContainer.getHeight(), 0);
            animator.addUpdateListener(animation -> vSubMenuContainer.setTranslationY((int) animation.getAnimatedValue()));
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
        FoodUETool.getInstance(null).setTargetActivity(currentTopActivity);
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


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                lastY = downY;
                lastX = downX;
                break;
            case MotionEvent.ACTION_MOVE:
                params.y += event.getRawY() - lastY;
                params.x += event.getRawX() - lastX;
                if (params.y < 0) {
                    params.y = 0;
                } else if (params.y + v.getHeight() > SCREEN_HEIGHT) {
                    params.y = SCREEN_HEIGHT - v.getHeight();
                }
                if (params.x < 0) {
                    params.x = 0;
                } else if (params.x + v.getWidth() > SCREEN_WIDTH) {
                    params.x = SCREEN_WIDTH = v.getWidth();
                }

                windowManager.updateViewLayout(FoodUEMenu.this, params);
                lastY = event.getRawY();
                lastX = event.getRawX();
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(event.getRawX() - downX) < touchSlop && Math.abs(event.getRawY() - downY) < touchSlop) {
                    v.performClick();
                    return false;
                }
                break;
        }
        return true;
    }

    @Override
    public void onSubMenuClick(MenuModel model, View subMenu) {
        if (model.getType() == FoodUEToolsActivity.Type.TYPE_EXIT) {
            exitListener.onClick(getContext());
        } else {
            triggerOpen(model.getType());
        }
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
