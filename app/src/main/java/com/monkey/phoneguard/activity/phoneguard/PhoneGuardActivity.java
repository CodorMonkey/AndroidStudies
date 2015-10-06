package com.monkey.phoneguard.activity.phoneguard;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.monkey.phoneguard.R;

public class PhoneGuardActivity extends Activity {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        boolean isSetup = sp.getBoolean("isSetup", false);
        if (isSetup) {
            setContentView(R.layout.activity_phone_guard);
        } else {
            startActivity(new Intent(this, Setup1Activity.class));
            finish();
        }
    }

    /**
     * 重新进入设置向导
     * @param view
     */
    public void reSetup(View view) {
        startActivity(new Intent(this, Setup1Activity.class));
        finish();
    }
}