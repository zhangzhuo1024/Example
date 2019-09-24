package com.example.zz.example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.zz.example.clickevent.ClickEventActivity;
import com.example.zz.example.game.PlaneMainActivity;

public class MainActivity extends AppCompatActivity {

    private View mDatabase;
    private View.OnClickListener mButtonListerner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.getId();
            switch (view.getId()){
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
        mDatabase = findViewById(R.id.database);
        mGame = findViewById(R.id.game);
        mClickEvent = findViewById(R.id.click_event);

        mDatabase.setOnClickListener(mButtonListerner);
        mGame.setOnClickListener(mButtonListerner);
        mClickEvent.setOnClickListener(mButtonListerner);
    }
}
