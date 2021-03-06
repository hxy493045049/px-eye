package com.meituan.android.uitool.biz.attr.dialog.viewholder;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.meituan.android.uitool.biz.attr.dialog.viewholder.PxeBaseViewHolder.PxeAttrHolderType;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午4:45
 * {@link androidx.appcompat.widget.RecyclerView.ViewHolder} 创建器
 */
public class PxeViewHolderFactory {
    private PxeViewHolderFactory() {
    }

    public static PxeViewHolderFactory getInstance() {
        return Holder.factory;
    }

    public PxeBaseViewHolder createViewHolder(ViewGroup parent, int type) {
        switch (type) {
            case PxeAttrHolderType.TITLE:
                return new PxeTitleHolder(parent);
            case PxeAttrHolderType.TEXT:
                return new PxeTextHolder(parent);
            case PxeAttrHolderType.BITMAP:
                return new PxeBitmapHolder(new LinearLayout(parent.getContext()));//todo 待完善
            default:
                return new PxeTextHolder(parent);
        }
    }

    private static class Holder {
        private static PxeViewHolderFactory factory = new PxeViewHolderFactory();
    }
}
