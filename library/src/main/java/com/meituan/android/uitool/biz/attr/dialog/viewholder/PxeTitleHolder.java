package com.meituan.android.uitool.biz.attr.dialog.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meituan.android.uitool.biz.attr.dialog.adapter.PxeAttrDialogAdapter;
import com.meituan.android.uitool.biz.attr.dialog.mode.PxeBaseItem;
import com.meituan.android.uitool.biz.attr.dialog.mode.PxeWrapperItem;
import com.meituan.android.uitool.biz.attr.dialog.provider.IPxeItemsProvider;
import com.meituan.android.uitool.library.R;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午5:01
 */
public class PxeTitleHolder extends PxeExtendableHolder implements PxeBaseViewHolder.IPxeClickableHolder {
    private TextView title;
    private PxeBaseItem data;
    private PxeAttrDialogAdapter.OnItemClickListener clickListener;

    public PxeTitleHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.pxe_attr_dialog_title, parent, false));
        title = itemView.findViewById(R.id.title);
        title.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    @Override
    public <T extends PxeBaseItem> void onBindViewHolder(T data) {
        this.data = data;
        if (data instanceof PxeWrapperItem) {
            if (title != null) {
                PxeWrapperItem attr = (PxeWrapperItem) data;
                title.setText(attr.getTitle());
            }
        }
    }

    @Override
    public void setOnClickListener(PxeAttrDialogAdapter.OnItemClickListener listener) {
        this.clickListener = listener;
    }

    @Override
    protected void doExpand(boolean isExpanded) {
        if (expandedCallBack != null && data instanceof IPxeItemsProvider) {
            expandedCallBack.onHolderExpanded(isExpanded, this, (IPxeItemsProvider) data);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //执行外部的click代理事件
        if (clickListener != null) {
            int position = getAdapterPosition();
            if (position == RecyclerView.NO_POSITION) {
                return;
            }
            clickListener.onItemClicked(this,  data);
        }
    }
}
