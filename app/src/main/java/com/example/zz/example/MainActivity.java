package com.example.zz.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.zz.example.clickevent.ClickEventActivity;
import com.example.zz.example.game.PlaneMainActivity;
import com.example.zz.example.mediaplayer.ServiceActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.click_media_play)
    Button clickMediaPlay;
    private View mDatabase;
    private View.OnClickListener mButtonListerner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.getId();
            switch (view.getId()) {
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
            }

        }

        private void changeToActivity(String activityName) {
            Intent intent = new Intent();
            intent.setClassName(MainActivity.this, activityName);
            startActivity(intent);
        }

    };
    private View mGame;
    private View mClickEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mDatabase = findViewById(R.id.database);
        mGame = findViewById(R.id.game);
        mClickEvent = findViewById(R.id.click_event);

        mDatabase.setOnClickListener(mButtonListerner);
        mGame.setOnClickListener(mButtonListerner);
        mClickEvent.setOnClickListener(mButtonListerner);
    }

    @OnClick(R.id.click_media_play)
    public void onViewClicked() {
        Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
        startActivity(intent);
    }
}
