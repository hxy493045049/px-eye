package com.meituan.android.uitool.biz.uitest.base;


/**
 * Created by wenbin on 2018/9/30.
 */

public interface UITestDialogCallback {
    void enableMove();

    void showValidViews(boolean isChecked);

    void selectView(Element element);

    void generateScreenshot(Element element);
}