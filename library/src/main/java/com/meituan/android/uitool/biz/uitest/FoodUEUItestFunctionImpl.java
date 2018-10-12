package com.meituan.android.uitool.biz.uitest;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.biz.IFoodUEFunction;
import com.meituan.android.uitool.biz.uitest.view.FoodUEUITestLayout;
import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.plugin.FoodUEBoardTextView;

/**
 * Created by wenbin on 2018/10/11.
 */

public class FoodUEUItestFunctionImpl implements IFoodUEFunction {

    @Override
    public View getView(ViewGroup container) {
        container.addView(new FoodUEUITestLayout(container.getContext()));
        FoodUEBoardTextView boardTextView = new FoodUEBoardTextView(container.getContext());
        String defalutInfo = container.getResources().getString(R.string.uet_name) + " / " + FoodUETool.getInstance().getTargetActivity().getClass().getName();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        boardTextView.setText(defalutInfo);
        container.addView(boardTextView,params);
        return container;
    }
}
