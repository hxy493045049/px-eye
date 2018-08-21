package com.meituan.android.biz.element.provider.impl;

import android.view.View;

import com.meituan.android.biz.element.provider.IFoodUEAttrProvider;
import com.meituan.android.model.FoodUEBaseAttr;
import com.meituan.android.model.FoodUEViewInfo;
import com.meituan.android.utils.FoodUEAttrUtils;
import com.meituan.android.utils.FoodUEDimensionUtils;
import com.meituan.android.utils.FoodUEResourceUtils;

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
        items.add(new FoodUEBaseAttr<>("通用属性", viewInfo));
        items.add(new FoodUEBaseAttr<>("类名", view.getClass().getName(), viewInfo));
//        items.add(new FoodUEBaseAttr<>("Id", FoodUEResourceUtils.getResId(view), viewInfo));
//        items.add(new FoodUEBaseAttr<>("ResName", FoodUEResourceUtils.getResourceName(view.getId()), viewInfo));
//        items.add(new FoodUEBaseAttr<>("Clickable", Boolean.toString(view.isClickable()).toUpperCase(), viewInfo));
        items.add(new FoodUEBaseAttr<>("ID", FoodUEResourceUtils.getResourceName(view.getId()), viewInfo));
        items.add(new FoodUEBaseAttr<>("宽度(dp)", FoodUEDimensionUtils.px2dip(view.getWidth()), viewInfo));
        items.add(new FoodUEBaseAttr<>("高度(dp)", FoodUEDimensionUtils.px2dip(view.getHeight()), viewInfo));
        items.add(new FoodUEBaseAttr<>("透明度", String.valueOf(view.getAlpha()), viewInfo));
//        Object background = FoodUEViewUtils.getBackground(view);
//        if (background instanceof String) {
//            items.add(new FoodUEBaseAttr<>("Background", (String) background, viewInfo));
//        } else if (background instanceof Bitmap) {
//            items.add(new FoodUEBaseAttr<>("Background", (Bitmap) background, viewInfo));
//        }
    }
}
