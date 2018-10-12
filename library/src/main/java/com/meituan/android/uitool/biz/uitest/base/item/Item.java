package com.meituan.android.uitool.biz.uitest.base.item;

import android.content.Context;
import android.view.View;

public abstract class Item {

    protected View itemView;
    public boolean isValid() {
        return true;
    }

    public abstract View getView(Context context);
}
