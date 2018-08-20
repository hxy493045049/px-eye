package com.meituan.android.model;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.meituan.android.biz.element.dialog.FoodUEAttrDialogAdapter;
import com.meituan.android.constant.FoodUEHolderType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午4:41
 * view的属性模型, 子类需要重载{@link FoodUEAttrDialogAdapter.ViewHolderTypeFactory}接口,用于创建不同的ViewHol实现
 * <p> 不同的泛型K,V对应不同的mHolderType</p>
 * <p>
 * {@link AttrType} 区分属性的用途
 * {@link FoodUEHolderType.AttrDialogHolder} 标识渲染这个attr需要哪种viewHolder,默认为{@link FoodUEHolderType.AttrDialogHolder#NORMAL}
 * viewHolder通过{@link com.meituan.android.factory.FoodUEViewHolderFactory}创建
 */
public class FoodUEBaseAttr<K, V> implements FoodUEAttrDialogAdapter.ViewHolderTypeFactory {
    private Pair<K, V> mAttr;
    private WeakReference<FoodUEViewInfo> viewInfoRef;
    @AttrType
    private int mAttrType = AttrType.NONE; //区分属性的用途

    @FoodUEHolderType.AttrDialogHolder
    private int mHolderType = FoodUEHolderType.AttrDialogHolder.NORMAL;//holderType和泛型K,V对应

    public FoodUEBaseAttr(K k, FoodUEViewInfo viewInfo) {
        this(k, null, viewInfo, AttrType.NONE);
    }

    public FoodUEBaseAttr(K k, V v, FoodUEViewInfo viewInfo) {
        this(k, v, viewInfo, AttrType.NONE);
    }

    public FoodUEBaseAttr(K k, V v, FoodUEViewInfo viewInfo, @AttrType int type) {
        this(new Pair<>(k, v), viewInfo, type);
    }

    public FoodUEBaseAttr(Pair<K, V> pair, FoodUEViewInfo viewInfo) {
        this(pair, viewInfo, AttrType.NONE);
    }

    public FoodUEBaseAttr(Pair<K, V> pair, FoodUEViewInfo viewInfo, @AttrType int type) {
        mAttr = pair;
        viewInfoRef = new WeakReference<>(viewInfo);
        mAttrType = type;
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

    public Pair getAttrPair() {
        return mAttr;
    }

    @Nullable
    public FoodUEViewInfo getViewInfo() {
        if (viewInfoRef != null) {
            return viewInfoRef.get();
        }
        return null;
    }

    @AttrType
    public int getAttrType() {
        return mAttrType;
    }

    @Override
    public int getHolderType() {
        return mHolderType;
    }

    @Override
    public void setHolderType(int holderType) {
        this.mHolderType = holderType;
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
        int TYPE_TEXT = 1; //文本
        int TYPE_TEXT_SIZE = 2;//字体大小
        int TYPE_TEXT_COLOR = 3;//字体颜色
        int TYPE_WIDTH = 4;//宽
        int TYPE_HEIGHT = 5;//高
        int TYPE_PADDING_LEFT = 6;
        int TYPE_PADDING_RIGHT = 7;
        int TYPE_PADDING_TOP = 8;
        int TYPE_PADDING_BOTTOM = 9;
    }
}
