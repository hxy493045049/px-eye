package com.meituan.android.measure;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.meituan.android.uitool.library.R;

/**
 * Author: gaojin
 * Time: 2018/8/2 下午3:03
 */

public class FoodSetValueDialog extends Dialog implements View.OnClickListener {

    private InputMethodManager manager;
    private onClickListener listener;
    private EditText editText;

    public FoodSetValueDialog(@NonNull Context context) {
        this(context, R.style.FoodSetValueDialog);
    }

    public FoodSetValueDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getWindow() != null) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        setContentView(R.layout.food_ui_set_value_dialog);
        manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        editText = findViewById(R.id.dialog_edit_text);
        TextView ensureBtn = findViewById(R.id.dialog_ensure);
        ensureBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (manager != null) {
            manager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
        dismiss();
        if (listener != null) {
            listener.onClick(editText.getText().toString());
        }
    }

    public void setOnClickListener(onClickListener listener) {
        this.listener = listener;
    }

    public interface onClickListener {
        void onClick(String value);
    }
}
