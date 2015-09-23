package com.monkey.phoneguard.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.monkey.phoneguard.R;

import java.io.File;
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

    TextView tvVersion;
    TextView tvProgress;
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
        tvVersion = (TextView) findViewById(R.id.tv_version);
        tvVersion.setText("版本号：" + getVersionName());
        tvProgress = (TextView) findViewById(R.id.tv_progress);
        checkVersion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHome();
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取当前版本名称
     *
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
     *
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
        //设置返回按钮监听
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });
        builder.show();
    }

    /**
     * 更新app
     */
    private void doUpdate() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String target = Environment.getExternalStorageDirectory() + "/update.apk";
            HttpUtils http = new HttpUtils();
            System.out.println("url:" + mDownloadUrl);
            System.out.println("target:" + target);
            HttpHandler handler = http.download(mDownloadUrl, target,
                    new RequestCallBack<File>() {
                        @Override
                        public void onStart() {
                            tvProgress.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {
                            tvProgress.setText("下载进度：" + current * 100 / total + "%");
                        }

                        @Override
                        public void onSuccess(ResponseInfo<File> responseInfo) {
//                            Toast.makeText(SplashActivity.this, "下载完成~~", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(responseInfo.result), "application/vnd.android.package-archive");
                            startActivityForResult(intent, 0);
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            Toast.makeText(SplashActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "你木有SD卡，无法安装", Toast.LENGTH_SHORT).show();
        }
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