package com.meituan.android.uitool.base.adapter;


import com.meituan.android.uitool.FoodUEToolsActivity;
import com.meituan.android.uitool.base.fragment.PxeLazyBaseFragment;
import com.meituan.android.uitool.base.interfaces.IPxeFunction;
import com.meituan.android.uitool.biz.attr.fragment.PxeAttrFragment;
import com.meituan.android.uitool.biz.color.PxeColorFragment;
import com.meituan.android.uitool.biz.measure.fragment.PxeMeasureFragment;
import com.meituan.android.uitool.biz.mock.fragment.PxeUiMockFragment;
import com.meituan.android.uitool.biz.relative.fragment.PxeRelativeFragment;
import com.meituan.android.uitool.biz.uitest.fragment.PxeUiCheckFragment;
import com.meituan.android.uitool.utils.PxeCollectionUtils;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/9/17 on 上午10:33
 * 构造不同功能的fragment实现
 */
public class PxeFragmentAdapter extends FragmentPagerAdapter {
    private List<Integer> typeList;

    public PxeFragmentAdapter(FragmentManager fm, List<Integer> typeList) {
        super(fm);
        this.typeList = typeList;
    }

    @Override
    public long getItemId(int position) {
        if (PxeCollectionUtils.isEmpty(typeList)) {
            return -1;
        } else {
            //功能对应的类型值
            return typeList.get(position);
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (PxeCollectionUtils.isEmpty(typeList) || position >= typeList.size()) {
            return null;
        }
        @FoodUEToolsActivity.Type
        int type = typeList.get(position);
        PxeLazyBaseFragment fragment = null;
        switch (type) {
            case FoodUEToolsActivity.Type.TYPE_COLOR:
                fragment = PxeColorFragment.newInstance();
                break;
            case FoodUEToolsActivity.Type.TYPE_EDIT_ATTR:
                fragment = PxeAttrFragment.newInstance();
                break;
            case FoodUEToolsActivity.Type.TYPE_MEASURE:
                fragment = PxeMeasureFragment.newInstance();
                break;
            case FoodUEToolsActivity.Type.TYPE_RELATIVE_POSITION:
                fragment = PxeRelativeFragment.newInstance();
                break;
            case FoodUEToolsActivity.Type.TYPE_UI_CHECK:
                fragment = PxeUiCheckFragment.newInstance();
                break;
            case FoodUEToolsActivity.Type.TYPE_MOCK:
                fragment = PxeUiMockFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        int index = -1;
        if (object instanceof IPxeFunction) {
            index = indexOfType(((IPxeFunction) object).getCurrentFunctionType());
        }
        return index == -1 ? POSITION_NONE : index;
    }

    @Override
    public int getCount() {
        return typeList.size();
    }

    private int indexOfType(int id) {
        if (PxeCollectionUtils.isEmpty(typeList)) {
            return -1;
        } else {
            return typeList.indexOf(id);
        }
    }
}
