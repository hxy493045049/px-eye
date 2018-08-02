package com.meituan.android.uitool;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meituan.android.uitool.library.R;


public class FoodUESubMenu extends LinearLayout {

    private ImageView vImage;
    private TextView vTitle;

    public FoodUESubMenu(Context context) {
        this(context, null);
    }

    public FoodUESubMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoodUESubMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.food_ue_sub_menu_layout, this);
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        vImage = findViewById(R.id.image);
        vTitle = findViewById(R.id.title);
    }

    public void update(SubMenu subMenu) {
        vImage.setImageResource(subMenu.getImageRes());
        vTitle.setText(subMenu.getTitle());
        setOnClickListener(subMenu.getOnClickListener());
    }

    public static class SubMenu {
        private String title;
        private int imageRes;
        private OnClickListener onClickListener;

        public SubMenu(String title, int imageRes, OnClickListener onClickListener) {
            this.title = title;
            this.imageRes = imageRes;
            this.onClickListener = onClickListener;
        }

        public String getTitle() {
            return title;
        }

        public int getImageRes() {
            return imageRes;
        }

        public OnClickListener getOnClickListener() {
            return onClickListener;
        }
    }
}
