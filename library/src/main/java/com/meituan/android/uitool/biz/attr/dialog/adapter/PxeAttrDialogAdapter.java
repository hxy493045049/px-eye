package com.meituan.android.uitool.biz.attr.dialog.adapter;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.ViewGroup;

import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.biz.attr.dialog.mode.PxeBaseItem;
import com.meituan.android.uitool.biz.attr.dialog.provider.IPxeItemsProvider;
import com.meituan.android.uitool.biz.attr.dialog.provider.PxeAttrProviderFactory;
import com.meituan.android.uitool.biz.attr.dialog.viewholder.PxeBaseViewHolder;
import com.meituan.android.uitool.biz.attr.dialog.viewholder.PxeExtendableHolder;
import com.meituan.android.uitool.biz.attr.dialog.viewholder.PxeViewHolderFactory;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;
import com.meituan.android.uitool.utils.PxeCollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/11 on 下午4:05
 */
public class PxeAttrDialogAdapter extends Adapter<PxeBaseViewHolder> implements PxeExtendableHolder.OnExpandedCallBack {
    private List<PxeBaseItem> items = new ArrayList<>();
    //通过wrapperItem生成的subItem数据集
    private ArrayMap<IPxeItemsProvider, List<PxeBaseItem>> subItems = new ArrayMap<>();
    private PxeViewInfo mViewInfo;
    private OnItemClickListener mClickListener;

    @Override
    public PxeBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PxeViewHolderFactory.getInstance().createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(PxeBaseViewHolder holder, int position) {
        holder.onBindViewHolder(items.get(position));
        if (holder instanceof PxeBaseViewHolder.IPxeClickableHolder && mClickListener != null) {
            ((PxeBaseViewHolder.IPxeClickableHolder) holder).setOnClickListener(mClickListener);
        }
        if (holder instanceof PxeBaseViewHolder.IPxeExpandableHolder) {
            ((PxeBaseViewHolder.IPxeExpandableHolder) holder).setOnExpandedCallBack(this);
        }
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void notifyDataSetChanged(PxeViewInfo viewInfo) {
        if (viewInfo != null) {
            mViewInfo = viewInfo;
            generateAttrMap();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        PxeBaseItem attr = items.get(position);
        if (attr != null) {
            return attr.getHolderType();
        }
        return super.getItemViewType(position);
    }

    //------------ private ---------------
    private void generateAttrMap() {
        items.clear();
        for (String providerName : FoodUETool.getInstance().getAttrProviderNames()) {
            IPxeItemsProvider provider = PxeAttrProviderFactory.getCachedProviderByName(providerName);
            if (provider != null) {
                List<? extends PxeBaseItem> attr = provider.getItems(mViewInfo);
                if (!PxeCollectionUtils.isEmpty(attr)) {
                    items.addAll(attr);
                }
            }
        }
    }

    private List<PxeBaseItem> getSubItemInfo(IPxeItemsProvider item) {
        if (subItems.containsKey(item)) {
            return subItems.get(item);
        } else {
            List<PxeBaseItem> subItem = new ArrayList<>();
            subItems.put(item, subItem);
            return subItem;
        }
    }

    @Override
    public void onHolderExpanded(boolean isExpanded, PxeBaseViewHolder holder, IPxeItemsProvider provider) {
        int position = holder.getAdapterPosition();
        if (position == RecyclerView.NO_POSITION || mViewInfo == null) {
            return;
        }

        if (holder instanceof PxeBaseViewHolder.IPxeExpandableHolder) {
            List<PxeBaseItem> temp = new ArrayList<>(provider.getItems(mViewInfo));
            if (PxeCollectionUtils.isEmpty(temp)) {
                return;
            }

            //在当前坐标的下一个位置插入新值
            int subPosition = holder.getAdapterPosition() + 1;
            //获取缓存
            List<PxeBaseItem> subItem = getSubItemInfo(provider);

            if (isExpanded) {//展开时将拓展的item记录下来, 用于后续移除
                subItem.clear();
                subItem.addAll(temp);
                items.addAll(subPosition, subItem);
                notifyItemRangeChanged(subPosition, subItem.size());
            } else {
                items.removeAll(subItem);
                notifyItemRangeRemoved(subPosition, subItem.size());
            }
        }
    }


    public interface OnItemClickListener {
        void onItemClicked(PxeBaseViewHolder holder, PxeBaseItem item);
    }
}
