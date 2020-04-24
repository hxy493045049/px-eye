package com.meituan.android.uitool.biz.attr.dialog.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meituan.android.uitool.biz.attr.dialog.mode.PxeBaseItem;
import com.meituan.android.uitool.biz.attr.dialog.mode.PxeTextItem;
import com.meituan.android.uitool.library.R;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午5:01
 */
public class PxeTextHolder extends PxeBaseViewHolder {
    public TextView attrName, attrValue;

    public PxeTextHolder(ViewGroup parent) {
        super(View.inflate(parent.getContext(), R.layout.pxe_attr_dialog_item, null));
        attrName = itemView.findViewById(R.id.attrName);
        attrValue = itemView.findViewById(R.id.attrValue);
    }

    @Override
    public <T extends PxeBaseItem> void onBindViewHolder(T data) {
        if (data instanceof PxeTextItem) {
            PxeTextItem attr = (PxeTextItem) data;
            if (attrName != null) {
                attrName.setText(attr.getKey());
            }
            if (attrValue != null) {
                attrValue.setText(attr.getValue());
            }
        }
    }
}
