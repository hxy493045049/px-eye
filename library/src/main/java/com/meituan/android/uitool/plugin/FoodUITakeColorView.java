package com.meituan.android.uitool.plugin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.meituan.android.uitool.utils.FoodUEDimensionUtils;

public class FoodUITakeColorView extends FrameLayout {

    public FoodUITakeColorView(Context context) {
        super(context);
        init();
    }

    public FoodUITakeColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FoodUITakeColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("NewApi")
    public FoodUITakeColorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private float lastX;
    private float lastY;

    private float pressX;
    private float pressY;

    private long pressTime;

    private void init(){
        addView(new TakeColorView(getContext()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = event.getRawX();
            lastY = event.getRawY();
            pressX = lastX;
            pressY = lastY;
            pressTime = System.currentTimeMillis();
        } else {
            float curX = event.getRawX();
            float curY = event.getRawY();
            ViewGroup.LayoutParams params = getLayoutParams();
            if (!(params instanceof MarginLayoutParams)) {
                return super.onTouchEvent(event);
            }
            MarginLayoutParams marginLayoutParams = ((MarginLayoutParams) params);
            marginLayoutParams.leftMargin += (curX - lastX);
            marginLayoutParams.topMargin += (curY - lastY);
            setLayoutParams(marginLayoutParams);
            lastX = curX;
            lastY = curY;

        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (System.currentTimeMillis() - pressTime > 2 * 1000) {
                float curX = event.getRawX();
                float curY = event.getRawY();
                int dis = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                if (Math.abs(curX - pressX) <= dis && Math.abs(curY - pressY) <= dis) {
//                    ((ViewGroup) getParent()).removeView(this);
                }
            }
        }

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof OnMoveListener) {
                OnMoveListener moveListener = ((OnMoveListener) child);
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        moveListener.down(event.getX(), event.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveListener.move(event.getX(), event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        moveListener.up(event.getX(), event.getY());
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        moveListener.cancel(event.getX(), event.getY());
                        break;
                }
            }
        }

        return true;
    }

    interface OnMoveListener {
        void down(float x, float y);

        void move(float x, float y);

        void up(float x, float y);

        void cancel(float x, float y);
    }

    public static class TakeColorView extends View implements FoodUITakeColorView.OnMoveListener {
        public TakeColorView(Context context) {
            super(context);
            init();
        }

        public TakeColorView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public TakeColorView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        @SuppressLint("NewApi")
        public TakeColorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            init();
        }

        private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        private int textW;
        private int textH;

        private Integer ltColor;
        private Integer rtColor;
        private Integer lbColor;
        private Integer rbColor;

        private Bitmap bitmap;

        private void init() {
            mPaint.setColor(Color.BLACK);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(FoodUEDimensionUtils.dip2px(12));
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
            textH = fontMetrics.bottom - fontMetrics.top;
            textW = (int) mPaint.measureText("#ffffffff");


//        String txt = "#ffffffff ";
//        Rect rect = new Rect();
//        mPaint.getTextBounds(txt, 0, txt.length(), rect);
//        textH = rect.height();
//        textW = rect.width();

            setMeasuredDimension(textW * 3, textH * 6);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int w = getWidth();
            int h = getHeight();

            mPaint.setStyle(Paint.Style.FILL);
            if (ltColor != null) {
                drawColor(canvas, ltColor, 0, 0, w / 2, h / 2);
            }
            if (lbColor != null) {
                drawColor(canvas, lbColor, 0, h / 2, w / 2, h);
            }

            if (rtColor != null) {
                drawColor(canvas, rtColor, w / 2, 0, w, h / 2);
            }
            if (rbColor != null) {
                drawColor(canvas, rbColor, w / 2, h / 2, w, h);
            }

            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.BLACK);
            canvas.drawRect(0, 0, w - 1, h - 1, mPaint);


        }

        private void drawColor(Canvas canvas, int color, int l, int t, int r, int b) {
            mPaint.setColor(color);
            canvas.drawRect(l, t, r, b, mPaint);
            if ((~color | 0xff000000) == color) {
                color = 0xffff0000;
            } else {
                color = (~color | 0xff000000);
            }
            mPaint.setColor(color);
            canvas.drawText(String.format("#%08x", color), l, t + textH, mPaint);

        }

        @Override
        public void down(float x, float y) {
            ltColor = null;
            rtColor = null;
            lbColor = null;
            rbColor = null;
        }

        @Override
        public void move(float x, float y) {

        }

        @Override
        public void up(float x, float y) {

            setVisibility(INVISIBLE);
            try {
                View root = findRootView();
                if (bitmap == null) {
                    bitmap = Bitmap.createBitmap(root.getWidth(), root.getHeight(), Bitmap.Config.ARGB_8888);
                }
                if (bitmap.getWidth() < root.getWidth() || bitmap.getHeight() < root.getHeight()) {
                    bitmap = Bitmap.createBitmap(root.getWidth(), root.getHeight(), Bitmap.Config.ARGB_8888);
                }
                root.draw(new Canvas(bitmap));
                int location[] = new int[2];
                getLocationOnScreen(location);
                try {
                    ltColor = bitmap.getPixel(location[0], location[1]);
                } catch (Exception e) {
                    ltColor = null;
                }
                try {
                    rtColor = bitmap.getPixel(location[0] + getWidth(), location[1]);
                } catch (Exception e) {
                    rtColor = null;
                }
                try {
                    lbColor = bitmap.getPixel(location[0], location[1] + getHeight());
                } catch (Exception e) {
                    lbColor = null;
                }
                try {
                    rbColor = bitmap.getPixel(location[0] + getWidth(), location[1] + getHeight());
                } catch (Exception e) {
                    rbColor = null;
                }

            } catch (Exception e) {
                ltColor = null;
                rtColor = null;
                lbColor = null;
                rbColor = null;
            }
            setVisibility(VISIBLE);
        }

        private View findRootView() {
            return getRootView();
        }

        @Override
        public void cancel(float x, float y) {

        }
    }

}
