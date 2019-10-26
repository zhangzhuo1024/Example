package com.example.zz.zzplayer.ui.fragment

import android.graphics.Color
import android.view.TextureView
import android.view.View
import android.widget.TextView
import com.example.zz.zzplayer.base.BaseFragment

class VbangFragment: BaseFragment() {
    override fun initView(): View? {
        val tv = TextView(context)
        tv.text = javaClass.simpleName
        tv.setTextColor(Color.RED)
        return tv
    }
}