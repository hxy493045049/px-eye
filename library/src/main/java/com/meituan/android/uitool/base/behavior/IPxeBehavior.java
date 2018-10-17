package com.meituan.android.uitool.base.behavior;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/21 on 下午3:43
 * 定义了实现功能的类和具体view交互的接口
 */
public interface IPxeBehavior {
    void onDraw(Canvas canvas);

    boolean onActionDown(MotionEvent event);

    boolean onActionMove(MotionEvent event);

    boolean onActionUp(MotionEvent event);

    void onAttach2View();

    void onDetachedFromView();
}
