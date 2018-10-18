package com.meituan.android.uitool.biz.attr.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.meituan.android.uitool.biz.attr.dialog.adapter.PxeAttrDialogAdapter;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;
import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.utils.PxeActivityUtils;
import com.meituan.android.uitool.utils.PxeDimensionUtils;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/11 on 下午3:53
 */
public class PxeAttrsDialog extends Dialog {
    private PxeAttrDialogAdapter adapter = new PxeAttrDialogAdapter();
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
    private static final int MARGIN = PxeDimensionUtils.dip2px(55);
    private static final int HEIGHT = PxeDimensionUtils.dip2px(45) * 4;
    private static final int WIDTH = PxeDimensionUtils.getScreenWidth() - MARGIN * 2;
    private static final int WINDOW_VERTICAL_MARGIN = PxeDimensionUtils.dip2px(20);
    private static final int STATUS_BAR = PxeActivityUtils.getStatusBarHeight();
    private static final int SCREEN_HEIGHT = PxeDimensionUtils.getScreenHeight();

    public PxeAttrsDialog(@NonNull Context context) {
        super(context, R.style.Food_UE_Attr_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pxe_attr_dialog);
        RecyclerView list = findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void show(PxeViewInfo viewInfo) {
        show();

        Window dialogWindow = getWindow();
        if (dialogWindow == null) {
            return;
        }
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        if (lp == null) {
            lp = new WindowManager.LayoutParams();
        }
        dialogWindow.setGravity(Gravity.START | Gravity.TOP);

        int bottom = viewInfo.getRect().bottom;
        int top = viewInfo.getRect().top;


        //WINDOW显示的初始坐标是在status_bar下面,
        // 而View的坐标系 KITKAT以上包含了statusBar,KITKAT以下不包含(在viewInfo中已经排除)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            bottom -= STATUS_BAR;
            top -= STATUS_BAR;
        }

        int result = bottom + WINDOW_VERTICAL_MARGIN;
        if (bottom + HEIGHT + WINDOW_VERTICAL_MARGIN > SCREEN_HEIGHT
                && top - WINDOW_VERTICAL_MARGIN < HEIGHT) {
            result = (SCREEN_HEIGHT - HEIGHT) / 2;
        } else if (bottom + HEIGHT + WINDOW_VERTICAL_MARGIN > SCREEN_HEIGHT
                && top - WINDOW_VERTICAL_MARGIN > HEIGHT) {
            result = top - HEIGHT - WINDOW_VERTICAL_MARGIN;
        }


        lp.x = MARGIN;
        lp.y = result;
        lp.width = WIDTH;
        lp.height = HEIGHT;

        dialogWindow.setAttributes(lp);
        adapter.notifyDataSetChanged(viewInfo);
        layoutManager.scrollToPosition(0);

    }
}
