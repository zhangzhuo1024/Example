package com.example.zz.zzplayer.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.example.zz.zzplayer.R
import com.example.zz.zzplayer.adapter.HomeAdapter
import com.example.zz.zzplayer.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {
    override fun initView(): View? {
        val inflate = View.inflate(context, R.layout.fragment_home, null)
        return inflate
    }

    override fun initListener() {
        recycleView.layoutManager = LinearLayoutManager(context)
        val homeAdapter = HomeAdapter()
        recycleView.adapter = homeAdapter
    }

}
