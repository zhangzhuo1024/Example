package com.example.zz.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.zz.example.clickevent.ClickEventActivity;
import com.example.zz.example.clickspanner.WeiboContentTestActivity;
import com.example.zz.example.customcontrol.CustomControlActivity;
import com.example.zz.example.extractorvideo.MediaExtractorActivity;
import com.example.zz.example.game.PlaneMainActivity;
import com.example.zz.example.mediaplayer.ServiceActivity;
import com.example.zz.example.memorylink.MemoryLinkActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.click_media_play)
    Button clickMediaPlay;
    private View mDatabase;
    private View mGame;
    private View mClickEvent;
    private View mMemoryLink;
    private View mRecyclerView;
    private View mExtractorVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        findViewById(R.id.database).setOnClickListener(this);
        findViewById(R.id.game).setOnClickListener(this);
        findViewById(R.id.click_event).setOnClickListener(this);
        findViewById(R.id.click_memory_link).setOnClickListener(this);
        findViewById(R.id.click_recycler_view).setOnClickListener(this);
        findViewById(R.id.click_extractor_video).setOnClickListener(this);
        findViewById(R.id.click_url).setOnClickListener(this);
        findViewById(R.id.click_customcontrol).setOnClickListener(this);


    }

    @OnClick(R.id.click_media_play)
    public void onViewClicked() {
        Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
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
            default:
                return;
        }
    }
}
