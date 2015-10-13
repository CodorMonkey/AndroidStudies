package com.monkey.phoneguard.activity.phoneguard.base;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by MonkeyKiky on 2015/10/11.
 */
public abstract class BaseSetupActivity extends Activity {
    private GestureDetector gestureDetector;
    protected SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        gestureDetector = new GestureDetector(this, new MyGestureListener());
    }

    /**
     * 下一页按钮点击事件
     *
     * @param view
     */
    public void next(View view) {
        showNextPage();
    }

    /**
     * 上一页按钮点击事件
     *
     * @param view
     */
    public void previous(View view) {
        showPreviousPage();
    }

    /**
     * 显示下一页
     */
    public abstract void showNextPage();

    /**
     * 显示上一页
     */
    public abstract void showPreviousPage();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //滑动速度过慢
            if (Math.abs(velocityX) < 100) {
                return true;
            }
            //纵向滑动幅度过大
            if (Math.abs(e1.getRawY() - e2.getRawY()) > 200) {
                return true;
            }
            //向左滑动，显示下一页
            if (e1.getRawX() - e2.getRawX() > 150) {
                showNextPage();
            }
            //向右滑动，显示上一页
            if (e2.getRawX() - e1.getRawX() > 150) {
                showPreviousPage();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
