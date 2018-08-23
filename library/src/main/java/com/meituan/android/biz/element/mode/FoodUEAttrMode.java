package com.meituan.android.biz.element.mode;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.meituan.android.biz.FoodUEBaseElementMode;
import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.library.R;
import com.meituan.android.utils.FoodUEDimensionUtils;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/11 on 下午12:38
 */
public class FoodUEAttrMode extends FoodUEBaseElementMode {
    //线距离view的偏移量
    private final int lineBorderDistance = FoodUEDimensionUtils.dip2px(5);

    public FoodUEAttrMode(OnViewInfoSelectedListener listener) {
        super(listener);
        areaPaint.setColor(FoodUETool.getResource().getColor(R.color.food_ue_attr_selected_view_bg));
    }

    @Override
    public void onDraw(Canvas canvas) {//绘制边框线
        if (selectedViewInfo != null) {
            Rect rect = selectedViewInfo.getRect();
            canvas.drawRect(rect, areaPaint);
            //横线
            if (rect.top - lineBorderDistance <= 0 && rect.bottom + lineBorderDistance >= SCREEN_HEIGHT) {
                drawLineWithText(canvas, rect.left, rect.top + lineBorderDistance, rect.right, rect.top + lineBorderDistance);
            } else if (rect.top - lineBorderDistance < 0 && rect.bottom + lineBorderDistance < SCREEN_HEIGHT) {
                drawLineWithText(canvas, rect.left, rect.bottom + lineBorderDistance, rect.right, rect.bottom + lineBorderDistance);
            } else {
                drawLineWithText(canvas, rect.left, rect.top - lineBorderDistance, rect.right, rect.top - lineBorderDistance);
            }

            //竖线
            if (rect.right + lineBorderDistance >= SCREEN_WIDTH && rect.left - lineBorderDistance <= 0) {
                drawLineWithText(canvas, rect.right - lineBorderDistance, rect.top, rect.right - lineBorderDistance, rect.bottom);
            } else if (rect.right + lineBorderDistance >= SCREEN_WIDTH && rect.left - lineBorderDistance > 0) {
                drawLineWithText(canvas, rect.left - lineBorderDistance, rect.top, rect.left - lineBorderDistance, rect.bottom);
            } else {
                drawLineWithText(canvas, rect.right + lineBorderDistance, rect.top, rect.right + lineBorderDistance, rect.bottom);
            }
        }
    }
}
