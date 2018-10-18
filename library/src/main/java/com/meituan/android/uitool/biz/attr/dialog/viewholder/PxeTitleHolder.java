package com.meituan.android.uitool.biz.attr.dialog.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meituan.android.uitool.biz.attr.dialog.mode.PxeBaseAttr;
import com.meituan.android.uitool.library.R;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午5:01
 */
public class PxeTitleHolder extends PxeBaseViewHolder<String, String> {
    public TextView title;

    public PxeTitleHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.pxe_attr_dialog_title, parent, false));
        title = itemView.findViewById(R.id.title);
    }

    @Override
    public void onBindViewHolder(PxeBaseAttr<String, String> data) {
        if (data == null) {
            return;
        }
        if (title != null) {
            title.setText(data.getKey());
        }
    }
}
