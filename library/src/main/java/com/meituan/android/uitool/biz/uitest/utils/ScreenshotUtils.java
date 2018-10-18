package com.meituan.android.uitool.biz.uitest.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import com.meituan.android.uitool.biz.uitest.base.UiTestUploadResult;
import com.meituan.android.uitool.biz.uitest.base.Element;

import java.io.ByteArrayOutputStream;


public class ScreenshotUtils {
    public static void shotViewAndUpload(final Context context, String user, View view, final Rect rect) {
        Bitmap bitmap = getCacheBitmap(view, rect);
        bitmap = compressBitmap(context,bitmap);
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            NetWorkUtils.uploadImage(byteArrayOutputStream.toByteArray(), user, new NetWorkUtils.OnResponse() {
                @Override
                public void onSuccess(UiTestUploadResult result) {
                    if (result != null) {
                        Toast.makeText(context,result.message,Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailue() {
                    Toast.makeText(context,"网络请求失败",Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(context, "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    private static Bitmap getCacheBitmap(View rootView, Rect rect) {
        rootView.setDrawingCacheEnabled(true);
        rootView.buildDrawingCache(true);
        Bitmap drawingCache = rootView.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            Element element = new Element(rootView);
            Rect rootViewRect = element.getRect();
            rect.left -= rootViewRect.left;
            rect.right -= rootViewRect.left;
            rect.top -= rootViewRect.top;
            rect.bottom -= rootViewRect.top;
            if (rect.left < 0) {
                rect.left = 0;
            }
            if (rect.right > drawingCache.getWidth()) {
                rect.right = drawingCache.getWidth();
            }
            if (rect.top < 0) {
                rect.top = 0;
            }
            if (rect.bottom > drawingCache.getHeight()) {
                rect.bottom = drawingCache.getHeight();
            }
            bitmap = Bitmap.createBitmap(drawingCache, rect.left, rect.top, rect.width(), rect.height());
            rootView.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

    /**
     * 等比例压缩图片
     * @param context activity
     * @param resBitmap 原图片bitmap
     * @return
     */
    private static Bitmap compressBitmap(Context context,Bitmap resBitmap) {

        if (resBitmap == null || !(context instanceof Activity)) {
            return resBitmap;
        }

        Display display = ((Activity)context).getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        float scale = 750.0f/displayMetrics.widthPixels;
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale);
        return Bitmap.createBitmap(resBitmap,0,0,resBitmap.getWidth(),resBitmap.getHeight(),matrix,true);

    }
}