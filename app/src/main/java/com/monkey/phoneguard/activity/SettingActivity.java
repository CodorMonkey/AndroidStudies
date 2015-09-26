package com.monkey.phoneguard.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.monkey.phoneguard.R;
import com.monkey.phoneguard.view.SettingItemView;

public class SettingActivity extends Activity {

    SettingItemView itemUpdate;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        itemUpdate = (SettingItemView) findViewById(R.id.item_update);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        boolean autoUpdate = sp.getBoolean("auto_update", true);
        if (autoUpdate) {
            itemUpdate.setDesc("当前状态为开启");
        } else {
            itemUpdate.setDesc("当前状态为关闭");
        }
        itemUpdate.setTitle("自动更新");
        itemUpdate.setChecked(autoUpdate);
        itemUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = itemUpdate.isChecked();
                if (isChecked) {
                    itemUpdate.setDesc("当前状态为关闭");
                } else {
                    itemUpdate.setDesc("当前状态为开启");
                }
                itemUpdate.setChecked(!isChecked);
                sp.edit().putBoolean("auto_update", !isChecked).commit();
            }
        });
    }

}
