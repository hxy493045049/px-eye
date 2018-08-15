package com.meituan.android.biz.element.impl;

import android.graphics.Bitmap;
import android.view.View;

import com.meituan.android.biz.IFoodUEAttrProvider;
import com.meituan.android.model.FoodUEBaseAttr;
import com.meituan.android.model.FoodUEViewInfo;
import com.meituan.android.utils.FoodUEAttrUtils;
import com.meituan.android.utils.FoodUEDimensionUtils;
import com.meituan.android.utils.FoodUEResourceUtils;
import com.meituan.android.utils.FoodUEViewUtils;
import com.sankuai.common.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午5:02
 */
public class FoodUEDefaultAttrProviderImpl implements IFoodUEAttrProvider {
    @Override
    public List<FoodUEBaseAttr> getAttrs(FoodUEViewInfo viewInfo) {
        List<FoodUEBaseAttr> items = new ArrayList<>();

        View view = viewInfo.getView();

        IFoodUEAttrProvider iAttrs = FoodUEAttrUtils.createAttrs(view);
        if (iAttrs != null) {
            List<FoodUEBaseAttr> newAttr = iAttrs.getAttrs(viewInfo);
            if (!CollectionUtils.isEmpty(newAttr)) {
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
        items.add(new FoodUEBaseAttr<>("COMMON", "", viewInfo));
        items.add(new FoodUEBaseAttr<>("Class", view.getClass().getName(), viewInfo));
        items.add(new FoodUEBaseAttr<>("Id", FoodUEResourceUtils.getResId(view), viewInfo));
        items.add(new FoodUEBaseAttr<>("ResName", FoodUEResourceUtils.getResourceName(view.getId()), viewInfo));
        items.add(new FoodUEBaseAttr<>("Clickable", Boolean.toString(view.isClickable()).toUpperCase(), viewInfo));
        items.add(new FoodUEBaseAttr<>("Focused", Boolean.toString(view.isFocused()).toUpperCase(), viewInfo));
        items.add(new FoodUEBaseAttr<>("Width（dp）", FoodUEDimensionUtils.px2dip(view.getWidth()), viewInfo));
        items.add(new FoodUEBaseAttr<>("Height（dp）", FoodUEDimensionUtils.px2dip(view.getHeight()), viewInfo));
        items.add(new FoodUEBaseAttr<>("Alpha", String.valueOf(view.getAlpha()), viewInfo));
//        Object background = FoodUEViewUtils.getBackground(view);
//        if (background instanceof String) {
//            items.add(new FoodUEBaseAttr<>("Background", (String) background, viewInfo));
//        } else if (background instanceof Bitmap) {
//            items.add(new FoodUEBaseAttr<>("Background", (Bitmap) background, viewInfo));
//        }
    }
}
