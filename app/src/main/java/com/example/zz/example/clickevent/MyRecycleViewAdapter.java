package com.example.zz.example.clickevent;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
class MyRecycleViewAdapter extends RecyclerView.Adapter {
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new TitleHolder(View.inflate(parent.getContext(), R.layout.recycler_title_pager, null));
        } else {
            View inflate = View.inflate(parent.getContext(), R.layout.recycler_view_item, null);
            return new MyRecycleHolder(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            ((TitleHolder) holder).bindData();
        } else {
            ((MyRecycleHolder) holder).mTitle.setText("这是recycleview的第 " + position + " 个条目");
        }
    }


    @Override
    public int getItemCount() {
        return 10;
    }

    private class MyRecycleHolder extends RecyclerView.ViewHolder {

        private final TextView mTitle;

        public MyRecycleHolder(View inflate) {
            super(inflate);
            mTitle = inflate.findViewById(R.id.title_recycler);
        }
    }

    private class TitleHolder extends RecyclerView.ViewHolder {

        private final ViewPager innerPager;

        public TitleHolder(View inflate) {
            super(inflate);
            innerPager = inflate.findViewById(R.id.inner_pagger);
        }

        public void bindData(){
            innerPager.setAdapter(new InnerPagerAdapter());
        }
    }

    private class InnerPagerAdapter extends PagerAdapter {
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
            TextView textView = new TextView(container.getContext());
            textView.setTextSize(20);
            textView.setText("轮播图第 " + position + " 个界面");

            container.addView(textView);
            return textView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(((View) object));
        }
    }
}
