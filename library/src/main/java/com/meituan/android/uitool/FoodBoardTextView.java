package com.meituan.android.uitool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.meituan.android.base.BaseConfig;

public class FoodBoardTextView extends AppCompatTextView {

    private final String defaultInfo = "food" + " / " + FoodUETool.getInstance().getTargetActivity().getClass().getName();
    private final int padding = BaseConfig.dp2px(3);

    public FoodBoardTextView(Context context) {
        this(context, null);
    }

    public FoodBoardTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoodBoardTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setBackgroundColor(0x902395ff);
        setPadding(padding, padding, padding, padding);
        setTextColor(0xffffffff);
        setTextSize(9);
        setText(defaultInfo);
    }

    @SuppressLint("SetTextI18n")
    public void updateInfo(String info) {
        setText(info + "\n" + defaultInfo);
    }
}
