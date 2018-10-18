package com.meituan.android.uitool.helper.mode;

import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;

import com.meituan.android.uitool.utils.PxeActivityUtils;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/9 on 下午5:18
 * 捕捉到的view模型, 提供rect数据和View信息
 */
public class PxeViewInfo {
    private View view;
    private Rect originRect = new Rect();
    private Rect rect = new Rect();
    private int[] location = new int[2];
    private PxeViewInfo parentElement;

    public PxeViewInfo(View view) {
        this.view = view;
        reset();
        originRect.set(rect.left, rect.top, rect.right, rect.bottom);
    }

    public View getView() {
        return view;
    }

    public Rect getRect() {
        return rect;
    }

    public Rect getOriginRect() {
        return originRect;
    }

    public void reset() {
        view.getLocationOnScreen(location);
        int width = view.getWidth();
        int height = view.getHeight();

        int left = location[0];
        int right = left + width;
        int top = location[1];
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {//19及以下,去掉statusbar高度
            top -= PxeActivityUtils.getStatusBarHeight();
        }
        int bottom = top + height;

        rect.set(left, top, right, bottom);
    }

    /**
     * 判断是否还有父类原始
     *
     * @return true : 有;  false :无
     */
    public boolean hasParentViewInfo() {
        return getParentViewInfo() != null;
    }

    @Nullable
    public PxeViewInfo getParentViewInfo() {
        if (parentElement == null) {
            Object parentView = view.getParent();
            if (parentView instanceof View) {
                parentElement = new PxeViewInfo((View) parentView);
            }
        }
        return parentElement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PxeViewInfo info = (PxeViewInfo) o;
        return view != null ? view.equals(info.view) : info.view == null;
    }

    @Override
    public int hashCode() {
        return view != null ? view.hashCode() : 0;
    }
}
