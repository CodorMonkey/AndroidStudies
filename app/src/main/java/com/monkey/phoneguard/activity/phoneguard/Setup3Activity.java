package com.monkey.phoneguard.activity.phoneguard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.monkey.phoneguard.R;
import com.monkey.phoneguard.activity.phoneguard.base.BaseSetupActivity;
import com.monkey.phoneguard.constant.SharedPreferencesKeys;

/**
 * Created by MonkeyKiky on 2015/10/4.
 */
public class Setup3Activity extends BaseSetupActivity {

    private static final int CODE_SELECT_CONTACT = 0;
    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        etPhone = (EditText) findViewById(R.id.et_phone);
        String safePhone = sp.getString(SharedPreferencesKeys.SAFE_PHONE, null);
        if (!TextUtils.isEmpty(safePhone)) {
            etPhone.setText(safePhone);
        }
    }

    @Override
    public void showNextPage() {
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "安全号码必须填写！", Toast.LENGTH_SHORT).show();
        } else {
            sp.edit().putString(SharedPreferencesKeys.SAFE_PHONE, phone).commit();
            startActivity(new Intent(this, Setup4Activity.class));
            finish();
            overridePendingTransition(R.anim.page_right_in, R.anim.page_left_out);
        }
    }

    @Override
    public void showPreviousPage() {
        startActivity(new Intent(this, Setup2Activity.class));
        finish();
        overridePendingTransition(R.anim.page_left_in, R.anim.page_right_out);
    }

    public void selectContact(View view) {
        startActivityForResult(new Intent(this, ContactActivity.class), CODE_SELECT_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODE_SELECT_CONTACT) {
                String phone = data.getStringExtra("phone");
                etPhone.setText(phone);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
