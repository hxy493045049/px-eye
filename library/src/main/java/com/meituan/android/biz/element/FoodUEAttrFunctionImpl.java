package com.meituan.android.biz.element;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.meituan.android.biz.FoodUEBaseElementMode;
import com.meituan.android.biz.IFoodUEFunction;
import com.meituan.android.biz.element.dialog.FoodUEAttrsDialog;
import com.meituan.android.model.FoodUEViewInfo;
import com.meituan.android.plugin.FoodUEBoardTextView;
import com.meituan.android.plugin.FoodUEElementLayout;
import com.meituan.android.plugin.FoodUEGriddingLayout;
import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.library.R;

import java.lang.ref.WeakReference;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/10 on 上午10:37
 * 属性捕捉功能实现
 */
public class FoodUEAttrFunctionImpl implements IFoodUEFunction, FoodUEBaseElementMode.OnViewInfoSelectedListener {
    private FoodUEAttrsDialog dialog;
    private WeakReference<Context> contextRef;

    @Override
    public View getView(ViewGroup container) {
        reset();
        contextRef = new WeakReference<>(container.getContext());
        FoodUEElementLayout layout = new FoodUEElementLayout(container.getContext());
        container.addView(layout);
        layout.setOnViewInfoSelectedListener(this);

        //底部提示
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        container.addView(initBottomHint(contextRef.get()), params);
        return container;
    }

    @Override
    public void onViewInfoSelected(FoodUEViewInfo selectedViewInfo) {
        if (contextRef == null || contextRef.get() == null) {
            return;
        }
        Context context = contextRef.get();
        Activity act = null;
        if (context instanceof Activity) {
            act = (Activity) context;
        }
        if (act == null || act.isFinishing()) {
            return;
        }
        if (dialog == null) {
            dialog = new FoodUEAttrsDialog(context);
            dialog.show(selectedViewInfo);
        } else if (dialog.isShowing() || selectedViewInfo == null) {
            dialog.dismiss();
        } else {
            dialog.show(selectedViewInfo);
        }
    }

    //-----------private-----------
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

    private void reset() {
        dialog = null;
        contextRef = null;
    }
}
