package com.meituan.android.uitool.factory;

import android.view.ViewGroup;

import com.meituan.android.uitool.biz.element.dialog.viewholder.FoodUEBitmapHolder;
import com.meituan.android.uitool.biz.element.dialog.viewholder.FoodUENormalHolder;
import com.meituan.android.uitool.biz.element.dialog.viewholder.FoodUETitleHolder;
import com.meituan.android.uitool.model.FoodUEBaseViewHolder;

import static com.meituan.android.uitool.constant.FoodUEHolderType.AttrDialogHolder;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午4:45
 * {@link android.support.v7.widget.RecyclerView.ViewHolder} 创建器
 */
public class FoodUEViewHolderFactory {
    private FoodUEViewHolderFactory() {
    }

    public static FoodUEViewHolderFactory getInstance() {
        return Holder.factory;
    }

    public FoodUEBaseViewHolder createViewHolder(ViewGroup parent, int type) {
        switch (type) {
            case AttrDialogHolder.TITLE:
                return new FoodUETitleHolder(parent);
            case AttrDialogHolder.NORMAL:
                return new FoodUENormalHolder(parent);
            case AttrDialogHolder.BITMAP:
                return new FoodUEBitmapHolder(null);//todo 待完善
            default:
                return new FoodUENormalHolder(parent);
        }
    }

    private static class Holder {
        private static FoodUEViewHolderFactory factory = new FoodUEViewHolderFactory();
    }
}
