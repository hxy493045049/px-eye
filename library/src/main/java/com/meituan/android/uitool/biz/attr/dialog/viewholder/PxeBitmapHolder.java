package com.meituan.android.uitool.biz.attr.dialog.viewholder;

import android.graphics.Bitmap;
import android.view.ViewGroup;

import com.meituan.android.uitool.biz.attr.dialog.mode.PxeBaseAttr;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午5:07
 */
public class PxeBitmapHolder extends PxeBaseViewHolder<String, Bitmap> {
    public PxeBitmapHolder(ViewGroup parent) {
        super(null);
    }

    @Override
    public void onBindViewHolder(PxeBaseAttr<String, Bitmap> data) {
        // TODO: 2018/8/20 如果数据源时图片类型,在这里渲染
    }
}
