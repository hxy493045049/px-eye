package com.meituan.android.uitool.biz.attr.dialog.mode;

import com.meituan.android.uitool.biz.attr.dialog.provider.IPxeItemsProvider;
import com.meituan.android.uitool.biz.attr.dialog.viewholder.PxeBaseViewHolder;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;
import com.meituan.android.uitool.utils.PxeCollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/2 on 下午3:08
 * 允许拓展的item, 点击展开更多子item
 */
public class PxeWrapperItem extends PxeElementItem implements IPxeItemsProvider {
    private List<IPxeItemsProvider> providers = new ArrayList<>();
    private String mTitle;

    public PxeWrapperItem(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setAttrProvider(IPxeItemsProvider provider) {
        providers.add(provider);
    }

    @Override
    public List<? extends PxeBaseItem> getItems(PxeViewInfo viewInfo) {
        if(viewInfo==null){
            return null;
        }
        //创建新的数据源
        List<PxeBaseItem> subItems = new ArrayList<>();
        if (!PxeCollectionUtils.isEmpty(providers)) {
            for (IPxeItemsProvider provider : providers) {
                subItems.addAll(provider.getItems(viewInfo));
            }
        }
        return subItems;
    }

    @Override
    protected int getChildHolderType() {
        return PxeBaseViewHolder.PxeAttrHolderType.TITLE;
    }
}
