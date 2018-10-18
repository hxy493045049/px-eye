package com.meituan.android.uitool.biz.attr.fragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.meituan.android.uitool.FoodUEToolsActivity;
import com.meituan.android.uitool.base.behavior.PxeBaseBehavior;
import com.meituan.android.uitool.base.fragment.PxeBaseFunctionFragment;
import com.meituan.android.uitool.base.painter.PxeBasePainter;
import com.meituan.android.uitool.biz.attr.behavior.PxeViewShowBehavior;
import com.meituan.android.uitool.biz.attr.dialog.PxeAttrsDialog;
import com.meituan.android.uitool.helper.PxeActivityRecorder;
import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;
import com.meituan.android.uitool.plugin.PxeBoardTextView;
import com.meituan.android.uitool.plugin.PxeFunctionView;
import com.meituan.android.uitool.plugin.PxeGriddingLayout;
import com.meituan.android.uitool.utils.ApplicationSingleton;
import com.meituan.android.uitool.utils.PxeActivityUtils;
import com.meituan.android.uitool.utils.PxeResourceUtils;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/9/19 on 下午4:21
 */
public class PxeAttrFragment extends PxeBaseFunctionFragment implements PxeBaseBehavior.OnViewInfoSelectedListener {
    private PxeAttrsDialog dialog;

    public static PxeAttrFragment newInstance() {
        PxeAttrFragment fragment = new PxeAttrFragment();
        setArgument(fragment, FoodUEToolsActivity.Type.TYPE_EDIT_ATTR);
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        if (dialog == null) {
            dialog = new PxeAttrsDialog(getActivity());
        }
    }

    @Override
    protected View createContentView() {
        if (getContext() == null) {
            return new LinearLayout(ApplicationSingleton.getApplicationContext());
        }
        View root = LayoutInflater.from(getContext()).inflate(R.layout.pxe_relative_layout, new FrameLayout(getContext()), true);

        PxeViewShowBehavior behavior = new PxeViewShowBehavior(new PxeBasePainter());
        behavior.setViewSelectedListener(this);

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

    @Override
    public void onViewInfoSelected(PxeViewInfo selectedViewInfo) {
        if (PxeActivityUtils.isActivityInvalid(getActivity())) {
            return;
        }
        if (dialog == null) {
            dialog = new PxeAttrsDialog(getActivity());
            dialog.show(selectedViewInfo);
        } else if (dialog.isShowing() || selectedViewInfo == null) {
            dialog.dismiss();
        } else {
            dialog.show(selectedViewInfo);
        }
    }
}
