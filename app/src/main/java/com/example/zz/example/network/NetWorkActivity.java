package com.example.zz.example.network;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.zz.example.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 参考文献：https://blog.csdn.net/u011082160/article/details/90773293
 */

public class NetWorkActivity extends AppCompatActivity {

    public static final String mUrlApi = "https://api.apiopen.top/getJoke?page=1&count=2&type=video";
    private Button mOkHttpEnqueue;
    private TextView mOkHttpEnqueueResult;
    private Button mOkHttpExecute;
    private TextView mOkHttpExecuteResult;
    private Button mRetrofitEnqueue;
    private TextView mRetrofitEnqueueResult;
    private Button mRetrofitExecute;
    private TextView mRetrofiExecuteResult;
    private Handler mHandler;

    public static final String TYPE_GIRLS = "jandan.get_ooxx_comments";


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

        mOkHttpExecute = findViewById(R.id.okhttp_execute);
        mOkHttpExecuteResult = findViewById(R.id.okhttp_execute_result);

        mRetrofitEnqueue = findViewById(R.id.retrofit_enqueue);
        mRetrofitEnqueueResult = findViewById(R.id.retrofit_enqueue_result);

        mRetrofitExecute = findViewById(R.id.retrofit_execute);
        mRetrofiExecuteResult = findViewById(R.id.retrofit_execute_result);

        mOkHttpEnqueue.setOnClickListener(v -> {

            //OkHttp异步请求--对应enqueue方法
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();
            Request.Builder builder1 = new Request.Builder();
            builder1.url("https://api.apiopen.top/getJoke?page=1&count=2&type=video");
            Request request = builder1.build();
            okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    //运行在子线程
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
                        mOkHttpEnqueueResult.setText("收到的结果是：" + finalS);
                    });
                }
            });
        });
        mOkHttpExecute.setOnClickListener(v -> {

            //OkHttp同步请求--对应execute方法,运行在主线程
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();
            Request.Builder builder1 = new Request.Builder();
            builder1.url("https://api.douban.com/v2/book/search?q=金瓶梅&tag=&start=0&count=1");
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

            JanDanApiService mService = retrofit.create(JanDanApiService.class);
            retrofit2.Call<BelleEntity> detailData = mService.getDetailData(mUrlApi, TYPE_GIRLS, 1);
            detailData.enqueue(new Callback<BelleEntity>() {
                @Override
                public void onResponse(retrofit2.Call<BelleEntity> call, retrofit2.Response<BelleEntity> response) {
                    //运行在主线程
                    Log.e("111111", response + "");
                    mRetrofitEnqueueResult.setText(response.message());
                }

                @Override
                public void onFailure(retrofit2.Call<BelleEntity> call, Throwable t) {
                    //运行在主线程
                }
            });
        });


        mRetrofitExecute.setOnClickListener(v -> {
            //Retrofit同步请求--对应enqueue方法
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.douban.com/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            BookService bookService = retrofit.create(BookService.class);
            retrofit2.Call<Book> call = bookService.getSearchBook("金瓶梅", null, 1, 1);
            try {
                //由于是在主线程执行网络请求，会报错
                retrofit2.Response<Book> execute = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



    }

    ArrayList<String> idList = new ArrayList<>(20);
    ArrayList<String> nameList = new ArrayList<>(20);

    private void parseJsonWithJsonObject(Response response) throws IOException {
        String responseData = response.body().string();
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
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id=jsonObject.getString("sid");
                String name=jsonObject.getString("text");
                idList.add(id);
                nameList.add(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}