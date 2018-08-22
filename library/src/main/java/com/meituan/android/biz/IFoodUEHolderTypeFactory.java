package com.meituan.android.biz;

import com.meituan.android.constant.FoodUEHolderType;

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
