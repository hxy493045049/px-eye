package com.meituan.android.uitool.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.meituan.android.uitool.library.R;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/9/17 on 上午10:37
 */
public abstract class PxeBaseFragment extends PxeProgressDialogFragment {
    protected View contentView;
    protected View progressView;
    protected View defaultEmptyView;
    protected View errorEmptyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Context context = getActivity();
        FrameLayout root = new FrameLayout(context);
        contentView = createContentView();
        root.addView(contentView);
        progressView = createProgressView();
        root.addView(progressView);
        defaultEmptyView = createDefaultEmptyView();
        root.addView(defaultEmptyView);
        errorEmptyView = createErrorEmptyView();
        root.addView(errorEmptyView);
        showContentView();
        return root;
    }

    //-------------------abstract method------------------
    protected abstract View createContentView();

    protected abstract void loadData();

    //-------------------protected method------------------

    protected View createProgressView() {
        Context context = getContext();
        return LayoutInflater.from(context).inflate(R.layout.pxe_progress_layout, null);
    }

    protected View createDefaultEmptyView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pxe_empty_view, null);
        ((TextView) view.findViewById(R.id.empty_text)).setText(getEmptyText());
        return view;
    }

    protected View createErrorEmptyView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pxe_error, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressView();
                loadData();
            }
        });
        return view;
    }

    protected void showProgressView() {
        contentView.setVisibility(View.GONE);
        errorEmptyView.setVisibility(View.GONE);
        defaultEmptyView.setVisibility(View.GONE);
        progressView.setVisibility(View.VISIBLE);
    }

    protected void showContentView() {
        progressView.setVisibility(View.GONE);
        errorEmptyView.setVisibility(View.GONE);
        defaultEmptyView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }

    protected void showDefaultEmptyView() {
        progressView.setVisibility(View.GONE);
        contentView.setVisibility(View.GONE);
        errorEmptyView.setVisibility(View.GONE);
        defaultEmptyView.setVisibility(View.VISIBLE);
    }

    protected void showErrorEmptyView() {
        progressView.setVisibility(View.GONE);
        contentView.setVisibility(View.GONE);
        defaultEmptyView.setVisibility(View.GONE);
        errorEmptyView.setVisibility(View.VISIBLE);
    }

    protected CharSequence getEmptyText() {
        return getString(R.string.empty_info);
    }
}
