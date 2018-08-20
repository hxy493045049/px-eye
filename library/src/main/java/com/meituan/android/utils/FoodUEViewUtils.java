package com.meituan.android.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.LinearGradient;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.text.SpannedString;
import android.text.style.ImageSpan;
import android.util.Pair;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.meituan.android.model.FoodUEViewInfo;
import com.meituan.android.uitool.library.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/9 on 下午4:46
 */
public class FoodUEViewUtils {

    /**
     * 获取挡墙view的中心点
     *
     * @param view 要测量的view
     * @return view的中心点坐标
     */
    public static int[] getCenter(View view) {
        Rect rect = new Rect();
        view.getLocalVisibleRect(rect);
        int[] center = new int[2];
        center[0] = rect.right / 2;
        center[1] = rect.bottom / 2;
        return center;
    }

    /**
     * @return 获取目标Activity中所有的view信息集合, 如果activity不存在返回null
     */
    @SuppressLint("PrivateApi")
    @SuppressWarnings("unchecked")
    @Nullable
    public static List<FoodUEViewInfo> getTargetActivityViews(Activity targetActivity) {
        if (targetActivity == null || targetActivity.isFinishing()) {
            return null;
        }
        try {
            List<FoodUEViewInfo> viewsInfo = new ArrayList<>();
            WindowManager windowManager = targetActivity.getWindowManager();
            Field mGlobalField = Class.forName("android.view.WindowManagerImpl").getDeclaredField("mGlobal");
            mGlobalField.setAccessible(true);
            Object global = mGlobalField.get(windowManager);

            //fixme 这里很奇怪, 我看api23源码中也有views字段, 但是为什么判断到23及以下呢
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                Field mViewsField = Class.forName("android.view.WindowManagerGlobal").getDeclaredField("mViews");
                mViewsField.setAccessible(true);
                List<View> views = (List<View>) mViewsField.get(global);//global中的mViews保存的是decorView
                for (int i = views.size() - 1; i >= 0; i--) {
                    View targetView = isDecorViewInTargetActivity(targetActivity, views.get(i));
                    if (targetView != null) {
                        traverseRecordViews(targetView, viewsInfo);
                        break;
                    }
                }
            } else {//API大于23
                //获取WindowManagerGlobal中的viewRootImpl集合
                Field mViewRootImplField = Class.forName("android.view.WindowManagerGlobal").getDeclaredField("mRoots");
                mViewRootImplField.setAccessible(true);
                List viewRootsImpl = (List) mViewRootImplField.get(mGlobalField.get(windowManager));
                for (int i = viewRootsImpl.size() - 1; i >= 0; i--) {
                    Object viewRootImpl = viewRootsImpl.get(i);//获取viewRootImpl对象

                    //获取viewRootImpl中的WindowManager.LayoutParams
                    Class viewRootImplClz = Class.forName("android.view.ViewRootImpl");
                    Field mWindowAttributesField = viewRootImplClz.getDeclaredField("mWindowAttributes");
                    mWindowAttributesField.setAccessible(true);
                    WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) mWindowAttributesField.get(viewRootImpl);

                    //ViewRootImpl中的mView就是DecorView
                    Field mViewField = viewRootImplClz.getDeclaredField("mView");
                    mViewField.setAccessible(true);
                    View decorView = (View) mViewField.get(viewRootImpl);

                    //如果当前viewRootImpl中的LayoutParams中存在该activity的名称,则匹配
                    //或者找到的decorView能在目标activity中对应上,则匹配
                    if (layoutParams.getTitle().toString().contains(targetActivity.getClass().getName())
                            || isDecorViewInTargetActivity(targetActivity, decorView) != null) {
                        traverseRecordViews(decorView, viewsInfo);
                        break;
                    }
                }
            }
            return viewsInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 迭代view,将它和它的子view封装成ViewInfo添加到集合中
     * 如果view设置了ignoreTag并且值为true则会过滤
     * 如果view不可见或者透明度为0,则过滤
     * fixme 当view的可见性动态变化后如何再添加
     *
     * @param view     目标view
     * @param viewInfo 集合
     */
    public static void traverseRecordViews(View view, List<? super FoodUEViewInfo> viewInfo) {
        if (view == null) {
            return;
        }
        Object ignoreTag;
        //如果view设置了ignore标签,则过滤它和它的子view
        if ((ignoreTag = view.getTag(R.id.food_ui_tools_ignore)) != null && ((Boolean) ignoreTag)) {
            return;
        }

        if (view.getAlpha() == 0 || view.getVisibility() != View.VISIBLE) {
            return;
        }
        viewInfo.add(new FoodUEViewInfo(view));
        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            for (int i = 0; i < parent.getChildCount(); i++) {
                traverseRecordViews(parent.getChildAt(i), viewInfo);
            }
        }
    }

    /**
     * 根据view中的context,判断view是否存在于指定的activity中,
     * 如果decorView存在于dialog中,dialog的context必须是targetActivity对象,否则无法捕捉
     * 理论上无法响应系统window
     *
     * @param targetActivity 目标activity
     * @param decorView      decorView
     * @return 如果在目标activity中, 返回view, 反之返回null
     */
    @Nullable
    public static View isDecorViewInTargetActivity(Activity targetActivity, View decorView) {
        View targetView = null;
        Context context = decorView.getContext();
        if (context == targetActivity) {
            targetView = decorView;
        } else {
            while (context instanceof ContextThemeWrapper) {
                Context baseContext = ((ContextThemeWrapper) context).getBaseContext();
                if (baseContext == targetActivity) {
                    targetView = decorView;
                    break;
                }
                context = baseContext;
            }
        }
        return targetView;
    }

    /**
     * 判断目标{@link FoodUEViewInfo} 中的位置,并向上迭代其父类, 判断可见性
     *
     * @param target 目标 viewInfo
     * @return 不可见返回true, 可见返回true
     */
    public static boolean isViewInfoNotVisible(FoodUEViewInfo target) {
        if (target == null) {
            return true;
        }
        if (target.getRect().left >= FoodUEDimensionUtils.getScreenWidth()
                || target.getRect().top >= FoodUEDimensionUtils.getScreenHeight()) {
            return true;
        } else {//屏幕之内
            return target.hasParentViewInfo() && isViewInfoNotVisible(target.getParentViewInfo());
        }
    }

    public static Object getBackground(View view) {
        Drawable drawable = view.getBackground();
        if (drawable instanceof ColorDrawable) {
            return FoodUEResourceUtils.intToHexColor(((ColorDrawable) drawable).getColor());
        } else if (drawable instanceof GradientDrawable) {
            try {
                Field mFillPaintField = GradientDrawable.class.getDeclaredField("mFillPaint");
                mFillPaintField.setAccessible(true);
                Paint mFillPaint = (Paint) mFillPaintField.get(drawable);
                Shader shader = mFillPaint.getShader();
                if (shader instanceof LinearGradient) {
                    Field mColorsField = LinearGradient.class.getDeclaredField("mColors");
                    mColorsField.setAccessible(true);
                    int[] mColors = (int[]) mColorsField.get(shader);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0, N = mColors.length; i < N; i++) {
                        sb.append(FoodUEResourceUtils.intToHexColor(mColors[i]));
                        if (i < N - 1) {
                            sb.append(" -> ");
                        }
                    }
                    return sb.toString();
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            return getDrawableBitmap(drawable);
        }
        return null;
    }

    public static List<Pair<String, Bitmap>> getTextViewBitmap(TextView textView) {
        List<Pair<String, Bitmap>> bitmaps = new ArrayList<>();
        bitmaps.addAll(getTextViewDrawableBitmap(textView));
        bitmaps.addAll(getTextViewImageSpanBitmap(textView));
        return bitmaps;
    }

    public static List<Pair<String, Bitmap>> getTextViewDrawableBitmap(TextView textView) {
        List<Pair<String, Bitmap>> bitmaps = new ArrayList<>();
        try {
            Drawable[] drawables = textView.getCompoundDrawables();
            if (getDrawableBitmap(drawables[0]) != null) {
                bitmaps.add(new Pair<>("DrawableLeft", getDrawableBitmap(drawables[0])));
            }
            if (getDrawableBitmap(drawables[1]) != null) {
                bitmaps.add(new Pair<>("DrawableTop", getDrawableBitmap(drawables[1])));
            }
            if (getDrawableBitmap(drawables[2]) != null) {
                bitmaps.add(new Pair<>("DrawableRight", getDrawableBitmap(drawables[2])));
            }
            if (getDrawableBitmap(drawables[3]) != null) {
                bitmaps.add(new Pair<>("DrawableBottom", getDrawableBitmap(drawables[3])));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmaps;
    }

    public static List<Pair<String, Bitmap>> getTextViewImageSpanBitmap(TextView textView) {
        List<Pair<String, Bitmap>> bitmaps = new ArrayList<>();
        try {
            CharSequence text = textView.getText();
            if (text instanceof SpannedString) {
                Field mSpansField = Class.forName("android.text.SpannableStringInternal").getDeclaredField("mSpans");
                mSpansField.setAccessible(true);
                Object[] spans = (Object[]) mSpansField.get(text);
                for (Object span : spans) {
                    if (span instanceof ImageSpan) {
                        bitmaps.add(new Pair<>("SpanBitmap", getDrawableBitmap(((ImageSpan) span).getDrawable())));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmaps;
    }

    public static Bitmap getImageViewBitmap(ImageView imageView) {
        return getDrawableBitmap(imageView.getDrawable());
    }

    public static Bitmap getDrawableBitmap(Drawable drawable) {
        try {
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            } else if (drawable instanceof NinePatchDrawable) {
                NinePatch ninePatch = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Field mNinePatchStateFiled = NinePatchDrawable.class.getDeclaredField("mNinePatchState");
                    mNinePatchStateFiled.setAccessible(true);
                    Object mNinePatchState = mNinePatchStateFiled.get(drawable);
                    Field mNinePatchFiled = mNinePatchState.getClass().getDeclaredField("mNinePatch");
                    mNinePatchFiled.setAccessible(true);
                    ninePatch = (NinePatch) mNinePatchFiled.get(mNinePatchState);
                    return ninePatch.getBitmap();
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Field mNinePatchFiled = NinePatchDrawable.class.getDeclaredField("mNinePatch");
                    mNinePatchFiled.setAccessible(true);
                    ninePatch = (NinePatch) mNinePatchFiled.get(drawable);
                    return ninePatch.getBitmap();
                }
            } else if (drawable instanceof ClipDrawable) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return ((BitmapDrawable) ((ClipDrawable) drawable).getDrawable()).getBitmap();
                }
            } else if (drawable instanceof StateListDrawable) {
                return ((BitmapDrawable) drawable.getCurrent()).getBitmap();
            } else if (drawable instanceof VectorDrawableCompat) {
                Field mVectorStateField = VectorDrawableCompat.class.getDeclaredField("mVectorState");
                mVectorStateField.setAccessible(true);
                Field mCachedBitmapField = Class.forName("android.support.graphics.drawable.VectorDrawableCompat$VectorDrawableCompatState").getDeclaredField("mCachedBitmap");
                mCachedBitmapField.setAccessible(true);
                return (Bitmap) mCachedBitmapField.get(mVectorStateField.get(drawable));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getImageViewScaleType(ImageView imageView) {
        return imageView.getScaleType().name();
    }
}
