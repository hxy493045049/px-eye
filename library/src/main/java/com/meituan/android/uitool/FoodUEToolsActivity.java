package com.meituan.android.uitool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.meituan.android.uitool.base.activity.PxeBaseActivity;
import com.meituan.android.uitool.base.adapter.PxeFragmentAdapter;
import com.meituan.android.uitool.helper.PxeActivityRecorder;
import com.meituan.android.uitool.helper.PxeViewRecorder;
import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.utils.PxeActivityUtils;
import com.meituan.android.uitool.utils.PxeCollectionUtils;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.viewpager.widget.ViewPager;

/**
 * Author: shawn
 * Time: 2018/6/19 下午4:03
 */
public class FoodUEToolsActivity extends PxeBaseActivity {
    public static final String ACTION = "imeituan://www.meituan.com/food/pxe";
    public static final String CURRENT_FUNCTION_TYPE = "functionType";
    //当前正在展示的的功能
    private int mCurrentFunctionType = -1;
    private ViewPager mViewPager;
    private View loadingView;

    //loader的id,用于在子线程中加载view信息
    private static final int LOADER_VIEWS_ID = 1;
    private DefaultLoaderCallback loaderCallback = new DefaultLoaderCallback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            //不支持状态恢复, 如果系统异常导致act被销毁, ueTool默认关闭
            finish();
            return;
        }
        PxeActivityUtils.setStatusBarColor(getWindow(), Color.TRANSPARENT);
        PxeActivityUtils.enableFullscreen(getWindow());


        //设置状态栏颜色
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.pxe_light_black));
        }

        setContentView(R.layout.pxe_activity_transparent);
        loadingView = findViewById(R.id.loading_view);
        mViewPager = findViewById(R.id.container);
        functions = initFragmentList();
        PxeFragmentAdapter mAdapter = new PxeFragmentAdapter(getSupportFragmentManager(), functions);
        mViewPager.setAdapter(mAdapter);

        loadViews();
        mCurrentFunctionType = -1;
        processExtraData(getIntent().getExtras());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        processExtraData(intent.getExtras());
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    //-----------------private-----------------
    private void processExtraData(Bundle bundle) {
        FoodUETool.getInstance().triggerMenuAnim();
        if (bundle != null) {
            int type = bundle.getInt(CURRENT_FUNCTION_TYPE);
            if (mCurrentFunctionType == type || PxeCollectionUtils.isEmpty(functions)) {
                //当再次点击相同功能时或没有功能列表(理论上不存在),关闭功能
                finish();
            } else {
                //切换到新功能
                mCurrentFunctionType = type;
                int functionIndex = functions.indexOf(mCurrentFunctionType);
                if (functionIndex >= 0) {
                    mViewPager.setCurrentItem(functionIndex, true);
                } else {
                    //找不到指定功能
                    finish();
                }
            }
        }
    }


    private void loadViews() {
        PxeActivityRecorder.getInstance().setTargetActivity(PxeActivityRecorder.getInstance().getTopActivity(false));
        loadingView.setVisibility(View.VISIBLE);
        LoaderManager manager = getSupportLoaderManager();
        manager.restartLoader(LOADER_VIEWS_ID, null, loaderCallback);
    }

    /**
     * 加载原activity中的所有view到{@link PxeViewRecorder}中
     */
    private static class SimpleLoader extends AsyncTaskLoader<Void> {
        private SimpleLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            Activity act = PxeActivityRecorder.getInstance().getTargetActivity();
            if (!PxeActivityUtils.isActivityInvalid(act)) {
                forceLoad();
            } else {
                deliverResult(null);
            }
        }

        @Override
        public Void loadInBackground() {
            Activity act = PxeActivityRecorder.getInstance().getTargetActivity();
            if (!PxeActivityUtils.isActivityInvalid(act)) {
                PxeViewRecorder.getInstance().recordViews(act);
            }
            return null;
        }

        @Override
        protected void onStopLoading() {
            cancelLoad();
        }

        @Override
        public void cancelLoadInBackground() {
            PxeViewRecorder.getInstance().reset();
        }

        @Override
        protected void onReset() {
            PxeViewRecorder.getInstance().reset();
        }
    }

    private class DefaultLoaderCallback implements LoaderManager.LoaderCallbacks<Void> {

        @Override
        public Loader<Void> onCreateLoader(int id, Bundle args) {
            return new SimpleLoader(FoodUEToolsActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<Void> loader, Void data) {
            if (loadingView != null) {
                loadingView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onLoaderReset(Loader<Void> loader) {
            loader.reset();
        }
    }

}
