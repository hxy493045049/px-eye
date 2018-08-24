package com.meituan.android.uitool.plugin;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.utils.FoodUEViewUtils;

import java.lang.ref.WeakReference;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/8 on 下午5:59
 */
public class FoodUiSteeringWheel extends AppCompatImageButton {
    private OnWheelTouchListener listener;
    private DefaultHandler mDefaultHandler;
    public static final int TYPE_360 = 0;//支持360度偏移
    public static final int TYPE_4 = 0;//支持东南西北偏移
    private int mDefaultType = TYPE_4;
    private float[] position = new float[2];
    private int[] center = new int[2];

    public FoodUiSteeringWheel(Context context) {
        this(context, null);
    }

    public FoodUiSteeringWheel(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoodUiSteeringWheel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        setFocusableInTouchMode(true);
        setBackground(getResources().getDrawable(R.drawable.food_ue_steerwheel));
        mDefaultHandler = new DefaultHandler(this);
    }


    public void setOnWheelTouchListener(OnWheelTouchListener l) {
        listener = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        position[0] = event.getX();
        position[1] = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            center = FoodUEViewUtils.getCenter(this);
            mDefaultHandler.updatePosition(center, position);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mDefaultHandler.updatePosition(center, position);
            mDefaultHandler.sendEmptyMessage(DefaultHandler.START_MOVE);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            mDefaultHandler.sendEmptyMessage(DefaultHandler.STOP);
            performClick();
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    /**
     * 设置方向盘控制模式, 默认为4个方向, 支持360度控制
     *
     * @param type 控制模式, 参考{@link #TYPE_4} or {@link #TYPE_360}
     */
    public void setDirectionType(int type) {
        this.mDefaultType = type;
    }

    public interface OnWheelTouchListener {
        //将手指点击的位置和中心点之间的夹角对外暴露
        void onDirection(double arc);
    }

    public static class DefaultHandler extends Handler {
        private WeakReference<FoodUiSteeringWheel> wheelRef;
        private static final int START_MOVE = 1;
        private static final int STOP = 2;
        private static final int MOVE = 3;
        private float[] position = new float[2];
        private int[] center = new int[2];
        private static final double limit = Math.PI / 4;

        private DefaultHandler(FoodUiSteeringWheel wheel) {
            wheelRef = new WeakReference<>(wheel);
        }

        @Override
        public void handleMessage(Message msg) {
            FoodUiSteeringWheel wheel = wheelRef.get();
            if (wheel == null || wheel.listener == null) {
                return;
            }
            super.handleMessage(msg);
            switch (msg.what) {
                case START_MOVE:
                    wheel.listener.onDirection(wheel.mDefaultType == TYPE_4 ? calculateDirection() : calculateArc());
                    removeMessages(MOVE);
                    sendEmptyMessageDelayed(MOVE, 20);
                    break;
                case MOVE:
                    sendEmptyMessageDelayed(MOVE, 20);
                    wheel.listener.onDirection(wheel.mDefaultType == TYPE_4 ? calculateDirection() : calculateArc());
                    break;
                case STOP:
                    removeMessages(START_MOVE);
                    removeMessages(MOVE);
            }
        }

        /**
         * 计算当前弧度是东南西北中的哪个方向
         *
         * @return 东南西北任一方向
         */
        private double calculateDirection() {
            double arc = calculateArc();
            if (arc <= limit || arc > limit * 7) {
                return 2 * Math.PI;//左
            } else if (arc <= limit * 3 && arc > limit) {
                return 0.5 * Math.PI;//上
            } else if (arc <= limit * 5 && arc > limit * 3) {
                return Math.PI;//左
            } else if (arc <= limit * 7 && arc > limit * 5) {
                return 1.5 * Math.PI;//下
            } else {
                return 0;
            }
        }

        /**
         * 计算弧度, 注意是弧度而不是角度
         *
         * @return 弧度
         */
        private double calculateArc() {
            float deltaX = position[0] - center[0];
            float deltaY = position[1] - center[1];
            if (deltaX > 0 && deltaY < 0) {//东北方向
                return Math.atan(Math.abs(deltaY / deltaX));
            } else if (deltaX < 0 && deltaY < 0) {//西北方向
                return Math.PI - Math.atan(Math.abs(deltaY / deltaX));
            } else if (deltaX < 0 && deltaY > 0) {//西南方向
                return Math.PI + Math.atan(Math.abs(deltaY / deltaX));
            } else if (deltaX > 0 && deltaY > 0) {//东南方向
                return 2 * Math.PI - Math.atan(Math.abs(deltaY / deltaX));
            } else if (deltaX > 0 && deltaY == 0) {
                return 2 * Math.PI;//右
            } else if (deltaX == 0 && deltaY < 0) {
                return 0.5 * Math.PI;//上
            } else if (deltaX < 0 && deltaY == 0) {
                return Math.PI;//左
            } else if (deltaX == 0 && deltaY > 0) {
                return 1.5 * Math.PI;//下
            } else {//原点
                return 0;
            }
        }

        /**
         * 更新中心点坐标和按压的坐标
         *
         * @param center   view中心点
         * @param position 手指按压坐标
         */
        private void updatePosition(int[] center, float[] position) {
            this.position = position;
            this.center = center;
        }
    }
}
