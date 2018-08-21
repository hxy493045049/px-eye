package com.meituan.android.biz.element.provider.impl;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.meituan.android.biz.element.provider.IFoodUEAttrProvider;
import com.meituan.android.constant.FoodUEHolderType;
import com.meituan.android.model.FoodUEViewInfo;
import com.meituan.android.model.attr.FoodUEBaseAttr;
import com.meituan.android.model.attr.FoodUENormalAttr;
import com.meituan.android.utils.FoodUEAttrUtils;
import com.meituan.android.utils.FoodUEDimensionUtils;
import com.meituan.android.utils.FoodUEResourceUtils;
import com.meituan.android.utils.FoodUEViewUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午5:02
 * 提供所有View通用的属性, 然后调用{@link FoodUEAttrUtils} 识别view类型并获取特定类型的attr
 */
public class FoodUEDefaultAttrProvider implements IFoodUEAttrProvider {
    @Override
    public List<FoodUEBaseAttr> getAttrs(FoodUEViewInfo viewInfo) {
        List<FoodUEBaseAttr> items = new ArrayList<>();
        View view = viewInfo.getView();

        IFoodUEAttrProvider iAttrs = FoodUEAttrUtils.createAttrs(view);
        if (iAttrs != null) {
            List<FoodUEBaseAttr> newAttr = iAttrs.getAttrs(viewInfo);
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
    private void generateCommonAttr(FoodUEViewInfo viewInfo, View view, List<FoodUEBaseAttr> items) {
        FoodUENormalAttr title = new FoodUENormalAttr("通用属性", viewInfo);
        title.setHolderType(FoodUEHolderType.AttrDialogHolder.TITLE);
        items.add(title);
        items.add(new FoodUENormalAttr("类名", view.getClass().getName(), viewInfo));
        items.add(new FoodUENormalAttr("ID", FoodUEResourceUtils.getResourceName(view.getId()), viewInfo));
        items.add(new FoodUENormalAttr("宽度(dp)", FoodUEDimensionUtils.px2dip(view.getWidth()), viewInfo));
        items.add(new FoodUENormalAttr("高度(dp)", FoodUEDimensionUtils.px2dip(view.getHeight()), viewInfo));
        items.add(new FoodUENormalAttr("透明度", String.valueOf(view.getAlpha()), viewInfo));
        try {
            ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (param != null) {
                items.add(new FoodUENormalAttr("左边距(px)", String.valueOf(param.leftMargin), viewInfo, FoodUEBaseAttr.AttrType.TYPE_MARGIN_LEFT));
                items.add(new FoodUENormalAttr("上边距(px)", String.valueOf(param.topMargin), viewInfo, FoodUEBaseAttr.AttrType.TYPE_MARGIN_TOP));
                items.add(new FoodUENormalAttr("右边距(px)", String.valueOf(param.rightMargin), viewInfo, FoodUEBaseAttr.AttrType.TYPE_MARGIN_RIGHT));
                items.add(new FoodUENormalAttr("下边距(px)", String.valueOf(param.bottomMargin), viewInfo, FoodUEBaseAttr.AttrType.TYPE_MARGIN_BOTTOM));
            }
        } catch (Exception e) {
            Log.e("", "", e);
        }

        Object background = FoodUEViewUtils.getBackground(view);
        if (background instanceof String) {
            items.add(new FoodUENormalAttr("背景色", (String) background, viewInfo));
        } else if (background instanceof Bitmap) {
            // TODO: 2018/8/22
//            items.add(new BitmapItem("Background", (Bitmap) background));
        }

//        Object background = FoodUEViewUtils.getBackground(view);
//        if (background instanceof String) {
//            items.add(new FoodUEBaseAttr<>("Background", (String) background, viewInfo));
//        } else if (background instanceof Bitmap) {
//            items.add(new FoodUEBaseAttr<>("Background", (Bitmap) background, viewInfo));
//        }
//        items.add(new FoodUEBaseAttr<>("ResName", FoodUEResourceUtils.getResourceName(view.getId()), viewInfo));
//        items.add(new FoodUEBaseAttr<>("Clickable", Boolean.toString(view.isClickable()).toUpperCase(), viewInfo));

    }
}
