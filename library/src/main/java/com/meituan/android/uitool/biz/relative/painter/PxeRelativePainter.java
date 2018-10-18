package com.meituan.android.uitool.biz.relative.painter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;

import com.meituan.android.uitool.base.painter.PxeBasePainter;
import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;
import com.meituan.android.uitool.utils.PxeDimensionUtils;
import com.meituan.android.uitool.utils.PxeResourceUtils;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/9/19 on 上午10:45
 */
public class PxeRelativePainter extends PxeBasePainter {
    //线头线尾的偏移距离,防止线头线尾被遮挡
    public int endPointSpace = PxeDimensionUtils.dip2px(2);

    public PxeRelativePainter() {
        areaPaint.setColor(Color.RED);
        areaPaint.setStyle(Paint.Style.STROKE);
        areaPaint.setStrokeWidth(PxeDimensionUtils.dip2px(1));

        int solidWidth = PxeDimensionUtils.dip2px(4);
        int dashWidth = PxeDimensionUtils.dip2px(8);
        dashLinePaint.setColor(PxeResourceUtils.getResource().getColor(R.color.pxe_relative_dash_link_color));
        dashLinePaint.setPathEffect(new DashPathEffect(new float[]{solidWidth, dashWidth}, 0));
    }

    /**
     * 绘制四边矩形延申到屏幕的虚线, 以及选中矩形的边框
     *
     * @param canvas   画布
     * @param viewInfo 选中元素
     */
    public void drawDashLineAndRect(Canvas canvas, @NonNull PxeViewInfo viewInfo) {
        Rect rect = viewInfo.getRect();
        canvas.drawLine(0, rect.top, screenWidth, rect.top, dashLinePaint);
        canvas.drawLine(0, rect.bottom, screenWidth, rect.bottom, dashLinePaint);
        canvas.drawLine(rect.left, 0, rect.left, screenHeight, dashLinePaint);
        canvas.drawLine(rect.right, 0, rect.right, screenHeight, dashLinePaint);
        canvas.drawRect(rect, areaPaint);
    }

    /**
     * 绘制两个view重叠的情况, 只处理firstRect被包含在secondRect中的情况
     *
     * @param canvas     画布
     * @param firstRect  被包含的viewInfo
     * @param secondRect 最外面的viewInfo
     */
    public void drawNestedAreaLine(Canvas canvas, Rect firstRect, Rect secondRect) {
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
