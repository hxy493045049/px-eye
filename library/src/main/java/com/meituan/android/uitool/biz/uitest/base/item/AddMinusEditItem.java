package com.meituan.android.uitool.biz.uitest.base.item;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.meituan.android.uitool.biz.uitest.base.Element;
import com.meituan.android.uitool.library.R;


public class AddMinusEditItem extends EditTextItem {

    public AddMinusEditItem(String name, Element element, int type, String detail) {
        super(name, element, type, detail);
    }

    @Override
    public View getView(Context context) {
        if (itemView != null) {
            return itemView;
        }
        itemView = LayoutInflater.from(context).inflate(R.layout.uet_cell_add_minus_edit, null);

        View vAdd = itemView.findViewById(R.id.add);
        TextView vName = itemView.findViewById(R.id.name);
        View vMinus = itemView.findViewById(R.id.minus);
        vDetail = itemView.findViewById(R.id.detail);
        vColor = itemView.findViewById(R.id.color);

        vName.setText(getName());
        vDetail.setText(getDetail());
        if (vColor != null) {
            try {
                vColor.setBackgroundColor(Color.parseColor(getDetail()));
                vColor.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                vColor.setVisibility(View.GONE);
            }
        }

        vAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int textSize = Integer.valueOf(vDetail.getText().toString());
                    vDetail.setText(String.valueOf(++textSize));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        vMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int textSize = Integer.valueOf(vDetail.getText().toString());
                    if (textSize > 0) {
                        vDetail.setText(String.valueOf(--textSize));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        vDetail.addTextChangedListener(textWatcher);
        return itemView;
    }
}
