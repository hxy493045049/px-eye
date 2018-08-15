package com.meituan.android.plugin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.meituan.android.base.BaseConfig;
import com.meituan.android.uitool.FoodUETool;

/**
 * 测量功能的边框
 */
public class FoodUEGriddingLayout extends View {

    public static final int LINE_INTERVAL_DP = 5;
    public static final int LINE_INTERVAL = BaseConfig.dp2px(LINE_INTERVAL_DP);
    private final int screenWidth = BaseConfig.width;
    private final int screenHeight = BaseConfig.height;

    private Paint paintRed = new Paint();
    private Activity targetActivity = FoodUETool.getInstance().getTargetActivity();

    public FoodUEGriddingLayout(Context context) {
        this(context, null);
    }

    public FoodUEGriddingLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoodUEGriddingLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paintRed.setAntiAlias(true);
        paintRed.setColor(Color.RED);
        paintRed.setStrokeWidth(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int startX = LINE_INTERVAL * 3;
        canvas.drawLine(startX, 0, startX, screenHeight, paintRed);

        int rightPadding = screenWidth - LINE_INTERVAL * 3;
        canvas.drawLine(rightPadding, 0, rightPadding, screenHeight, paintRed);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        targetActivity.dispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        targetActivity = null;
    }
}
