package com.example.zz.example;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.zz.example.broadcast.BroadcastActivity;
import com.example.zz.example.clickevent.ClickEventActivity;
import com.example.zz.example.clickspanner.WeiboContentTestActivity;
import com.example.zz.example.customcontrol.CustomControlActivity;
import com.example.zz.example.extractorvideo.MediaExtractorActivity;
import com.example.zz.example.game.PlaneMainActivity;
import com.example.zz.example.handler.HandlerActivity;
import com.example.zz.example.mediaplayer.ServiceActivity;
import com.example.zz.example.mediaplayer.ZzPlayerActivity;
import com.example.zz.example.memorylink.MemoryLinkActivity;
import com.example.zz.example.network.NetWorkActivity;
import com.example.zz.example.pattern.PatternActivity;
import com.example.zz.example.pattern.rxjava.RxjavaActivity;
import com.example.zz.example.pictureload.PictureLoadActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.click_media_play)
    Button clickMediaPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("加油，我的世界 ！");
        }
        ButterKnife.bind(this);
        findViewById(R.id.database).setOnClickListener(this);
        findViewById(R.id.game).setOnClickListener(this);
        findViewById(R.id.click_event).setOnClickListener(this);
        findViewById(R.id.click_memory_link).setOnClickListener(this);
        findViewById(R.id.click_recycler_view).setOnClickListener(this);
        findViewById(R.id.click_extractor_video).setOnClickListener(this);
        findViewById(R.id.click_url).setOnClickListener(this);
        findViewById(R.id.click_customcontrol).setOnClickListener(this);
        findViewById(R.id.click_mode).setOnClickListener(this);
        findViewById(R.id.click_handler).setOnClickListener(this);
        findViewById(R.id.pic_load).setOnClickListener(this);
        findViewById(R.id.zz_player).setOnClickListener(this);
        findViewById(R.id.broadcast).setOnClickListener(this);
        findViewById(R.id.network).setOnClickListener(this);
        findViewById(R.id.rxjava).setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= 23) {//6.0才用动态权限
            initPermission();
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

    @OnClick(R.id.click_media_play)
    public void onViewClicked() {
        Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        LogUtils.e("onClick ");
        switch (v.getId()) {
            case R.id.database:
                Intent intent = new Intent(MainActivity.this, DatabaseActivity.class);
                startActivity(intent);
                break;
            case R.id.game:
                Intent intent2 = new Intent(MainActivity.this, PlaneMainActivity.class);
                startActivity(intent2);
                break;
            case R.id.click_event:
                Intent intent3 = new Intent(MainActivity.this, ClickEventActivity.class);
                startActivity(intent3);
                break;
            case R.id.click_memory_link:
                Intent intent4 = new Intent(MainActivity.this, MemoryLinkActivity.class);
                startActivity(intent4);
                break;
            case R.id.click_recycler_view:
                Intent intent5 = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent5);
                break;
            case R.id.click_extractor_video:
                Intent intent6 = new Intent(MainActivity.this, MediaExtractorActivity.class);
                startActivity(intent6);
                break;
            case R.id.click_url:
                Intent intent7 = new Intent(MainActivity.this, WeiboContentTestActivity.class);
                startActivity(intent7);
                break;
            case R.id.click_customcontrol:
                Intent intent8 = new Intent(MainActivity.this, CustomControlActivity.class);
                startActivity(intent8);
                break;
            case R.id.click_mode:
                ActivityUtils.getInstance().goToActivity(MainActivity.this, PatternActivity.class);
                break;
            case R.id.click_handler:
                ActivityUtils.getInstance().goToActivity(MainActivity.this, HandlerActivity.class);
                break;
            case R.id.pic_load:
                ActivityUtils.getInstance().goToActivity(MainActivity.this, PictureLoadActivity.class);
                break;
            case R.id.zz_player:
                ActivityUtils.getInstance().goToActivity(MainActivity.this, ZzPlayerActivity.class);
                break;
            case R.id.broadcast:
                ActivityUtils.getInstance().goToActivity(MainActivity.this, BroadcastActivity.class);
                break;
            case R.id.network:
                ActivityUtils.getInstance().goToActivity(MainActivity.this, NetWorkActivity.class);
                break;
            case R.id.rxjava:
                ActivityUtils.getInstance().goToActivity(MainActivity.this, RxjavaActivity.class);
                break;
            default:
                return;
        }
    }

    public boolean Find(int target, int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (target == array[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
