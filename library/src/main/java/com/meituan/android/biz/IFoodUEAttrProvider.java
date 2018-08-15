package com.meituan.android.biz;

import com.meituan.android.model.FoodUEBaseAttr;
import com.meituan.android.model.FoodUEViewInfo;

import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午4:38
 */
public interface IFoodUEAttrProvider {
    List<FoodUEBaseAttr> getAttrs(FoodUEViewInfo viewInfo);
}
