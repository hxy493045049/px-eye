package com.meituan.android.uitool.helper;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.meituan.android.uitool.helper.mode.PxeViewInfo;
import com.meituan.android.uitool.utils.PxeActivityUtils;
import com.meituan.android.uitool.utils.PxeCollectionUtils;
import com.meituan.android.uitool.utils.PxeViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/9/18 on 下午3:11
 */
public class PxeViewRecorder {

    private List<PxeViewInfo> viewsInfo;//捕捉到的activity中元素的集合

    public static PxeViewRecorder getInstance() {
        return Holder.instance;
    }

    /**
     * 返回指定坐标的view,每次选中新的view之后,对用的anchor和cursor都会变化
     *
     * @param x          触摸时,X轴的坐标
     * @param y          Y坐标
     * @param anchorView 锚点view,用于区分在哪个viewGroup中查找元素
     * @param cursorView 游标View
     * @return 返回3个view元素, 1:新的anchorView,2: cursorView, 3: selectedView
     */
    @Nullable
    public PxeViewInfo[] getViewInfoByPosition(float x, float y,
                                               PxeViewInfo anchorView, PxeViewInfo cursorView) {
        if (viewsInfo == null || viewsInfo.size() < 1) {
            return null;
        }
        PxeViewInfo target[] = new PxeViewInfo[3];
        PxeViewInfo selected = null;
        for (int i = viewsInfo.size() - 1; i >= 0; i--) {
            final PxeViewInfo viewInfo = viewsInfo.get(i);
            if (viewInfo.getRect().contains((int) x, (int) y)) {
                //如果view不可见则略过
                if (PxeViewUtils.isViewInfoNotVisible(viewInfo.getParentViewInfo())) {
                    continue;
                }
                //点击同一个坐标范围的元素时viewInfo一定是该viewGroup的最上层元素
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
                selected = cursorView;
                break;
            }
        }

        target[0] = anchorView;
        target[1] = cursorView;
        target[2] = selected;
        return target;
    }

    @Nullable
    public List<PxeViewInfo> getTargetElements(float x, float y) {
        if (PxeCollectionUtils.isEmpty(viewsInfo)) {
            return null;
        }
        List<PxeViewInfo> validList = new ArrayList<>();
        for (int i = viewsInfo.size() - 1; i >= 0; i--) {
            final PxeViewInfo info = viewsInfo.get(i);
            if (info.getRect().contains((int) x, (int) y)) {
                validList.add(info);
            }
        }
        return validList;
    }

    public void reset() {
        if (viewsInfo != null) {
            viewsInfo.clear();
            viewsInfo = null;
        }
    }

    /**
     * 记录act中的view, 可以放到子线程中运行
     *
     * @param act 目标activity
     */
    public void recordViews(Activity act) {
        if (PxeActivityUtils.isActivityInvalid(act)) {
            return;
        }
        viewsInfo = PxeViewUtils.getTargetActivityViews(act);
    }
    //-------------private -----------------

    private PxeViewRecorder() {
    }

    private static class Holder {
        private static PxeViewRecorder instance = new PxeViewRecorder();
    }

}
