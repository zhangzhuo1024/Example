package com.example.zz.example.mediaplayer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.zz.example.R;
import com.example.zz.example.mediaplayer.zzplayer.PlayActivity;
import com.example.zz.example.mediaplayer.zzplayer.adapter.VideoAdapter;
import com.example.zz.example.mediaplayer.zzplayer.bean.VideoBean;
import com.example.zz.example.mediaplayer.zzplayer.constant.IntentConstant;

public class ZzPlayerActivity extends AppCompatActivity {

    private static final int REQ_CODE = 200;
    private VideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zzplayer);

        // RecyclerView 的使用：
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_rv);
        //1 设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //2 设置Adapter
        videoAdapter = new VideoAdapter(this);
        videoAdapter.init();
        recyclerView.setAdapter(videoAdapter);

        videoAdapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, VideoBean videoBean) {
                goPlayActivity(position, videoBean);
            }
        });
        AlertDialog.Builder builder;


    }

    int currentPosition;

    private void goPlayActivity(int position, VideoBean videoBean) {
        currentPosition = position;
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra(IntentConstant.KEY_HAS_NEXT, currentPosition < videoAdapter.getItemCount() - 1);
        intent.putExtra(IntentConstant.KEY_HAS_PREV, currentPosition > 0);
        intent.putExtra("VideoBean", videoBean);
//        startActivity(intent);
        startActivityForResult(intent, REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE && resultCode == Activity.RESULT_OK) {
            String action = data.getAction();
            if (IntentConstant.ACTION_NEXT.equals(action)) {
//                Toast.makeText(this,"ACTION_NEXT",Toast.LENGTH_SHORT).show();
                int targetPosition = currentPosition + 1;
                VideoBean videoBean = videoAdapter.getVideoBean(targetPosition);
                goPlayActivity(targetPosition, videoBean);
            } else if (IntentConstant.ACTION_PREV.equals(action)) {
//                Toast.makeText(this,"ACTION_PREV",Toast.LENGTH_SHORT).show();
                int targetPosition = currentPosition - 1;
                VideoBean videoBean = videoAdapter.getVideoBean(targetPosition);
                goPlayActivity(targetPosition, videoBean);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoAdapter.release();
    }
}
