package com.example.zz.example.clickevent;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zz.example.R;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/5/5
 */
class MyViewPagerAdapter extends PagerAdapter {

    private ClickEventActivity activity;
    private RecyclerView mRecycleView;

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View recycleview = inflater.inflate(R.layout.recycle_view_layout, null);
        mRecycleView = ((RecyclerView) recycleview.findViewById(R.id.recycler_view_in_viewpager));
        mRecycleView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        mRecycleView.setAdapter(new MyRecycleViewAdapter());
        TextView textView = new TextView(container.getContext());
        textView.setTextSize(60);
        textView.setText("这是第 " + position + " 个界面");

        if (position == 0) {
            container.addView(recycleview);
            return recycleview;
        } else {
            container.addView(textView);
            return textView;
        }

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(((View) object));
    }
}
