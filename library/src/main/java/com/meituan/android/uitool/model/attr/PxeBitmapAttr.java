package com.meituan.android.uitool.model.attr;

import android.graphics.Bitmap;
import android.util.Pair;

import com.meituan.android.uitool.constant.PxeHolderType;
import com.meituan.android.uitool.model.PxeViewInfo;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午6:19
 */
public class PxeBitmapAttr extends PxeBaseAttr<String, Bitmap> {
    public PxeBitmapAttr(Pair<String, Bitmap> pair, PxeViewInfo viewInfo) {
        super(pair, viewInfo);
    }

    @Override
    void initHolderType() {
        setHolderType(PxeHolderType.AttrDialogHolder.BITMAP);
    }
}
