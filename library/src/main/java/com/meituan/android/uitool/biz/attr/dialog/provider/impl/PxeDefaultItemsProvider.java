package com.meituan.android.uitool.biz.attr.dialog.provider.impl;

import android.view.View;

import com.meituan.android.uitool.biz.attr.dialog.mode.PxeBaseItem;
import com.meituan.android.uitool.biz.attr.dialog.mode.PxeWrapperItem;
import com.meituan.android.uitool.biz.attr.dialog.provider.IPxeItemsProvider;
import com.meituan.android.uitool.biz.attr.dialog.provider.PxeAttrProviderFactory;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午5:02
 * 提供所有View通用的属性, 然后调用{@link PxeAttrProviderFactory} 识别view类型并获取特定类型的attr
 */
public class PxeDefaultItemsProvider implements IPxeItemsProvider {
    @Override
    public List<? extends PxeBaseItem> getItems(PxeViewInfo viewInfo) {
        List<PxeBaseItem> items = new ArrayList<>();
        if (viewInfo == null || viewInfo.getView() == null) {
            return items;
        }
        //构造所有view公用的属性
        generateCommonAttr(items);

        //构造独有属性
        View view = viewInfo.getView();
        PxeWrapperItem concreteItem = new PxeWrapperItem(view.getClass().getSimpleName() + "属性");
        concreteItem.setAttrProvider(PxeAttrProviderFactory.getAttrProviderByView(viewInfo));
        items.add(concreteItem);

        return items;
    }

    /**
     * 生成一些通用的属性
     */
    private void generateCommonAttr(List<PxeBaseItem> items) {

        PxeWrapperItem classWrapper = new PxeWrapperItem("类属性");
        classWrapper.setAttrProvider(new PxeCommonItemsProvider());
        items.add(classWrapper);

        PxeWrapperItem marginWrapper = new PxeWrapperItem("外间距(px)");
        marginWrapper.setAttrProvider(new PxeMarginProvider());
        items.add(marginWrapper);

        PxeWrapperItem paddingWrapper = new PxeWrapperItem("内间距(px)");
        paddingWrapper.setAttrProvider(new PxeMarginProvider());
        items.add(paddingWrapper);
    }
}
