package com.meituan.android.biz.element.dialog.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meituan.android.model.FoodUEBaseViewHolder;
import com.meituan.android.model.attr.FoodUEBaseAttr;
import com.meituan.android.uitool.library.R;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午5:01
 */
public class FoodUENormalHolder extends FoodUEBaseViewHolder<String, String> {
    public TextView attrName, attrValue;

    public FoodUENormalHolder(ViewGroup parent) {
        super(View.inflate(parent.getContext(), R.layout.food_ue_attr_dialog_item, null));
        attrName = itemView.findViewById(R.id.attrName);
        attrValue = itemView.findViewById(R.id.attrValue);
    }

    @Override
    public void onBindViewHolder(FoodUEBaseAttr<String, String> data) {
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
