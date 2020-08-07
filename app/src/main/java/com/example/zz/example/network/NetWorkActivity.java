package com.example.zz.example.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zz.example.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * 参考文献：https://blog.csdn.net/u011082160/article/details/90773293
 */

public class NetWorkActivity extends AppCompatActivity {

    public static final String mUrlApi = "https://api.apiopen.top/";
    private Button mOkHttpEnqueue;
    private TextView mOkHttpEnqueueResult;
    private Button mOkHttpExecute;
    private TextView mOkHttpExecuteResult;
    private Button mRetrofitEnqueue;
    private TextView mRetrofitEnqueueResult;
    private Button mRetrofitExecute;
    private TextView mRetrofiExecuteResult;
    private Button mRetrofitRxjavaExecute;
    private TextView mRetrofiRxjavaExecuteResult;
    private Handler mHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work);

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {

                return false;
            }
        });

        mOkHttpEnqueue = findViewById(R.id.okhttp_enqueue);
        mOkHttpEnqueueResult = findViewById(R.id.okhttp_enqueue_result);
        mOkHttpEnqueueResult.setMovementMethod(new ScrollingMovementMethod());

        mOkHttpExecute = findViewById(R.id.okhttp_execute);
        mOkHttpExecuteResult = findViewById(R.id.okhttp_execute_result);

        mRetrofitEnqueue = findViewById(R.id.retrofit_enqueue);
        mRetrofitEnqueueResult = findViewById(R.id.retrofit_enqueue_result);
        mRetrofitEnqueueResult.setMovementMethod(new ScrollingMovementMethod());

        mRetrofitExecute = findViewById(R.id.retrofit_execute);
        mRetrofiExecuteResult = findViewById(R.id.retrofit_execute_result);


        mRetrofitRxjavaExecute = findViewById(R.id.retrofit_and_rxjava_execute);
        mRetrofiRxjavaExecuteResult = findViewById(R.id.retrofit_and_rxjava_execute_result);
        mRetrofiRxjavaExecuteResult.setMovementMethod(new ScrollingMovementMethod());

        mOkHttpEnqueue.setOnClickListener(v -> {

            //OkHttp异步请求--对应enqueue方法
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();
            Request.Builder builder1 = new Request.Builder();
            builder1.url(mUrlApi + "getJoke?page=1&count=2&type=text");
            Request request = builder1.build();
            okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    //运行在子线程
                    mHandler.post(() -> {
                        //通过handler发送到主线程更新ui

                        Toast.makeText(NetWorkActivity.this, " mOkHttpEnqueue call = " + call + "  e = " + e, Toast.LENGTH_SHORT).show();

                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //运行在子线程
                    parseJsonWithJsonObject(response);


                    String s = "";
                    for (int i = 0; i < nameList.size(); i++) {
                        s = s + nameList.get(i) + "  ";
                    }

                    String finalS = s;
                    mHandler.post(() -> {
                        //通过handler发送到主线程更新ui

                        mOkHttpEnqueueResult.setText("收到的结果是：" +
                                "\nresponse.code() = " + code +
                                "\nresponse.body() = " + body +
                                "\nresponse.message() = " + message +
                                "\nresponse.body().string() = " + string +
                                "\n每个条目的title = " + finalS);
                    });
                }
            });
        });
        mOkHttpExecute.setOnClickListener(v -> {

            //OkHttp同步请求--对应execute方法,运行在主线程
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();
            Request.Builder builder1 = new Request.Builder();
            builder1.url(mUrlApi);
            Request request = builder1.build();

            try {
                //由于是在主线程执行网络请求，会报错
                Response response = okHttpClient.newCall(request).execute();
                mOkHttpExecuteResult.setText(response.message());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        mRetrofitEnqueue.setOnClickListener(v -> {
            //Retrofit异步请求--对应enqueue方法
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(mUrlApi)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            NewsService mService = retrofit.create(NewsService.class);
            //此处我们添加的返回数据类型是ResponseBody，因为不知道服务其中的数据类型是怎样定义的
            //如果此处像zztakeout的知道数据类型，可以按照服务器的数据类型新建实例对象，如ResponseInfo这样的进行获取，获取之后可以直接转化成集合
            retrofit2.Call<ResponseBody> call = mService.getJokeList(1, 3, "text");
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    //运行在主线程
                    int code = response.code();
                    ResponseBody body = response.body();
                    try {
                        String string = body.string();
                        mRetrofitEnqueueResult.setText("收到的结果是：" +
                                "\nresponse.code() = " + code +
                                "\nresponse.body().string() = " + string);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                    //运行在主线程
                    Toast.makeText(NetWorkActivity.this, " mRetrofitEnqueue call = " + call + "  t = " + t, Toast.LENGTH_SHORT).show();
                }
            });
        });


        mRetrofitExecute.setOnClickListener(v -> {
            //Retrofit同步请求--对应enqueue方法
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(mUrlApi)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            NewsService mService = retrofit.create(NewsService.class);
            retrofit2.Call<ResponseBody> call = mService.getJokeList(1, 3, "text");
            try {
                //由于是在主线程执行网络请求，会报错
                retrofit2.Response<ResponseBody> execute = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mRetrofitRxjavaExecute.setOnClickListener(v -> {
            //Retrofit异步请求 结合rxjava 使用后接口返回类型不是Call，而是被观察者Obserable
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(mUrlApi)
                    //为了使Retrofit支持数据返回自动解析成实体，添加依赖：'com.squareup.retrofit2:converter-gson:2.5.0'
                    .addConverterFactory(GsonConverterFactory.create())
                    //使用RXjava，只需要在此加入RxJavaCallAdapterFactory,
                    // gradle里面要加入retrofit支持rajava的依赖'com.squareup.retrofit2:adapter-rxjava2:2.5.0'  ！！！ 注意不是adapter-rxjava:2.5.0'
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            NewsService mService = retrofit.create(NewsService.class);
            Observable<ResponseBody> observable = mService.getJokeList2(1, 3, "text");
            observable.subscribeOn(Schedulers.io())//请求数据的事件发生在io子线程
                    .observeOn(AndroidSchedulers.mainThread())//请求数据的事件发生在主线程，注意！！！，此处容易导包导错，要导rxandroid2.1的，不是1.3的，因为别的包里面引入了1.3所以可能存在误导
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                String string = responseBody.string();
                                mRetrofiRxjavaExecuteResult.setText("收到的结果是：" +
                                        "\nresponseBody.string() = " + string);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(NetWorkActivity.this, " mOkHttpEnqueue  " + "  e = " + e, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        });


    }

    ArrayList<String> idList = new ArrayList<>(20);
    ArrayList<String> nameList = new ArrayList<>(20);


    int code;
    ResponseBody body;
    String string;
    String message;

    private void parseJsonWithJsonObject(Response response) throws IOException {
        code = response.code();
        body = response.body();
        message = response.message();
        string = response.body().string();
        String responseData = string;
        try {
            JSONArray jsonArray = null;
            try {
//                JSONArray 将字符串直接解析为json数组，要求responseData的数据是 [{id=001,name=张三},{id=002,name=李四}] 中括号包裹里面大括号数组
//                jsonArray = new JSONArray(responseData);

                //JSONObject 将字符串转化为json对象，再从json对象中 通过关键字获取到对应的数组
                JSONObject jsonObject = new JSONObject(responseData);
                jsonArray = jsonObject.getJSONArray("result");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("sid");
                String name = jsonObject.getString("text");
                idList.add(id);
                nameList.add(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}