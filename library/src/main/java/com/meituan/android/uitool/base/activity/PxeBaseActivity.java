package com.meituan.android.uitool.base.activity;

import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/10/17 on 上午11:50
 */
public abstract class PxeBaseActivity extends AppCompatActivity {
    protected List<Integer> functions;

    /**
     * 配置功能列表,注意顺序
     *
     * @return 功能列表
     */
    protected List<Integer> initFragmentList() {
        List<Integer> types = new ArrayList<>();
        types.add(Type.TYPE_MEASURE, Type.TYPE_MEASURE);
        types.add(Type.TYPE_RELATIVE_POSITION, Type.TYPE_RELATIVE_POSITION);
        types.add(Type.TYPE_EDIT_ATTR, Type.TYPE_EDIT_ATTR);
        types.add(Type.TYPE_COLOR, Type.TYPE_COLOR);
        return types;
    }


    /**
     * 注意, 以下属性的值不能乱填, 必须是连续的, 并且属性的值和menu中的item以及显示的功能顺序相等
     */
    @IntDef({
            Type.TYPE_EDIT_ATTR,
            Type.TYPE_MEASURE,
            Type.TYPE_RELATIVE_POSITION,
            Type.TYPE_COLOR
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        int TYPE_MEASURE = 0;//测量类型
        int TYPE_RELATIVE_POSITION = 1;//相对位置
        int TYPE_EDIT_ATTR = 2;//属性捕捉
        int TYPE_COLOR = 3;//关闭
        int TYPE_EXIT = 4;//关闭
    }
}
