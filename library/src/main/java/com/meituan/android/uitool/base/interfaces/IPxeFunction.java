package com.meituan.android.uitool.base.interfaces;

import com.meituan.android.uitool.FoodUEToolsActivity;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/9/17 on 下午7:37
 */
public interface IPxeFunction {
    /**
     * @return 获取当前功能的类型
     */
    @FoodUEToolsActivity.Type
    int getCurrentFunctionType();
}
