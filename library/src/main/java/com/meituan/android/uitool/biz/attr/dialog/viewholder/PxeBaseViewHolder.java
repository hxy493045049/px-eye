package com.meituan.android.uitool.biz.attr.dialog.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meituan.android.uitool.biz.attr.dialog.mode.PxeBaseAttr;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午4:40
 */
public abstract class PxeBaseViewHolder<K, V> extends RecyclerView.ViewHolder {

    public PxeBaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBindViewHolder(PxeBaseAttr<K, V> data);

}