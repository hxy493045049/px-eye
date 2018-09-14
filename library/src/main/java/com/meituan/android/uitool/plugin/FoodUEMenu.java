package com.meituan.android.uitool.plugin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
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
 * Author: shawn
 * Time: 2018/6/19 下午3:34
 */
public class FoodUEMenu extends LinearLayout implements View.OnTouchListener, FoodUESubMenuAdapter.SubMenuClickListener {
    private View vSubMenuContainer;
    private RecyclerView subMenuRecycler;
    private ValueAnimator animator;
    private Interpolator defaultInterpolator = new DecelerateInterpolator();
    private boolean hasAttach2Window;

    private WindowManager windowManager;
    private WindowManager.LayoutParams params;
    private int touchSlop;

    private static final int SCREEN_HEIGHT = FoodUEDimensionUtils.getScreenHeight();
    private static final int SCREEN_WIDTH = FoodUEDimensionUtils.getScreenWidth();

    private SubMenuClickEvent exitListener;
    private int ANIM_HEIGHT = 0;

    private float downX, downY;
    private float lastY, lastX;
    private View mMainMenu;
    //------------- public -------------

    public FoodUEMenu(final Context context) {
        super(context);
        inflate(context, R.layout.food_ue_menu_container, this);
        setOrientation(VERTICAL);
        setGravity(Gravity.END);

        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        windowManager = (WindowManager) FoodUETool.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        mMainMenu = findViewById(R.id.menu);
        vSubMenuContainer = findViewById(R.id.sub_menu_container);

        subMenuRecycler = findViewById(R.id.sub_menu_recycler_view);
        subMenuRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        subMenuRecycler.setHasFixedSize(true);
        FoodUESubMenuAdapter subMenuAdapter = new FoodUESubMenuAdapter(initMenuData());
        subMenuAdapter.setOnSubMenuClickListener(this);
        subMenuRecycler.setAdapter(subMenuAdapter);

        mMainMenu.setOnTouchListener(this);
        subMenuRecycler.post(new Runnable() {
            @Override
            public void run() {
                ANIM_HEIGHT = subMenuRecycler.getHeight();
                //如果这里不设置gone的话,ui按钮无法滑动到底部
                vSubMenuContainer.setVisibility(GONE);
            }
        });
    }

    public void show() {
        try {
            windowManager.addView(this, initToolParams());
            hasAttach2Window = true;
        } catch (Exception e) {
            Log.e("FoodUEMenu", "failed add to window", e);
            hasAttach2Window = false;
        }
    }

    public void dismiss() {
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                if (isAttachedToWindow()) {
                    windowManager.removeView(this);
                }
            } else if (hasAttach2Window) {
                //取消ui工具后应该还原到最初始的配置
                windowManager.removeView(this);
            }
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
        menuModels.add(new MenuModel("测量条", R.drawable.food_ue_measure, FoodUEToolsActivity.Type.TYPE_MEASURE));
        menuModels.add(new MenuModel("属性", R.drawable.food_ue_attr, FoodUEToolsActivity.Type.TYPE_EDIT_ATTR));
        menuModels.add(new MenuModel("相对位置", R.drawable.food_ue_relative, FoodUEToolsActivity.Type.TYPE_RELATIVE_POSITION));
        menuModels.add(new MenuModel("取色器", R.drawable.food_ue_attr, FoodUEToolsActivity.Type.TYPE_COLOR));
        menuModels.add(new MenuModel("关闭", R.drawable.food_ue_close, FoodUEToolsActivity.Type.TYPE_EXIT));
        return menuModels;
    }

    private void startAnim() {
        final boolean isOpen = subMenuRecycler.getTranslationY() <= -subMenuRecycler.getHeight();

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
                if (isOpen) {
                    vSubMenuContainer.setVisibility(VISIBLE);
                } else {
                    vSubMenuContainer.setVisibility(GONE);
                }
            }
        });
        animator.start();
    }

    private void ensureAnim() {
        if (animator == null) {
            subMenuRecycler.setVisibility(VISIBLE);
            animator = ValueAnimator.ofInt(-ANIM_HEIGHT, 0);
            animator.addUpdateListener(animation ->
                    subMenuRecycler.setTranslationY((int) animation.getAnimatedValue()));
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
        startFunctionByType(type, currentTopActivity);
        startAnim();
    }

    private void startFunctionByType(@FoodUEToolsActivity.Type int type, Activity act) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri.Builder builder = Uri.parse(FoodUEToolsActivity.ACTION).buildUpon();
        intent.setData(builder.build());
        intent.putExtra(FoodUEToolsActivity.EXTRA_TYPE, type);

        act.startActivity(intent);
        act.overridePendingTransition(0, 0);
        FoodUETool.getInstance(null).setTargetActivity(act);
    }

    /**
     * @return 整个ui工具初始的param状态
     */
    private WindowManager.LayoutParams initToolParams() {
        if (params == null) {
            params = new WindowManager.LayoutParams();
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
            params.x = -500;
            params.y = 10;
        }
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
                } else if (params.y + mMainMenu.getHeight() > SCREEN_HEIGHT) {
                    params.y = SCREEN_HEIGHT - mMainMenu.getHeight();
                }
                if (params.x < 0) {
                    params.x = 0;
                } else if (params.x + mMainMenu.getWidth() > SCREEN_WIDTH) {
                    params.x = SCREEN_WIDTH - mMainMenu.getWidth();
                }

                windowManager.updateViewLayout(FoodUEMenu.this, params);
                lastY = event.getRawY();
                lastX = event.getRawX();
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(event.getRawX() - downX) < touchSlop && Math.abs(event.getRawY() - downY) < touchSlop) {
                    startAnim();
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public void onSubMenuClick(MenuModel model, View subMenu) {
        if (model.getType() == FoodUEToolsActivity.Type.TYPE_EXIT) {
            if (exitListener != null) {
                exitListener.onClick(getContext());
            }
            FoodUETool.getInstance(null).exit();
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
