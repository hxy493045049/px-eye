package com.meituan.android.uitool.biz.mock.popwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.PopupWindow;

import com.meituan.android.uitool.library.R;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/15 on 下午4:57
 */
public class PxeMockChooseWindow extends PopupWindow {
    public static PxeMockChooseWindow newInstance(Context ctx) {
        PxeMockChooseWindow pop = new PxeMockChooseWindow(ctx);
        return pop;
    }

    public PxeMockChooseWindow(Context ctx) {
        LayoutInflater.from(ctx).inflate(R.layout.pxe_recycler_view_only, null, false);
    }
}
