package com.meituan.android.uitool;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/8/20 on 下午8:29
 */
public class ListViewAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            return convertView;
        }
        return View.inflate(parent.getContext(), R.layout.simple_item, null);
    }
}
