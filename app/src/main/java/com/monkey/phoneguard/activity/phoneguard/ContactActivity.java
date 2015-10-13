package com.monkey.phoneguard.activity.phoneguard;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.monkey.phoneguard.R;
import com.monkey.phoneguard.constant.ContactMimetype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactActivity extends Activity {

    private ListView lvContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        lvContact = (ListView) findViewById(R.id.lv_contact);
        final List<Map<String, String>> data = readContact();
        lvContact.setAdapter(new SimpleAdapter(this, data, R.layout.item_contact,
                new String[]{"name", "phone"}, new int[]{R.id.tv_name, R.id.tv_phone}));
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phone = data.get(position).get("phone");
                Intent intent = new Intent();
                intent.putExtra("phone", phone);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private List<Map<String, String>> readContact() {
        String content = "content://com.android.contacts/";
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(Uri.parse(content.concat("raw_contacts")),
                new String[]{"contact_id"}, "deleted=?", new String[]{"0"}, null);
        List<Map<String, String>> list = null;
        if (cursor != null) {
            list = new ArrayList<>();
            while (cursor.moveToNext()) {
                String contactId = cursor.getString(0);
                Cursor cursorData = resolver.query(Uri.parse(content.concat("data")),
                        new String[]{"mimetype", "data1"}, "contact_id=?", new String[]{contactId}, null);
                if (cursorData != null) {
                    Map<String, String> item = new HashMap<>();
                    while (cursorData.moveToNext()) {
                        String mimetype = cursorData.getString(0);
                        String data1 = cursorData.getString(1);
                        if (ContactMimetype.PHONE.equals(mimetype)) {
                            item.put("phone", data1.replaceAll("-", "").replaceAll(" ", ""));
                        } else if (ContactMimetype.NAME.equals(mimetype)) {
                            item.put("name", data1);
                        }
                    }
                    list.add(item);
                }
                cursorData.close();
            }
            cursor.close();
        }
        return list;
    }
}
