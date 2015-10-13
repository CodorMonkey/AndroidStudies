package com.monkey.phoneguard.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.monkey.phoneguard.constant.SharedPreferencesKeys;

/**
 * Created by MonkeyKiky on 2015/10/11.
 */
public class BootCompleteReceiver extends BroadcastReceiver {

    private SharedPreferences sp;

    @Override
    public void onReceive(Context context, Intent intent) {
        sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        boolean isGuard = sp.getBoolean(SharedPreferencesKeys.IS_GUARD, false);
        if (isGuard) {
            String simNo = sp.getString("sim_no", null);
            if (!TextUtils.isEmpty(simNo)) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String currentSimNo = telephonyManager.getSimSerialNumber();
                if (!simNo.equals(currentSimNo)) {
                    System.out.println("sim卡变更啦~~~赶紧报警！！");
                }
            }
        }
    }
}