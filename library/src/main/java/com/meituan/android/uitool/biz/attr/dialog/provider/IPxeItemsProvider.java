package com.meituan.android.uitool.biz.attr.dialog.provider;

import com.meituan.android.uitool.biz.attr.dialog.mode.PxeBaseItem;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;

import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午4:38
 * 子类实现此接口, 获取自定义的属性源
 */
public interface IPxeItemsProvider {
    List<? extends PxeBaseItem> getItems(PxeViewInfo viewInfo);
}
