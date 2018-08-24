package com.meituan.android.uitool.plugin.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.plugin.model.MenuModel;
import com.meituan.android.uitool.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/24 on 下午2:46
 */

public class FoodUESubMenuAdapter extends RecyclerView.Adapter<FoodUESubMenuAdapter.SubMenuHolder> implements View.OnClickListener {
    private List<MenuModel> subMenuModels = new ArrayList<>();
    private SubMenuClickListener subMenuClickListener;

    public FoodUESubMenuAdapter(List<MenuModel> data) {
        if (!CollectionUtils.isEmpty(data)) {
            subMenuModels.clear();
            subMenuModels.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void setOnSubMenuClickListener(SubMenuClickListener listener) {
        subMenuClickListener = listener;
    }

    public void notifyDataSetChanged(List<MenuModel> data) {
        subMenuModels.clear();
        if (!CollectionUtils.isEmpty(data)) {
            subMenuModels.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public SubMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View subMenu = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_ue_sub_menu_layout, parent, false);
        return new SubMenuHolder(subMenu);
    }

    @Override
    public void onBindViewHolder(SubMenuHolder holder, int position) {
        MenuModel model = subMenuModels.get(position);
        holder.vImage.setImageResource(model.getImageRes());
        holder.vTitle.setText(model.getTitle());
        holder.item.setTag(model);
        holder.item.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return subMenuModels.size();
    }

    @Override
    public void onClick(View v) {
        MenuModel model = null;
        try {
            model = (MenuModel) v.getTag();
        } catch (Exception e) {
            Log.e("FoodUESubMenuAdapter", "invalid MenuModel", e);
        }
        if (model != null && subMenuClickListener != null) {
            subMenuClickListener.onSubMenuClick(model, v);
        }
    }

    class SubMenuHolder extends RecyclerView.ViewHolder {
        private ImageView vImage;
        private TextView vTitle;
        private View item;

        private SubMenuHolder(View itemView) {
            super(itemView);
            this.item = itemView;
            vImage = itemView.findViewById(R.id.image);
            vTitle = itemView.findViewById(R.id.title);
        }
    }

    public interface SubMenuClickListener {
        void onSubMenuClick(MenuModel model, View subMenu);
    }

}
