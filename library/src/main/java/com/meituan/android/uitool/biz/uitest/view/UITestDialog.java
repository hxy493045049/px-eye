package com.meituan.android.uitool.biz.uitest.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.meituan.android.uitool.biz.uitest.base.UITestDialogCallback;
import com.meituan.android.uitool.biz.uitest.base.Element;
import com.meituan.android.uitool.biz.uitest.base.item.BriefDescItem;
import com.meituan.android.uitool.biz.uitest.base.item.Item;
import com.meituan.android.uitool.library.R;

import java.util.ArrayList;
import java.util.List;

import static com.meituan.android.uitool.utils.PxeDimensionUtils.dip2px;
import static com.meituan.android.uitool.utils.PxeDimensionUtils.getScreenHeight;
import static com.meituan.android.uitool.utils.PxeDimensionUtils.getScreenWidth;


public class UITestDialog extends Dialog implements View.OnClickListener{

    private CustomListView customListView;
    private View dragBar;
    private Switch validViews;
    private TextView uploadImg;
    private LinearLayout rightArrowContainer;
    private TextView rightArrowText;
    private ImageView rightArrow;
    private UITestDialogCallback callback;
    private LinearLayout uitestSettingContainer;

    public UITestDialog(Context context) {
        super(context, R.style.uet_Theme_Holo_Dialog_background_Translucent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uet_dialog_attrs);
        initView();
        initAction();
        initData();
    }

    private void initView() {
        customListView = findViewById(R.id.customListView);
        dragBar = findViewById(R.id.drag_bar);
        validViews = findViewById(R.id.validViews);
        uploadImg = findViewById(R.id.uploadImg);
        rightArrowContainer = findViewById(R.id.rightArrowContainer);
        rightArrowText = findViewById(R.id.rightArrowText);
        rightArrow = findViewById(R.id.rightArrow);
        uitestSettingContainer = findViewById(R.id.uitestSettingContainer);
    }
    private void initAction() {
        rightArrowContainer.setOnClickListener(this);
        uploadImg.setOnClickListener(this);

        dragBar.setOnTouchListener(new View.OnTouchListener() {
            float lastX;
            float lastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx = event.getRawX() - lastX;
                        float dy = event.getRawY() - lastY;
                        Window dialogWindow = getWindow();
                        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                        lp.x = (int) Math.max(Math.min(getScreenWidth() - lp.width, lp.x + dx), 0);
                        lp.y = (int) Math.max(Math.min(getScreenHeight() - lp.height, lp.y + dy), 0);
                        dialogWindow.setAttributes(lp);
                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        break;
                }
                return true;
            }
        });
        validViews.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (callback != null) {
                    callback.showValidViews(isChecked);
                }
                if (isChecked) {
                    customListView.setVisibility(View.VISIBLE);
                }else {
                    customListView.setVisibility(View.GONE);
                }
                rightArrowText.setText(getContext().getResources().getString(R.string.uet_click_to_open_attr_change));
                rightArrow.setImageResource(R.drawable.uet_right_arrow);
            }
        });
        uitestSettingContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDailog.showDialog(getContext());
            }
        });
    }
    private void initData() {
        customListView.setAttrDialogCallback(callback);

    }
    public void closeValidViews() {
        validViews.setChecked(false);
    }

    public void show(Element element) {
        setCanceledOnTouchOutside(true);
        show();
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.width = getScreenWidth() - dip2px(15);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.x = Math.max(Math.min(getScreenWidth() - lp.width, element.getRect().left), 0);
        lp.y = Math.max(Math.min(getScreenHeight() - lp.height, element.getRect().bottom), 0);
        dialogWindow.setAttributes(lp);
        customListView.notifyViewAttrChanged(element);
    }
    public void updateCurrentElement(Element element) {
        customListView.updateCurrentItem(element);
    }

    public void notifyValidViewItemInserted(List<Element> validElements, Element targetElement) {
        List<Item> validItems = new ArrayList<>();
        for (int i = 0, N = validElements.size(); i < N; i++) {
            Element element = validElements.get(i);
            validItems.add(new BriefDescItem(element, targetElement.equals(element)));
        }
        customListView.notifyValidViewItemInserted(validItems);
    }

    public final void notifyItemRangeRemoved() {
        customListView.notifyValidViewItemRemoved();
    }

    public void setAttrDialogCallback(UITestDialogCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.uploadImg) {
            callback.generateScreenshot(customListView.getCurrentClickedItem());
        }else if (i == R.id.rightArrowContainer) {
            if (TextUtils.equals(rightArrowText.getText(),this.getContext().getResources().getString(R.string.uet_click_to_open_attr_change))) {
                validViews.setChecked(false);
                rightArrowText.setText(this.getContext().getResources().getString(R.string.uet_click_to_close_attr_change));
                rightArrow.setImageResource(R.drawable.uet_left_arrow);
                customListView.setVisibility(View.VISIBLE);
            }else {
                rightArrowText.setText(this.getContext().getResources().getString(R.string.uet_click_to_open_attr_change));
                rightArrow.setImageResource(R.drawable.uet_right_arrow);
                customListView.setVisibility(View.GONE);
            }
        }

    }

}

