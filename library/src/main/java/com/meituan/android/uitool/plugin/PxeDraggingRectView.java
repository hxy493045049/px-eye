package com.meituan.android.uitool.plugin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.utils.PxeDimensionUtils;

/**
 * Author: gaojin
 * Time: 2018/6/25 下午9:34
 */
public class PxeDraggingRectView extends View {
    private final static int UNIT = PxeDimensionUtils.dip2px(1);
    private final static int SCREEN_WIDTH = PxeDimensionUtils.getScreenWidth();
    private final static int SCREEN_HEIGHT = PxeDimensionUtils.getScreenHeight();
    private Paint paintRed = new Paint();
    private Paint paintText = new Paint();
    private int textHeight = -1;
    private int touchSlop;
    private int height = 50;
    private GestureDetectorCompat mDetectorCompat;

    public PxeDraggingRectView(Context context) {
        this(context, null);
    }

    public PxeDraggingRectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PxeDraggingRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //draw line
        paintRed.setAntiAlias(true);
        paintRed.setColor(Color.RED);
        paintRed.setStrokeWidth(1);
        //draw text
        paintText.setAntiAlias(true);
        paintText.setColor(Color.RED);
        paintText.setStrokeWidth(1);
        paintText.setTextSize(24);
        paintText.setTextAlign(Paint.Align.LEFT);
        Paint.FontMetrics metrics = paintText.getFontMetrics();
        textHeight = (int) (metrics.bottom - metrics.top);

        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, PxeDimensionUtils.dip2px(height));
        params.gravity = Gravity.CENTER;
        setLayoutParams(params);
        setBackgroundColor(getResources().getColor(R.color.pxe_measure_bar_bg_color));
        mDetectorCompat = new GestureDetectorCompat(getContext(), new DefaultGestureListener());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetectorCompat.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float y = getMeasuredHeight() / 2;
        canvas.drawLine(0, y, SCREEN_WIDTH, y, paintRed);
        canvas.drawText("width: " + PxeDimensionUtils.px2dip(getWidth()) + "dp", 0, y - 10, paintText);
        canvas.drawText("height:" + PxeDimensionUtils.px2dip(getHeight()) + "dp", 0, textHeight + y, paintText);
    }

    //根据方向盘的弧度微调位置
    public void updatePosition(double arc) {
        float translationX = 0;
        float translationY = 0;
        if (arc == 2 * Math.PI) {
            translationX = UNIT;
            translationY = 0;
        } else if (arc == 0.5 * Math.PI) {
            translationX = 0;
            translationY = -UNIT;
        } else if (arc == Math.PI) {
            translationX = -UNIT;
            translationY = 0;
        } else if (arc == 1.5 * Math.PI) {
            translationX = 0;
            translationY = UNIT;
        } else if (arc > 0 && arc < Math.PI) {
            translationX = (float) (UNIT * Math.cos(arc));
            translationY = -(float) (UNIT * Math.sin(arc));
        } else if (arc > 0.5 * Math.PI && arc < Math.PI) {
            translationX = -(float) (UNIT * Math.cos(Math.PI - arc));
            translationY = -(float) (UNIT * Math.sin(Math.PI - arc));
        } else if (arc > Math.PI && arc < 1.5 * Math.PI) {
            translationX = -(float) (UNIT * Math.cos(arc - Math.PI));
            translationY = (float) (UNIT * Math.sin(arc - Math.PI));
        } else if (arc > 1.5 * Math.PI && arc < 2 * Math.PI) {
            translationX = (float) (UNIT * Math.cos(Math.PI * 2 - arc));
            translationY = (float) (UNIT * Math.sin(Math.PI * 2 - arc));
        }

        updateTranslation(translationX, translationY);
    }

    private void updateTranslation(float translationX, float translationY) {
        if (getX() + translationX <= 0) {
            setX(0);
        } else if (getX() + translationX >= SCREEN_WIDTH - getWidth()) {
            setX(SCREEN_WIDTH - getWidth());
        } else {
            setTranslationX(getTranslationX() + translationX);
        }

        if (getY() + translationY <= 0) {
            setY(0);
        } else if (getY() + translationY >= SCREEN_HEIGHT - getHeight()) {
            setY(SCREEN_HEIGHT - getHeight());
        } else {
            setTranslationY(getTranslationY() + translationY);
        }
    }

    private class DefaultGestureListener extends GestureDetector.SimpleOnGestureListener {

        private float rawX, rawY;//手指按下时的坐标
        private float offectX, offectY;

        @Override
        public boolean onDown(MotionEvent e) {
            rawX = e.getRawX();
            rawY = e.getRawY();
            offectX = getWidth() / 2 - e.getX();
            offectY = getHeight() / 2 - e.getY();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float newX = e2.getRawX() - getWidth() / 2 + offectX;
            float newY = e2.getRawY() - getHeight() / 2 + offectY;


            if (newX + getWidth() > SCREEN_WIDTH) {
                setX(SCREEN_WIDTH - getWidth());
            } else if (newX <= 0) {
                setX(0);
            } else {
                setX(newX);
            }


            if (newY + getHeight() > SCREEN_HEIGHT) {
                setY(SCREEN_HEIGHT - getHeight());
            } else if (newY <= 0) {
                setY(0);
            } else {
                setY(newY);
            }
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (Math.abs(e.getRawX() - rawX) <= touchSlop && Math.abs(e.getRawY() - rawY) <= touchSlop) {
                performClick();
            }
            return true;
        }
    }
}
