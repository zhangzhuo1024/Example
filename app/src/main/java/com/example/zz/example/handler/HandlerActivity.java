package com.example.zz.example.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.zz.example.R;

import java.lang.ref.WeakReference;

/**
 * handler作用：1、线程消息切换；2、延时执行或者循环执行（同线程或者不同线程）
 * handler缺点：内存泄漏
 * handler两种写法：写法一、写法二、简化写法，在发送延迟消息时都会导致内存泄漏，写法二使用匿名内部内在编写时就会提示内存泄漏风险
 * <p>
 * 解决内存泄漏：
 * 1、在destroy中将handler的所有消息全部清空，没有延时消息之后就不会导致activity释放不了，
 * 下面mHandler2实测，removeCallbacksAndMessages在destroy中调用内存不泄漏，不调用时，延时消息会导致内存泄漏
 * 2、使用静态handler，参考mHandler4，静态的handler对HandlerActivity是弱引用，不影响activity的回收
 */

public class HandlerActivity extends AppCompatActivity {

    private int n1 = 0;
    private int n2 = 0;

    //常规写法一：
    private Handler mHandler1 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            button1_num.setText("" + n1);
            switch (msg.what) {
                case 22:
                    int arg1 = msg.arg1;
                    String obj = (String) msg.obj;
                    Bundle data = msg.getData();
                    int num = data.getInt("num");
                    String name = data.getString("name");
                    String s = " " + arg1 + " " + obj + " " + num + " " + name;
                    button1_num.setText(s);
            }
            return false;
        }
    });

    //常规写法二：
    private Handler mHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            button2_num.setText("" + n2);
            n2++;
            mHandler2.sendEmptyMessageDelayed(0, 100);
        }
    };

    //简化写法：适用于在子线程随时更新界面
    private Handler mHandler3 = new Handler(Looper.getMainLooper());

    //谷歌标准版内存不泄漏写法，（上面的三种都可能出现内存泄漏）
    private final MyHandler mHandler4 = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<HandlerActivity> mActivity;

        public MyHandler(HandlerActivity activity) {
            mActivity = new WeakReference<HandlerActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HandlerActivity activity = mActivity.get();
            if (activity != null) {
                activity.button4_num.setText("不会内存泄漏handler发送的消息");
            }
        }
    }

    private static final Runnable sRunnable = new Runnable() {
        @Override
        public void run() {
            //静态方法内无法取到HandlerActivity的变量，这样使用不好刷新界面，不推荐
        }
    };

    Button button1;
    Button button2;
    Button button3;
    Button button4;

    TextView button1_num;
    TextView button2_num;
    TextView button3_num;
    TextView button4_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        button1_num = (TextView) findViewById(R.id.button1_num);
        button2_num = (TextView) findViewById(R.id.button2_num);
        button3_num = (TextView) findViewById(R.id.button3_num);
        button4_num = (TextView) findViewById(R.id.button4_num);

        //线程切换，在子线程中计算，发送到主线程进行更新界面
        button1.setOnClickListener(v -> {
            new Thread(() -> {
                //在子线程计算
                n1 = n1 + 1;

                //更新方式一：通过Runnable 进行主线程更新
                mHandler1.post(() -> {
                    button1_num.setText("" + n1);
                });

                //更新方式二：发送empty消息进行主线程更新
                n1 = n1 + 2;
                mHandler1.sendEmptyMessage(11);

                //更新方式三：发送消息体区分不同消息进行更新
                Message message = mHandler1.obtainMessage();
                message.what = 22;  //消息id
                message.arg1 = 123;  //传递数字
                message.obj = "传递字符串";  //传递字符串
                Bundle bundle = new Bundle();   //传递对象
                bundle.putInt("num", 10);
                bundle.putString("name", "zhang");
                message.setData(bundle);
                mHandler1.sendMessage(message);
            }).start();
        });

        //延时执行或者循环执行（同线程不同线程均可）
        button2.setOnClickListener(v -> {
            mHandler2.sendEmptyMessageDelayed(0, 10000);
        });

        //简化版使用
        button3.setOnClickListener(v -> {
            mHandler3.post(() -> {
                button3_num.setText("简化版");
            });
        });

        //谷歌标准内存无泄漏版本
        button4.setOnClickListener(v -> {
            mHandler4.sendEmptyMessageDelayed(0, 3000);
            mHandler4.postDelayed(sRunnable, 10000);
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler2.removeCallbacksAndMessages(null);
    }
}
