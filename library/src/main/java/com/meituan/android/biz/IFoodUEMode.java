package com.meituan.android.biz;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/21 on 下午3:43
 */
public interface IFoodUEMode {
    void onDraw(Canvas canvas);

    void onActionDown(MotionEvent event);

    void triggerActionMove(MotionEvent event);

    void triggerActionUp(MotionEvent event);

    void onAttach2Window();

    void onDetachedFromWindow();
}
