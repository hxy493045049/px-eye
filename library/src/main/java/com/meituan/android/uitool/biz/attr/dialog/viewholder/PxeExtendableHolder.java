package com.meituan.android.uitool.biz.attr.dialog.viewholder;

import android.view.View;

import com.meituan.android.uitool.biz.attr.dialog.provider.IPxeItemsProvider;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/6 on 上午11:32
 * 可以展开更多的holder
 */
public abstract class PxeExtendableHolder extends PxeBaseViewHolder implements PxeBaseViewHolder.IPxeExpandableHolder, View.OnClickListener {
    protected boolean hasExpanded;
    protected OnExpandedCallBack expandedCallBack;
    protected boolean enableExpand = true;

    public PxeExtendableHolder(View itemView) {
        super(itemView);
    }

    @Override
    public boolean onTriggerExpand() {
        hasExpanded = !hasExpanded;
        return hasExpanded;
    }

    @Override
    public void enableExpand(boolean flag) {
        enableExpand = flag;
    }

    @Override
    public void setOnExpandedCallBack(OnExpandedCallBack callBack) {
        this.expandedCallBack = callBack;
    }

    public interface OnExpandedCallBack {
        void onHolderExpanded(boolean isExpanded, PxeBaseViewHolder holder, IPxeItemsProvider provider);
    }

    @Override
    public void onClick(View v) {
        if (!enableExpand) {
            return;
        }
        //在判断当前holder的position是否正常
        int position = getAdapterPosition();
        if (position == RecyclerView.NO_POSITION) {
            return;
        }
        boolean isExpanded = onTriggerExpand();
        doExpand(isExpanded);
    }

    protected abstract void doExpand(boolean isExpanded);
}
