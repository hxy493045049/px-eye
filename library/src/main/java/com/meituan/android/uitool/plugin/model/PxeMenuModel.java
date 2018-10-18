package com.meituan.android.uitool.plugin.model;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/24 on 下午3:35
 */
public class PxeMenuModel {
    private String title;
    private int imageRes;
    private int type;

    public PxeMenuModel(String title, int imageRes, int type) {
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
