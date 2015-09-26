package com.monkey.phoneguard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monkey.phoneguard.R;


/**
 * Created by wateradu on 2015/9/26.
 */
public class SettingItemView extends RelativeLayout {

    private TextView tvTitle;
    private TextView tvDesc;
    private CheckBox cbSwitch;

    public SettingItemView(Context context) {
        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.setting_item, this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        cbSwitch = (CheckBox) findViewById(R.id.cb_switch);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setDesc(String desc) {
        tvDesc.setText(desc);
    }

    public boolean isChecked() {
        return cbSwitch.isChecked();
    }

    public void setChecked(boolean checked) {
        cbSwitch.setChecked(checked);
    }
}
