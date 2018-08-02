package com.meituan.android.uitool;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.meituan.android.measure.FoodDraggingRectView;
import com.meituan.android.measure.FoodGriddingLayout;
import com.meituan.android.uitool.library.R;
import com.meituan.android.utils.FoodDevUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author: gaojin
 * Time: 2018/6/19 下午4:03
 */

public class FoodTransparentActivity extends AppCompatActivity {
    public static final String EXTRA_TYPE = "extra_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            finish();
            return;
        }
        FoodDevUtils.setStatusBarColor(getWindow(), Color.TRANSPARENT);
        FoodDevUtils.enableFullscreen(getWindow());
        setContentView(R.layout.food_ue_activity_transparent);

        ViewGroup vContainer = findViewById(R.id.container);
        final FoodBoardTextView board = new FoodBoardTextView(this);
        int type = getIntent().getIntExtra(EXTRA_TYPE, Type.TYPE_UNKNOWN);
        switch (type) {
            case Type.TYPE_SHOW_GRIDDING:
                vContainer.addView(new FoodGriddingLayout(this));
                vContainer.addView(new FoodDraggingRectView(this));
                board.updateInfo("间距: " + String.valueOf(FoodGriddingLayout.LINE_INTERVAL_DP) + "dp");
                break;
            default:
                finish();
                break;
        }

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        vContainer.addView(board, params);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FoodUETool.getInstance().release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @IntDef({
            Type.TYPE_EDIT_ATTR,
            Type.TYPE_UNKNOWN,
            Type.TYPE_SHOW_GRIDDING,
            Type.TYPE_RELATIVE_POSITION,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        int TYPE_UNKNOWN = -1;
        int TYPE_EDIT_ATTR = 1;
        int TYPE_SHOW_GRIDDING = 2;
        int TYPE_RELATIVE_POSITION = 3;
    }
}
