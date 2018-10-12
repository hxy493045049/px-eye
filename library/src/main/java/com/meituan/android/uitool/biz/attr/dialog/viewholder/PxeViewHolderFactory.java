package com.meituan.android.uitool.biz.attr.dialog.viewholder;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.meituan.android.uitool.model.PxeBaseViewHolder;

import static com.meituan.android.uitool.constant.PxeHolderType.AttrDialogHolder;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午4:45
 * {@link android.support.v7.widget.RecyclerView.ViewHolder} 创建器
 */
public class PxeViewHolderFactory {
    private PxeViewHolderFactory() {
    }

    public static PxeViewHolderFactory getInstance() {
        return Holder.factory;
    }

    public PxeBaseViewHolder createViewHolder(ViewGroup parent, int type) {
        switch (type) {
            case AttrDialogHolder.TITLE:
                return new PxeTitleHolder(parent);
            case AttrDialogHolder.NORMAL:
                return new PxeNormalHolder(parent);
            case AttrDialogHolder.BITMAP:
                return new PxeBitmapHolder(new LinearLayout(parent.getContext()));//todo 待完善
            default:
                return new PxeNormalHolder(parent);
        }
    }

    private static class Holder {
        private static PxeViewHolderFactory factory = new PxeViewHolderFactory();
    }
}
