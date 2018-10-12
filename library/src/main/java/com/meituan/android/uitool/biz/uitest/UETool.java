package com.meituan.android.uitool.biz.uitest;

import java.util.LinkedHashSet;
import java.util.Set;


public class UETool {

    private static volatile UETool instance;
    private Set<String> attrsProviderSet = new LinkedHashSet<String>() {
        {
            add(UETCore.class.getName());
        }
    };

    private UETool() {

    }

    public static UETool getInstance() {
        if (instance == null) {
            synchronized (UETool.class) {
                if (instance == null) {
                    instance = new UETool();
                }
            }
        }
        return instance;
    }
    public Set<String> getAttrsProvider() {
        return attrsProviderSet;
    }

}
