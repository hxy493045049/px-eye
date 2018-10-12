package com.meituan.android.uitool.biz.attr.dialog.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meituan.android.uitool.model.PxeBaseViewHolder;
import com.meituan.android.uitool.model.attr.PxeBaseAttr;
import com.meituan.android.uitool.library.R;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午5:01
 */
public class PxeNormalHolder extends PxeBaseViewHolder<String, String> {
    public TextView attrName, attrValue;

    public PxeNormalHolder(ViewGroup parent) {
        super(View.inflate(parent.getContext(), R.layout.pxe_attr_dialog_item, null));
        attrName = itemView.findViewById(R.id.attrName);
        attrValue = itemView.findViewById(R.id.attrValue);
    }

    @Override
    public void onBindViewHolder(PxeBaseAttr<String, String> data) {
        if (data == null) {
            return;
        }
        if (attrName != null) {
            attrName.setText(data.getKey());
        }
        if (attrValue != null) {
            attrValue.setText(data.getValue());
        }
    }
}
