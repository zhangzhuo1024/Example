package com.example.zz.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DatabaseActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "DatabaseActivity";
    private Button mCreateDatabase;
    private Button mUpdateDatabase;
    private Button mInsertTable;
    private Button mUptadeTable;
    private Button mSelectTable;
    private Button mDeleteTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_activity);
        mCreateDatabase = findViewById(R.id.createdatabase);
        mUpdateDatabase = findViewById(R.id.updatedatabase);
        mInsertTable = findViewById(R.id.insert_table);
        mUptadeTable = findViewById(R.id.updatedatabase);
        mSelectTable = findViewById(R.id.select_table);
        mDeleteTable = findViewById(R.id.delete_table);

//        mCreateDatabase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(DatabaseActivity.this, "ddddddddd", Toast.LENGTH_SHORT);
//            }
//        });
        mCreateDatabase.setOnClickListener(this);
        mUpdateDatabase.setOnClickListener(this);
        mInsertTable.setOnClickListener(this);
        mUptadeTable.setOnClickListener(this);
        mSelectTable.setOnClickListener(this);
        mDeleteTable.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.createdatabase:
                Log.i(TAG, "createdatabase");
                Intent intent = new Intent(DatabaseActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.updatedatabase:
                break;
            case R.id.insert_table:
                break;
            case R.id.update_table:
                break;
            case R.id.select_table:
                break;
            case R.id.delete_table:
            default:
                break;
        }
    }
}
