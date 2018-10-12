package com.meituan.android.uitool.base.painter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.utils.PxeDimensionUtils;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/9/19 on 上午10:37
 * 提供基础的绘制功能和绘制配置
 */
public class PxeBasePainter {
    public int screenWidth = PxeDimensionUtils.getScreenWidth();
    public int screenHeight = PxeDimensionUtils.getScreenHeight();

    //文字相对红线的偏移量
    public final int textLineDistance = PxeDimensionUtils.dip2px(5);
    //文字两边的填充空白
    public final int textBgFillingSpace = PxeDimensionUtils.dip2px(2);
    //线头线尾
    public final int halfEndPointWidth = PxeDimensionUtils.dip2px(2.5f);
    //线距离view的偏移量
    public final int lineBorderDistance = PxeDimensionUtils.dip2px(5);

    //用于绘制选中元素的画笔,添加灰色半透明蒙层
    public Paint areaPaint = new Paint() {
        {
            setAntiAlias(true);
        }
    };

    //用于绘制 文本内容 及 描边 的红色画笔
    public Paint textPaint = new Paint() {
        {
            setAntiAlias(true);
            setTextSize(FoodUETool.getResource().getDimension(R.dimen.pxe_attr_text_size));
            setColor(Color.RED);
            setStrokeWidth(FoodUETool.getResource().getDimension(R.dimen.pxe_attr_stroke_width));
        }
    };

    //绘制文本的白色背景的画笔
    public Paint textBgPaint = new Paint() {
        {
            setAntiAlias(true);
            setColor(Color.WHITE);
            setStrokeJoin(Join.ROUND);
        }
    };

    //虚线
    public Paint dashLinePaint = new Paint() {
        {
            setStyle(Style.STROKE);
            setAntiAlias(true);
        }
    };

    public void drawLineWithText(Canvas canvas, int startX, int startY, int endX, int endY) {
        drawLineWithText(canvas, startX, startY, endX, endY, 0);
    }

    public void drawLineWithText(Canvas canvas, int startX, int startY, int endX, int endY, int endPointSpace) {
        if (startX == endX && startY == endY) {
            return;
        }
        if (startX > endX) {
            int tempX = startX;
            startX = endX;
            endX = tempX;
        }
        if (startY > endY) {
            int tempY = startY;
            startY = endY;
            endY = tempY;
        }

        if (startX == endX) {//竖线
            drawLineWithEndPoint(canvas, startX, startY + endPointSpace, endX, endY - endPointSpace);
            String text = PxeDimensionUtils.px2dip(endY - startY, true);
            drawText(canvas, text, startX + textLineDistance,
                    startY + (endY - startY) / 2 + PxeDimensionUtils.getTextHeight(text, textPaint) / 2);

        } else if (startY == endY) {//横线
            drawLineWithEndPoint(canvas, startX + endPointSpace, startY, endX - endPointSpace, endY);
            String text = PxeDimensionUtils.px2dip(endX - startX, true);
            drawText(canvas, text, startX + (endX - startX) / 2 - PxeDimensionUtils.getTextWidth(text, textPaint) / 2,
                    startY - textLineDistance);
        }
    }

    public void drawText(Canvas canvas, String text, float x, float y) {
        float left = x - textBgFillingSpace;
        float top = y - PxeDimensionUtils.getTextHeight(text, textPaint);
        float right = x + PxeDimensionUtils.getTextWidth(text, textPaint) + textBgFillingSpace;
        float bottom = y + textBgFillingSpace;
        // ensure text in screen bound
        if (left < 0) {
            right -= left;
            left = 0;
        }
        if (top < 0) {
            bottom -= top;
            top = 0;
        }
        if (bottom > screenHeight) {
            float diff = top - bottom;
            bottom = screenHeight;
            top = bottom + diff;
        }
        if (right > screenWidth) {
            float diff = left - right;
            right = screenWidth;
            left = right + diff;
        }
        canvas.drawRect(left, top, right, bottom, textBgPaint);
        canvas.drawText(text, left + textBgFillingSpace, bottom - textBgFillingSpace, textPaint);
    }

    public void drawLineWithEndPoint(Canvas canvas, int startX, int startY, int endX, int endY) {
        canvas.drawLine(startX, startY, endX, endY, textPaint);
        if (startX == endX) {//画竖线线头线尾
            canvas.drawLine(startX - halfEndPointWidth, startY, endX + halfEndPointWidth, startY, textPaint);
            canvas.drawLine(startX - halfEndPointWidth, endY, endX + halfEndPointWidth, endY, textPaint);
        } else if (startY == endY) {//画横线线头线尾
            canvas.drawLine(startX, startY - halfEndPointWidth, startX, endY + halfEndPointWidth, textPaint);
            canvas.drawLine(endX, startY - halfEndPointWidth, endX, endY + halfEndPointWidth, textPaint);
        }
    }

}
