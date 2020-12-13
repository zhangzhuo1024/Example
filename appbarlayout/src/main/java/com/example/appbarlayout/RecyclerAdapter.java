package com.example.appbarlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<String> mData;
    private Context mContext;

    public RecyclerAdapter(Context context,ArrayList<String> list){
        mData = list;
        mContext = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        MyViewHolder viewHolder =new MyViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textView;
        private ImageView imageView;
        public MyViewHolder(View itemview)
        {
            super(itemview);
            textView= (TextView) itemview.findViewById(R.id.content);
            imageView= (ImageView) itemview.findViewById(R.id.image);

        }
    }
}