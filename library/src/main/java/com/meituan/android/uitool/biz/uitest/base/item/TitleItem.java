package com.meituan.android.uitool.biz.uitest.base.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.meituan.android.uitool.library.R;


public class TitleItem extends Item {

    private String name;

    public TitleItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public View getView(Context context) {
        if (itemView != null) {
            return itemView;
        }
        itemView = LayoutInflater.from(context).inflate(R.layout.uet_cell_title, null);
        TextView textView = itemView.findViewById(R.id.title);
        textView.setText(name);
        return itemView;
    }
}
