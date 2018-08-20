package com.meituan.android.biz.measure;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.meituan.android.base.BaseConfig;
import com.meituan.android.biz.IFoodUEFunction;
import com.meituan.android.biz.measure.dialog.FoodUESetValueDialog;
import com.meituan.android.plugin.FoodUEBoardTextView;
import com.meituan.android.plugin.FoodUEDraggingRectView;
import com.meituan.android.plugin.FoodUEGriddingLayout;
import com.meituan.android.plugin.FoodUiSteeringWheel;
import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.library.R;

import roboguice.util.Ln;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/8 on 下午5:03
 * 测量功能的具体实现, 业务逻辑放这里
 */
public class FoodUEMeasureFunctionImpl implements IFoodUEFunction, View.OnClickListener, FoodUiSteeringWheel.OnWheelTouchListener {
    private FoodUEDraggingRectView measureBar;//测量条
    private FoodUiSteeringWheel steeringWheel;//方向盘

    @Override
    public View getView(ViewGroup container) {
        Context ctx = container.getContext();
        container.addView(new FoodUEGriddingLayout(ctx));//边距线

        measureBar = new FoodUEDraggingRectView(ctx);//测量条
        measureBar.setOnClickListener(this);
        measureBar.setId(R.id.food_ui_tools_measure_bar);
        container.addView(measureBar);

        steeringWheel = new FoodUiSteeringWheel(ctx);
        steeringWheel.setOnWheelTouchListener(this);
        int size = (int) ctx.getResources().getDimension(R.dimen.food_ue_tools_measure_wheel_size);
        FrameLayout.LayoutParams wheelParams = new FrameLayout.LayoutParams(size, size);
        wheelParams.rightMargin = BaseConfig.dp2px(18);
        wheelParams.bottomMargin = BaseConfig.dp2px(18);
        wheelParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        container.addView(steeringWheel, wheelParams);

        //底部提示
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        container.addView(initBottomHint(ctx), params);
        return container;
    }

    //---------------private---------------
    private View initBottomHint(Context ctx) {
        Activity targetActivity = FoodUETool.getInstance().getTargetActivity();
        String defaultInfo = "";
        if (targetActivity != null) {
            defaultInfo = "food" + " / " + targetActivity.getClass().getName();
        }
        FoodUEBoardTextView board = new FoodUEBoardTextView(ctx);//底部提示
        board.setText(ctx.getResources().getString(R.string.ue_measure_bottom_hint,
                String.valueOf(FoodUEGriddingLayout.LINE_INTERVAL_DP), defaultInfo));

        return board;
    }

    private void updateFrame(String width, String height) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) measureBar.getLayoutParams();
        if (params == null) {
            return;
        }

        if (!TextUtils.isEmpty(width)) {
            try {
                int w = BaseConfig.dp2px(Integer.valueOf(width));
                if (w > BaseConfig.width) {
                    w = FrameLayout.LayoutParams.MATCH_PARENT;
                }
                params.width = w;
            } catch (NumberFormatException e) {
                Ln.e(e, "invalid width");
            }
        }
        if (!TextUtils.isEmpty(height)) {
            try {
                int h = BaseConfig.dp2px(Integer.valueOf(height));
                if (h > BaseConfig.height) {
                    h = FrameLayout.LayoutParams.MATCH_PARENT;
                }
                params.height = h;
            } catch (NumberFormatException e) {
                Ln.e(e, "invalid width");
            }
        }
        measureBar.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.food_ui_tools_measure_bar) {
            FoodUESetValueDialog dialog = new FoodUESetValueDialog(v.getContext());
            dialog.setOnClickListener(this::updateFrame);
            dialog.show();
        }
    }

    @Override
    public void onDirection(double arc) {
        measureBar.updatePosition(arc);
    }
}
