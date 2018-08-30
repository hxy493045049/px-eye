package com.meituan.android.uitool.biz.measure;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.meituan.android.uitool.biz.IFoodUEFunction;
import com.meituan.android.uitool.biz.measure.dialog.FoodUESetValueDialog;
import com.meituan.android.uitool.plugin.FoodUEBoardTextView;
import com.meituan.android.uitool.plugin.FoodUEDraggingRectView;
import com.meituan.android.uitool.plugin.FoodUEGriddingLayout;
import com.meituan.android.uitool.plugin.FoodUiSteeringWheel;
import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.utils.FoodUEDimensionUtils;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/8 on 下午5:03
 * 测量功能的具体实现, 业务逻辑放这里
 */
public class FoodUEMeasureFunctionImpl implements IFoodUEFunction, View.OnClickListener, FoodUiSteeringWheel.OnWheelTouchListener {
    private FoodUEDraggingRectView measureBar;//测量条
    private FoodUiSteeringWheel steeringWheel;//方向盘
    private static final int SCREEN_WIDTH = FoodUEDimensionUtils.getScreenWidth();
    private static final int SCREEN_HEIGHT = FoodUEDimensionUtils.getScreenHeight();

    @Override
    public View getView(ViewGroup container) {
        //边距线
        Context ctx = container.getContext();
        container.addView(new FoodUEGriddingLayout(ctx));

        //测量条
        measureBar = new FoodUEDraggingRectView(ctx);
        measureBar.setOnClickListener(this);
        measureBar.setId(R.id.food_ui_tools_measure_bar);
        container.addView(measureBar);

        //方向盘
        steeringWheel = new FoodUiSteeringWheel(ctx);
        steeringWheel.setOnWheelTouchListener(this);
        int size = (int) ctx.getResources().getDimension(R.dimen.food_ue_tools_measure_wheel_size);
        FrameLayout.LayoutParams wheelParams = new FrameLayout.LayoutParams(size, size);
        wheelParams.rightMargin = FoodUEDimensionUtils.dip2px(18);
        wheelParams.bottomMargin = FoodUEDimensionUtils.dip2px(18);
        wheelParams.gravity = Gravity.BOTTOM | Gravity.END;
        container.addView(steeringWheel, wheelParams);

        //底部提示
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        container.addView(initBottomHint(ctx), params);
        return container;
    }

    //---------------private---------------
    private View initBottomHint(Context ctx) {
        Activity targetActivity = FoodUETool.getInstance(ctx).getTargetActivity();
        String defaultInfo = "";
        if (targetActivity != null) {
            defaultInfo = "food" + " / " + targetActivity.getClass().getName();
        }
        FoodUEBoardTextView board = new FoodUEBoardTextView(ctx);//底部提示
        board.setText(ctx.getResources().getString(R.string.ue_measure_bottom_hint,
                String.valueOf(FoodUEGriddingLayout.LINE_INTERVAL_DP), defaultInfo));

        return board;
    }

    private void updateMeasureBar(String width, String height) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) measureBar.getLayoutParams();
        if (params == null) {
            return;
        }

        if (!TextUtils.isEmpty(width)) {
            try {
                int w = FoodUEDimensionUtils.dip2px(Integer.valueOf(width));

                if (w > SCREEN_WIDTH) {
                    w = FrameLayout.LayoutParams.MATCH_PARENT;
                }
                params.width = w;
            } catch (NumberFormatException e) {
                Log.e("FoodUEMeasureFunction", "invalid width", e);
            }
        }
        if (!TextUtils.isEmpty(height)) {
            try {
                int h = FoodUEDimensionUtils.dip2px(Integer.valueOf(height));
                if (h > SCREEN_HEIGHT) {
                    h = FrameLayout.LayoutParams.MATCH_PARENT;
                }
                params.height = h;
            } catch (NumberFormatException e) {
                Log.e("FoodUEMeasureFunction", "invalid width", e);
            }
        }
        measureBar.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.food_ui_tools_measure_bar) {
            FoodUESetValueDialog dialog = new FoodUESetValueDialog(v.getContext());
            dialog.setHint(v.getMeasuredWidth(),v.getMeasuredHeight());
            dialog.setOnClickListener(this::updateMeasureBar);
             
            dialog.show();
        }
    }

    @Override
    public void onDirection(double arc) {
        measureBar.updatePosition(arc);
    }
}
