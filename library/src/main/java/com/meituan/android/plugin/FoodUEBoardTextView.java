package com.meituan.android.plugin;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.meituan.android.utils.FoodUEDimensionUtils;

public class FoodUEBoardTextView extends AppCompatTextView {

    private final int padding = FoodUEDimensionUtils.dip2px(3);

    public FoodUEBoardTextView(Context context) {
        this(context, null);
    }

    public FoodUEBoardTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoodUEBoardTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
