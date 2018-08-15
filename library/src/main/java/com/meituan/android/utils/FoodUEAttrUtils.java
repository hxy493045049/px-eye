package com.meituan.android.utils;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meituan.android.biz.element.impl.FoodUEImageViewProvider;
import com.meituan.android.biz.element.impl.FoodUETextViewProvider;
import com.meituan.android.biz.IFoodUEAttrProvider;

import java.util.WeakHashMap;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午4:59
 */
public class FoodUEAttrUtils {
    private static WeakHashMap<String, IFoodUEAttrProvider> cachedProvider = new WeakHashMap<>();

    public static IFoodUEAttrProvider createAttrs(View view) {
        if (view instanceof TextView) {
            return new FoodUETextViewProvider();
        } else if (view instanceof ImageView) {
            return new FoodUEImageViewProvider();
        }
        return null;
    }

    @Nullable
    public static IFoodUEAttrProvider getCachedProviderByName(String className) {
        IFoodUEAttrProvider provider = cachedProvider.get(className);
        if (provider == null) {
            try {
                provider = (IFoodUEAttrProvider) Class.forName(className).newInstance();
                cachedProvider.put(className, provider);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return provider;
    }
}
