package com.meituan.android.biz.element;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.meituan.android.model.FoodUEViewInfo;
import com.meituan.android.uitool.library.R;
import com.meituan.android.utils.FoodUEDimensionUtils;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/11 on 下午3:53
 */
public class FoodUEAttrsDialog extends Dialog {
    private FoodUEAttrDialogAdapter adapter = new FoodUEAttrDialogAdapter();
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
    private static final int MARGIN = FoodUEDimensionUtils.dip2px(55);
    private static final int HEIGHT = FoodUEDimensionUtils.dip2px(45) * 4;
    private static final int WIDTH = FoodUEDimensionUtils.getScreenWidth() - MARGIN * 2;

    public FoodUEAttrsDialog(@NonNull Context context) {
        super(context, R.style.Food_UE_Attr_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_ue_attr_dialog);
        RecyclerView list = findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setLayoutManager(layoutManager);
    }

    public void show(FoodUEViewInfo viewInfo) {
        show();
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.START | Gravity.TOP);

        int y = viewInfo.getRect().bottom;
        if (viewInfo.getRect().bottom + HEIGHT > FoodUEDimensionUtils.getScreenHeight()
                && viewInfo.getRect().top < HEIGHT) {
            y = (FoodUEDimensionUtils.getScreenHeight() - HEIGHT) / 2;
        } else if (viewInfo.getRect().bottom + HEIGHT > FoodUEDimensionUtils.getScreenHeight()
                && viewInfo.getRect().top > HEIGHT) {
            y = viewInfo.getRect().top - HEIGHT;
        }

        lp.x = MARGIN;
        lp.y = y;
        lp.width = WIDTH;
        lp.height = HEIGHT;

        dialogWindow.setAttributes(lp);
        adapter.notifyDataSetChanged(viewInfo);
        layoutManager.scrollToPosition(0);
    }
}
