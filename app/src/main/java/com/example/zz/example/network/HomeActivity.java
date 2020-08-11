package com.example.zz.example.network;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.zz.example.R;
import com.example.zz.example.network.bean.WangYiNews;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends Activity {

    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    ArrayList<WangYiNews> mNewsList = new ArrayList<>();
    private NewsService mNewsService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initData();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerViewAdapter = new MyRecyclerViewAdapter();
        recyclerView.setAdapter(myRecyclerViewAdapter);

        RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                int size = mNewsList.size();
                Call<ResponseBody> wangYiNews = mNewsService.getWangYiNews(1, size);
                wangYiNews.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //运行在主线程
                        int code = response.code();
                        ResponseBody body = response.body();
                        try {
                            String string = body.string();
                            ArrayList<WangYiNews> tempList = parseJsonWithJsonObject(string);
                            diffArraylist(tempList);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        refreshlayout.finishLoadMore(false);
                    }
                });
                refreshlayout.finishLoadMore(true);




                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                int size = mNewsList.size();
                Call<ResponseBody> wangYiNews = mNewsService.getWangYiNews(1, size + 10);
                wangYiNews.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //运行在主线程
                        int code = response.code();
                        ResponseBody body = response.body();
                        try {
                            String string = body.string();
                            ArrayList<WangYiNews> tempList = parseJsonWithJsonObject(string);
                            mNewsList.clear();
                            mNewsList.addAll(tempList);
                            myRecyclerViewAdapter.updateData(mNewsList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        refreshlayout.finishLoadMore(false);
                    }
                });
                refreshlayout.finishLoadMore(true);

                //refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    private void diffArraylist(ArrayList<WangYiNews> tempList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return mNewsList.size();
            }

            @Override
            public int getNewListSize() {
                return tempList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldItemPosition == newItemPosition;
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                WangYiNews wangYiNews1 = mNewsList.get(oldItemPosition);
                WangYiNews wangYiNews2 = tempList.get(newItemPosition);
                return wangYiNews1.equals(wangYiNews2);
            }
        }, true);
        diffResult.dispatchUpdatesTo(myRecyclerViewAdapter);
    }

    private void initData() {
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder.baseUrl("https://api.apiopen.top/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mNewsService = retrofit.create(NewsService.class);
        Call<ResponseBody> wangYiNews = mNewsService.getWangYiNews(1, 10);
        wangYiNews.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //运行在主线程
                int code = response.code();
                ResponseBody body = response.body();
                try {
                    String string = body.string();
                    ArrayList<WangYiNews> tempList = parseJsonWithJsonObject(string);
                    mNewsList.clear();
                    mNewsList.addAll(tempList);
                    myRecyclerViewAdapter.updateData(mNewsList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private ArrayList<WangYiNews> parseJsonWithJsonObject(String string) throws IOException {
        String responseData = string;
        ArrayList<WangYiNews> tempList = new ArrayList<>();
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
                String path = jsonObject.getString("path");
                String image = jsonObject.getString("image");
                String title = jsonObject.getString("title");
                String passtime = jsonObject.getString("passtime");
                WangYiNews wangYiNews = new WangYiNews(path, image, title, passtime);
                tempList.add(wangYiNews);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tempList;
    }
}
