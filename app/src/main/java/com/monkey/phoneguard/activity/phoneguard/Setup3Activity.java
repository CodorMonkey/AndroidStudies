package com.monkey.phoneguard.activity.phoneguard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.monkey.phoneguard.R;

/**
 * Created by MonkeyKiky on 2015/10/4.
 */
public class Setup3Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
    }

    public void next(View view) {
        startActivity(new Intent(this, Setup4Activity.class));
        finish();
        overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out);
    }

    public void previous(View view) {
        startActivity(new Intent(this, Setup2Activity.class));
        finish();
        overridePendingTransition(R.anim.page_left_in, R.anim.page_right_out);
    }

    public void selectContact(View view) {

    }
}
