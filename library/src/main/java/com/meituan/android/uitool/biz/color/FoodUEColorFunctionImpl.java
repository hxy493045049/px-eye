package com.meituan.android.uitool.biz.color;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.biz.IFoodUEFunction;
import com.meituan.android.uitool.plugin.FoodUITakeColorView;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/24 on 下午5:05
 */
public class FoodUEColorFunctionImpl implements IFoodUEFunction {
    @Override
    public View getView(ViewGroup container) {
        Activity activity = FoodUETool.getInstance(null).getTargetActivity();
        ViewGroup decorView = ((ViewGroup) activity.getWindow().getDecorView());
        FoodUITakeColorView takeColorView = new FoodUITakeColorView(activity);
        takeColorView.setRoot(decorView.getRootView());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        takeColorView.setLayoutParams(params);
        return takeColorView;
    }
}
