package com.meituan.android.uitool.biz.mock.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.meituan.android.uitool.FoodUEToolsActivity;
import com.meituan.android.uitool.base.fragment.PxeBaseFunctionFragment;
import com.meituan.android.uitool.base.fragment.PxeLazyBaseFragment;
import com.meituan.android.uitool.biz.mock.PxeMockBean;
import com.meituan.android.uitool.biz.mock.PxeMockPrefs;
import com.meituan.android.uitool.biz.mock.adapter.PxeMockDetailAdapter;
import com.meituan.android.uitool.biz.mock.adapter.PxeVersionAdapter;
import com.meituan.android.uitool.biz.mock.structure.PxeFixedLinkedList;
import com.meituan.android.uitool.library.R;
import com.meituan.android.uitool.utils.ApplicationSingleton;
import com.meituan.android.uitool.utils.PxeActivityUtils;
import com.meituan.android.uitool.utils.PxeCollectionUtils;
import com.meituan.android.uitool.utils.PxeDimensionUtils;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/8 on 下午3:33
 */
public class PxeUiMockFragment extends PxeBaseFunctionFragment implements PxeVersionAdapter.OnVersionSelectedListener, PxeMockDetailAdapter.OnCaseSelectedListener {
    private PxeFixedLinkedList<String> cache = new PxeFixedLinkedList<>(10);
//    private CallLoaderCallbacks<PxeMockBean> mockDataCallBack;
    private PxeMockBean mockData;
    private PxeVersionAdapter versionAdapter;
    private PxeMockDetailAdapter detailAdapter;
    private TextView tipsView;
    private LinearLayout mockContainer;

    @Override
    protected void lazyLoad() {
//        showProgressView();
//        if (mockDataCallBack == null) {
//            mockDataCallBack = getDataCallBack();
//        }
//        getLoaderManager().restartLoader(PxeLoaderIdUtils.IdList.PXE_MOCK_INFO, null, mockDataCallBack);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        if (v != null) {
            v.setBackgroundColor(getResources().getColor(R.color.pxe_white));
        }
        return v;
    }

    @Override
    protected View createContentView() {
        if (getContext() == null) {
            return new LinearLayout(ApplicationSingleton.getApplicationContext());
        }
        View root = LayoutInflater.from(getContext()).inflate(R.layout.pxe_mock_layout, null, false);
        tipsView = root.findViewById(R.id.tv_mock_hint);
        ViewGroup.MarginLayoutParams margin = (ViewGroup.MarginLayoutParams) tipsView.getLayoutParams();
        margin.topMargin = PxeActivityUtils.getStatusBarHeight() + PxeDimensionUtils.dip2px(10);

        AutoCompleteTextView textView = root.findViewById(R.id.edit_mock_mis);
        List<String> old = PxeMockPrefs.get(getContext());
        if (!PxeCollectionUtils.isEmpty(old)) {
            cache.addAll(old);
        }

        //todo 自动将上一次的记录写到tv中
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, cache);
        textView.setAdapter(adapter);

        mockContainer = root.findViewById(R.id.mock_container);
        // TODO: 2018/11/8 还需要将mis号记录到cache中
        Switch sw = root.findViewById(R.id.switch_mock);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO: 2018/11/8 打开mock开关
                mockContainer.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });

        RecyclerView rvVersion = root.findViewById(R.id.rv_mock_version);
        versionAdapter = new PxeVersionAdapter();
        versionAdapter.setOnVersionSelectedListener(this);
        rvVersion.setAdapter(versionAdapter);
        rvVersion.setHasFixedSize(true);
        rvVersion.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView rvDetail = root.findViewById(R.id.rv_mock_detail);
        detailAdapter = new PxeMockDetailAdapter();
        detailAdapter.setOnItemSelectedListener(this);
        rvDetail.setAdapter(detailAdapter);
        rvDetail.setHasFixedSize(true);
        rvDetail.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    public static PxeLazyBaseFragment newInstance() {
        PxeUiMockFragment fragment = new PxeUiMockFragment();
        setArgument(fragment, FoodUEToolsActivity.Type.TYPE_MOCK);
        return fragment;
    }

//    private CallLoaderCallbacks<PxeMockBean> getDataCallBack() {
//        return new CallLoaderCallbacks<PxeMockBean>(getContext()) {
//            @Override
//            public Call<PxeMockBean> onCreateCall(int id, Bundle args) {
//                return PxeRetrofits.getInstances().getMockInfo();
//            }
//
//            @Override
//            public void onSuccess(Loader loader, PxeMockBean data) {
//                mockData = data;
//                onDataSuccess();
//            }
//
//            @Override
//            public void onFailure(Loader loader, Throwable throwable) {
//                showErrorEmptyView();
//            }
//        };
//    }

    /**
     * 成功收到服务器数据,展示ui
     */
    private void onDataSuccess() {
        if (mockData == null || mockData.data == null || PxeCollectionUtils.isEmpty(mockData.data.mockData)) {
            showDefaultEmptyView();
            return;
        }
        if (tipsView != null) {
            tipsView.setText(mockData.data.tips);
        }

        if (versionAdapter != null) {
            versionAdapter.notifyDataChange(mockData);
        }
        if (detailAdapter != null && !PxeCollectionUtils.isEmpty(mockData.data.mockData)) {
            detailAdapter.notifyDataChange(mockData.data.mockData.get(0).caseX);
        }
        showContentView();
    }

    @Override
    public void onCaseSelected(PxeMockBean.CaseBean caseBean) {
        // TODO: 2018/11/15

    }

    @Override
    public void onVersionSelected(PxeMockBean.MockDataBean versionBean) {
        if (detailAdapter != null && versionBean != null) {
            detailAdapter.notifyDataChange(versionBean.caseX);
        }
    }
}
