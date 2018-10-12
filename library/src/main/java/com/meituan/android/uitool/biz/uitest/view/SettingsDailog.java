package com.meituan.android.uitool.biz.uitest.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.meituan.android.uitool.biz.uitest.utils.DataManager;
import com.meituan.android.uitool.library.R;


/**
 * Created by wenbin on 2018/10/8.
 */

public class SettingsDailog {

    public static void showDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("设置");
        View view = LayoutInflater.from(context).inflate(R.layout.uet_settings_dialog,null);
        final EditText userName = view.findViewById(R.id.uetMisId);
        final EditText uetDensityView = view.findViewById(R.id.etUetDensity);
        userName.setText(DataManager.getUser());
        uetDensityView.setText(DataManager.getDensity()+"");
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataManager.saveUser(userName.getText().toString());

                DataManager.saveDensity(uetDensityView.getText().toString());

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
