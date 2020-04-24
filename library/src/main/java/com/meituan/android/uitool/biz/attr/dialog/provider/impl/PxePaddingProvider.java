package com.meituan.android.uitool.biz.attr.dialog.provider.impl;

import android.view.View;

import com.meituan.android.uitool.biz.attr.dialog.mode.PxeBaseItem;
import com.meituan.android.uitool.biz.attr.dialog.mode.PxeTextItem;
import com.meituan.android.uitool.biz.attr.dialog.provider.IPxeItemsProvider;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/5 on 下午6:28
 */
public class PxePaddingProvider implements IPxeItemsProvider {
    @Override
    public List<? extends PxeBaseItem> getItems(PxeViewInfo viewInfo) {
        if (viewInfo == null || viewInfo.getView() == null) {
            return null;
        }
        View view = viewInfo.getView();
        List<PxeBaseItem> items = new ArrayList<>();
        items.add(new PxeTextItem("left", String.valueOf(view.getPaddingLeft())));
        items.add(new PxeTextItem("top", String.valueOf(view.getPaddingTop())));
        items.add(new PxeTextItem("right", String.valueOf(view.getPaddingRight())));
        items.add(new PxeTextItem("bottom", String.valueOf(view.getPaddingBottom())));
        return items;
    }
}
