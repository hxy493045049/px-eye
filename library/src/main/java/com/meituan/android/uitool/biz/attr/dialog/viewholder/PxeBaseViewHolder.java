package com.meituan.android.uitool.biz.attr.dialog.viewholder;

import android.view.View;

import com.meituan.android.uitool.biz.attr.dialog.adapter.PxeAttrDialogAdapter;
import com.meituan.android.uitool.biz.attr.dialog.mode.PxeBaseItem;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午4:40
 */
public abstract class PxeBaseViewHolder extends RecyclerView.ViewHolder {

    public PxeBaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract <T extends PxeBaseItem> void onBindViewHolder(T data);

    /**
     * 定义了有哪几种holderType
     */
    @IntDef({
            PxeAttrHolderType.TEXT,
            PxeAttrHolderType.BITMAP,
            PxeAttrHolderType.NONE,
            PxeAttrHolderType.TITLE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PxeAttrHolderType {
        int NONE = 0;//未指定使用哪种viewHolder类型
        int TEXT = 1;//
        int TITLE = 2;//
        int BITMAP = 3;//
    }

    /**
     * 使用这个接口, 是因为在adapter中只能对itemView做点击处理, 要处理子view必须在Holder中内部设置
     */
    public interface IPxeClickableHolder {
        void setOnClickListener(PxeAttrDialogAdapter.OnItemClickListener listener);
    }

    public interface IPxeExpandableHolder extends IPxeClickableHolder {
        boolean onTriggerExpand();

        void enableExpand(boolean flag);

        void setOnExpandedCallBack(PxeExtendableHolder.OnExpandedCallBack callBack);
    }

}