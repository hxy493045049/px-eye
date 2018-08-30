package com.meituan.android.uitool.biz.element.provider.impl;

import android.graphics.Bitmap;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.meituan.android.uitool.biz.element.provider.IFoodUEAttrProvider;
import com.meituan.android.uitool.constant.FoodUEHolderType;
import com.meituan.android.uitool.model.FoodUEViewInfo;
import com.meituan.android.uitool.model.attr.FoodUEBaseAttr;
import com.meituan.android.uitool.model.attr.FoodUEBitmapAttr;
import com.meituan.android.uitool.model.attr.FoodUENormalAttr;
import com.meituan.android.uitool.utils.FoodUEResourceUtils;
import com.meituan.android.uitool.utils.FoodUEViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午4:59
 */
public class FoodUETextViewProvider implements IFoodUEAttrProvider {
    @Override
    public List<FoodUEBaseAttr> getAttrs(FoodUEViewInfo viewInfo) {
        List<FoodUEBaseAttr> items = new ArrayList<>();
        View view = viewInfo.getView();
        TextView textView;
        if (view instanceof TextView) {
            textView = (TextView) view;
            FoodUENormalAttr title = new FoodUENormalAttr(textView.getClass().getSimpleName(), viewInfo);
            title.setHolderType(FoodUEHolderType.AttrDialogHolder.TITLE);
            items.add(title);
            items.add(new FoodUENormalAttr("字体大小(px)", textView.getTextSize() + "", viewInfo));
            items.add(new FoodUENormalAttr("字体颜色", FoodUEResourceUtils.intToHexColor(textView.getCurrentTextColor()), viewInfo));

//            List<Pair<String, Bitmap>> pairs = FoodUEViewUtils.getTextViewBitmap(textView);
//            for (Pair<String, Bitmap> pair : pairs) {
//                items.add(new FoodUEBitmapAttr(pair, viewInfo));
//            }
        }
        return items;
    }
}
