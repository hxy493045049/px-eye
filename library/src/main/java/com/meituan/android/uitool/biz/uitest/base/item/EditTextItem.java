package com.meituan.android.uitool.biz.uitest.base.item;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.meituan.android.uitool.biz.uitest.base.Element;
import com.meituan.android.uitool.library.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.meituan.android.uitool.utils.PxeDimensionUtils.dip2px;


public class EditTextItem extends ElementItem {

    private @Type
    int type;
    private String detail;

    EditText vDetail;
    View vColor;

    public EditTextItem(String name, Element element, @Type int type, String detail) {
        super(name, element);
        this.type = type;
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public int getType() {
        return type;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        int TYPE_TEXT = 1;
        int TYPE_TEXT_SIZE = 2;
        int TYPE_TEXT_COLOR = 3;
        int TYPE_WIDTH = 4;
        int TYPE_HEIGHT = 5;
        int TYPE_PADDING_LEFT = 6;
        int TYPE_PADDING_RIGHT = 7;
        int TYPE_PADDING_TOP = 8;
        int TYPE_PADDING_BOTTOM = 9;
    }

    protected TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                if (getType() == EditTextItem.Type.TYPE_TEXT) {
                    TextView textView = ((TextView) (getElement().getView()));
                    if (!TextUtils.equals(textView.getText().toString(), s.toString())) {
                        textView.setText(s.toString());
                    }
                } else if (getType() == EditTextItem.Type.TYPE_TEXT_SIZE) {
                    TextView textView = ((TextView) (getElement().getView()));
                    float textSize = Float.valueOf(s.toString());
                    if (textView.getTextSize() != textSize) {
                        textView.setTextSize(textSize);
                    }
                } else if (getType() == EditTextItem.Type.TYPE_TEXT_COLOR) {
                    TextView textView = ((TextView) (getElement().getView()));
                    int color = Color.parseColor(vDetail.getText().toString());
                    if (color != textView.getCurrentTextColor()) {
                        vColor.setBackgroundColor(color);
                        textView.setTextColor(color);
                    }
                } else if (getType() == EditTextItem.Type.TYPE_WIDTH) {
                    View view = getElement().getView();
                    int width = dip2px(Integer.valueOf(s.toString()));
                    if (Math.abs(width - view.getWidth()) >= dip2px(1)) {
                        view.getLayoutParams().width = width;
                        view.requestLayout();
                    }
                } else if (getType() == EditTextItem.Type.TYPE_HEIGHT) {
                    View view = getElement().getView();
                    int height = dip2px(Integer.valueOf(s.toString()));
                    if (Math.abs(height - view.getHeight()) >= dip2px(1)) {
                        view.getLayoutParams().height = height;
                        view.requestLayout();
                    }
                } else if (getType() == EditTextItem.Type.TYPE_PADDING_LEFT) {
                    View view = getElement().getView();
                    int paddingLeft = dip2px(Integer.valueOf(s.toString()));
                    if (Math.abs(paddingLeft - view.getPaddingLeft()) >= dip2px(1)) {
                        view.setPadding(paddingLeft, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
                    }
                } else if (getType() == EditTextItem.Type.TYPE_PADDING_RIGHT) {
                    View view = getElement().getView();
                    int paddingRight = dip2px(Integer.valueOf(s.toString()));
                    if (Math.abs(paddingRight - view.getPaddingRight()) >= dip2px(1)) {
                        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), paddingRight, view.getPaddingBottom());
                    }
                } else if (getType() == EditTextItem.Type.TYPE_PADDING_TOP) {
                    View view = getElement().getView();
                    int paddingTop = dip2px(Integer.valueOf(s.toString()));
                    if (Math.abs(paddingTop - view.getPaddingTop()) >= dip2px(1)) {
                        view.setPadding(view.getPaddingLeft(), paddingTop, view.getPaddingRight(), view.getPaddingBottom());
                    }
                } else if (getType() == EditTextItem.Type.TYPE_PADDING_BOTTOM) {
                    View view = getElement().getView();
                    int paddingBottom = dip2px(Integer.valueOf(s.toString()));
                    if (Math.abs(paddingBottom - view.getPaddingBottom()) >= dip2px(1)) {
                        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), paddingBottom);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public View getView(Context context) {
        if (itemView != null) {
            return itemView;
        }
        itemView = LayoutInflater.from(context).inflate(R.layout.uet_cell_edit_text,null);

        TextView vName = itemView.findViewById(R.id.name);
        vDetail = itemView.findViewById(R.id.detail);
        vColor = itemView.findViewById(R.id.color);

        vName.setText(getName());
        vDetail.setText(getDetail());
        if (vColor != null) {
            try {
                vColor.setBackgroundColor(Color.parseColor(getDetail()));
                vColor.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                vColor.setVisibility(View.GONE);
            }
        }
        vDetail.addTextChangedListener(textWatcher);
        return itemView;
    }
}
