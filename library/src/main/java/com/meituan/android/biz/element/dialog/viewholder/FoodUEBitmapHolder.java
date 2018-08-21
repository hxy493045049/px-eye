package com.meituan.android.biz.element.dialog.viewholder;

import android.graphics.Bitmap;
import android.view.ViewGroup;

import com.meituan.android.model.FoodUEBaseViewHolder;
import com.meituan.android.model.attr.FoodUEBaseAttr;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午5:07
 */
public class FoodUEBitmapHolder extends FoodUEBaseViewHolder<String, Bitmap> {
    public FoodUEBitmapHolder(ViewGroup parent) {
        super(null);
    }

    @Override
    public void onBindViewHolder(FoodUEBaseAttr<String, Bitmap> data) {
        // TODO: 2018/8/20 如果数据源时图片类型,在这里渲染
    }
}
