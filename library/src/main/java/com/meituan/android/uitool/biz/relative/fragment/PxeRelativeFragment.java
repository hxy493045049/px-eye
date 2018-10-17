package com.meituan.android.uitool.biz.relative.fragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.FoodUEToolsActivity;
import com.meituan.android.uitool.base.fragment.PxeBaseFunctionFragment;
import com.meituan.android.uitool.biz.relative.behavior.PxeRelativeBehavior;
import com.meituan.android.uitool.biz.relative.painter.PxeRelativePainter;
import com.meituan.android.uitool.helper.PxeActivityRecorder;
import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.plugin.PxeBoardTextView;
import com.meituan.android.uitool.plugin.PxeGriddingLayout;
import com.meituan.android.uitool.plugin.PxeFunctionView;
import com.meituan.android.uitool.utils.ApplicationSingleton;
import com.meituan.android.uitool.utils.PxeActivityUtils;
import com.meituan.android.uitool.utils.PxeResourceUtils;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/9/17 on 下午7:18
 */
public class PxeRelativeFragment extends PxeBaseFunctionFragment {

    public static PxeRelativeFragment newInstance() {
        PxeRelativeFragment fragment = new PxeRelativeFragment();
        setArgument(fragment, FoodUEToolsActivity.Type.TYPE_RELATIVE_POSITION);
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }


    @Override
    protected View createContentView() {
        if (getContext() == null) {
            return new LinearLayout(ApplicationSingleton.getApplicationContext());
        }
        View root = LayoutInflater.from(getContext()).inflate(R.layout.pxe_relative_layout, new FrameLayout(getContext()), true);

        PxeRelativeBehavior behavior = new PxeRelativeBehavior(new PxeRelativePainter());
        PxeFunctionView functionView = root.findViewById(R.id.function_view);
        functionView.setBehavior(behavior);

        initBottom(root);
        return root;
    }

    private void initBottom(View root) {
        Activity targetActivity = PxeActivityRecorder.getInstance().getTargetActivity();
        String defaultInfo = "";
        if (targetActivity != null && !PxeActivityUtils.isActivityInvalid(targetActivity)) {
            defaultInfo = "food" + " / " + targetActivity.getClass().getName();
        }
        PxeBoardTextView board = root.findViewById(R.id.pxe_view_info);
        board.setText(PxeResourceUtils.getResource().getString(R.string.ue_measure_bottom_hint,
                String.valueOf(PxeGriddingLayout.LINE_INTERVAL_DP), defaultInfo));
    }
}
