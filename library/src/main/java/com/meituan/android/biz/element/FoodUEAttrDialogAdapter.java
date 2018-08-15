package com.meituan.android.biz.element;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meituan.android.biz.IFoodUEAttrProvider;
import com.meituan.android.model.FoodUEBaseAttr;
import com.meituan.android.model.FoodUEViewInfo;
import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.library.R;
import com.meituan.android.utils.FoodUEAttrUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/11 on 下午4:05
 */
public class FoodUEAttrDialogAdapter extends Adapter<FoodUEAttrDialogAdapter.DefaultHolder> {
    private List<FoodUEBaseAttr> attrs = new ArrayList<>();
    private FoodUEViewInfo mViewInfo;

    @Override
    public DefaultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DefaultHolder(View.inflate(parent.getContext(), R.layout.food_ue_attr_dialog_item, null));
    }

    @Override
    public void onBindViewHolder(DefaultHolder holder, int position) {
        holder.attrName.setText((String) attrs.get(position).getKey());
        holder.attrValue.setText((String) attrs.get(position).getValue());
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

    private void generateAttrMap() {
        attrs.clear();
        for (String providerName : FoodUETool.getInstance().getAttrProviders()) {
            IFoodUEAttrProvider provider = FoodUEAttrUtils.getCachedProviderByName(providerName);
            if (provider != null) {
                attrs.addAll(provider.getAttrs(mViewInfo));
            }
        }
    }

    class DefaultHolder extends RecyclerView.ViewHolder {
        private TextView attrName, attrValue;

        private DefaultHolder(View itemView) {
            super(itemView);
            attrName = itemView.findViewById(R.id.attrName);
            attrValue = itemView.findViewById(R.id.attrValue);
        }
    }
}
