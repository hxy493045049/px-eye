package com.meituan.android.uitool.biz.measure.fragment;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.meituan.android.uitool.FoodUEToolsActivity;
import com.meituan.android.uitool.base.fragment.PxeBaseFunctionFragment;
import com.meituan.android.uitool.biz.measure.dialog.PxeSetValueDialog;
import com.meituan.android.uitool.helper.PxeActivityRecorder;
import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.plugin.PxeBoardTextView;
import com.meituan.android.uitool.plugin.PxeDraggingRectView;
import com.meituan.android.uitool.plugin.PxeGriddingLayout;
import com.meituan.android.uitool.plugin.PxeSteeringWheel;
import com.meituan.android.uitool.utils.ApplicationSingleton;
import com.meituan.android.uitool.utils.PxeActivityUtils;
import com.meituan.android.uitool.utils.PxeDimensionUtils;
import com.meituan.android.uitool.utils.PxeResourceUtils;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/9/19 on 下午4:45
 */
public class PxeMeasureFragment extends PxeBaseFunctionFragment implements View.OnClickListener, PxeSteeringWheel.OnWheelTouchListener {
    private PxeDraggingRectView measureBar;//测量条
    private PxeSetValueDialog dialog;

    public static PxeMeasureFragment newInstance() {
        PxeMeasureFragment fragment = new PxeMeasureFragment();
        setArgument(fragment, FoodUEToolsActivity.Type.TYPE_MEASURE);
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        if (!PxeActivityUtils.isActivityInvalid(getActivity())) {
            dialog = new PxeSetValueDialog(getActivity());
        }
    }

    @Override
    protected View createContentView() {
        if (getContext() == null) {
            return new LinearLayout(ApplicationSingleton.getApplicationContext());
        }
        View root = LayoutInflater.from(getContext()).inflate(R.layout.pxe_measure_layout, null, true);

        //测量条
        measureBar = root.findViewById(R.id.pxe_measure_bar);
        measureBar.setOnClickListener(this);

        //方向盘
        PxeSteeringWheel steeringWheel = root.findViewById(R.id.pxe_wheel);
        steeringWheel.setOnWheelTouchListener(this);

        //底部提示
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

    private void updateMeasureBar(String width, String height) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) measureBar.getLayoutParams();
        if (params == null) {
            return;
        }

        if (!TextUtils.isEmpty(width)) {
            try {
                int w = PxeDimensionUtils.dip2px(Integer.valueOf(width));

                if (w > PxeDimensionUtils.getScreenWidth()) {
                    w = FrameLayout.LayoutParams.MATCH_PARENT;
                }
                params.width = w;
            } catch (NumberFormatException e) {
                Log.e("FoodUEMeasureFunction", "invalid width", e);
            }
        }
        if (!TextUtils.isEmpty(height)) {
            try {
                int h = PxeDimensionUtils.dip2px(Integer.valueOf(height));
                if (h > PxeDimensionUtils.getScreenHeight()) {
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
        if (v.getId() == R.id.pxe_measure_bar) {
            if (dialog == null) {
                dialog = new PxeSetValueDialog(v.getContext());
            }
            dialog.setHint(v.getMeasuredWidth(), v.getMeasuredHeight());
            dialog.setOnClickListener(this::updateMeasureBar);
            dialog.show();
        }
    }

    @Override
    public void onDirection(double arc) {
        measureBar.updatePosition(arc);
    }
}
