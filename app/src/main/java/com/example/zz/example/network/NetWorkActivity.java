package com.example.zz.example.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zz.example.R;
import com.example.zz.example.network.bean.JsonBean;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * 参考博客地址：https://blog.csdn.net/u011082160/article/details/90773293
 * 原博客中联网地址不可用，使用另外的三房免费api进行测试，免费api联网地址：https://www.kancloud.cn/lizhixuan/free_api/1159536
 * 如实时段子接口，GET请求地址为：https://api.apiopen.top/getJoke，
 * 可以在网页中设置获取的段子信息，如设置第1页数据，获取3条，类型是text，就可以获取到结果，
 * 也可以网页直接设置筛选后访问地址https://api.apiopen.top/getJoke?page=1&count=3&type=text
 * 下面类中测试通过okhttp和retrofit进行联网和获取数据
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
    private Button mOkHttpEnqueuePost;
    private TextView mOkHttpEnqueueResultPost;


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

        mOkHttpEnqueuePost = findViewById(R.id.okhttp_enqueue_post);
        mOkHttpEnqueueResultPost = findViewById(R.id.okhttp_enqueue_post_result);
        mOkHttpEnqueueResultPost.setMovementMethod(new ScrollingMovementMethod());

        mOkHttpEnqueue.setOnClickListener(v -> {

            //OkHttp异步请求--对应enqueue方法
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.connectTimeout(10, TimeUnit.SECONDS);
//            builder.addInterceptor(incepter);
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
                    for (int i = 0; i < stuList.size(); i++) {
                        s = s + stuList.get(i) + "  ";
                    }

                    String finalS = s;
                    mHandler.post(() -> {
                        //通过handler发送到主线程更新ui
                        //注意：response.body().string();      string不能调用两次 被调用一次就关闭了，这里调用两次会报异常
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

            //OkHttp同步请求--对应execute方法,运行在主线程会报错
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();
            Request.Builder builder1 = new Request.Builder();
            builder1.url(mUrlApi);
            Request request = builder1.build();

            try {
                //同步请求会阻塞UI线程，由于是在主线程执行网络请求，会报错，所以需要在子线程运行
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
//                    .client(httpClient)  //添加httpClient，通过httpClient可以设置超时、拦截器
//                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            NewsService mService = retrofit.create(NewsService.class);
            //此处我们添加的返回数据类型是ResponseBody，因为不知道服务其中的数据类型是怎样定义的，所以这样获取的和okhttp获取的是一样，需要自己json转对象或者集合
            //如果此处像zztakeout的知道数据类型，可以按照服务器的数据类型新建实例对象，如ResponseInfo这样的进行获取，获取之后可以直接转化成集合或者对象
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
                //同步请求会阻塞UI线程，由于是在主线程执行网络请求，会报错，使用时必须在子线程
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
                    .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更新UI，注意！！！，此处容易导包导错，要导rxandroid2.1的，不是1.3的，因为别的包里面引入了1.3所以可能存在误导
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

        mOkHttpEnqueuePost.setOnClickListener(v -> {
            okHttpPost();

        });

        easyJsonString();

    }


    /**
     * 测试okhttp的post方法
     */
    private void okHttpPost() {
        String url = "http://api.k780.com/?app=weather.history";//
        // &weaid=1&date=2018-08-13&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json";

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("weaid", "1")
                .add("date", "2018-08-13")
                .add("appkey", "10003")
                .add("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4")
                .add("format", "json")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                ResponseBody body = response.body();
                try {
                    String string = body.string();
                    //发送到主线程更新text
                    mHandler.post(() -> {
                        mOkHttpEnqueueResultPost.setText("收到的结果是：" +
                                "\nresponse.code() = " + code +
                                "\nresponse.body().string() = " + string);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    ArrayList<JsonBean> stuList = new ArrayList<>(20);

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
        //网络请求的返回结果response的body()数据，理论上可以是各种各样的数据，有可能是xml数据，也有可能是json数据；由于json数据好用，所以绝大多少大家约定都用json
        //如上面的response.body().string();返回的就是一个json字符串；json字符串的特点就是，字符串都是key：value的形式，value可以是array、int、String等类型
        //也可能是各种类型组合起来的，解析时，参考下面 orgJson2Bean() 这个方法进行解析

        try {
            JSONArray jsonArray = null;
            try {
//                JSONArray 将字符串直接解析为json数组，要求responseData的数据是 [{id=001,name=张三},{id=002,name=李四}] 中括号包裹里面大括号数组
//                jsonArray = new JSONArray(responseData);

                //JSONObject 将字符串转化为json对象，再从json对象中 通过关键字获取到对应的数组
                JSONObject jsonObject = new JSONObject(responseData);
                jsonArray = jsonObject.getJSONArray("result");


                int code = jsonObject.getInt("code");
                String message = jsonObject.optString("message");
                Log.e("eeeeee", "message = " + message + " code " + code);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("sid");
                String name = jsonObject.getString("text");
                JsonBean jsonBean = new JsonBean();
                jsonBean.setName(name);
                jsonBean.setNum(id);
                stuList.add(jsonBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * json字符串转化有自带的org.Json、谷歌出品的Gson、FastJson、Jackson、Json-Lib等方式
     * 这里只对org.Json和Gson的使用做说明
     */
    //直接使用JSON相关的类进行构造json字符串
    private void easyJsonString() {
        try {
            //JSONObject构造1，使用key：value赋值形式
            JSONObject obj = new JSONObject();
            System.out.println(obj.toString());//输出空对象: {}
            obj.put("key1", 1);
            obj.put("key2", 2);
            System.out.println(obj.toString());//输出: {"key2":2,"key1":1}

            //JSONObject构造2，参数传入json格式的字符串
            String j = "{\"1000\":2,\"100\":111}";
            JSONObject obj2 = new JSONObject(j);
            System.out.println(obj2.toString());

            //嵌套复杂Json字符串
            JSONObject obj3 = new JSONObject();
            System.out.println(obj3.toString());//输出空对象: {}
            obj3.put("key3", 3);
            obj3.put("key4", obj);
            System.out.println(obj3.toString());//输出: {"key3":3,"key4":{"key1":1,"key2":2}}

            //JSONArray 是用来存储JSONObject的数组
            //JSONArray构造1
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(obj);
            jsonArray.put(obj2);
            System.out.println(jsonArray.toString()); //输出: [{"key1":1,"key2":2},{"key1":1,"key2":2}]

            //JSONArray构造2，传入json数组格式的字符串
            String jsonArrStr = "[{\"1000\":2,\"100\":111},{\"1000\":2,\"100\":222}]";
            JSONArray array = new JSONArray(jsonArrStr);
            System.out.println(array.toString());//输出: [{"1000":2,"100":111},{"1000":2,"100":222}]


            //JSONArray遍历
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObj = array.getJSONObject(i);
                System.out.println(jsonObj.toString());
            }

            //JSONObject属性遍历
            Iterator<String> it = obj2.keys();
            while (it.hasNext()) {
                String key = it.next();
                System.out.println(key + "=" + (int) obj2.get(key));
            }

            //获取JSONObject对象某个属性的值
            String key = "key1";
            if (obj.has(key)) {
                System.out.println(obj.get(key));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //通过对象、集合转为json
    private void bean2OrgJson() {
        //对象转json
        JsonBean jsonBean = new JsonBean("张三", 18);
        String s = new Gson().toJson(jsonBean);
        Log.e("NetWorkActivity", "bean2OrgJson  s = " + s);

        //集合转json
        List list = new ArrayList();
        list.add(new JsonBean("zhangSan", 13));
        list.add(new JsonBean("liSi", 14));//创建json集合
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        System.out.println(jsonString);

    }

    //json转为对象、集合
    private void orgJson2Bean() {
        //jsonstr是直接粘贴的json字符串，注意引号是不一样的
        String jsonstr = "{“id”:“66”,“name”:“helloword”,“age”:“18”}";
        //手动输入的json字符串，输入不了上面的引号的，需要加转义字符
        String orgJson = "{\"name\":\"张三\",\"age\":\"18\"}";

        //1、json转对象，要求最外面是大括号
        try {
            JSONObject jsonObject = new JSONObject(orgJson);
            JsonBean jsonBean = new JsonBean();
            jsonBean.setName(jsonObject.getString("name"));//getString没有值时会抛异常
            jsonBean.setAge(jsonObject.optInt("age"));//optxxx这种方式没有值时，返回空
            Log.e("NetWorkActivity", "orgJson2Bean  name = " + jsonBean.getName());
            Log.e("NetWorkActivity", "orgJson2Bean  name = " + jsonBean.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //2、json转数组(集合)，要求json数据是 [{id=001,name=张三},{id=002,name=李四}] 这种类型，中括号包裹里面大括号数组
        String jsonArrayString = "[{\"语文\":77},{\"数学\":99}]";
        try {
            JSONArray jsonArray = new JSONArray(jsonArrayString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("sid");
                String name = jsonObject.getString("text");
                JsonBean jsonBean = new JsonBean();
                jsonBean.setName(name);
                jsonBean.setNum(id);
                stuList.add(jsonBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //3、复杂情况，分步解析，从外到内进行解析，外面是大括号就解析对象，外面是中括号就解析数组
        String json = "{\"name\":\"张三\",\"age\":\"18\",\"result\":\"[{\"语文\":\"77\"},{\"数学\":\"99\"}]\"}";
        try {
            JSONObject jsonObject = new JSONObject(json);
            JsonBean jsonBean = new JsonBean();
            jsonBean.setName(jsonObject.getString("name"));//getString没有值时会抛异常
            jsonBean.setAge(jsonObject.optInt("age"));//optxxx这种方式没有值时，返回空
            JSONArray result = jsonObject.getJSONArray("result");
            HashMap<String, String> s = new HashMap<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject o = result.getJSONObject(i);//注意是getJSONObject，而不是get！！！
                String y = o.getString("chinese");
                String sx = o.getString("math");
                s.put("chinese", y);
                s.put("math", sx);
            }
            jsonBean.setResult(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //4、使用框架gson进行转化
        String j = "{\"name\":\"张三\",\"age\":\"18\"}";
        Gson gson = new Gson();
        JsonBean jsonBean = gson.fromJson(j, JsonBean.class);
    }

}