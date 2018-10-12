package com.meituan.android.uitool.base.behavior;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.meituan.android.uitool.utils.PxeViewOperator;
import com.meituan.android.uitool.base.painter.PxeBasePainter;
import com.meituan.android.uitool.model.PxeViewInfo;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/11 on 下午1:10
 * 基础的功能实现类
 * 作用
 * 1. 和{@link PxeViewOperator}协作管理view信息
 * 2. 和{@link PxeBasePainter}协作绘制边框
 * 3. 子类处理具体的业务逻辑
 */
public abstract class PxeBaseBehavior implements IPxeBehavior {
    protected OnViewInfoSelectedListener mListener;
    protected OnViewChangeListener mViewChangeListener;
    protected PxeBasePainter basePainter;

    //选中的view
    protected PxeViewInfo selectedViewInfo;
    //手指点击时最上层的view
    private PxeViewInfo anchorView;
    //游标,当再次点击同一位置时响应当前view的上一级
    private PxeViewInfo cursorView;

    public PxeBaseBehavior(PxeBasePainter painter) {
        basePainter = painter;
    }

    public void setViewSelectedListener(OnViewInfoSelectedListener listener) {
        mListener = listener;
    }

    public void setOnViewChangeListener(OnViewChangeListener listener) {
        mViewChangeListener = listener;
    }

    @Override
    public void triggerActionUp(MotionEvent event) {
        PxeViewInfo result[] = PxeViewOperator.getInstance().
                getViewInfoByPosition(event.getX(), event.getY(), anchorView, cursorView);
        if (result != null && result.length == 3) {
            anchorView = result[0];
            cursorView = result[1];
            selectedViewInfo = result[2];
            if (mListener != null) {
                mListener.onViewInfoSelected(selectedViewInfo);
            }
        }
    }

    @Override
    public void onDetachedFromWindow() {
        anchorView = null;
        cursorView = null;
        selectedViewInfo = null;
        basePainter = null;
    }

    /**
     * 元素选中时的回调
     */
    public interface OnViewInfoSelectedListener {
        void onViewInfoSelected(PxeViewInfo selectedViewInfo);
    }

    public interface OnViewChangeListener {
        void onViewChange();
    }

    public static class PxeSimpleBehavior extends PxeBaseBehavior {

        public PxeSimpleBehavior(PxeBasePainter painter) {
            super(painter);
        }

        @Override
        public void onDraw(Canvas canvas) {

        }

        @Override
        public void onActionDown(MotionEvent event) {

        }

        @Override
        public void triggerActionMove(MotionEvent event) {

        }

        @Override
        public void onAttach2View() {

        }
    }
}
