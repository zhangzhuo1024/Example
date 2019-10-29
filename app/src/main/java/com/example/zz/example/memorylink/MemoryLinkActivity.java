package com.example.zz.example.memorylink;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.zz.example.R;

public class MemoryLinkActivity extends Activity {
    private static Link linkInstance;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_link);

        //懒汉式单例
//        TestUtil instance = TestUtil.getInstance(this);

        //内部类
//        LocalMethod localMethod = new LocalMethod();

//        if (linkInstance == null) {
//            linkInstance = new Link();
//        }

        //子线程
//        MyThread myThread = new MyThread();
//        myThread.start();
    }
    class Link {
        public void dosomething() {

        }
    }

    private class LocalMethod {
        public void doSomeThing() {

        }
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
