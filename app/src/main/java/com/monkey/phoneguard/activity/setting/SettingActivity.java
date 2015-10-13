package com.monkey.phoneguard.activity.setting;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.monkey.phoneguard.R;
import com.monkey.phoneguard.constant.SharedPreferencesKeys;
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
        itemUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = itemUpdate.isChecked();
                itemUpdate.setChecked(!isChecked);
                sp.edit().putBoolean(SharedPreferencesKeys.AUTO_UPDATE, !isChecked).commit();
            }
        });
    }

}
