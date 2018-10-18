package com.meituan.android.uitool.biz.attr.dialog.provider.impl;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.meituan.android.uitool.biz.attr.dialog.provider.IPxeAttrProvider;
import com.meituan.android.uitool.biz.attr.dialog.viewholder.PxeHolderType;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;
import com.meituan.android.uitool.biz.attr.dialog.mode.PxeBaseAttr;
import com.meituan.android.uitool.biz.attr.dialog.mode.PxeNormalAttr;
import com.meituan.android.uitool.utils.PxeAttrUtils;
import com.meituan.android.uitool.utils.PxeDimensionUtils;
import com.meituan.android.uitool.utils.PxeResourceUtils;
import com.meituan.android.uitool.utils.PxeViewUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午5:02
 * 提供所有View通用的属性, 然后调用{@link PxeAttrUtils} 识别view类型并获取特定类型的attr
 */
public class PxeDefaultAttrProvider implements IPxeAttrProvider {
    @Override
    public List<PxeBaseAttr> getAttrs(PxeViewInfo viewInfo) {
        List<PxeBaseAttr> items = new ArrayList<>();
        View view = viewInfo.getView();

        IPxeAttrProvider iAttrs = PxeAttrUtils.createAttrs(view);
        if (iAttrs != null) {
            List<PxeBaseAttr> newAttr = iAttrs.getAttrs(viewInfo);
            if (newAttr != null && newAttr.size() > 0) {
                items.addAll(newAttr);
            }
        }

        generateCommonAttr(viewInfo, view, items);
        return items;
    }

    /**
     * 生成一些通用的属性
     */
    private void generateCommonAttr(PxeViewInfo viewInfo, View view, List<PxeBaseAttr> items) {
        PxeNormalAttr title = new PxeNormalAttr("通用属性", viewInfo);
        title.setHolderType(PxeHolderType.AttrDialogHolder.TITLE);
        items.add(title);
        items.add(new PxeNormalAttr("类名", view.getClass().getName(), viewInfo));
        items.add(new PxeNormalAttr("ID", PxeResourceUtils.getResourceName(view.getId()), viewInfo));
        items.add(new PxeNormalAttr("宽度(dp)", PxeDimensionUtils.px2dip(view.getWidth()), viewInfo));
        items.add(new PxeNormalAttr("高度(dp)", PxeDimensionUtils.px2dip(view.getHeight()), viewInfo));
        items.add(new PxeNormalAttr("透明度", String.valueOf(view.getAlpha()), viewInfo));
        try {
            ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (param != null) {
                items.add(new PxeNormalAttr("左边距(px)", String.valueOf(param.leftMargin), viewInfo, PxeBaseAttr.AttrType.TYPE_MARGIN_LEFT));
                items.add(new PxeNormalAttr("上边距(px)", String.valueOf(param.topMargin), viewInfo, PxeBaseAttr.AttrType.TYPE_MARGIN_TOP));
                items.add(new PxeNormalAttr("右边距(px)", String.valueOf(param.rightMargin), viewInfo, PxeBaseAttr.AttrType.TYPE_MARGIN_RIGHT));
                items.add(new PxeNormalAttr("下边距(px)", String.valueOf(param.bottomMargin), viewInfo, PxeBaseAttr.AttrType.TYPE_MARGIN_BOTTOM));
            }
        } catch (Exception e) {
            Log.e("", "", e);
        }

        Object background = PxeViewUtils.getBackground(view);
        if (background instanceof String) {
            items.add(new PxeNormalAttr("背景色", (String) background, viewInfo));
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

    }
}
