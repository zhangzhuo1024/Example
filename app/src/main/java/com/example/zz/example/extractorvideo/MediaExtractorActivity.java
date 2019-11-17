package com.example.zz.example.extractorvideo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.zz.example.R;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 存储根目录放置input.mp4的视频，执行分离，就可以分离出，纯音频和纯视频文件
 */
public class MediaExtractorActivity extends AppCompatActivity {
    private static final String TAG = "MediaExtractorActivity";
    private final String mSdCard = Environment.getExternalStorageDirectory().getPath();
    private View mExtractorButton;


    List<String> mPermissionList = new ArrayList<>();
    private final int mRequestCode = 100;//权限请求码
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_extractor);
        if (Build.VERSION.SDK_INT >= 23) {//6.0才用动态权限
            initPermission();
        }
        Log.i(TAG, "onCreate");
        mExtractorButton = findViewById(R.id.extractor_video);
        mExtractorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extractorVideoAndAudio();
            }
        });
    }

    private void extractorAudio() {
        MediaExtractor mediaExtractor = new MediaExtractor();
        Log.i(TAG, "mSdCard " + mSdCard);
        MediaFormat trackFormat = null;
        int videoTtack = -1;
        try {
            mediaExtractor.setDataSource(mSdCard + "/input.mp4");
            int trackCount = mediaExtractor.getTrackCount();
            for (int i = 0; i < trackCount; i++) {
                trackFormat = mediaExtractor.getTrackFormat(i);
                String keyMime = trackFormat.getString(MediaFormat.KEY_MIME);
                if (keyMime.startsWith("audio")) {
                    videoTtack = i;
                    break;
                }
            }
            mediaExtractor.selectTrack(videoTtack);

            MediaMuxer mediaMuxer = new MediaMuxer(mSdCard + "/outputaudio.m4a", MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            int trackIndex = mediaMuxer.addTrack(trackFormat);

            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int maxInputSize = trackFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
            ByteBuffer buffer  = ByteBuffer.allocate(maxInputSize);
            mediaMuxer.start();
            while (true) {
                int sampleSize = mediaExtractor.readSampleData(buffer, 0);
                if (sampleSize < 0) {
                    break;
                }
                bufferInfo.flags = mediaExtractor.getSampleFlags();
                bufferInfo.presentationTimeUs = mediaExtractor.getSampleTime();
                bufferInfo.size = sampleSize;
                bufferInfo.offset = 0;
                mediaMuxer.writeSampleData(trackIndex, buffer, bufferInfo);
                mediaExtractor.advance();
            }
            mediaMuxer.stop();
            mediaMuxer.release();
            mediaExtractor.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPermission() {

        mPermissionList.clear();//清空没有通过的权限

        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }

        //申请权限
        if (mPermissionList.size() > 0) {
            //有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions, mRequestCode);
        }else{
            //说明权限都已经通过，可以做你想做的事情去
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                showPermissionDialog();//跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
            }else{
                //全部权限通过，可以进行下一步操作。。。

            }
        }
    }

    AlertDialog mPermissionDialog;
    String mPackName = "com.example.zz.example";
    private void showPermissionDialog() {
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(this)
                    .setMessage("已禁用权限，请手动授予")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();

                            Uri packageURI = Uri.parse("package:" + mPackName);
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //关闭页面或者做其他操作
                            cancelPermissionDialog();

                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    //关闭对话框
    private void cancelPermissionDialog() {
        mPermissionDialog.cancel();
    }



    private void extractorVideoAndAudio() {
        Log.i(TAG, "extractorVideoAndAudio");
        MediaExtractor mediaExtractor = new MediaExtractor();
        Log.i(TAG, "mSdCard " + mSdCard);
        MediaFormat audioTrackFormat = null;
        MediaFormat videoTrackFormat = null;
        int audioExtractorTtack = -1;
        int videoExtractorTtack = -1;
        try {
            mediaExtractor.setDataSource(mSdCard + "/input.mp4");
            int trackCount = mediaExtractor.getTrackCount();
            for (int i = 0; i < trackCount; i++) {
                MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                String keyMime = trackFormat.getString(MediaFormat.KEY_MIME);
                if (keyMime.startsWith("video")) {
                    videoTrackFormat = trackFormat;
                    videoExtractorTtack = i;
                }
                if (keyMime.startsWith("audio")) {
                    audioTrackFormat = trackFormat;
                    audioExtractorTtack = i;
                }
            }


            mediaExtractor.selectTrack(videoExtractorTtack);
            MediaMuxer mediaMuxer = new MediaMuxer(mSdCard + "/outputvideo.mp4", MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            int videoTrackIndex = mediaMuxer.addTrack(videoTrackFormat);

            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int maxInputSize = videoTrackFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
            ByteBuffer buffer  = ByteBuffer.allocate(maxInputSize);
            mediaMuxer.start();
            while (true) {
                int sampleSize = mediaExtractor.readSampleData(buffer, 0);
                if (sampleSize < 0) {
                    break;
                }
                bufferInfo.flags = mediaExtractor.getSampleFlags();
                bufferInfo.presentationTimeUs = mediaExtractor.getSampleTime();
                bufferInfo.size = sampleSize;
                bufferInfo.offset = 0;
                mediaMuxer.writeSampleData(videoTrackIndex, buffer, bufferInfo);
                mediaExtractor.advance();
            }

            mediaExtractor.selectTrack(audioExtractorTtack);
            MediaMuxer audioMediaMuxer = new MediaMuxer(mSdCard + "/outputaudio.m4a", MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            int audioTrackIndex = audioMediaMuxer.addTrack(audioTrackFormat);

            MediaCodec.BufferInfo audioBufferInfo = new MediaCodec.BufferInfo();
            int audioMaxInputSize = audioTrackFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
            ByteBuffer audioBuffer  = ByteBuffer.allocate(audioMaxInputSize);
            audioMediaMuxer.start();
            while (true) {
                int sampleSize = mediaExtractor.readSampleData(audioBuffer, 0);
                if (sampleSize < 0) {
                    break;
                }
                audioBufferInfo.flags = mediaExtractor.getSampleFlags();
                audioBufferInfo.presentationTimeUs = mediaExtractor.getSampleTime();
                audioBufferInfo.size = sampleSize;
                audioBufferInfo.offset = 0;
                audioMediaMuxer.writeSampleData(audioTrackIndex, audioBuffer, audioBufferInfo);
                mediaExtractor.advance();
            }


            mediaMuxer.stop();
            mediaMuxer.release();
            audioMediaMuxer.stop();
            audioMediaMuxer.release();
            mediaExtractor.release();
            Toast.makeText(MediaExtractorActivity.this, "分离出视频和音频 ， 成功！！！", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
