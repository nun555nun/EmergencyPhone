package com.example.emergencyphone;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emergencyphone.db.DatabaseHelper;
import com.example.emergencyphone.model.PhoneItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.emergencyphone.db.DatabaseHelper.COL_ID;
import static com.example.emergencyphone.db.DatabaseHelper.COL_IMAGE;
import static com.example.emergencyphone.db.DatabaseHelper.COL_NUMBER;
import static com.example.emergencyphone.db.DatabaseHelper.COL_TITLE;
import static com.example.emergencyphone.db.DatabaseHelper.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private DatabaseHelper mHelper;
    private SQLiteDatabase mDb;
    private List<PhoneItem> mPhoneItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new DatabaseHelper(this);
        mDb = mHelper.getWritableDatabase();

        loadPhoneData();
        setupListView();


    }


    private void loadPhoneData() {
        Cursor c = mDb.query(TABLE_NAME, null, null, null, null, null, null);
        mPhoneItemList = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(COL_ID));
            String title = c.getString(c.getColumnIndex(COL_TITLE));
            String number = c.getString(c.getColumnIndex(COL_NUMBER));
            String image = c.getString(c.getColumnIndex(COL_IMAGE));

            PhoneItem item = new PhoneItem(id, title, number, image);
            mPhoneItemList.add(item);

        }
        c.close();
    }

    private void setupListView() {
        ArrayAdapter<PhoneItem> adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                mPhoneItemList
        );
        ListView l = findViewById(R.id.result_list_view);
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhoneItem item = mPhoneItemList.get(position);
                Toast.makeText(MainActivity.this,item.number,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+item.number)); //ยังไม่โทร
                //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+item.number)); // โทรเบย

                startActivity(intent);
            }
        });
    }
}
