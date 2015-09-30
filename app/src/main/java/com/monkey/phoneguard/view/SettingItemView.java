package com.monkey.phoneguard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monkey.phoneguard.R;


/**
 * Created by MonkeyKiky on 2015/9/26.
 */
public class SettingItemView extends RelativeLayout {

    private final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private TextView tvTitle;
    private TextView tvDesc;
    private CheckBox cbSwitch;
    private String item_title;
    private String item_desc_on;
    private String item_desc_off;
    private boolean item_ischecked;

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        //读取xml中设置的自定义属性
        item_title = attrs.getAttributeValue(NAMESPACE, "item_title");
        item_desc_on = attrs.getAttributeValue(NAMESPACE, "item_desc_on");
        item_desc_off = attrs.getAttributeValue(NAMESPACE, "item_desc_off");
        item_ischecked = attrs.getAttributeBooleanValue(NAMESPACE, "item_ischecked", true);

        View view = View.inflate(getContext(), R.layout.setting_item, this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        cbSwitch = (CheckBox) findViewById(R.id.cb_switch);

        setTitle(item_title);
        setChecked(item_ischecked);
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

    public TextView getTitle() {
        return tvTitle;
    }

    public TextView getDesc() {
        return tvDesc;
    }

    public void setChecked(boolean checked) {
        cbSwitch.setChecked(checked);
        if (checked) {
            setDesc(item_desc_on);
        } else {
            setDesc(item_desc_off);
        }
    }
}
