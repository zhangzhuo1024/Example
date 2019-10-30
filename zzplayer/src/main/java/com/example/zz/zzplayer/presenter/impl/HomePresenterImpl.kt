package com.example.zz.zzplayer.presenter.impl

import com.example.heimaplayer.model.HomeItemBean
import com.example.heimaplayer.util.ThreadUtil
import com.example.zz.zzplayer.R.id.refreshLayout
import com.example.zz.zzplayer.presenter.interf.HomePresenter
import com.example.zz.zzplayer.util.URLProviderUtils
import com.example.zz.zzplayer.view.HomeView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type

class HomePresenterImpl(var homeView:HomeView) : HomePresenter {
    override fun loadDatas() {
            val path = URLProviderUtils.getHomeUrl(0, 20)
            val client = OkHttpClient()
            val request = Request.Builder().url(path)
                    .get()
                    .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    homeView.onError(e?.message)
                }

                override fun onResponse(call: Call, response: Response) {

//                    homeView.onSuccess()
                    val toString = response.body?.string()
                    var gson = Gson()
                    val list = gson.fromJson<List<HomeItemBean>>(toString, object : TypeToken<List<HomeItemBean>>() {}.type)
                    ThreadUtil.runOnMainThread(object : Runnable{
                        override fun run() {

                            homeView.loasSuccess(list)
                        }
                    })
//                    refreshLayout.isRefreshing = false
//                    mytoast("获取数据成功" + toString)
//                    println("获取数据成功" + toString);
                }

            })
    }

    override fun loadMore(offset: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}