package com.meituan.android.uitool.biz.relative.mode;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.MotionEvent;

import com.meituan.android.uitool.biz.FoodUEBaseElementMode;
import com.meituan.android.uitool.model.FoodUEViewInfo;
import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.utils.FoodUEDimensionUtils;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/23 on 下午3:36
 * 使用这个mode需要关闭硬件加速
 */
public class FoodUERelativeMode extends FoodUEBaseElementMode {
    //目前只支持两两相对
    private FoodUEViewInfo[] relativeViews = new FoodUEViewInfo[2];
    //选中元素的次数
    private int clickCount = 0;
    //线头线尾的偏移距离,防止线头线尾被遮挡
    private int endPointSpace = FoodUEDimensionUtils.dip2px(2);

    public FoodUERelativeMode(OnViewInfoSelectedListener viewInfoSelectedListener) {
        super(viewInfoSelectedListener);
        areaPaint.setColor(Color.RED);
        areaPaint.setStyle(Paint.Style.STROKE);
        areaPaint.setStrokeWidth(FoodUEDimensionUtils.dip2px(1));

        int solidWidth = FoodUEDimensionUtils.dip2px(4);
        int dashWidth = FoodUEDimensionUtils.dip2px(8);
        dashLinePaint.setColor(FoodUETool.getResource().getColor(R.color.food_ue_relative_dash_link_color));
        dashLinePaint.setPathEffect(new DashPathEffect(new float[]{solidWidth, dashWidth}, 0));
    }

    @Override
    public void triggerActionUp(MotionEvent event) {
        super.triggerActionUp(event);
        if (selectedViewInfo != null) {
            relativeViews[clickCount % 2] = selectedViewInfo;
            clickCount++;
            if (mViewChangeListener != null) {
                mViewChangeListener.onViewChange();
            }
        }
    }

    @Override
    public void onAttach2Window() {
        super.onAttach2Window();
        if (relativeViews == null) {
            relativeViews = new FoodUEViewInfo[2];
        } else {
            relativeViews[0] = null;
            relativeViews[1] = null;
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clickCount = 0;
        relativeViews = null;
    }

    @Override
    public void onDraw(Canvas canvas) {
        //判断是否已经有2个相对元素
        boolean valid = true;

        for (FoodUEViewInfo viewInfo : relativeViews) {
            if (viewInfo != null) {
                drawDashLineAndRect(canvas, viewInfo);
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
                drawLineWithText(canvas, x, firstRect.bottom, x, secondRect.top, endPointSpace);
            }
            if (firstRect.top > secondRect.bottom) {
                drawLineWithText(canvas, x, secondRect.bottom, x, firstRect.top, endPointSpace);
            }

            //画相对横线
            if (secondRect.left > firstRect.right) {
                drawLineWithText(canvas, secondRect.left, y, firstRect.right, y, endPointSpace);
            }
            if (firstRect.left > secondRect.right) {
                drawLineWithText(canvas, secondRect.right, y, firstRect.left, y, endPointSpace);
            }
            //处理两个view重叠的情况
            drawNestedAreaLine(canvas, firstRect, secondRect);
            drawNestedAreaLine(canvas, secondRect, firstRect);
        }
    }

    //-----------private------------

    /**
     * 绘制四边矩形延申到屏幕的虚线, 以及选中矩形的边框
     *
     * @param canvas   画布
     * @param viewInfo 选中元素
     */
    private void drawDashLineAndRect(Canvas canvas, @NonNull FoodUEViewInfo viewInfo) {
        Rect rect = viewInfo.getRect();
        canvas.drawLine(0, rect.top, SCREEN_WIDTH, rect.top, dashLinePaint);
        canvas.drawLine(0, rect.bottom, SCREEN_WIDTH, rect.bottom, dashLinePaint);
        canvas.drawLine(rect.left, 0, rect.left, SCREEN_HEIGHT, dashLinePaint);
        canvas.drawLine(rect.right, 0, rect.right, SCREEN_HEIGHT, dashLinePaint);
        canvas.drawRect(rect, areaPaint);
    }

    /**
     * 绘制两个view重叠的情况, 只处理firstRect被包含在secondRect中的情况
     *
     * @param canvas     画布
     * @param firstRect  被包含的viewInfo
     * @param secondRect 最外面的viewInfo
     */
    private void drawNestedAreaLine(Canvas canvas, Rect firstRect, Rect secondRect) {
        if (secondRect.left >= firstRect.left && secondRect.right <= firstRect.right && secondRect.top >= firstRect.top && secondRect.bottom <= firstRect.bottom) {

            int y = secondRect.top + secondRect.height() / 2;
            //内矩形到外矩形的左右两边距离
            drawLineWithText(canvas, secondRect.left, y, firstRect.left, y, endPointSpace);
            drawLineWithText(canvas, secondRect.right, y, firstRect.right, y, endPointSpace);

            int x = secondRect.left + secondRect.width() / 2;
            //内矩形到外矩形的上下距离
            drawLineWithText(canvas, x, secondRect.top, x, firstRect.top, endPointSpace);
            drawLineWithText(canvas, x, secondRect.bottom, x, firstRect.bottom, endPointSpace);
        }
    }

}
