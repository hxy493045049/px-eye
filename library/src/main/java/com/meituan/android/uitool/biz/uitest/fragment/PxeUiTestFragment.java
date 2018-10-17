package com.meituan.android.uitool.biz.uitest.fragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.meituan.android.uitool.FoodUETool;
import com.meituan.android.uitool.FoodUEToolsActivity;
import com.meituan.android.uitool.base.fragment.PxeBaseFunctionFragment;
import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.plugin.PxeBoardTextView;
import com.meituan.android.uitool.plugin.PxeGriddingLayout;
import com.meituan.android.uitool.utils.PxeActivityUtils;

/**
 * Created by wenbin on 2018/10/17.
 */

public class PxeUiTestFragment extends PxeBaseFunctionFragment {


    public static PxeUiTestFragment newInstance() {
        PxeUiTestFragment fragment = new PxeUiTestFragment();
        setArgument(fragment, FoodUEToolsActivity.Type.TYPE_UITEST);
        return fragment;
    }
    @Override
    protected void lazyLoad() {

    }
    @Override
    protected View createContentView() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.pxe_ui_test_layout, null, false);
        initBottom(root);
        return root;
    }

    private void initBottom(View root) {
        Activity targetActivity = FoodUETool.getInstance().getTargetActivity();
        String defaultInfo = "";
        if (targetActivity != null && !PxeActivityUtils.isActivityInvalid(targetActivity)) {
            defaultInfo = "ui检测" + " / " + targetActivity.getClass().getName();
        }
        PxeBoardTextView board = root.findViewById(R.id.pxe_view_info);
        board.setText(FoodUETool.getResource().getString(R.string.ue_measure_bottom_hint,
                String.valueOf(PxeGriddingLayout.LINE_INTERVAL_DP), defaultInfo));
    }
}
