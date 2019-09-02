package com.example.zz.example.game;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.zz.example.R;

public class PlaneGameActivity extends Activity {

    private GameView mGameView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_game);
        mGameView = findViewById(R.id.game_view);
        mGameView.start();
    }
}
