package com.meituan.android.uitool.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.meituan.android.uitool.FoodUEToolsActivity;
import com.meituan.android.uitool.base.interfaces.IPxeFunction;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/9/17 on 下午7:39
 */
public abstract class PxeBaseFunctionFragment extends PxeLazyBaseFragment implements IPxeFunction {
    protected static final String KEY_CURRENT_TYPE = "currentFunctionType";
    @FoodUEToolsActivity.Type
    protected int currentFunctionType = FoodUEToolsActivity.Type.TYPE_RELATIVE_POSITION;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        currentFunctionType = bundle.getInt(KEY_CURRENT_TYPE);
    }

    @Override
    public int getCurrentFunctionType() {
        return currentFunctionType;
    }

    protected static void setArgument(PxeBaseFunctionFragment fragment, @FoodUEToolsActivity.Type int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_CURRENT_TYPE, type);
        fragment.setArguments(bundle);
    }
}
