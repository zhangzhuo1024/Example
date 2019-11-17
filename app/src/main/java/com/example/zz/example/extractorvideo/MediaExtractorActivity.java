package com.example.zz.example.extractorvideo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 存储根目录放置input.mp4的视频，执行分离，就可以分离出，纯音频和纯视频文件
 */
public class MediaExtractorActivity extends AppCompatActivity {
    private static final String TAG = "MediaExtractorActivity";
    final static int TIMEOUT_USEC = 0;
    private final String mSdCard = Environment.getExternalStorageDirectory().getPath();
    private View mExtractorButton;


    List<String> mPermissionList = new ArrayList<>();
    private final int mRequestCode = 100;//权限请求码
    private View mAudioToPcm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_media_extractor);
        if (Build.VERSION.SDK_INT >= 23) {//6.0才用动态权限
            initPermission();
        }

        mExtractorButton = findViewById(R.id.extractor_video);
        mAudioToPcm = findViewById(R.id.audio_to_pcm);

        mExtractorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extractorVideoAndAudio();
            }
        });
        mAudioToPcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        audioToPcmThenToWav();
                    }
                }).start();
            }
        });
    }

    private void audioToPcmThenToWav() {
        MediaExtractor mediaExtractor = new MediaExtractor();
        try {
            mediaExtractor.setDataSource(mSdCard + "/outputaudio.m4a");
            FileOutputStream fileOutputStream = new FileOutputStream(mSdCard + "/audio_output_pcm.pcm");

            int trackCount = mediaExtractor.getTrackCount();
            MediaFormat audioTrackFormat = null;
            int trackExtractorIndex = -1;
            for (int i = 0; i < trackCount; i++) {
                MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                String keyMime = trackFormat.getString(MediaFormat.KEY_MIME);
                if (keyMime.startsWith("audio")) {
                    trackExtractorIndex = i;
                    audioTrackFormat = trackFormat;
                    break;
                }
            }
            mediaExtractor.selectTrack(trackExtractorIndex);

            Log.i(TAG, "createDecoderByType");
            MediaCodec decoderByType = MediaCodec.createDecoderByType(audioTrackFormat.getString(MediaFormat.KEY_MIME));
            decoderByType.configure(audioTrackFormat, null, null, 0);
            decoderByType.start();


            MediaMuxer mediaMuxer = new MediaMuxer(mSdCard + "/audio_pcm_muxer_to m4a.m4a", MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            int audioTrackIndex = mediaMuxer.addTrack(audioTrackFormat);
            mediaMuxer.start();


            ByteBuffer[] inputBuffers = decoderByType.getInputBuffers();
            MediaCodec.BufferInfo outputBufferInfo = new MediaCodec.BufferInfo();

            boolean inputDone = false;
            boolean codeOver = false;
            Log.i(TAG, "onCreate");
            while (!codeOver) {
                //从音频文件中取出数据存入编码器
                Log.i(TAG, "while begain");
                if (!inputDone){
                    Log.i(TAG, "dequeueInputBuffer");
                    for (int i = 0; i < inputBuffers.length; i++) {
                        int inputBufferIndex = decoderByType.dequeueInputBuffer(TIMEOUT_USEC);
                        if (inputBufferIndex >= 0) {
                            int sampleSize = mediaExtractor.readSampleData(inputBuffers[inputBufferIndex], 0);
                            if (sampleSize < 0) {
                                Log.i(TAG, " inputDone = true;");
                                inputDone = true;
                                decoderByType.queueInputBuffer(inputBufferIndex, 0, 0, 0L, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                            } else {
                                decoderByType.queueInputBuffer(inputBufferIndex, 0, sampleSize, mediaExtractor.getSampleTime(), mediaExtractor.getSampleFlags());
                                mediaExtractor.advance();
                            }
                        }
                    }
                }


                //从编码其中取出编码后的数据存入文件
                boolean outputDone = false;
                byte[] chunkPCM;

                while (!outputDone) {
                    Log.i(TAG, "dequeueOutputBuffer");
                    int outputBufferIndex = decoderByType.dequeueOutputBuffer(outputBufferInfo, TIMEOUT_USEC);
                    if (outputBufferIndex < 0) {
                        Log.i(TAG, "outputBufferIndex < 0");
                        break;
                    } else if (outputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {
                        //没有可用的解码器
                        Log.i(TAG, " outputDone = true;");
                        outputDone = true;
                    } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                        Log.i(TAG, "INFO_OUTPUT_BUFFERS_CHANGED");
                        ByteBuffer[] outputBuffers = decoderByType.getOutputBuffers();
                    } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                        Log.i(TAG, "INFO_OUTPUT_FORMAT_CHANGED");
                        MediaFormat outputFormat = decoderByType.getOutputFormat();
                    } else {
                        ByteBuffer outputBuffer = decoderByType.getOutputBuffer(outputBufferIndex);
                        Log.i(TAG, "getOutputBuffer ");

                        //pcm通过muxer保存为音频
                        mediaMuxer.writeSampleData(audioTrackIndex, outputBuffer, outputBufferInfo);

                        //pcm保存为file文件
                        chunkPCM = new byte[outputBufferInfo.size];
                        outputBuffer.get(chunkPCM);
                        outputBuffer.clear();
                        fileOutputStream.write(chunkPCM);
                        fileOutputStream.flush();


                        decoderByType.releaseOutputBuffer(outputBufferIndex,false);
                        if((outputBufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0){
                            decoderByType.stop();
                            decoderByType.release();
                            mediaExtractor.release();
                            mediaMuxer.stop();
                            mediaMuxer.release();
                            outputDone = true;
                            codeOver = true;
                        }
                    }
                }
            }
            Log.i(TAG, " end aac解码为pcm ");
            fileOutputStream.close();


            //给保存的pcm添加头，方法一
            int bufferSize = AudioRecord.getMinBufferSize(48000,1,AudioFormat.ENCODING_PCM_16BIT) *2;
            PCMCovWavUtil pcmCovWavUtil = new PCMCovWavUtil(48000, 1, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
            Log.i(TAG, "convertWaveFile();");
            pcmCovWavUtil.convertWaveFile();
            Log.i(TAG, "pcm to wav 1 end");

            //给保存的pcm添加头，方法二
            convertPcm2Wav(mSdCard + "/audio_output_pcm.pcm",
                    mSdCard + "/audio_output_wav.wav",
                    48000,
                    1,
                    8);


            Log.i(TAG, "pcm to wav 2 end");

            Looper.prepare();
            Toast.makeText(MediaExtractorActivity.this, "aac解码为pcm ， 成功！！！", Toast.LENGTH_LONG).show();
            Looper.loop();
        } catch (IOException e) {
            e.printStackTrace();

        }
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
            ByteBuffer buffer = ByteBuffer.allocate(maxInputSize);
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
            ByteBuffer audioBuffer = ByteBuffer.allocate(audioMaxInputSize);
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
        } else {
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
            } else {
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


    /**
     * PCM文件转WAV文件
     * @param inPcmFilePath 输入PCM文件路径
     * @param outWavFilePath 输出WAV文件路径
     * @param sampleRate 采样率，例如44100
     * @param channels 声道数 单声道：1或双声道：2
     * @param bitNum 采样位数，8或16
     */
    public static void convertPcm2Wav(String inPcmFilePath, String outWavFilePath, int sampleRate,
                                      int channels, int bitNum) {

        FileInputStream in = null;
        FileOutputStream out = null;
        byte[] data = new byte[1024];

        try {
            //采样字节byte率
            long byteRate = sampleRate * channels * bitNum / 8;

            in = new FileInputStream(inPcmFilePath);
            out = new FileOutputStream(outWavFilePath);

            //PCM文件大小
            long totalAudioLen = in.getChannel().size();

            //总大小，由于不包括RIFF和WAV，所以是44 - 8 = 36，在加上PCM文件大小
            long totalDataLen = totalAudioLen + 36;

            writeWaveFileHeader(out, totalAudioLen, totalDataLen, sampleRate, channels, byteRate);

            int length = 0;
            while ((length = in.read(data)) > 0) {
                out.write(data, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 输出WAV文件
     * @param out WAV输出文件流
     * @param totalAudioLen 整个音频PCM数据大小
     * @param totalDataLen 整个数据大小
     * @param sampleRate 采样率
     * @param channels 声道数
     * @param byteRate 采样字节byte率
     * @throws IOException
     */
    private static void writeWaveFileHeader(FileOutputStream out, long totalAudioLen,
                                            long totalDataLen, int sampleRate, int channels, long byteRate) throws IOException {
        byte[] header = new byte[44];
        header[0] = 'R'; // RIFF
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);//数据大小
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';//WAVE
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        //FMT Chunk
        header[12] = 'f'; // 'fmt '
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';//过渡字节
        //数据大小
        header[16] = 16; // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        //编码方式 10H为PCM编码格式
        header[20] = 1; // format = 1
        header[21] = 0;
        //通道数
        header[22] = (byte) channels;
        header[23] = 0;
        //采样率，每个通道的播放速度
        header[24] = (byte) (sampleRate & 0xff);
        header[25] = (byte) ((sampleRate >> 8) & 0xff);
        header[26] = (byte) ((sampleRate >> 16) & 0xff);
        header[27] = (byte) ((sampleRate >> 24) & 0xff);
        //音频数据传送速率,采样率*通道数*采样深度/8
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        // 确定系统一次要处理多少个这样字节的数据，确定缓冲区，通道数*采样位数
        header[32] = (byte) (channels * 16 / 8);
        header[33] = 0;
        //每个样本的数据位数
        header[34] = 16;
        header[35] = 0;
        //Data chunk
        header[36] = 'd';//data
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        out.write(header, 0, 44);
    }
}
