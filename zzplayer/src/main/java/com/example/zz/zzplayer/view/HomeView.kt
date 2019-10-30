package com.example.zz.zzplayer.view

import com.example.heimaplayer.model.HomeItemBean

interface HomeView {
    fun onError(message: String?)
    fun loasSuccess(list: List<HomeItemBean>?)
}