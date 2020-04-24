package com.meituan.android.uitool.biz.attr.dialog.provider.impl;

import android.view.View;
import android.view.ViewGroup;

import com.meituan.android.uitool.biz.attr.dialog.mode.PxeBaseItem;
import com.meituan.android.uitool.biz.attr.dialog.mode.PxeTextItem;
import com.meituan.android.uitool.biz.attr.dialog.provider.IPxeItemsProvider;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/5 on 下午6:22
 */
public class PxeMarginProvider implements IPxeItemsProvider {
    @Override
    public List<? extends PxeBaseItem> getItems(PxeViewInfo viewInfo) {
        if (viewInfo == null || viewInfo.getView() == null) {
            return null;
        }
        View view = viewInfo.getView();
        List<PxeBaseItem> items = new ArrayList<>();
        ViewGroup.LayoutParams base = view.getLayoutParams();
        if (base instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams) base;
            items.add(new PxeTextItem("left", String.valueOf(param.leftMargin)));
            items.add(new PxeTextItem("top", String.valueOf(param.topMargin)));
            items.add(new PxeTextItem("right", String.valueOf(param.rightMargin)));
            items.add(new PxeTextItem("bottom", String.valueOf(param.bottomMargin)));
        }

        return items;
    }
}
