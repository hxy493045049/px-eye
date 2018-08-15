package com.meituan.android.biz.element;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.meituan.android.utils.FoodUEDimensionUtils;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/11 on 下午12:38
 */
public class FoodUEAttrMode extends FoodUEBaseElementMode {
    private final int lineBorderDistance = FoodUEDimensionUtils.dip2px(5);//边框宽度

    public FoodUEAttrMode(OnViewInfoSelectedListener listener) {
        super(listener);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (selectedViewInfo != null) {
            Rect rect = selectedViewInfo.getRect();
            canvas.drawRect(rect, areaPaint);
            drawLineWithText(canvas, rect.left, rect.top - lineBorderDistance, rect.right, rect.top - lineBorderDistance);
            drawLineWithText(canvas, rect.right + lineBorderDistance, rect.top, rect.right + lineBorderDistance, rect.bottom);
        }
    }

    @Override
    public void triggerActionMove(MotionEvent event) {

    }

    @Override
    public void triggerActionUp(MotionEvent event) {
        super.triggerActionUp(event);
    }

}
