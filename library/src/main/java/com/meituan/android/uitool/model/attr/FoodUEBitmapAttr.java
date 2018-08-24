package com.meituan.android.uitool.model.attr;

import android.graphics.Bitmap;
import android.util.Pair;

import com.meituan.android.uitool.constant.FoodUEHolderType;
import com.meituan.android.uitool.model.FoodUEViewInfo;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午6:19
 */
public class FoodUEBitmapAttr extends FoodUEBaseAttr<String, Bitmap> {
    public FoodUEBitmapAttr(Pair<String, Bitmap> pair, FoodUEViewInfo viewInfo) {
        super(pair, viewInfo);
    }

    @Override
    void initHolderType() {
        setHolderType(FoodUEHolderType.AttrDialogHolder.BITMAP);
    }
}
