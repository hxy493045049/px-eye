package com.meituan.android.uitool.plugin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.helper.PxeActivityRecorder;
import com.meituan.android.uitool.utils.PxeActivityUtils;
import com.meituan.android.uitool.utils.PxeDimensionUtils;

/**
 * 测量功能的边框
 * todo 重构这个view
 */
public class PxeGriddingLayout extends View {

    public static final int LINE_INTERVAL_DP = 5;
    public static final int LINE_INTERVAL = PxeDimensionUtils.dip2px(LINE_INTERVAL_DP);
    private final int SCREEN_WIDTH = PxeDimensionUtils.getScreenWidth();
    private final int SCREEN_HEIGHT = PxeDimensionUtils.getScreenHeight();

    private Paint paintRed = new Paint();

    public PxeGriddingLayout(Context context) {
        this(context, null);
    }

    public PxeGriddingLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PxeGriddingLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paintRed.setAntiAlias(true);
        paintRed.setColor(Color.RED);
        paintRed.setStrokeWidth(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int startX = LINE_INTERVAL * 3;
        canvas.drawLine(startX, 0, startX, SCREEN_HEIGHT, paintRed);

        int rightPadding = SCREEN_WIDTH - LINE_INTERVAL * 3;
        canvas.drawLine(rightPadding, 0, rightPadding, SCREEN_HEIGHT, paintRed);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Activity targetActivity = PxeActivityRecorder.getInstance().getTargetActivity();
        if (targetActivity != null && !PxeActivityUtils.isActivityInvalid(targetActivity)) {
            targetActivity.dispatchTouchEvent(event);
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

}
