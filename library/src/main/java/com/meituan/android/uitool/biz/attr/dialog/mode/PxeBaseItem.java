package com.meituan.android.uitool.biz.attr.dialog.mode;

import com.meituan.android.uitool.biz.attr.dialog.viewholder.PxeBaseViewHolder;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/5 on 下午5:35
 */
public abstract class PxeBaseItem implements IPxeHolderTypeFactory {

    @PxeBaseViewHolder.PxeAttrHolderType
    protected int newHolderType = PxeBaseViewHolder.PxeAttrHolderType.NONE;

    protected boolean isValid() {
        return true;
    }

    @Override
    public void setHolderType(@PxeBaseViewHolder.PxeAttrHolderType int holderType) {
        this.newHolderType = holderType;
    }

    @Override
    public int getHolderType() {
        return newHolderType == PxeBaseViewHolder.PxeAttrHolderType.NONE ?
                getChildHolderType() : newHolderType;
    }

    protected abstract int getChildHolderType();
}
