package com.meituan.android.uitool.biz.attr.dialog.mode;

import com.meituan.android.uitool.helper.mode.PxeViewInfo;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/5 on 下午5:37
 * 可以操作view的item
 */
public abstract class PxeElementItem extends PxeBaseItem implements IPxeElementHolder {
    protected PxeViewInfo info;

    @Override
    public PxeViewInfo getViewInfo() {
        return info;
    }

    @Override
    public void setViewInfo(PxeViewInfo pxeViewInfo) {
        info = pxeViewInfo;
    }
}
