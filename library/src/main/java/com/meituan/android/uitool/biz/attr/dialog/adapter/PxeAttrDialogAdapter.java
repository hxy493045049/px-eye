package com.meituan.android.uitool.biz.attr.dialog.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.ViewGroup;

import com.meituan.android.uitool.biz.attr.provider.IPxeAttrProvider;
import com.meituan.android.uitool.biz.attr.dialog.viewholder.PxeViewHolderFactory;
import com.meituan.android.uitool.model.attr.PxeBaseAttr;
import com.meituan.android.uitool.model.PxeBaseViewHolder;
import com.meituan.android.uitool.model.PxeViewInfo;
import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.utils.PxeAttrUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/11 on 下午4:05
 */
public class PxeAttrDialogAdapter extends Adapter<PxeBaseViewHolder> {
    private List<PxeBaseAttr> attrs = new ArrayList<>();
    private PxeViewInfo mViewInfo;

    @Override
    public PxeBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PxeViewHolderFactory.getInstance().createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(PxeBaseViewHolder holder, int position) {
        holder.onBindViewHolder(attrs.get(position));
    }

    @Override
    public int getItemCount() {
        return attrs.size();
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
        PxeBaseAttr attr = attrs.get(position);
        if (attr != null) {
            return attr.getHolderType();
        }
        return super.getItemViewType(position);
    }

    //------------ private ---------------
    private void generateAttrMap() {
        attrs.clear();
        for (String providerName : FoodUETool.getInstance(null).getAttrProviderNames()) {
            IPxeAttrProvider provider = PxeAttrUtils.getCachedProviderByName(providerName);
            if (provider != null) {
                attrs.addAll(provider.getAttrs(mViewInfo));
            }
        }
    }
}
