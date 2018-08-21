package com.meituan.android.biz.element.dialog.viewholder;

import android.view.LayoutInflater;
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
public class FoodUETitleHolder extends FoodUEBaseViewHolder<String, String> {
    public TextView title;

    public FoodUETitleHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.food_ue_attr_dialog_title, parent, false));
        title = itemView.findViewById(R.id.title);
    }

    @Override
    public void onBindViewHolder(FoodUEBaseAttr<String, String> data) {
        if (data == null) {
            return;
        }
        if (title != null) {
            title.setText(data.getKey());
        }
    }
}
