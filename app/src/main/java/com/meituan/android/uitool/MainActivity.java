package com.meituan.android.uitool;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwitchCompat switchCompat = findViewById(R.id.switch_button);
        switchCompat.setOnCheckedChangeListener(this);
        FoodUETool.getInstance().setExportEvent(new SubMenuClickEvent() {
            @Override
            public void onClick(Context context) {
                Toast.makeText(context, "退出啦！！！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            FoodUETool.showUETMenu();
        } else {
            FoodUETool.dismissUETMenu();
        }
    }
}
