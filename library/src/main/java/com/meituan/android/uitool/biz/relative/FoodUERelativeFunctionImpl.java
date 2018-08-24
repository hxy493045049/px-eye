package com.meituan.android.uitool.biz.relative;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.meituan.android.uitool.biz.IFoodUEFunction;
import com.meituan.android.uitool.biz.relative.mode.FoodUERelativeMode;
import com.meituan.android.uitool.plugin.FoodUEBoardTextView;
import com.meituan.android.uitool.plugin.FoodUEElementLayout;
import com.meituan.android.uitool.plugin.FoodUEGriddingLayout;
import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.library.R;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/21 on 下午3:37
 */
public class FoodUERelativeFunctionImpl implements IFoodUEFunction {
    @Override
    public View getView(ViewGroup container) {
        Context ctx = container.getContext();
        FoodUEElementLayout layout = new FoodUEElementLayout(container.getContext());
        FoodUERelativeMode modeImpl = new FoodUERelativeMode(null);
        //因为FoodUERelativeMode使用了DashPathEffect,所以要关闭硬件加速
        layout.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        modeImpl.setOnViewChangeListener(layout::invalidate);
        layout.setModeImpl(modeImpl);
        container.addView(layout);

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
}
