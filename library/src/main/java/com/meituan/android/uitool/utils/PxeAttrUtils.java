package com.meituan.android.uitool.utils;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meituan.android.uitool.biz.attr.provider.IPxeAttrProvider;
import com.meituan.android.uitool.biz.attr.provider.impl.PxeImageViewProvider;
import com.meituan.android.uitool.biz.attr.provider.impl.PxeTextViewProvider;

import java.util.WeakHashMap;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午4:59
 */
public class PxeAttrUtils {
    private static WeakHashMap<String, IPxeAttrProvider> cachedProvider = new WeakHashMap<>();

    public static IPxeAttrProvider createAttrs(View view) {
        if (view instanceof TextView) {
            return new PxeTextViewProvider();
        } else if (view instanceof ImageView) {
            return new PxeImageViewProvider();
        }
        return null;
    }

    @Nullable
    public static IPxeAttrProvider getCachedProviderByName(String className) {
        IPxeAttrProvider provider = cachedProvider.get(className);
        if (provider == null) {
            try {
                provider = (IPxeAttrProvider) Class.forName(className).newInstance();
                cachedProvider.put(className, provider);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return provider;
    }
}
