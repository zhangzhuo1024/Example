package com.example.zz.example.memory;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zz.example.R;

import java.io.File;

public class MemoryActivity extends AppCompatActivity {
    private ProgressBar pb; // 进度条
    private int[] randData = new int[100]; // 数组
    private int index = 0; // 索引
    private int mProgressStaus = 0; // 设置进度条的长度
    private Handler mHandler;
    String totalStr;
    double availSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        TextView all_memory = (TextView) findViewById(R.id.all_memory);
        TextView available_memory = (TextView) findViewById(R.id.available_memory);


        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long totalBlocks = stat.getBlockCountLong();
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();

        long totalSize = blockSize * totalBlocks;
        availSize = blockSize * availableBlocks;

        //手机总内存
        totalStr = Formatter.formatFileSize(this, totalSize);
        //可用内存
        String availStr = Formatter.formatFileSize(this, (long)availSize);
        all_memory.setText(totalStr);
        available_memory.setText(availStr);


        pb = (ProgressBar) findViewById(R.id.pb);

        // 初始化handle，绑定在主线程中的队列消息中
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // 接收消息
                if (msg.what == 0x111) {
                    mProgressStaus = 100 - index;

                    available_memory.setText(availStr);
                    pb.setProgress(mProgressStaus);
                }
            }
        };

        // 创建子线程，在子线程中处理耗时工作
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (index < 100) {
                    File path = Environment.getDataDirectory();
                    StatFs stat = new StatFs(path.getPath());
                    long totalBlocks = stat.getBlockCountLong();
                    long blockSize = stat.getBlockSizeLong();
                    long totalSize = blockSize * totalBlocks;
                    blockSize = stat.getBlockSizeLong();
                    long availableBlocks = stat.getAvailableBlocksLong();
                    availSize = (double)blockSize * availableBlocks;
                    Log.e("mProgressStaus", " = " + availSize / totalSize);
                    index = (int) (availSize / totalSize * 100);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = new Message();
                    msg.what = 0x111;
                    mHandler.sendMessage(msg);
                }
            }

        }.start();
    }
}