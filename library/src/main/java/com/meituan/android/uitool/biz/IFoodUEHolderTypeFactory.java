package com.meituan.android.uitool.biz;

import com.meituan.android.uitool.constant.FoodUEHolderType;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/21 on 下午3:50
 */
public interface IFoodUEHolderTypeFactory {
    @FoodUEHolderType.AttrDialogHolder
    int getHolderType();

    void setHolderType(@FoodUEHolderType.AttrDialogHolder int holderType);
}
