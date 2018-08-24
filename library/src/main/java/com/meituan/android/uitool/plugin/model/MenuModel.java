package com.meituan.android.uitool.plugin.model;

import com.meituan.android.uitool.FoodUEToolsActivity;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/24 on 下午3:35
 */
public class MenuModel {
    private String title;
    private int imageRes;
    @FoodUEToolsActivity.Type
    private int type;

    public MenuModel(String title, int imageRes, @FoodUEToolsActivity.Type int type) {
        this.title = title;
        this.imageRes = imageRes;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public int getImageRes() {
        return imageRes;
    }

    public int getType() {
        return type;
    }
}
