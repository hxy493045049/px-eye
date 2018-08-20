package com.meituan.android.biz.element.dialog.viewholder;

import android.graphics.Bitmap;
import android.view.View;

import com.meituan.android.model.FoodUEBaseAttr;
import com.meituan.android.model.FoodUEBaseViewHolder;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午5:07
 */
public class FoodUEBitmapHolder extends FoodUEBaseViewHolder<String, Bitmap> {
    public FoodUEBitmapHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBindViewHolder(FoodUEBaseAttr<String, Bitmap> data) {
        // TODO: 2018/8/20 如果数据源时图片类型,在这里渲染
    }
}
