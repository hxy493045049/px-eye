package com.meituan.android.uitool.biz.mock.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meituan.android.uitool.biz.mock.PxeMockBean;
import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.utils.PxeCollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/13 on 下午6:44
 */
public class PxeVersionAdapter extends RecyclerView.Adapter<PxeVersionAdapter.Holder> implements View.OnClickListener {
    private List<PxeMockBean.MockDataBean> mockData = new ArrayList<>();
    private Holder lastSelected;
    private OnVersionSelectedListener listener;

    public void notifyDataChange(PxeMockBean data) {
        if (data == null || data.data == null || PxeCollectionUtils.isEmpty(data.data.mockData)) {
            return;
        }
        mockData = data.data.mockData;
        notifyDataSetChanged();
    }

    public void setOnVersionSelectedListener(OnVersionSelectedListener l) {
        listener = l;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pxe_simple_item, parent, false);
        view.setOnClickListener(this);
        Holder holder = new Holder(view);
        view.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if (!PxeCollectionUtils.isEmpty(mockData) && mockData.size() > position) {
            holder.tvVersion.setText(mockData.get(position).version);
            if (position == 0) {
                setSelectedHolder(holder);
            }
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
        if (PxeCollectionUtils.isEmpty(mockData)) {
            return 0;
        }
        return mockData.size();
    }

    @Override
    public void onClick(View v) {
        //单选
        if (v.getTag() instanceof Holder) {
            Holder holder = (Holder) v.getTag();
            setSelectedHolder(holder);
            if (listener != null) {
                listener.onVersionSelected(mockData.get(holder.getAdapterPosition()));
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

    public interface OnVersionSelectedListener {
        void onVersionSelected(PxeMockBean.MockDataBean versionBean);
    }
}
