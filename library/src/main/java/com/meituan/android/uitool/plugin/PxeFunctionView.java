package com.meituan.android.uitool.plugin;

import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.meituan.android.uitool.base.behavior.IPxeBehavior;
import com.meituan.android.uitool.base.behavior.PxeBaseBehavior;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/10 on 上午10:56
 * 实现具体功能的类,准确的说这个类只是一个代理类
 * 1. 管理view的生命周期
 * 2. 接受点击事件和view的绘制流程
 * 3. 具体的功能逻辑抽象于{@link IPxeBehavior}的实现
 */
public class PxeFunctionView extends FrameLayout implements PxeBaseBehavior.OnSelectedViewChangeListener {
    private IPxeBehavior mBehavior;

    public PxeFunctionView(Context context) {
        this(context, null);
    }

    public PxeFunctionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PxeFunctionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    public void setBehavior(IPxeBehavior behaviorImpl) {
        if (behaviorImpl != null) {
            mBehavior = behaviorImpl;
            mBehavior.onAttach2View();
            if (mBehavior instanceof PxeBaseBehavior) {
                ((PxeBaseBehavior) mBehavior).setOnViewChangeListener(this);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mBehavior != null) {
            mBehavior.onDetachedFromView();
            mBehavior = null;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
        // TODO: 2018/9/19 考虑下要不要做代理滚动,将滚动事件转移到目标act中
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mBehavior != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mBehavior.onActionDown(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mBehavior.onActionMove(event);
                    break;
                case MotionEvent.ACTION_UP:
                    mBehavior.onActionUp(event);
                    performClick();
                    break;
            }
            return true;
        } else {
            return super.onTouchEvent(event);
        }

    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBehavior != null) {
            mBehavior.onDraw(canvas);
        }
    }

    //选中的viewInfo发生了变化,重绘边框
    @Override
    public void onSelectedViewChange() {
        requestLayout();
        invalidate();
    }
}
