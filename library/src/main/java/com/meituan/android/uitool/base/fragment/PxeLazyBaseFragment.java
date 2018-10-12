package com.meituan.android.uitool.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/9/17 on 上午11:33
 */
public abstract class PxeLazyBaseFragment extends PxeBaseFragment {
    protected boolean isCreateView;
    protected boolean isLoadData;

    // 第一次进入ViewPager的时候我们需要直接加载，因为此时setUserVisibleHint 已经调用过了。
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint())
            loadData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        isCreateView = true;
        return v;
    }

    @Override
    protected void loadData() {
        lazyLoad();
        isLoadData = true;
    }

    /**
     * @param isVisibleToUser true if this fragment's UI is currently visible to the user (default),
     *                        false if it is not.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreateView && !isLoadData) {
            loadData();
        } else if (!isVisibleToUser) {
            onInvisible();
        }
    }

    protected void onInvisible() {
    }

    protected abstract void lazyLoad();
}
