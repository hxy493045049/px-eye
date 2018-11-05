package com.meituan.android.uitool.biz.attr.dialog.viewholder;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午5:25
 */
public interface PxeHolderType {
    @IntDef({
            AttrDialogHolder.NORMAL,
            AttrDialogHolder.BITMAP,
            AttrDialogHolder.NONE,
            AttrDialogHolder.TITLE
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface AttrDialogHolder {
        int NONE = 0;//为指定泛型类型
        int NORMAL = 1;//
        int TITLE = 2;//
        int BITMAP = 3;//
    }
}