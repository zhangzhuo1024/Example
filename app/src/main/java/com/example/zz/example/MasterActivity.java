package com.example.zz.example;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.zz.example.fragment.HomeFragment;
import com.example.zz.example.fragment.MoreFragment;
import com.example.zz.example.fragment.OrderFragment;
import com.example.zz.example.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class MasterActivity extends AppCompatActivity {

    private LinearLayout mMainBottomBar;
    private ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("加油，我的世界 ！");
        }
        mMainBottomBar = ((LinearLayout) findViewById(R.id.main_bottom_bar));
        if (Build.VERSION.SDK_INT >= 23) {//6.0才用动态权限
            initPermission();
        }

        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new OrderFragment());
        mFragmentList.add(new UserFragment());
        mFragmentList.add(new MoreFragment());

        initBottom();
    }

    private void initBottom() {
        changeIndex(0);
        for (int i = 0; i < mMainBottomBar.getChildCount(); i++) {
            int finalI = i;
            mMainBottomBar.getChildAt(i).setOnClickListener(v -> {
                changeIndex(finalI);
            });
        }
    }

    private void changeIndex(int index) {
        for (int i = 0; i < mMainBottomBar.getChildCount(); i++) {
            if (index == i) {
                setChildEnable(true, (FrameLayout) mMainBottomBar.getChildAt(i));
            } else {
                setChildEnable(false, (FrameLayout) mMainBottomBar.getChildAt(i));
            }
            getFragmentManager().beginTransaction().replace(R.id.main_content, mFragmentList.get(i));
        }
    }

    private void setChildEnable(boolean b, FrameLayout childAt) {
        for (int i = 0; i < childAt.getChildCount(); i++) {
            childAt.getChildAt(i).setEnabled(b);
        }
    }

    List<String> mPermissionList = new ArrayList<>();

    private void initPermission() {
        mPermissionList.clear();//清空没有通过的权限
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET};
        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }
        //申请权限
        if (mPermissionList.size() > 0) {
            //有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions, 500);
        } else {
            //说明权限都已经通过，可以做你想做的事情去
        }
    }

}
