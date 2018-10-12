package com.meituan.android.uitool.biz.attr.provider;

import com.meituan.android.uitool.model.attr.PxeBaseAttr;
import com.meituan.android.uitool.model.PxeViewInfo;

import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午4:38
 */
public interface IPxeAttrProvider {
    List<PxeBaseAttr> getAttrs(PxeViewInfo viewInfo);
}
