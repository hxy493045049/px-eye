package com.meituan.android.uitool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.meituan.android.uitool.base.adapter.PxeFragmentAdapter;
import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.utils.PxeActivityUtils;
import com.meituan.android.uitool.utils.PxeCollectionUtils;
import com.meituan.android.uitool.utils.PxeViewOperator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: shawn
 * Time: 2018/6/19 下午4:03
 */
public class FoodUEToolsActivity extends AppCompatActivity {
    public static final String ACTION = "imeituan://www.meituan.com/food/ui/tool";
    public static final String CURRENT_FUNCTION_TYPE = "functionType";
    //当前正在展示的的功能
    private int mCurrentFunctionType = -1;
    private ViewPager mViewPager;
    private List<Integer> functions;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FoodUETool.getInstance(FoodUETool.getApplicationContext()).release();
        PxeViewOperator.getInstance().reset();
    }

    //-----------------private-----------------

    private void processExtraData(Bundle bundle) {
        if (bundle != null) {
            int type = bundle.getInt(CURRENT_FUNCTION_TYPE);
            if (mCurrentFunctionType == type) {
                //当再次点击相同功能时,关闭
                FoodUETool.getInstance().triggerMenuAnim();
                finish();
            } else {
                if (!PxeCollectionUtils.isEmpty(functions)) {
                    mCurrentFunctionType = type;
                    mViewPager.setCurrentItem(functions.indexOf(mCurrentFunctionType), true);
                }
            }
        }
    }

    /**
     * 配置功能列表,注意顺序
     *
     * @return 功能列表
     */
    private List<Integer> initFragmentList() {
        List<Integer> types = new ArrayList<>();
        types.add(Type.TYPE_MEASURE, Type.TYPE_MEASURE);
        types.add(Type.TYPE_RELATIVE_POSITION, Type.TYPE_RELATIVE_POSITION);
        types.add(Type.TYPE_EDIT_ATTR, Type.TYPE_EDIT_ATTR);
        types.add(Type.TYPE_COLOR, Type.TYPE_COLOR);
        return types;
    }

    private void loadViews() {
        FoodUETool.getInstance().setTargetActivity(PxeActivityUtils.getTargetActivity(false));
        loadingView.setVisibility(View.VISIBLE);
        LoaderManager manager = getSupportLoaderManager();
        manager.restartLoader(LOADER_VIEWS_ID, null, loaderCallback);
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

    private static class SimpleLoader extends AsyncTaskLoader<Void> {
        private SimpleLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            Activity act = FoodUETool.getInstance().getTargetActivity();
            if (!PxeActivityUtils.isActivityInvalid(act)) {
                forceLoad();
            } else {
                deliverResult(null);
            }
        }

        @Override
        public Void loadInBackground() {
            Activity act = FoodUETool.getInstance().getTargetActivity();
            if (!PxeActivityUtils.isActivityInvalid(act)) {
                PxeViewOperator.getInstance().recordViews(act);
            }
            return null;
        }

        @Override
        protected void onStopLoading() {
            cancelLoad();
        }

        @Override
        public void cancelLoadInBackground() {
            PxeViewOperator.getInstance().reset();
        }

        @Override
        protected void onReset() {
            PxeViewOperator.getInstance().reset();
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
