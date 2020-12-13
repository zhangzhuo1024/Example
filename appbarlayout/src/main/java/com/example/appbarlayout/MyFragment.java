package com.example.appbarlayout;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

public class MyFragment extends Fragment {

    private String mTitle;
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerAdapter recyclerAdapter;

    private ArrayList<String> mData;
    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.fragment,container,false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        smartRefreshLayout = view.findViewById(R.id.smart_refresh_layout);
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mData.add("222222222222222222");
                        mData.add("222222222222222222");
                        mData.add("222222222222222222");
                        mData.add("222222222222222222");
                        mData.add("222222222222222222");
                        recyclerAdapter.notifyItemRangeInserted(mData.size() - 6,5);
                        smartRefreshLayout.finishLoadMore();
                    }
                }, 2000);
            }
        });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mData.clear();
                        mData.add("3333333333333333333333333");
                        mData.add("3333333333333333333333333");
                        mData.add("3333333333333333333333333");
                        mData.add("3333333333333333333333333");
                        mData.add("3333333333333333333333333");
                        mData.add("3333333333333333333333333");
                        mData.add("3333333333333333333333333");
                        mData.add("3333333333333333333333333");
                        recyclerAdapter.notifyDataSetChanged();
                        smartRefreshLayout.finishRefresh();
                    }
                }, 2000);
            }
        });

        smartRefreshLayout.setDisableContentWhenRefresh(true);
        smartRefreshLayout.setDisableContentWhenLoading(true);
        mData = new ArrayList<>();
        mData.add("111111111111111111111");
        mData.add("111111111111111111111");
        mData.add("111111111111111111111");
        mData.add("111111111111111111111");
        mData.add("111111111111111111111");
        mData.add("111111111111111111111");
        mData.add("111111111111111111111");
        mData.add("111111111111111111111");
        mData.add("111111111111111111111");
        mData.add("111111111111111111111");
        mData.add("111111111111111111111");
        mData.add("111111111111111111111");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter=new RecyclerAdapter(getActivity(),mData);
        recyclerView.setAdapter(recyclerAdapter);
        return view;
    }

    public String getmTitle() {
        return mTitle;
    }
}