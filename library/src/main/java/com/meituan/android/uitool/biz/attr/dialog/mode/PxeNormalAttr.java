package com.meituan.android.uitool.biz.attr.dialog.mode;

import com.meituan.android.uitool.biz.attr.dialog.viewholder.PxeHolderType;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/21 on 下午3:56
 */
public class PxeNormalAttr extends PxeBaseAttr<String, String> {
    public PxeNormalAttr(String s, String s2, PxeViewInfo viewInfo, int type) {
        super(s, s2, viewInfo, type);
    }

    public PxeNormalAttr(String s, PxeViewInfo viewInfo) {
        super(s, viewInfo);
    }

    public PxeNormalAttr(String s, String s2, PxeViewInfo viewInfo) {
        super(s, s2, viewInfo);
    }

    @Override
    void initHolderType() {
        setHolderType(PxeHolderType.AttrDialogHolder.NORMAL);
    }
}
