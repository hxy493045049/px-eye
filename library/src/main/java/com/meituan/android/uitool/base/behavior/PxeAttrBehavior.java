package com.meituan.android.uitool.base.behavior;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.base.painter.PxeBasePainter;
import com.meituan.android.uitool.library.R;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/11 on 下午12:38
 */
public class PxeAttrBehavior extends PxeBaseBehavior.PxeSimpleBehavior {
    public PxeAttrBehavior(PxeBasePainter painter) {
        super(painter);
        basePainter.areaPaint.setColor(FoodUETool.getResource().getColor(R.color.pxe_attr_selected_view_bg));
    }

    //绘制边框线
    @Override
    public void onDraw(Canvas canvas) {
        if (selectedViewInfo != null) {
            Rect rect = selectedViewInfo.getRect();
            canvas.drawRect(rect, basePainter.areaPaint);
            //横线
            if (rect.top - basePainter.lineBorderDistance <= 0
                    && rect.bottom + basePainter.lineBorderDistance >= basePainter.screenHeight) {

                basePainter.drawLineWithText(canvas, rect.left, rect.top + basePainter.lineBorderDistance,
                        rect.right, rect.top + basePainter.lineBorderDistance);

            } else if (rect.top - basePainter.lineBorderDistance < 0
                    && rect.bottom + basePainter.lineBorderDistance < basePainter.screenHeight) {

                basePainter.drawLineWithText(canvas, rect.left, rect.bottom + basePainter.lineBorderDistance,
                        rect.right, rect.bottom + basePainter.lineBorderDistance);

            } else {

                basePainter.drawLineWithText(canvas, rect.left, rect.top - basePainter.lineBorderDistance,
                        rect.right, rect.top - basePainter.lineBorderDistance);
            }

            //竖线
            if (rect.right + basePainter.lineBorderDistance >= basePainter.screenWidth
                    && rect.left - basePainter.lineBorderDistance <= 0) {

                basePainter.drawLineWithText(canvas, rect.right - basePainter.lineBorderDistance, rect.top,
                        rect.right - basePainter.lineBorderDistance, rect.bottom);

            } else if (rect.right + basePainter.lineBorderDistance >= basePainter.screenWidth
                    && rect.left - basePainter.lineBorderDistance > 0) {

                basePainter.drawLineWithText(canvas, rect.left - basePainter.lineBorderDistance, rect.top,
                        rect.left - basePainter.lineBorderDistance, rect.bottom);

            } else {

                basePainter.drawLineWithText(canvas, rect.right + basePainter.lineBorderDistance, rect.top,
                        rect.right + basePainter.lineBorderDistance, rect.bottom);
            }
        }
    }
}
