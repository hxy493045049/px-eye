package com.meituan.android.uitool;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.meituan.android.uitool.library.R;
import com.meituan.android.utils.FoodUEActivityUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author: gaojin
 * Time: 2018/6/19 下午4:03
 */

public class FoodUEToolsActivity extends AppCompatActivity {
    public static final String EXTRA_TYPE = "extra_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            //不支持状态恢复, 如果系统异常导致act被销毁, ueTool默认关闭
            finish();
            return;
        }
        FoodUEActivityUtils.setStatusBarColor(getWindow(), Color.TRANSPARENT);
        FoodUEActivityUtils.enableFullscreen(getWindow());

        View v = View.inflate(this, R.layout.food_ue_activity_transparent, null);
        ViewGroup vContainer = v.findViewById(R.id.container);
        int type = getIntent().getIntExtra(EXTRA_TYPE, Type.TYPE_UNKNOWN);
        View root = FoodUEViewManager.getInstance().generateView(type, vContainer);
        setContentView(root);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FoodUETool.getInstance(FoodUETool.applicationContext).release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @IntDef({
            Type.TYPE_EDIT_ATTR,
            Type.TYPE_UNKNOWN,
            Type.TYPE_MEASURE,
            Type.TYPE_RELATIVE_POSITION,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        int TYPE_UNKNOWN = -1;
        int TYPE_EDIT_ATTR = 1;//属性捕捉
        int TYPE_MEASURE = 2;//测量类型
        int TYPE_RELATIVE_POSITION = 3;//相对位置
    }
}
