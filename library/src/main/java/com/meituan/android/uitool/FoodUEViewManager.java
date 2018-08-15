package com.meituan.android.uitool;

import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.meituan.android.biz.IFoodUEFunction;
import com.meituan.android.biz.element.impl.FoodUEElementFunctionImpl;
import com.meituan.android.biz.measure.FoodUEMeasureFunctionImpl;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/8 on 下午4:46
 */
public class FoodUEViewManager {
    private SparseArray<IFoodUEFunction> factoryCache = new SparseArray<>();

    public View generateView(@FoodUEToolsActivity.Type int type, ViewGroup container) {
        IFoodUEFunction factory = getFunctionByType(type);
        return factory.getView(container);
    }

    public static FoodUEViewManager getInstance() {
        return Holder.instance;
    }

    //------------------------private------------------------
    @NonNull
    private IFoodUEFunction getFunctionByType(@FoodUEToolsActivity.Type int type) {
        IFoodUEFunction factory = factoryCache.get(type);
        if (factory == null) {
            factory = createNewFunction(type);
            factoryCache.put(type, factory);
        }
        return factory;
    }

    @NonNull
    private IFoodUEFunction createNewFunction(@FoodUEToolsActivity.Type int type) {
        IFoodUEFunction factory = null;
        switch (type) {
            case FoodUEToolsActivity.Type.TYPE_EDIT_ATTR:
                factory = new FoodUEElementFunctionImpl();
                break;
            case FoodUEToolsActivity.Type.TYPE_MEASURE:
                factory = new FoodUEMeasureFunctionImpl();
                break;
            case FoodUEToolsActivity.Type.TYPE_RELATIVE_POSITION:
                // TODO: 2018/8/8  
                break;
            case FoodUEToolsActivity.Type.TYPE_UNKNOWN:// TODO: 2018/8/10
            default:
                factory = new FoodUEMeasureFunctionImpl();// TODO: 2018/8/10
                break;
        }
        return factory;//fixme nonnull
    }

    private FoodUEViewManager() {

    }

    private static class Holder {
        private static FoodUEViewManager instance = new FoodUEViewManager();
    }
}
