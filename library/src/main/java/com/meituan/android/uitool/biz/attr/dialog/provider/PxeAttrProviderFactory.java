package com.meituan.android.uitool.biz.attr.dialog.provider;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meituan.android.uitool.biz.attr.dialog.provider.impl.PxeImageViewProvider;
import com.meituan.android.uitool.biz.attr.dialog.provider.impl.PxeTextViewProvider;
import com.meituan.android.uitool.helper.mode.PxeViewInfo;

import java.util.WeakHashMap;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午4:59
 */
public class PxeAttrProviderFactory {
    private static WeakHashMap<String, IPxeItemsProvider> cachedProvider = new WeakHashMap<>();

    public static IPxeItemsProvider getAttrProviderByView(PxeViewInfo viewInfo) {
        View view = viewInfo.getView();
        if (view instanceof TextView) {
            return getCachedProviderByName(PxeTextViewProvider.class.getName());
        } else if (view instanceof ImageView) {
            return getCachedProviderByName(PxeImageViewProvider.class.getName());
        }
        return null;
    }

    @Nullable
    public static IPxeItemsProvider getCachedProviderByName(String className) {
        IPxeItemsProvider provider = cachedProvider.get(className);
        if (provider == null) {
            try {
                provider = (IPxeItemsProvider) Class.forName(className).newInstance();
                cachedProvider.put(className, provider);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return provider;
    }
}
