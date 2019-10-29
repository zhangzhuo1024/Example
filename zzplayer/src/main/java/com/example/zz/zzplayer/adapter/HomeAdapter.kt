package com.example.zz.zzplayer.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.zz.zzplayer.widget.HomeItemVew

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):HomeHolder{
        return HomeHolder(HomeItemVew(parent?.context))
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
    }

    class HomeHolder(itemView:View):RecyclerView.ViewHolder(itemView)
}