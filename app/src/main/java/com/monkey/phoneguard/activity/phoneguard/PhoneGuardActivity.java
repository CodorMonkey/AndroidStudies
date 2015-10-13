package com.monkey.phoneguard.activity.phoneguard;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.monkey.phoneguard.R;
import com.monkey.phoneguard.constant.SharedPreferencesKeys;

public class PhoneGuardActivity extends Activity {

    private SharedPreferences sp;
    private TextView tvSafePhone;
    private ImageView ivGuardLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        boolean isSetup = sp.getBoolean(SharedPreferencesKeys.IS_SETUP, false);
        if (isSetup) {
            setContentView(R.layout.activity_phone_guard);
            tvSafePhone = (TextView) findViewById(R.id.tv_safe_phone);
            ivGuardLock = (ImageView) findViewById(R.id.iv_guard_lock);
        } else {
            startActivity(new Intent(this, Setup1Activity.class));
            finish();
        }
        boolean isGuard = sp.getBoolean(SharedPreferencesKeys.IS_GUARD, false);
        if (isGuard) {
            ivGuardLock.setImageResource(R.mipmap.lock);
        }
        String safePhone = sp.getString(SharedPreferencesKeys.SAFE_PHONE, "");
        tvSafePhone.setText(safePhone);
    }

    /**
     * 重新进入设置向导
     *
     * @param view
     */
    public void reSetup(View view) {
        startActivity(new Intent(this, Setup1Activity.class));
        finish();
    }
}