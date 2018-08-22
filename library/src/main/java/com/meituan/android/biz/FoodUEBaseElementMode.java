package com.meituan.android.biz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.widget.Toast;

import com.meituan.android.model.FoodUEViewInfo;
import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.library.R;
import com.meituan.android.utils.FoodUEDimensionUtils;
import com.meituan.android.utils.FoodUEViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/11 on 下午1:10
 * 基础mode,提供绘制边框线,宽高信息,选中状态,捕捉元素,释放资源功能
 */

public abstract class FoodUEBaseElementMode implements IFoodUEMode {
    protected Context APPLICATION_CONTEXT = FoodUETool.getApplicationContext();
    protected List<FoodUEViewInfo> viewsInfo;//捕捉到的activity中元素的集合
    protected FoodUEViewInfo anchorView;//手指点击时最上层的view
    protected FoodUEViewInfo cursorView;//游标,当再次点击同一位置时响应当前view的上一级
    protected FoodUEViewInfo selectedViewInfo;//选中的view
    protected float actionDownX, actionDownY;//点击时actionDown的坐标

    private final int textLineDistance = FoodUEDimensionUtils.dip2px(5);
    private final int textBgFillingSpace = FoodUEDimensionUtils.dip2px(2);
    private final int halfEndPointWidth = FoodUEDimensionUtils.dip2px(2.5f);
    private OnViewInfoSelectedListener mListener;

    public FoodUEBaseElementMode(OnViewInfoSelectedListener listener) {
        mListener = listener;
    }

    //用于绘制选中元素的画笔,添加蒙层
    protected Paint areaPaint = new Paint() {
        {
            setAntiAlias(true);
            setColor(FoodUETool.getApplicationContext().getResources().getColor(R.color.food_ue_selected_view_bg));
        }
    };

    //用于绘制文本的画笔
    protected Paint textPaint = new Paint() {
        {
            setAntiAlias(true);
            setTextSize(FoodUEDimensionUtils.sp2px(10));
            setColor(Color.RED);
            setStrokeWidth(FoodUEDimensionUtils.dip2px(1));
        }
    };

    //绘制文字所在矩形的画笔
    private Paint textBgPaint = new Paint() {
        {
            setAntiAlias(true);
            setColor(Color.WHITE);
            setStrokeJoin(Join.ROUND);
        }
    };

    @Override
    public void onActionDown(MotionEvent event) {
        actionDownX = event.getX();
        actionDownY = event.getY();
    }

    @Override
    public void triggerActionUp(MotionEvent event) {
        selectedViewInfo = getViewInfoByPosition(event.getX(), event.getY());
        if (mListener != null) {
            mListener.onViewInfoSelected(selectedViewInfo);
        }
    }

    @Override
    public void onAttach2Window() {
        viewsInfo = FoodUEViewUtils.getTargetActivityViews(FoodUETool.getInstance(null).getTargetActivity());
    }

    @Override
    public void onDetachedFromWindow() {
        viewsInfo.clear();
        anchorView = null;
        cursorView = null;
    }

    @Nullable
    protected FoodUEViewInfo getViewInfoByPosition(float x, float y) {
        if (viewsInfo == null || viewsInfo.size() < 1) {
            return null;
        }
        FoodUEViewInfo target = null;
        for (int i = viewsInfo.size() - 1; i >= 0; i--) {
            final FoodUEViewInfo viewInfo = viewsInfo.get(i);
            if (viewInfo.getRect().contains((int) x, (int) y)) {
                //如果view不可见则略过
                if (FoodUEViewUtils.isViewInfoNotVisible(viewInfo.getParentViewInfo())) {
                    continue;
                }
                //点击同一个坐标范围的元素时viewInfo一定是该viewTree的最上层元素
                //所以如果viewInfo和锚点元素不同时,表明更换了元素树,这时重新设置锚点和游标
                if (viewInfo != anchorView) {
                    anchorView = viewInfo;
                    cursorView = anchorView;
                } else {
                    //当再次点击同一坐标范围,返回的锚点和viewInfo相同时, 目标应该为当前选中元素的父元素
                    //当第三次在点击该范围时, 返回的应该是其父元素的父元素
                    cursorView = cursorView.getParentViewInfo();
                }
                //当游标已经是最底层元素时,不在变化
                target = cursorView;
                break;
            }
        }
        if (target == null) {
            anchorView = null;
            cursorView = null;
            Toast.makeText(APPLICATION_CONTEXT, APPLICATION_CONTEXT.getResources().getString(R.string.ue_attr_view_not_found, x, y), Toast.LENGTH_SHORT).show();
        }
        return target;
    }

