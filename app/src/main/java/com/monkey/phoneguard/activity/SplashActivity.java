package com.monkey.phoneguard.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.monkey.phoneguard.R;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.monkey.phoneguard.utils.StreamUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends Activity {

    private static final int CODE_UPDATE_DIALOG = 0;
    private static final int CODE_URL_ERROR = 1;
    private static final int CODE_NET_ERROR = 2;
    private static final int CODE_JSON_ERROR = 3;
    private static final int CODE_ENTER_HOME = 4;

    TextView tv_version;
    private String mDes;
    private int mVersionCode;
    private String mVersionName;
    private String mDownloadUrl;

    private final Handler handler = new MyHandler(this);

    /**
     * 使用静态内部类创建handler，防止内存泄露
     */
    private static class MyHandler extends Handler {
        private final WeakReference<SplashActivity> mActivity;

        public MyHandler(SplashActivity activity) {
            mActivity = new WeakReference<SplashActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SplashActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case CODE_UPDATE_DIALOG:
                        activity.showUpateDialog();
                        break;
                    case CODE_URL_ERROR:
                        Toast.makeText(activity, "URL错误", Toast.LENGTH_SHORT).show();
                        activity.enterHome();
                        break;
                    case CODE_NET_ERROR:
                        Toast.makeText(activity, "网络错误", Toast.LENGTH_SHORT).show();
                        activity.enterHome();
                        break;
                    case CODE_JSON_ERROR:
                        Toast.makeText(activity, "数据解析错误", Toast.LENGTH_SHORT).show();
                        activity.enterHome();
                        break;
                    case CODE_ENTER_HOME:
                        activity.enterHome();
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText("版本号：" + getVersionName());
        checkVersion();
    }

    /**
     * 获取当前版本名称
     * @return 当前版本名称
     */
    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        String versionName = "1.0";
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取当前版本号
     * @return 当前版本号
     */
    private int getVersionCode() {
        PackageManager packageManager = getPackageManager();
        int versionCode = 0;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 检测当前版本是否需要更新
     */
    private void checkVersion() {
        new Thread() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                Message msg = Message.obtain();
                HttpURLConnection conn = null;
                try {
                    URL url = new URL("http://10.0.2.2:8080/androidTest/update.json");
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    conn.connect();
                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        String result = StreamUtils.readFromStream(conn.getInputStream());
                        JSONObject jo = new JSONObject(result);
                        mDes = jo.getString("description");
                        mVersionCode = jo.getInt("versionCode");
                        mVersionName = jo.getString("versionName");
                        mDownloadUrl = jo.getString("downloadUrl");
                        if (mVersionCode > getVersionCode()) {
                            msg.what = CODE_UPDATE_DIALOG;
                        } else {
                            msg.what = CODE_ENTER_HOME;
                        }
                    }
                } catch (MalformedURLException e) {
                    msg.what = CODE_URL_ERROR;
                    e.printStackTrace();
                } catch (IOException e) {
                    msg.what = CODE_NET_ERROR;
                    e.printStackTrace();
                } catch (JSONException e) {
                    msg.what = CODE_JSON_ERROR;
                    e.printStackTrace();
                } finally {
                    long end = System.currentTimeMillis();
                    long timeUsed = end - start;
                    if (timeUsed < 2000) {
                        try {
                            sleep(2000 - timeUsed);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.sendMessage(msg);
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        }.start();
    }

    /**
     * 弹出更新提示窗口
     */
    private void showUpateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("发现新版本：" + mVersionName);
        builder.setMessage(mDes);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doUpdate();
            }
        });
        builder.setNegativeButton("日后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });
        builder.show();
    }

    /**
     * 更新app
     */
    private void doUpdate() {

    }

    /**
     * 跳转到主Activity
     */
    private void enterHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        this.finish();
    }

}