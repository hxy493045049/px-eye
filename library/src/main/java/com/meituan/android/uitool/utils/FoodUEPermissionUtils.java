package com.meituan.android.uitool.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/7 on 上午11:49
 */
public class FoodUEPermissionUtils {
    @TargetApi(Build.VERSION_CODES.M)
    public static void requestOverlayPermission(Context context) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
