package com.meituan.android.biz.element.dialog;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.ViewGroup;

import com.meituan.android.biz.element.provider.IFoodUEAttrProvider;
import com.meituan.android.constant.FoodUEHolderType;
import com.meituan.android.factory.FoodUEViewHolderFactory;
import com.meituan.android.model.FoodUEBaseAttr;
import com.meituan.android.model.FoodUEBaseViewHolder;
import com.meituan.android.model.FoodUEViewInfo;
import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.utils.FoodUEAttrUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/11 on 下午4:05
 */
public class FoodUEAttrDialogAdapter extends Adapter<FoodUEBaseViewHolder> {
    private List<FoodUEBaseAttr> attrs = new ArrayList<>();
    private FoodUEViewInfo mViewInfo;

    @Override
    public FoodUEBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return FoodUEViewHolderFactory.getInstance().createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(FoodUEBaseViewHolder holder, int position) {
        holder.onBindViewHolder(attrs.get(position));
    }

    @Override
    public int getItemCount() {
        return attrs.size();
    }

    public void notifyDataSetChanged(FoodUEViewInfo viewInfo) {
        if (viewInfo != null) {
            mViewInfo = viewInfo;
            generateAttrMap();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        FoodUEBaseAttr attr = attrs.get(position);
        if (attr != null) {
            return attr.getHolderType();
        }
        return super.getItemViewType(position);
    }

    public interface ViewHolderTypeFactory {

        @FoodUEHolderType.AttrDialogHolder
        int getHolderType();

        void setHolderType(@FoodUEHolderType.AttrDialogHolder int holderType);
    }

    //------------ private ---------------
    private void generateAttrMap() {
        attrs.clear();
        for (String providerName : FoodUETool.getInstance().getAttrProviderNames()) {
            IFoodUEAttrProvider provider = FoodUEAttrUtils.getCachedProviderByName(providerName);
            if (provider != null) {
                attrs.addAll(provider.getAttrs(mViewInfo));
            }
        }
    }
}