    @Nullable
    protected List<FoodUEViewInfo> getTargetElements(float x, float y) {
        if (viewsInfo == null || viewsInfo.size() < 1) {
            return null;
        }
        List<FoodUEViewInfo> validList = new ArrayList<>();
        for (int i = viewsInfo.size() - 1; i >= 0; i--) {
            final FoodUEViewInfo info = viewsInfo.get(i);
            if (info.getRect().contains((int) x, (int) y)) {
                validList.add(info);
            }
        }
        return validList;
    }

    //------------绘制相关------------
    protected void drawLineWithText(Canvas canvas, int startX, int startY, int endX, int endY) {
        drawLineWithText(canvas, startX, startY, endX, endY, 0);
    }

    protected void drawLineWithText(Canvas canvas, int startX, int startY, int endX, int endY, int endPointSpace) {
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

        if (startX == endX) {
            drawLineWithEndPoint(canvas, startX, startY + endPointSpace, endX, endY - endPointSpace);
            String text = FoodUEDimensionUtils.px2dip(endY - startY, true);
            drawText(canvas, text, startX + textLineDistance,
                    startY + (endY - startY) / 2 + FoodUEDimensionUtils.getTextHeight(text, textPaint) / 2);

        } else if (startY == endY) {
            drawLineWithEndPoint(canvas, startX + endPointSpace, startY, endX - endPointSpace, endY);
            String text = FoodUEDimensionUtils.px2dip(endX - startX, true);
            drawText(canvas, text, startX + (endX - startX) / 2 - FoodUEDimensionUtils.getTextWidth(text, textPaint) / 2,
                    startY - textLineDistance);
        }
    }

    protected void drawText(Canvas canvas, String text, float x, float y) {
        float left = x - textBgFillingSpace;
        float top = y - FoodUEDimensionUtils.getTextHeight(text, textPaint);
        float right = x + FoodUEDimensionUtils.getTextWidth(text, textPaint) + textBgFillingSpace;
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
        if (bottom > FoodUEDimensionUtils.getScreenHeight()) {
            float diff = top - bottom;
            bottom = FoodUEDimensionUtils.getScreenHeight();
            top = bottom + diff;
        }
        if (right > FoodUEDimensionUtils.getScreenWidth()) {
            float diff = left - right;
            right = FoodUEDimensionUtils.getScreenWidth();
            left = right + diff;
        }
        canvas.drawRect(left, top, right, bottom, textBgPaint);
        canvas.drawText(text, left + textBgFillingSpace, bottom - textBgFillingSpace, textPaint);
    }

    //------------private--------------
    private void drawLineWithEndPoint(Canvas canvas, int startX, int startY, int endX, int endY) {
        canvas.drawLine(startX, startY, endX, endY, textPaint);
        if (startX == endX) {
            canvas.drawLine(startX - halfEndPointWidth, startY, endX + halfEndPointWidth, startY, textPaint);
            canvas.drawLine(startX - halfEndPointWidth, endY, endX + halfEndPointWidth, endY, textPaint);
        } else if (startY == endY) {
            canvas.drawLine(startX, startY - halfEndPointWidth, startX, endY + halfEndPointWidth, textPaint);
            canvas.drawLine(endX, startY - halfEndPointWidth, endX, endY + halfEndPointWidth, textPaint);
        }
    }

    /**
     * 元素选中时的回调
     */
    public interface OnViewInfoSelectedListener {
        void onViewInfoSelected(FoodUEViewInfo selectedViewInfo);
    }
}
