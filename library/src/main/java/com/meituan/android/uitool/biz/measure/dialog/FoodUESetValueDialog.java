package com.meituan.android.uitool.biz.measure.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.utils.FoodUEDimensionUtils;

/**
 * Author: gaojin
 * Time: 2018/8/2 下午3:03
 */

public class FoodUESetValueDialog extends Dialog implements View.OnClickListener {

    private InputMethodManager manager;
    private onClickListener listener;
    private EditText editHeight, editWidth;

    private String
            measureBarWidth = FoodUETool.getResource().getString(R.string.ue_dialog_set_width),
            measureBarHeight = FoodUETool.getResource().getString(R.string.ue_dialog_set_height);

    public FoodUESetValueDialog(@NonNull Context context) {
        this(context, R.style.Food_UE_Measure_Dialog);
    }

    public FoodUESetValueDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getWindow() != null) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        setContentView(R.layout.food_ui_measure_dialog);
        manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        editHeight = findViewById(R.id.dialog_edit_text_height);
        editHeight.setHint(measureBarHeight);
        editWidth = findViewById(R.id.dialog_edit_text_width);
        editWidth.setHint(measureBarWidth);
        TextView ensureBtn = findViewById(R.id.dialog_ensure);
        ensureBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (manager != null) {
            manager.hideSoftInputFromWindow(editHeight.getWindowToken(), 0);
        }
        dismiss();
        if (listener != null) {
            listener.onClick(editWidth.getText().toString(), editHeight.getText().toString());
        }
    }

    public void setOnClickListener(onClickListener listener) {
        this.listener = listener;
    }


    public void setHint(int measuredWidth, int measuredHeight) {
        measureBarWidth = "当前宽度: " + FoodUEDimensionUtils.px2dip(measuredWidth) + " dp";
        measureBarHeight = "当前高度: " + FoodUEDimensionUtils.px2dip(measuredHeight) + " dp";
    }

    public interface onClickListener {
        void onClick(String width, String height);
    }
}
