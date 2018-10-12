package com.meituan.android.uitool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.meituan.android.uitool.plugin.PxeMenu;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwitchCompat switchCompat = findViewById(R.id.switch_button);
        switchCompat.setOnCheckedChangeListener(this);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        FoodUETool.getInstance(getApplicationContext()).setOnExitListener(new PxeMenu.SubMenuClickEvent() {
            @Override
            public void onClick(Context context) {
                Toast.makeText(context, "退出啦！！！", Toast.LENGTH_SHORT).show();
            }
        });

        ListView lv = findViewById(R.id.lv);
        lv.setAdapter(new ListViewAdapter());
        Log.e("MainActivity", "onCreate" + this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MainActivity", "onStop" + this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MainActivity", "onStart" + this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MainActivity", "onResume" + this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("MainActivity", "onPause" + this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("MainActivity", "onRestart" + this);
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
