package com.meituan.android.uitool.plugin;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.meituan.android.uitool.utils.PxeDimensionUtils;

public class PxeBoardTextView extends AppCompatTextView {

    private final int padding = PxeDimensionUtils.dip2px(3);

    public PxeBoardTextView(Context context) {
        this(context, null);
    }

    public PxeBoardTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PxeBoardTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setBackgroundColor(0x902395ff);
        setPadding(padding, padding, padding, padding);
        setTextColor(0xffffffff);
        setTextSize(9);
    }
}
