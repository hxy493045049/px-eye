package com.meituan.android.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午5:25
 */
public class FoodUEHolderType {
    @IntDef({
            AttrDialogHolder.NORMAL,
            AttrDialogHolder.BITMAP
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface AttrDialogHolder {
        int NORMAL = 1;//对应FoodUEBaseAttr的泛型为<String,String>
        int BITMAP = 2;//<String,Bitmap>
    }
}
