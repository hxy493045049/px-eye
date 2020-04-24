package com.meituan.android.uitool.biz.attr.dialog.mode;

import com.meituan.android.uitool.biz.attr.dialog.viewholder.PxeBaseViewHolder;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/21 on 下午3:50
 */
public interface IPxeHolderTypeFactory {
    @PxeBaseViewHolder.PxeAttrHolderType
    int getHolderType();

    void setHolderType(@PxeBaseViewHolder.PxeAttrHolderType int holderType);
}
