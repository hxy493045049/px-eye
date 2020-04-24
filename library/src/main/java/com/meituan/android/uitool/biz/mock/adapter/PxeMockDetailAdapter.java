package com.meituan.android.uitool.biz.mock.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meituan.android.uitool.biz.mock.PxeMockBean;
import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.utils.PxeCollectionUtils;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/13 on 下午6:50
 */
public class PxeMockDetailAdapter extends RecyclerView.Adapter<PxeMockDetailAdapter.Holder> implements View.OnClickListener {
    private Holder lastSelected;
    private List<PxeMockBean.CaseBean> caseX;
    private OnCaseSelectedListener listener;

    public void setOnItemSelectedListener(OnCaseSelectedListener l) {
        listener = l;
    }

    public void notifyDataChange(List<PxeMockBean.CaseBean> data) {
        if (data == null || PxeCollectionUtils.isEmpty(data)) {
            return;
        }
        caseX = data;
        setSelectedHolder(null);
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pxe_simple_item, parent, false);
        item.setOnClickListener(this);
        Holder holder = new Holder(item);
        item.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if (!PxeCollectionUtils.isEmpty(caseX) && caseX.size() > position) {
            holder.tvVersion.setText(caseX.get(position).des);
        }
    }

    private void setSelectedHolder(Holder holder) {
        if (lastSelected != null) {
            lastSelected.tvVersion.setSelected(false);
        }
        if (holder != null) {
            holder.tvVersion.setSelected(true);
            lastSelected = holder;
        }
    }

    @Override
    public int getItemCount() {
        if (PxeCollectionUtils.isEmpty(caseX)) {
            return 0;
        }
        return caseX.size();
    }

    @Override
    public void onClick(View v) {
        //单选
        if (v.getTag() instanceof Holder) {
            Holder holder = (Holder) v.getTag();
            setSelectedHolder(holder);
            if (listener != null) {
                listener.onCaseSelected(caseX.get(holder.getAdapterPosition()));
            }
        }
    }

    static class Holder extends RecyclerView.ViewHolder {
        private TextView tvVersion;

        private Holder(View itemView) {
            super(itemView);
            tvVersion = itemView.findViewById(android.R.id.text1);
        }
    }

    public interface OnCaseSelectedListener {
        void onCaseSelected(PxeMockBean.CaseBean caseBean);
    }
}
