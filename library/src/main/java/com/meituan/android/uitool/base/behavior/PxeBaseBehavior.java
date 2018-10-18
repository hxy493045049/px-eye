package com.meituan.android.uitool.base.behavior;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.meituan.android.uitool.base.painter.PxeBasePainter;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;
import com.meituan.android.uitool.helper.PxeViewRecorder;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/11 on 下午1:10
 * 基础的功能实现类, behavior沟通{@link com.meituan.android.uitool.plugin.PxeFunctionView}和实现功能的fragment
 * 作用
 * 1. 和{@link PxeViewRecorder}协作管理view信息
 * 2. 和{@link PxeBasePainter}协作绘制边框
 * 3. 子类处理具体的业务逻辑
 */
public abstract class PxeBaseBehavior implements IPxeBehavior {
    protected OnViewInfoSelectedListener mSelectedListener;
    protected OnSelectedViewChangeListener mViewChangeListener;
    protected PxeBasePainter basePainter;

    //选中的view
    protected PxeViewInfo selectedViewInfo;
    //手指点击时最上层的view
    private PxeViewInfo anchorView;
    //游标,当再次点击同一位置时响应当前view的上一级
    private PxeViewInfo cursorView;

    PxeBaseBehavior(PxeBasePainter painter) {
        basePainter = painter;
    }

    public void setViewSelectedListener(OnViewInfoSelectedListener listener) {
        mSelectedListener = listener;
    }

    public void setOnViewChangeListener(OnSelectedViewChangeListener listener) {
        mViewChangeListener = listener;
    }

    @Override
    public boolean onActionUp(MotionEvent event) {
        PxeViewInfo result[] = PxeViewRecorder.getInstance().
                getViewInfoByPosition(event.getX(), event.getY(), anchorView, cursorView);
        if (result != null && result.length == 3) {
            anchorView = result[0];
            cursorView = result[1];
            selectedViewInfo = result[2];
            if (mSelectedListener != null) {
                mSelectedListener.onViewInfoSelected(selectedViewInfo);
            }
        }
        return false;
    }

    @Override
    public void onDetachedFromView() {
        anchorView = null;
        cursorView = null;
        selectedViewInfo = null;
        basePainter = null;
        mSelectedListener=null;
        mViewChangeListener=null;
    }


    /**
     * 元素被选中的回调
     */
    public interface OnViewInfoSelectedListener {
        void onViewInfoSelected(PxeViewInfo selectedViewInfo);
    }

    /**
     * 选中元素变化的回调,会触发{@link com.meituan.android.uitool.plugin.PxeFunctionView}重绘
     */
    public interface OnSelectedViewChangeListener {
        void onSelectedViewChange();
    }

    public static class PxeSimpleBehavior extends PxeBaseBehavior {

        public PxeSimpleBehavior(PxeBasePainter painter) {
            super(painter);
        }

        @Override
        public void onDraw(Canvas canvas) {

        }

        @Override
        public boolean onActionDown(MotionEvent event) {
            return false;
        }

        @Override
        public boolean onActionMove(MotionEvent event) {
            return false;
        }

        @Override
        public void onAttach2View() {

        }
    }
}
