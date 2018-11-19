package com.example.emergencyphone;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emergencyphone.db.DatabaseHelper;

import static com.example.emergencyphone.db.DatabaseHelper.COL_ID;
import static com.example.emergencyphone.db.DatabaseHelper.COL_NUMBER;
import static com.example.emergencyphone.db.DatabaseHelper.COL_TITLE;
import static com.example.emergencyphone.db.DatabaseHelper.TABLE_NAME;

public class EditActivity extends AppCompatActivity {

    private DatabaseHelper mHelper;
    private SQLiteDatabase mDb;
    private Long mId;
    private String mNumber;
    private String mTitle;
    private EditText eTitle;
    private EditText eNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mHelper = new DatabaseHelper(this);
        mDb = mHelper.getWritableDatabase();

        Intent i = getIntent();
        mId = i.getLongExtra("id", 0);
        mTitle = i.getStringExtra("title");
        mNumber = i.getStringExtra("number");

        eTitle = findViewById(R.id.edit_title);
        eNumber = findViewById(R.id.edit_number);
        eTitle.setText(mTitle);
        eTitle.setHint(mTitle);
        eNumber.setText(mNumber);
        eNumber.setHint(mNumber);

        Button editButton = findViewById(R.id.ebutton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues cv = new ContentValues();
                cv.put(COL_TITLE,eTitle.getText().toString());
                cv.put(COL_NUMBER,eNumber.getText().toString());
                if(eTitle.getText().toString().length()!=0&&eNumber.getText().toString().length()!=0){
                    mDb.update(
                            TABLE_NAME,
                            cv,
                            COL_ID+" = ?",
                            new String[]{String.valueOf(mId)});
                    Toast.makeText(EditActivity.this,"แก้ไขเรียบร้อย",Toast.LENGTH_SHORT).show();
                }
                finish();

            }

        });
    }
}
