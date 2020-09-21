package com.example.zz.example.customcontrol.newedittext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zz.example.LogUtils;
import com.example.zz.example.R;


public class NewEditTextActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("onCreate");
        setContentView(R.layout.edit_text_new);

    }
}
