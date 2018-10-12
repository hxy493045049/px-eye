package com.meituan.android.uitool.base.fragment;

import android.app.ProgressDialog;
import android.util.Log;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/9/17 on 上午10:51
 */
public class PxeProgressDialogFragment extends PxeActionbarFragment {
    protected ProgressDialog progressDialog;

    protected void showProgressDialog(int res) {
        progressDialog = ProgressDialog.show(getActivity(), "", getString(res));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    protected void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing() && isAdded()) {
            // fragment销毁重建后，再dismiss dialog会出错；
            // 如果销毁时dismiss重建后不方便重新showDialog，直接try-catch吧
            try {
                progressDialog.dismiss();
            } catch (IllegalArgumentException e) {
                Log.e("progressDialog", e.getMessage());
            }
        }
    }
}
