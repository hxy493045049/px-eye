package com.meituan.android.uitool.model.attr;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.meituan.android.uitool.biz.attr.provider.IPxeHolderTypeFactory;
import com.meituan.android.uitool.constant.PxeHolderType;
import com.meituan.android.uitool.biz.attr.dialog.viewholder.PxeViewHolderFactory;
import com.meituan.android.uitool.model.PxeViewInfo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/13 on 下午4:41
 * view的属性模型, 子类需要重载{@link IPxeHolderTypeFactory}接口,用于创建不同的ViewHolder实现
 * <p> 不同的泛型K,V对应不同的mHolderType</p>
 * <p>
 * {@link AttrType} 区分属性的用途
 * {@link PxeHolderType.AttrDialogHolder} 标识渲染这个attr需要哪种viewHolder,默认为{@link PxeHolderType.AttrDialogHolder#NORMAL}
 * viewHolder通过{@link PxeViewHolderFactory}创建
 */
public abstract class PxeBaseAttr<K, V> implements IPxeHolderTypeFactory {
    private Pair<K, V> mAttr;
    private WeakReference<PxeViewInfo> viewInfoRef;
    @AttrType
    private int mAttrType = AttrType.NONE; //区分属性的用途

    @PxeHolderType.AttrDialogHolder
    private int mHolderType = PxeHolderType.AttrDialogHolder.NONE;//holderType和泛型K,V对应

    public PxeBaseAttr(K k, PxeViewInfo viewInfo) {
        this(k, null, viewInfo, AttrType.NONE);
    }

    public PxeBaseAttr(K k, V v, PxeViewInfo viewInfo) {
        this(k, v, viewInfo, AttrType.NONE);
    }

    public PxeBaseAttr(K k, V v, PxeViewInfo viewInfo, @AttrType int type) {
        this(new Pair<>(k, v), viewInfo, type);
    }

    public PxeBaseAttr(Pair<K, V> pair, PxeViewInfo viewInfo) {
        this(pair, viewInfo, AttrType.NONE);
    }

    public PxeBaseAttr(Pair<K, V> pair, PxeViewInfo viewInfo, @AttrType int type) {
        mAttr = pair;
        viewInfoRef = new WeakReference<>(viewInfo);
        mAttrType = type;
        initHolderType();
    }

    abstract void initHolderType();

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
    public PxeViewInfo getViewInfo() {
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
            AttrType.TYPE_MARGIN_LEFT,
            AttrType.TYPE_MARGIN_RIGHT,
            AttrType.TYPE_MARGIN_TOP,
            AttrType.TYPE_MARGIN_BOTTOM
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface AttrType {
        int NONE = 0; //不指定
        int TYPE_TEXT = 1; //文本
        int TYPE_TEXT_SIZE = 2;//字体大小
        int TYPE_TEXT_COLOR = 3;//字体颜色
        int TYPE_WIDTH = 4;//宽
        int TYPE_HEIGHT = 5;//高
        int TYPE_PADDING_LEFT = 6;
        int TYPE_PADDING_RIGHT = 7;
        int TYPE_PADDING_TOP = 8;
        int TYPE_PADDING_BOTTOM = 9;
        int TYPE_MARGIN_LEFT = 10;
        int TYPE_MARGIN_RIGHT = 11;
        int TYPE_MARGIN_TOP = 12;
        int TYPE_MARGIN_BOTTOM = 13;
    }
}
