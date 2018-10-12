package com.meituan.android.uitool.biz.uitest.base.item;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.meituan.android.uitool.biz.uitest.base.UITestDialogCallback;
import com.meituan.android.uitool.biz.uitest.base.Element;
import com.meituan.android.uitool.library.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class SwitchItem extends ElementItem {

    @Type
    private int type;
    private boolean isChecked;
    private UITestDialogCallback callback;

    public SwitchItem(String name, Element element, @Type int type) {
        super(name, element);
        this.type = type;
    }

    public SwitchItem(String name, Element element, @Type int type, boolean isChecked) {
        super(name, element);
        this.type = type;
        this.isChecked = isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public int getType() {
        return type;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        int TYPE_IS_BOLD = 1; // 是否是加粗
        int TYPE_MOVE = 2; //移动控件
    }

    public void setCallback(UITestDialogCallback callback) {
        this.callback = callback;
    }
    @Override
    public View getView(Context context) {

        if (itemView != null) {
            return itemView;
        }
        itemView = LayoutInflater.from(context).inflate(R.layout.uet_cell_switch, null);

        TextView vName = itemView.findViewById(R.id.name);
        Switch vSwitch = itemView.findViewById(R.id.switch_view);
        vName.setText(getName());
        vSwitch.setChecked(isChecked());
        vSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if (getType() == SwitchItem.Type.TYPE_MOVE) {
                        if (callback != null && isChecked) {
                            callback.enableMove();
                        }
                        return;
                    }
                    if (getElement().getView() instanceof TextView) {
                        TextView textView = ((TextView) (getElement().getView()));
                        if (getType() == SwitchItem.Type.TYPE_IS_BOLD) {
                            textView.setTypeface(null, isChecked ? Typeface.BOLD : Typeface.NORMAL);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return itemView;
    }
}
