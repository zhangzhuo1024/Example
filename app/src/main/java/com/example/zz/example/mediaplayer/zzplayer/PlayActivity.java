package com.example.zz.example.mediaplayer.zzplayer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.example.zz.example.R;
import com.example.zz.example.mediaplayer.zzplayer.bean.VideoBean;
import com.example.zz.example.mediaplayer.zzplayer.constant.IntentConstant;
import com.example.zz.example.mediaplayer.zzplayer.widget.MyMediaController;

import java.io.File;

/**
 * Created by zz on 2019/6/29.
 */
public class PlayActivity extends AppCompatActivity {
    private VideoView videoView;
    private MyMediaController myMediaController;
    //    @Override
//    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        videoView = (VideoView) findViewById(R.id.play_vv);
        myMediaController = (MyMediaController) findViewById(R.id.play_mmc);



        VideoBean videoBean = (VideoBean) getIntent().getSerializableExtra("VideoBean");
        boolean hasNext =getIntent().getBooleanExtra(IntentConstant.KEY_HAS_NEXT,false);
        boolean hasPrev =getIntent().getBooleanExtra(IntentConstant.KEY_HAS_PREV,false);
//        Toast.makeText(this, "" + videoBean, Toast.LENGTH_SHORT).show();
        myMediaController.setHasNextAndPrev(hasNext, hasPrev);

        File file = new File(videoBean.getPath());
        Uri uri = Uri.fromFile(file);


//        videoView.setMediaController(new MediaController(this));

        myMediaController.setVideoView(videoView);
        myMediaController.setVideoURI(uri, videoBean.getName());
        myMediaController.setOnKeyClickListener(new MyMediaController.OnKeyClickListener() {
            @Override
            public void onBackClick() {
//                Toast.makeText(PlayActivity.this,"onBackClick",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onNextClick() {
                Intent intent = new Intent();
                intent.setAction(IntentConstant.ACTION_NEXT);
                setResult(Activity.RESULT_OK, intent);
                finish();
//                Toast.makeText(PlayActivity.this,"onNextClick",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrevClick() {
                Intent intent = new Intent();
                intent.setAction(IntentConstant.ACTION_PREV);
                setResult(Activity.RESULT_OK,intent );
                finish();
//                Toast.makeText(PlayActivity.this,"onPrevClick",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        myMediaController.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myMediaController.onStop();
    }
}
