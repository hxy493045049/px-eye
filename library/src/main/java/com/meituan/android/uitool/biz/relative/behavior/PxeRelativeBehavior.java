package com.meituan.android.uitool.biz.relative.behavior;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.meituan.android.uitool.base.behavior.PxeBaseBehavior;
import com.meituan.android.uitool.biz.relative.painter.PxeRelativePainter;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/23 on 下午3:36
 * 相对位置功能的逻辑控制段元
 */
public class PxeRelativeBehavior extends PxeBaseBehavior.PxeSimpleBehavior {
    //目前只支持两两相对
    private PxeViewInfo[] relativeViews = new PxeViewInfo[2];
    //点击元素的次数
    private int clickCount = 0;
    //绘图工具
    private PxeRelativePainter mPainter;

    public PxeRelativeBehavior(PxeRelativePainter painter) {
        super(painter);
        mPainter = painter;
    }

    @Override
    public boolean onActionUp(MotionEvent event) {
        super.onActionUp(event);
        if (selectedViewInfo != null) {
            relativeViews[clickCount % 2] = selectedViewInfo;
            clickCount++;
            if (mViewChangeListener != null) {
                mViewChangeListener.onSelectedViewChange();
            }
        }
        return false;
    }

    @Override
    public void onAttach2View() {
        super.onAttach2View();
        if (relativeViews == null) {
            relativeViews = new PxeViewInfo[2];
        } else {
            relativeViews[0] = null;
            relativeViews[1] = null;
        }
    }

    @Override
    public void onDetachedFromView() {
        super.onDetachedFromView();
        clickCount = 0;
        relativeViews = null;
    }

    @Override
    public void onDraw(Canvas canvas) {
        //判断是否已经有2个相对元素
        boolean valid = true;

        for (PxeViewInfo viewInfo : relativeViews) {
            if (viewInfo != null) {
                mPainter.drawDashLineAndRect(canvas, viewInfo);
            } else {
                valid = false;
            }
        }

        if (valid) {
            Rect firstRect = relativeViews[clickCount % 2].getRect();
            Rect secondRect = relativeViews[(clickCount + 1) % 2].getRect();
            //相对线都以第二个view为准心绘制
            int x = secondRect.left + secondRect.width() / 2;
            int y = secondRect.top + secondRect.height() / 2;

            //画相对竖线
            if (secondRect.top > firstRect.bottom) {
                mPainter.drawLineWithText(canvas, x, firstRect.bottom, x, secondRect.top, mPainter.endPointSpace);
            }
            if (firstRect.top > secondRect.bottom) {
                mPainter.drawLineWithText(canvas, x, secondRect.bottom, x, firstRect.top, mPainter.endPointSpace);
            }

            //画相对横线
            if (secondRect.left > firstRect.right) {
                mPainter.drawLineWithText(canvas, secondRect.left, y, firstRect.right, y, mPainter.endPointSpace);
            }
            if (firstRect.left > secondRect.right) {
                mPainter.drawLineWithText(canvas, secondRect.right, y, firstRect.left, y, mPainter.endPointSpace);
            }
            //处理两个view重叠的情况
            mPainter.drawNestedAreaLine(canvas, firstRect, secondRect);
            mPainter.drawNestedAreaLine(canvas, secondRect, firstRect);
        }
    }
}
