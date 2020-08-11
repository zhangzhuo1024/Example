package com.example.zz.example.network;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zz.example.DatabaseActivity;
import com.example.zz.example.MainActivity;
import com.example.zz.example.R;
import com.example.zz.example.network.bean.WangYiNews;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyHolder> {

    private ArrayList<WangYiNews> mNewsList = new ArrayList<>();

    public MyRecyclerViewAdapter() {

    }

    public void updateData(ArrayList<WangYiNews> mNewsList) {
        this.mNewsList = mNewsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.recycler_view_item, null);
        MyHolder myHolder = new MyHolder(inflate);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.setData(mNewsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private TextView recyclerTitle;
        private ImageView imageTitle;
        private TextView passtimeTitle;
        private WangYiNews mWangYiNews;


        public MyHolder(View itemView) {
            super(itemView);
            this.mContext = itemView.getContext();
            recyclerTitle = itemView.findViewById(R.id.title_recycler);
            imageTitle = itemView.findViewById(R.id.image_recycler);
            passtimeTitle = itemView.findViewById(R.id.passtime_recycler);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, NewsHtmlActivity.class);
                intent.putExtra("url", mWangYiNews.getPath());
                mContext.startActivity(intent);


            });

            itemView.setOnLongClickListener(view -> {
                //使用默认浏览器打开
                Uri uri = Uri.parse(mWangYiNews.getPath());  //url为你要链接的地址
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
                return false;
            });
        }

        public void setData(WangYiNews wangYiNews) {
            this.mWangYiNews = wangYiNews;
            recyclerTitle.setText(wangYiNews.getTitle());
            passtimeTitle.setText(wangYiNews.getPasstime());
            String path = wangYiNews.getImage();
            if (path != null && path.trim().length() != 0) {
                Picasso.with(mContext).load(wangYiNews.getImage()).into(imageTitle);
            }
        }
    }
}
