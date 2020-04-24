package com.meituan.android.uitool.biz.attr.dialog.provider.impl;

import android.graphics.Bitmap;
import android.view.View;

import com.meituan.android.uitool.biz.attr.dialog.mode.PxeBaseItem;
import com.meituan.android.uitool.biz.attr.dialog.mode.PxeTextItem;
import com.meituan.android.uitool.biz.attr.dialog.provider.IPxeItemsProvider;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;
import com.meituan.android.uitool.utils.PxeDimensionUtils;
import com.meituan.android.uitool.utils.PxeResourceUtils;
import com.meituan.android.uitool.utils.PxeViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/5 on 下午6:13
 */
public class PxeCommonItemsProvider implements IPxeItemsProvider {

    @Override
    public List<? extends PxeBaseItem> getItems(PxeViewInfo viewInfo) {
        if (viewInfo == null || viewInfo.getView() == null) {
            return null;
        }
        View view = viewInfo.getView();
        List<PxeBaseItem> items = new ArrayList<>();
        items.add(new PxeTextItem("类名", view.getClass().getName()));
        items.add(new PxeTextItem("ID", PxeResourceUtils.getResourceName(view.getId())));
        items.add(new PxeTextItem("宽度(dp)", PxeDimensionUtils.px2dip(view.getWidth())));
        items.add(new PxeTextItem("高度(dp)", PxeDimensionUtils.px2dip(view.getHeight())));
        items.add(new PxeTextItem("透明度", String.valueOf(view.getAlpha())));

        Object background = PxeViewUtils.getBackground(view);
        if (background instanceof String) {
            items.add(new PxeTextItem("背景色", (String) background));
        } else if (background instanceof Bitmap) {
            // TODO: 2018/8/22
//            items.add(new BitmapItem("Background", (Bitmap) background));
        }
//        Object background = PxeViewUtils.getBackground(view);
//        if (background instanceof String) {
//            items.add(new PxeBaseAttr<>("Background", (String) background, viewInfo));
//        } else if (background instanceof Bitmap) {
//            items.add(new PxeBaseAttr<>("Background", (Bitmap) background, viewInfo));
//        }
//        items.add(new PxeBaseAttr<>("ResName", PxeResourceUtils.getResourceName(view.getId()), viewInfo));
//        items.add(new PxeBaseAttr<>("Clickable", Boolean.toString(view.isClickable()).toUpperCase(), viewInfo));
        return items;
    }
}
