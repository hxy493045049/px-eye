package com.meituan.android.model.attr;

import android.util.Pair;

import com.meituan.android.constant.FoodUEHolderType;
import com.meituan.android.model.FoodUEViewInfo;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/21 on 下午3:56
 */
public class FoodUENormalAttr extends FoodUEBaseAttr<String, String> {
    public FoodUENormalAttr(String s, String s2, FoodUEViewInfo viewInfo, int type) {
        super(s, s2, viewInfo, type);
    }

    public FoodUENormalAttr(String s, FoodUEViewInfo viewInfo) {
        super(s, viewInfo);
    }

    public FoodUENormalAttr(String s, String s2, FoodUEViewInfo viewInfo) {
        super(s, s2, viewInfo);
    }

    @Override
    void initHolderType() {
        setHolderType(FoodUEHolderType.AttrDialogHolder.NORMAL);
    }
}
