package com.meituan.android.uitool.base.activity;

import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;

import com.meituan.android.uitool.FoodUEToolsActivity;

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
     * @return 功能列表
     */
    protected List<Integer> initFragmentList() {
        List<Integer> types = new ArrayList<>();
        types.add(FoodUEToolsActivity.Type.TYPE_MEASURE);
        types.add(FoodUEToolsActivity.Type.TYPE_RELATIVE_POSITION);
        types.add(FoodUEToolsActivity.Type.TYPE_EDIT_ATTR);
        types.add(FoodUEToolsActivity.Type.TYPE_COLOR);
        types.add(FoodUEToolsActivity.Type.TYPE_UI_CHECK);
        types.add(FoodUEToolsActivity.Type.TYPE_MOCK);
        return types;
    }


    @IntDef({
            Type.TYPE_EDIT_ATTR,
            Type.TYPE_MEASURE,
            Type.TYPE_RELATIVE_POSITION,
            Type.TYPE_COLOR,
            Type.TYPE_UI_CHECK,
            Type.TYPE_MOCK
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        int TYPE_MEASURE = 0;//测量类型
        int TYPE_RELATIVE_POSITION = 1;//相对位置
        int TYPE_EDIT_ATTR = 2;//属性捕捉
        int TYPE_COLOR = 3;//取色器
        int TYPE_UI_CHECK = 4;//UI检测
        int TYPE_EXIT = 5;//关闭
        int TYPE_MOCK = 6;//MOCK
    }
}
