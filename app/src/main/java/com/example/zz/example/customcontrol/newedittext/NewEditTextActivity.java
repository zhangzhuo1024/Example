package com.example.zz.example.customcontrol.newedittext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zz.example.LogUtils;
import com.example.zz.example.R;


public class NewEditTextActivity extends AppCompatActivity {


    private EditText normal_edit;
    private View touch_view;
    private EndEllipsisEditText endEllipsisEditText;
    /**
     * 软键盘弹出收起监听
     */
    private EndEllipsisEditText.OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener = new EndEllipsisEditText.OnSoftKeyBoardChangeListener() {
        @Override
        public void keyBoardShow(int height) {
            endEllipsisEditText.requestFocus();
            Toast.makeText(NewEditTextActivity.this, "键盘显示 高度 : " + height, Toast.LENGTH_LONG).show();
        }

        @Override
        public void keyBoardHide(int height) {
            endEllipsisEditText.clearFocus();
            touch_view.requestFocus();
            Toast.makeText(NewEditTextActivity.this, "键盘隐藏 高度 : " + height, Toast.LENGTH_LONG).show();
        }
    };

    KeyListener keyListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("onCreate");
        setContentView(R.layout.edit_text_new);
//        normal_edit = findViewById(R.id.normal_edit);
//
//        normal_edit.setText("这是普通edittext，如何加省略号");
//        normal_edit.setEllipsize(TextUtils.TruncateAt.END);

        endEllipsisEditText = findViewById(R.id.endEllipsisEditText);
        endEllipsisEditText.setText("这是测试数据");
        endEllipsisEditText.setListener(this, onSoftKeyBoardChangeListener);

        touch_view = findViewById(R.id.touch_view);
    }
}
