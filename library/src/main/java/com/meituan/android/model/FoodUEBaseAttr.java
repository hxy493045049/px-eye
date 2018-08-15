package com.meituan.android.model;

import android.support.annotation.IntDef;
import android.util.Pair;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午4:41
 */
public class FoodUEBaseAttr<K, V> {
    private Pair<K, V> mAttr;
    private FoodUEViewInfo mViewInfo;
    @AttrType
    private int mType = AttrType.NONE;

    public FoodUEBaseAttr(K k, V v, FoodUEViewInfo viewInfo) {
        this(k, v, viewInfo, AttrType.NONE);
    }

    public FoodUEBaseAttr(K k, V v, FoodUEViewInfo viewInfo, @AttrType int type) {
        mAttr = new Pair<>(k, v);
        mViewInfo = viewInfo;
        mType = type;
    }

    boolean isValid() {
        return true;
    }

    public K getKey() {
        if (mAttr != null)
            return mAttr.first;
        return null;
    }

    public V getValue() {
        if (mAttr != null) {
            return mAttr.second;
        }
        return null;
    }

    public Pair getAttr() {
        return mAttr;
    }

    @AttrType
    public int getType() {
        return mType;
    }

    @IntDef({
            AttrType.TYPE_TEXT,
            AttrType.TYPE_TEXT_SIZE,
            AttrType.TYPE_TEXT_COLOR,
            AttrType.TYPE_WIDTH,
            AttrType.TYPE_HEIGHT,
            AttrType.TYPE_PADDING_LEFT,
            AttrType.TYPE_PADDING_RIGHT,
            AttrType.TYPE_PADDING_TOP,
            AttrType.TYPE_PADDING_BOTTOM,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface AttrType {
        int NONE = 0;
        int TYPE_TEXT = 1;
        int TYPE_TEXT_SIZE = 2;
        int TYPE_TEXT_COLOR = 3;
        int TYPE_WIDTH = 4;
        int TYPE_HEIGHT = 5;
        int TYPE_PADDING_LEFT = 6;
        int TYPE_PADDING_RIGHT = 7;
        int TYPE_PADDING_TOP = 8;
        int TYPE_PADDING_BOTTOM = 9;
    }
}
