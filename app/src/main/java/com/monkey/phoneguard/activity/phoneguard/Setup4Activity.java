package com.monkey.phoneguard.activity.phoneguard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.monkey.phoneguard.R;
import com.monkey.phoneguard.activity.phoneguard.base.BaseSetupActivity;
import com.monkey.phoneguard.constant.SharedPreferencesKeys;
import com.monkey.phoneguard.view.SettingItemView;

/**
 * Created by MonkeyKiky on 2015/10/4.
 */
public class Setup4Activity extends BaseSetupActivity {

    private SettingItemView sivSafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        sivSafe = (SettingItemView) findViewById(R.id.siv_safe);
        boolean isGuard = sp.getBoolean(SharedPreferencesKeys.IS_GUARD, false);
        sivSafe.setChecked(isGuard);
        sivSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isGuard = sivSafe.isChecked();
                sivSafe.setChecked(!isGuard);
                SharedPreferences.Editor editor = sp.edit();
                if (isGuard) {
                    //未开启保护
                    editor.putBoolean(SharedPreferencesKeys.IS_GUARD, false).commit();
                } else {
                    //开启保护
                    editor.putBoolean(SharedPreferencesKeys.IS_GUARD, true).commit();
                }
            }
        });
    }

    @Override
    public void showNextPage() {
        sp.edit().putBoolean(SharedPreferencesKeys.IS_SETUP, true).commit();
        startActivity(new Intent(this, PhoneGuardActivity.class));
        finish();
        overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out);
    }

    @Override
    public void showPreviousPage() {
        startActivity(new Intent(this, Setup3Activity.class));
        finish();
        overridePendingTransition(R.anim.page_left_in, R.anim.page_right_out);
    }

}
