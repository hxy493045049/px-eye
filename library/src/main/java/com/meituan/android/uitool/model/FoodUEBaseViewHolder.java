package com.meituan.android.uitool.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meituan.android.uitool.model.attr.FoodUEBaseAttr;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午4:40
 */
public abstract class FoodUEBaseViewHolder<K, V> extends RecyclerView.ViewHolder {

    public FoodUEBaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBindViewHolder(FoodUEBaseAttr<K, V> data);

}