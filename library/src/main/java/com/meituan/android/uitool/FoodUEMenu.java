package com.meituan.android.uitool;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
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
import com.meituan.android.uitool.library.R;
import com.meituan.android.utils.FoodDevUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import roboguice.util.Ln;

/**
 * Author: gaojin
 * Time: 2018/6/19 下午3:34
 */

@SuppressLint("ViewConstructor")
public class FoodUEMenu extends LinearLayout {

    private View vMenu;
    private ViewGroup vSubMenuContainer;
    private ValueAnimator animator;
    private Interpolator defaultInterpolator = new AccelerateDecelerateInterpolator();

    private WindowManager windowManager;
    private WindowManager.LayoutParams params = new WindowManager.LayoutParams();
    private int touchSlop;

    private SubMenuClickEvent exportEvent;

    public FoodUEMenu(final Context context) {
        super(context);
        inflate(context, R.layout.food_ue_menu_layout, this);
        setGravity(Gravity.CENTER_VERTICAL);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        windowManager = (WindowManager) ApplicationSingleton.getInstance().getSystemService(Context.WINDOW_SERVICE);

        vMenu = findViewById(R.id.menu);
        vSubMenuContainer = findViewById(R.id.sub_menu_container);

        List<FoodUESubMenu.SubMenu> subMenus = new ArrayList<>();
        subMenus.add(new FoodUESubMenu.SubMenu("测量条", R.drawable.food_ue_show_gridding, new OnClickListener() {
            @Override
            public void onClick(View v) {
                open(FoodTransparentActivity.Type.TYPE_SHOW_GRIDDING);
            }
        }));

        subMenus.add(new FoodUESubMenu.SubMenu("关闭", R.drawable.ui_close, new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (exportEvent != null) {
                    exportEvent.onClick(getContext());
                }
            }
        }));

        LayoutParams subMenuParams = new LayoutParams(BaseConfig.dp2px(50), BaseConfig.dp2px(50));
        subMenuParams.leftMargin = BaseConfig.dp2px(10);
        for (FoodUESubMenu.SubMenu subMenu : subMenus) {
            FoodUESubMenu UESubMenu = new FoodUESubMenu(getContext());
            UESubMenu.update(subMenu);
            vSubMenuContainer.addView(UESubMenu, subMenuParams);
        }

        vMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim();
            }
        });

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
                            try {
                                Field field = View.class.getDeclaredField("mListenerInfo");
                                field.setAccessible(true);
                                Object object = field.get(vMenu);
                                field = object.getClass().getDeclaredField("mOnClickListener");
                                field.setAccessible(true);
                                object = field.get(object);
                                if (object != null && object instanceof OnClickListener) {
                                    ((OnClickListener) object).onClick(vMenu);
                                }
                            } catch (Exception e) {
                                Ln.e(e);
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void startAnim() {
        ensureAnim();
        final boolean isOpen = vSubMenuContainer.getTranslationX() <= -vSubMenuContainer.getWidth();
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
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    vSubMenuContainer.setTranslationX((int) animation.getAnimatedValue());
                }
            });
            animator.setDuration(400);
        }
    }

    private void open(@FoodTransparentActivity.Type int type) {
        Activity currentTopActivity = FoodDevUtils.getCurrentActivity();
        if (currentTopActivity == null) {
            return;
        } else if (currentTopActivity.getClass() == FoodTransparentActivity.class) {
            currentTopActivity.finish();
            return;
        }
        Intent intent = new Intent(currentTopActivity, FoodTransparentActivity.class);
        intent.putExtra(FoodTransparentActivity.EXTRA_TYPE, type);
        currentTopActivity.startActivity(intent);
        currentTopActivity.overridePendingTransition(0, 0);
        FoodUETool.getInstance().setTargetActivity(currentTopActivity);
    }

    public void show() {
        try {
            windowManager.addView(this, getWindowLayoutParams());
        } catch (Exception e) {
            Ln.e(e);
        }
    }

    public void dismiss() {
        try {
            windowManager.removeView(this);
        } catch (Exception e) {
            Ln.e(e);
        }
    }

    public void setExportEvent(SubMenuClickEvent exportEvent) {
        this.exportEvent = exportEvent;
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
        params.gravity = Gravity.TOP | Gravity.LEFT;
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
}
