package com.meituan.android.uitool.biz.uitest.base.item;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.meituan.android.uitool.biz.uitest.base.UITestDialogCallback;
import com.meituan.android.uitool.biz.uitest.base.Element;
import com.meituan.android.uitool.library.R;

import static com.meituan.android.uitool.utils.PxeResourceUtils.getResourceName;


public class BriefDescItem extends ElementItem {

    private boolean isSelected;
    private UITestDialogCallback callback;
    private TextView vDesc;

    public BriefDescItem(Element element) {
        this(element, false);
    }

    public BriefDescItem(Element element, boolean isSelected) {
        super("", element);
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        if (vDesc!= null) {
            if (isSelected) {
                vDesc.setBackgroundColor(vDesc.getResources().getColor(R.color.uet_theme_color_red));
            }else {
                vDesc.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    @Override
    public boolean isValid() {
        return true;
    }


    public void setCallback(UITestDialogCallback callback) {
        this.callback = callback;
    }
    @Override
    public View getView(Context context) {
        if (itemView != null) {
            return itemView;
        }
        itemView = LayoutInflater.from(context).inflate(R.layout.uet_cell_brief_view_desc, null);
        vDesc = (TextView) itemView;

        vDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.selectView(getElement());
                }

            }
        });

        View view = getElement().getView();
        StringBuilder sb = new StringBuilder();
        sb.append(view.getClass().getName());
        String resName = getResourceName(view.getId());
        if (!TextUtils.isEmpty(resName)) {
            sb.append("@").append(resName);
        }
        vDesc.setText(sb.toString());
        setSelected(isSelected());

        return itemView;
    }
}
