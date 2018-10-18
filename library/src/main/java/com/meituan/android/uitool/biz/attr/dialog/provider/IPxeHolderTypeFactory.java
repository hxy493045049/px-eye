package com.meituan.android.uitool.biz.attr.dialog.provider;

import com.meituan.android.uitool.biz.attr.dialog.viewholder.PxeHolderType;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/21 on 下午3:50
 */
public interface IPxeHolderTypeFactory {
    @PxeHolderType.AttrDialogHolder
    int getHolderType();

    void setHolderType(@PxeHolderType.AttrDialogHolder int holderType);
}
