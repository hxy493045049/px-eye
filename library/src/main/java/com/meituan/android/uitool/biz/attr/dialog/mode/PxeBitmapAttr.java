package com.meituan.android.uitool.biz.attr.dialog.mode;

import android.graphics.Bitmap;
import android.util.Pair;

import com.meituan.android.uitool.biz.attr.dialog.viewholder.PxeHolderType;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;

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
