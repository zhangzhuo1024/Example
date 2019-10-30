package com.example.zz.zzplayer.ui.fragment

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.heimaplayer.model.HomeItemBean
import com.example.zz.zzplayer.R
import com.example.zz.zzplayer.adapter.HomeAdapter
import com.example.zz.zzplayer.base.BaseFragment
import com.example.zz.zzplayer.presenter.impl.HomePresenterImpl
import com.example.zz.zzplayer.view.HomeView
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() , HomeView {
    override fun onError(message: String?) {
        mytoast("获取数据失败")
    }

    override fun loasSuccess(list: List<HomeItemBean>?) {
        refreshLayout.isRefreshing = false
//        adapter.update(list)
    }

    val presenter by lazy { HomePresenterImpl(this) }

    override fun initView(): View? {
        val inflate = View.inflate(context, R.layout.fragment_home, null)
        return inflate
    }

    override fun initListener() {
        recycleView.layoutManager = LinearLayoutManager(context)
        val homeAdapter = HomeAdapter()
        recycleView.adapter = homeAdapter

        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE)
    }

    override fun initData() {
//        loadDatas()
        presenter.loadDatas()
    }




}
