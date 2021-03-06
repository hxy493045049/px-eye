package com.meituan.android.uitool.biz.measure.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.utils.PxeDimensionUtils;
import com.meituan.android.uitool.utils.PxeResourceUtils;

/**
 * Author: gaojin
 * Time: 2018/8/2 下午3:03
 * 用于变更测量条宽高的dialog
 */
public class PxeSetValueDialog extends Dialog implements View.OnClickListener {

    private InputMethodManager manager;
    private onClickListener listener;
    private EditText editHeight, editWidth;

    private String
            measureBarWidth = PxeResourceUtils.getResource().getString(R.string.pxe_dialog_set_width),
            measureBarHeight = PxeResourceUtils.getResource().getString(R.string.pxe_dialog_set_height);

    public PxeSetValueDialog(@NonNull Context context) {
        this(context, R.style.Food_UE_Measure_Dialog);
    }

    public PxeSetValueDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getWindow() != null) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        setContentView(R.layout.pxe_measure_dialog);
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
        measureBarWidth = "当前宽度: " + PxeDimensionUtils.px2dip(measuredWidth) + " dp";
        measureBarHeight = "当前高度: " + PxeDimensionUtils.px2dip(measuredHeight) + " dp";
    }

    public interface onClickListener {
        void onClick(String width, String height);
    }
}
