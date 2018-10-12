package com.meituan.android.uitool.biz.uitest.base.item;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.utils.FoodUEActivityUtils;


public class TextItem extends TitleItem {

    private String detail;
    private boolean enableCopy;

    public TextItem(String name, String detail) {
        super(name);
        this.detail = detail;
    }

    public TextItem(String name, String detail, boolean enableCopy) {
        super(name);
        this.detail = detail;
        this.enableCopy = enableCopy;
    }

    public String getDetail() {
        return detail;
    }

    public boolean isEnableCopy() {
        return enableCopy;
    }

    @Override
    public boolean isValid() {
        if (TextUtils.isEmpty(detail)) {
            return false;
        }
        return true;
    }

    @Override
    public View getView(Context context) {

        if (itemView != null) {
            return itemView;
        }
        itemView = LayoutInflater.from(context).inflate(R.layout.uet_cell_text, null);


        TextView vName = itemView.findViewById(R.id.name);
        TextView vDetail = itemView.findViewById(R.id.detail);
        vName.setText(getName());
        vDetail.setText(getDetail());
        vDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnableCopy()) {
                    clipText(getDetail());
                }
            }
        });
        return itemView;
    }
    private static void clipText(String clipText) {
        Context context = FoodUEActivityUtils.getApplication();
        ClipData clipData = ClipData.newPlainText("", clipText);
        ((ClipboardManager) (context.getSystemService(Context.CLIPBOARD_SERVICE))).setPrimaryClip(clipData);
        Toast.makeText(context, "copied", Toast.LENGTH_SHORT).show();
    }

}