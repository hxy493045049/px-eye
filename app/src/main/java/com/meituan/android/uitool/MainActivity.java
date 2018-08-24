package com.meituan.android.uitool;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.meituan.android.uitool.plugin.FoodUEMenu;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwitchCompat switchCompat = findViewById(R.id.switch_button);
        switchCompat.setOnCheckedChangeListener(this);

        FoodUETool.getInstance(getApplicationContext()).setOnExitListener(new FoodUEMenu.SubMenuClickEvent() {
            @Override
            public void onClick(Context context) {
                Toast.makeText(context, "退出啦！！！", Toast.LENGTH_SHORT).show();
            }
        });

        ListView lv = findViewById(R.id.lv);
        lv.setAdapter(new ListViewAdapter());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            FoodUETool.getInstance(getApplicationContext()).open();
        } else {
            FoodUETool.getInstance(getApplicationContext()).exit();
        }
    }
}
