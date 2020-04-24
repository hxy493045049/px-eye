package com.meituan.android.uitool.plugin.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.plugin.model.PxeMenuModel;
import com.meituan.android.uitool.utils.PxeCollectionUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/24 on 下午2:46
 * todo  单选功能, viewpage切换带动menu切换
 */
public class PxeSubMenuAdapter extends RecyclerView.Adapter<PxeSubMenuAdapter.SubMenuHolder> implements View.OnClickListener {
    private List<PxeMenuModel> subPxeMenuModels = new ArrayList<>();
    private SubMenuClickListener subMenuClickListener;
    private SubMenuHolder selectedHolder;

    public PxeSubMenuAdapter(List<PxeMenuModel> data) {
        if (!PxeCollectionUtils.isEmpty(data)) {
            subPxeMenuModels.clear();
            subPxeMenuModels.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void setOnSubMenuClickListener(SubMenuClickListener listener) {
        subMenuClickListener = listener;
    }

    public void notifyDataSetChanged(List<PxeMenuModel> data) {
        subPxeMenuModels.clear();
        if (!PxeCollectionUtils.isEmpty(data)) {
            subPxeMenuModels.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public SubMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View subMenu = LayoutInflater.from(parent.getContext()).inflate(R.layout.pxe_sub_menu_container, parent, false);
        return new SubMenuHolder(subMenu);
    }

    @Override
    public void onBindViewHolder(SubMenuHolder holder, int position) {
        PxeMenuModel model = subPxeMenuModels.get(position);
        holder.vImage.setImageResource(model.getImageRes());
        holder.vTitle.setText(model.getTitle());
        holder.item.setTag(holder);
        holder.item.setOnClickListener(this);
        holder.model = model;
    }

    @Override
    public int getItemCount() {
        return subPxeMenuModels.size();
    }

    @Override
    public void onClick(View v) {
        SubMenuHolder holder = null;
        try {
            holder = (SubMenuHolder) v.getTag();
            holder.vImage.setSelected(true);
            holder.vTitle.setSelected(true);
        } catch (Exception e) {
            Log.e("PxeSubMenuAdapter", "invalid PxeMenuModel", e);
        }
        if (holder != null && subMenuClickListener != null) {
            subMenuClickListener.onSubMenuClick(holder.model, v);
        }
        if (selectedHolder != null && selectedHolder != holder) {
            selectedHolder.vImage.setSelected(false);
            selectedHolder.vTitle.setSelected(false);
        }
        selectedHolder = holder;
    }

    class SubMenuHolder extends RecyclerView.ViewHolder {
        private ImageView vImage;
        private TextView vTitle;
        private ViewGroup item;
        private PxeMenuModel model;

        private SubMenuHolder(View itemView) {
            super(itemView);
            this.item = (ViewGroup) itemView;
            vImage = itemView.findViewById(R.id.image);
            vTitle = itemView.findViewById(R.id.title);
        }
    }

    public interface SubMenuClickListener {
        void onSubMenuClick(PxeMenuModel model, View subMenu);
    }

}
