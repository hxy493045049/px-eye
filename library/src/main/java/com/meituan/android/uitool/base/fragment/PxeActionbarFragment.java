package com.meituan.android.uitool.base.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/9/17 on 上午10:37
 */
public class PxeActionbarFragment extends Fragment {
    public AppCompatActivity getActionBarActivity() {
        return (AppCompatActivity) getActivity();
    }

    public ActionBar getActionBar() {
        return getActionBarActivity().getSupportActionBar();
    }

    public void startActionMode(ActionMode.Callback callback) {
        if (getActionBarActivity() != null) {
            getActionBarActivity().startSupportActionMode(callback);
        }
    }

    public void setTitle(String title) {
        if (getActionBar() != null) {
            getActionBar().setTitle(title);
        }
    }

    public void setTitle(int resId) {
        if (getActionBar() != null) {
            getActionBar().setTitle(resId);
        }
    }

    public void invalidateOptionsMenu() {
        if (getActionBarActivity() != null) {
            getActionBarActivity().supportInvalidateOptionsMenu();
        }
    }
}
