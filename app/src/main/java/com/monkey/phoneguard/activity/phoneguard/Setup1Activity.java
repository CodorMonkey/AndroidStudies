package com.monkey.phoneguard.activity.phoneguard;

import android.content.Intent;
import android.os.Bundle;

import com.monkey.phoneguard.R;
import com.monkey.phoneguard.activity.phoneguard.base.BaseSetupActivity;

public class Setup1Activity extends BaseSetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }

    @Override
    public void showNextPage() {
        startActivity(new Intent(this, Setup2Activity.class));
        finish();
        overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out);
    }

    @Override
    public void showPreviousPage() {

    }
}