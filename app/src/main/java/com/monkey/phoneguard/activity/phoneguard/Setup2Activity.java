package com.monkey.phoneguard.activity.phoneguard;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.monkey.phoneguard.R;
import com.monkey.phoneguard.activity.phoneguard.base.BaseSetupActivity;
import com.monkey.phoneguard.constant.SharedPreferencesKeys;
import com.monkey.phoneguard.view.SettingItemView;

public class Setup2Activity extends BaseSetupActivity {

    private SettingItemView sivSim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        sivSim = (SettingItemView) findViewById(R.id.siv_sim);
        String simNo = sp.getString(SharedPreferencesKeys.SIM_NO, null);
        if (!TextUtils.isEmpty(simNo)) {
            sivSim.setChecked(true);
        }
        sivSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = sivSim.isChecked();
                sivSim.setChecked(!isChecked);
                if (isChecked) {
                    //取消绑定sim卡
                    sp.edit().remove(SharedPreferencesKeys.SIM_NO).commit();
                } else {
                    //开启绑定sim卡
                    TelephonyManager telManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    String simNo = telManager.getSimSerialNumber();
                    sp.edit().putString(SharedPreferencesKeys.SIM_NO, simNo).commit();
                }
            }
        });
    }

    @Override
    public void showNextPage() {
        if (sivSim.isChecked()) {
            startActivity(new Intent(this, Setup3Activity.class));
            finish();
            overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out);
        } else {
            Toast.makeText(this, "请先绑定SIM卡！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showPreviousPage() {
        startActivity(new Intent(this, Setup1Activity.class));
        finish();
        overridePendingTransition(R.anim.page_left_in, R.anim.page_right_out);
    }

}