package com.meituan.android.plugin;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.meituan.android.biz.FoodUEBaseElementMode;
import com.meituan.android.biz.IFoodUEMode;
import com.meituan.android.biz.element.mode.FoodUEAttrMode;
import com.meituan.android.biz.relative.mode.FoodUERelativeMode;
import com.meituan.android.model.FoodUEViewInfo;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/10 on 上午10:56
 */
public class FoodUEElementLayout extends View implements FoodUEBaseElementMode.OnViewChangeListener {
    private IFoodUEMode mModeImpl;
    private FoodUEBaseElementMode.OnViewInfoSelectedListener viewInfoSelectedListener;

    public FoodUEElementLayout(Context context) {
        this(context, null);
    }

    public FoodUEElementLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoodUEElementLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mModeImpl = new FoodUEAttrMode(new DefaultSelectedViewInfoListener());
    }

    public void setOnViewInfoSelectedListener(FoodUEBaseElementMode.OnViewInfoSelectedListener l) {
        viewInfoSelectedListener = l;
    }

    public void setModeImpl(IFoodUEMode modeImpl) {
        if (modeImpl != null) {
            mModeImpl = modeImpl;
            if (mModeImpl instanceof FoodUEBaseElementMode) {
                ((FoodUEBaseElementMode) mModeImpl).setOnViewChangeListener(this);
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mModeImpl.onAttach2Window();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mModeImpl.onDetachedFromWindow();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mModeImpl == null) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mModeImpl.onActionDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mModeImpl.triggerActionMove(event);
                break;
            case MotionEvent.ACTION_UP:
                mModeImpl.triggerActionUp(event);
                invalidate();
                performClick();
                break;

        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mModeImpl != null) {
            mModeImpl.onDraw(canvas);
        }
    }

    //mode的逻辑触发了view变化,重绘
    @Override
    public void onViewChange() {
        invalidate();
    }


    private class DefaultSelectedViewInfoListener implements FoodUEBaseElementMode.OnViewInfoSelectedListener {

        @Override
        public void onViewInfoSelected(FoodUEViewInfo selectedViewInfo) {
            if (viewInfoSelectedListener != null) {
                viewInfoSelectedListener.onViewInfoSelected(selectedViewInfo);
            }
        }
    }
}
