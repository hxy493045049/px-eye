package com.meituan.android.uitool.biz.attr.dialog.provider;

import com.meituan.android.uitool.biz.attr.dialog.mode.PxeBaseAttr;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;

import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午4:38
 */
public interface IPxeAttrProvider {
    List<PxeBaseAttr> getAttrs(PxeViewInfo viewInfo);
}
