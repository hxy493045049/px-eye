package com.meituan.android.measure;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.meituan.android.base.BaseConfig;

/**
 * Author: gaojin
 * Time: 2018/6/25 下午9:34
 */

public class FoodDraggingRectView extends View implements View.OnClickListener {

    private Paint paintRed = new Paint();
    private Paint paintText = new Paint();
    private int touchSlop;
    private int height = 50;

    public FoodDraggingRectView(Context context) {
        this(context, null);
    }

    public FoodDraggingRectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoodDraggingRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BaseConfig.dp2px(height));
        params.gravity = Gravity.CENTER;
        setLayoutParams(params);
        setBackgroundColor(Color.parseColor("#26FF0000"));
        setOnClickListener(this);
    }

    @SuppressWarnings("PMD.SingularField")
    private float startY;
    @SuppressWarnings("PMD.SingularField")
    private float downY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getRawY();
                startY = getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getRawY();
                float y = startY + (moveY - downY);
                if (y > 0 && (y + getMeasuredHeight()) < BaseConfig.height) {
                    setY(y);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(event.getRawY() - downY) <= touchSlop) {
                    performClick();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float y = getMeasuredHeight() / 2;
        canvas.drawLine(0, y, BaseConfig.width, y, paintRed);
        canvas.drawText("height: " + height + "dp", 0, y, paintText);
    }

    @Override
    public void onClick(View v) {
        showDialog();
    }

    /**
     * @param height dp
     */
    private void setViewHeight(int height) {
        this.height = height;
        getLayoutParams().height = BaseConfig.dp2px(height);
        requestLayout();
    }

    private void showDialog() {
        FoodSetValueDialog dialog = new FoodSetValueDialog(getContext());
        dialog.setOnClickListener(new FoodSetValueDialog.onClickListener() {
            @Override
            public void onClick(String value) {
                try {
                    setViewHeight(Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "输入格式错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
}
