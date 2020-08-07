package com.example.zz.example.pattern.rxjava;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.zz.example.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * https://www.jianshu.com/p/fce825833d36
 * <p>
 * RxJava 使用
 * Observable：被观察者
 * Observer：观察者
 * subscribe：订阅动作
 * 使用时，新建被观察者对象和观察者对象，通过subscribe将两个对象连接，之后就可以通过Observable对象发送事件给subscribe对象接受
 * <p>
 * observable.subscribe（observer）；
 */

public class RxjavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        findViewById(R.id.subscribeOn).setOnClickListener(v -> {
            subscribeEvent();
        });

    }

    private void subscribeEvent() {
        //第一步：创建被观察者，注意创建的泛型里面传入的类型
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 5; i++) {
                            emitter.onNext(i);
                        }
                    }
                }, 1000);

            }
        });

        //第二步：创建观察者， 注意泛型中的类型
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Toast.makeText(RxjavaActivity.this, "result : " + integer, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        //第三步：将观察者和被观察者联系起来，调用此代码后，就会触发observable中的subscrib发送事件，在observer中的onNext接收
        observable.subscribe(observer);
    }
}