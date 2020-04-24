package com.meituan.android.uitool.biz.attr.dialog.mode;

import com.meituan.android.uitool.biz.attr.dialog.viewholder.PxeBaseViewHolder;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/5 on 下午5:44
 */
public class PxeTextItem extends PxeBaseItem {
    private String mKey, mValue;

    public PxeTextItem(String key, String value) {
        mKey = key;
        mValue = value;
    }

    public String getKey() {
        return mKey;
    }

    public String getValue() {
        return mValue;
    }

    @Override
    protected int getChildHolderType() {
        return PxeBaseViewHolder.PxeAttrHolderType.TEXT;
    }
}
