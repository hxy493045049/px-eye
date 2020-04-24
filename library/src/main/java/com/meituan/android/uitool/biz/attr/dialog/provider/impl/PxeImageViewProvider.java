package com.meituan.android.uitool.biz.attr.dialog.provider.impl;

import android.widget.ImageView;

import com.meituan.android.uitool.biz.attr.dialog.mode.PxeBaseItem;
import com.meituan.android.uitool.biz.attr.dialog.provider.IPxeItemsProvider;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;

import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午5:00
 * 提供ImageView类型特有的属性
 */
public class PxeImageViewProvider implements IPxeItemsProvider {
    @Override
    public List<? extends PxeBaseItem> getItems(PxeViewInfo viewInfo) {
        if (viewInfo.getView() instanceof ImageView) {
            // TODO: 2018/10/23
        }

        return null;
    }
}
