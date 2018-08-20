package com.meituan.android.model;

import android.graphics.Bitmap;
import android.util.Pair;

import com.meituan.android.constant.FoodUEHolderType;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午6:19
 */
public class FoodUEBitmapAttr extends FoodUEBaseAttr<String, Bitmap> {
    public FoodUEBitmapAttr(Pair<String, Bitmap> pair, FoodUEViewInfo viewInfo) {
        super(pair, viewInfo);
        setHolderType(FoodUEHolderType.AttrDialogHolder.BITMAP);
    }
}
