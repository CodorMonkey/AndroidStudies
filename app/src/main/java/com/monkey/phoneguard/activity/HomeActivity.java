package com.monkey.phoneguard.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.monkey.phoneguard.R;

public class HomeActivity extends Activity {

    GridView gvHome;

    private String[] mItemTitles = {"手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计", "手机杀毒",
            "缓存清理", "高级工具", "设置中心"};
    private int[] mItemIconIds = {R.mipmap.home_safe, R.mipmap.home_callmsgsafe, R.mipmap.home_apps,
            R.mipmap.home_taskmanager, R.mipmap.home_netmanager, R.mipmap.home_trojan,
            R.mipmap.home_sysoptimize, R.mipmap.home_tools, R.mipmap.home_settings};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gvHome = (GridView) findViewById(R.id.gv_home);
        gvHome.setAdapter(new MyAdapter());
        gvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

                        break;
                    case 8:     //设置中心
                        Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

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

}
