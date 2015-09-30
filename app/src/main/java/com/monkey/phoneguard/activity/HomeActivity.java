package com.monkey.phoneguard.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.monkey.phoneguard.R;

public class HomeActivity extends Activity {

    private GridView gvHome;

    private String[] mItemTitles = {"手机防盗", "通讯卫士", "软件管理",
            "进程管理", "流量统计", "手机杀毒",
            "缓存清理", "高级工具", "设置中心"};
    private int[] mItemIconIds = {R.mipmap.home_safe, R.mipmap.home_callmsgsafe, R.mipmap.home_apps,
            R.mipmap.home_taskmanager, R.mipmap.home_netmanager, R.mipmap.home_trojan,
            R.mipmap.home_sysoptimize, R.mipmap.home_tools, R.mipmap.home_settings};
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gvHome = (GridView) findViewById(R.id.gv_home);
        gvHome.setAdapter(new MyAdapter());
        gvHome.setOnItemClickListener(new MyItemClickListener());
    }

    /**
     * 主界面GridView适配器
     */
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mItemTitles.length;
        }

        @Override
        public Object getItem(int position) {
            return mItemTitles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(HomeActivity.this, R.layout.gridview_homeitem, null);
            ImageView ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            ivIcon.setImageResource(mItemIconIds[position]);
            tvTitle.setText(mItemTitles[position]);
            return view;
        }
    }

    class MyItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:     //手机防盗
                    showPasswordDialog();
                    break;
                case 1:     //通讯卫士

                    break;
                case 2:     //软件管理

                    break;
                case 3:     //进程管理

                    break;
                case 4:     //流量统计

                    break;
                case 5:     //手机杀毒

                    break;
                case 6:     //缓存清理

                    break;
                case 7:     //高级工具

                    break;
                case 8:     //设置中心
                    Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    /**
     * 弹出密码对话框
     */
    private void showPasswordDialog() {
        //判断是否已经设置了密码
        sp = getSharedPreferences("config", MODE_PRIVATE);
        String password = sp.getString("password", null);
        if (TextUtils.isEmpty(password)) {
            //弹出设置密码对话框
            showPasswordSetDialog();
        } else {
            //弹出输入密码对话框
            showPasswordInputDialog();
        }
    }

    /**
     * 弹出输入密码对话框
     */
    private void showPasswordInputDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View view = View.inflate(this, R.layout.dialog_input_password, null);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btnOk = (Button) view.findViewById(R.id.btn_ok);
        final EditText etPassword = (EditText) view.findViewById(R.id.et_password);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = sp.getString("password", null);
                String passwordIn = etPassword.getText().toString();
                if (!TextUtils.isEmpty(password) && password.equals(passwordIn)) {
                    Toast.makeText(HomeActivity.this, "密码正确~~", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(HomeActivity.this, "密码错误!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
    }

    /**
     * 弹出设置密码对话框
     */
    private void showPasswordSetDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View view = View.inflate(this, R.layout.dialog_set_password, null);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btnOk = (Button) view.findViewById(R.id.btn_ok);
        final EditText etPassword = (EditText) view.findViewById(R.id.et_password);
        final EditText etPasswordConfirm = (EditText) view.findViewById(R.id.et_password_confirm);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString();
                String passwordConfirm = etPasswordConfirm.getText().toString();
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfirm)) {
                    Toast.makeText(HomeActivity.this, "密码不能为空!!", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(passwordConfirm)) {
                    Toast.makeText(HomeActivity.this, "两次密码不一致!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, "设置密码成功~~", Toast.LENGTH_SHORT).show();
                    sp.edit().putString("password", password).commit();
                    dialog.dismiss();
                }
            }
        });
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
    }
}
