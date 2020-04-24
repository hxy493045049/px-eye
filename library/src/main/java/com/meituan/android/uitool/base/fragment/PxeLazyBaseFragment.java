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
        if (getUserVisibleHint()) {
            loadData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        isCreateView = true;
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //view被销毁后,重新刷新数据
        isCreateView = false;
        isLoadData = false;
    }

    @Override
    protected void loadData() {
        lazyLoad();
        isLoadData = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //view创建完毕 并且 用户可见 并且 还未加载数据
        if (isVisibleToUser && isCreateView && !isLoadData) {
            loadData();
        } else if (!isVisibleToUser) {
            onInvisible();
        }
    }

    protected void onInvisible() {
    }

    /**
     * 这个方法会确保在view创建完毕后才调用
     */
    protected abstract void lazyLoad();
}
