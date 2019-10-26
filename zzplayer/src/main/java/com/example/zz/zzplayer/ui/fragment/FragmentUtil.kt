package com.example.zz.zzplayer.ui.fragment

import android.graphics.Color
import android.support.v4.app.Fragment
import android.view.TextureView
import android.view.View
import android.widget.TextView
import com.example.zz.zzplayer.R
import com.example.zz.zzplayer.base.BaseFragment

class FragmentUtil private constructor(){
    companion object{
        val fragmentUtil by lazy { FragmentUtil() }
//        val homeFragment by lazy { HomeFragment() }
//        val mvFragment by lazy { MvFragment() }
//        val vbangFragment by lazy { VbangFragment() }
//        val yuedanFragment by lazy { YuedanFragment() }
    }
    private val homeFragment by lazy { HomeFragment() }
    private val mvFragment by lazy { MvFragment() }
    private val vbangFragment by lazy { VbangFragment() }
    private val yuedanFragment by lazy { YueDanFragment() }

    fun getFragment(tabId:Int):BaseFragment?{
        when(tabId){
            R.id.tab_home->return homeFragment
            R.id.tab_mv->return mvFragment
            R.id.tab_vbang->return vbangFragment
            R.id.tab_yuedan->return yuedanFragment
        }
        return null
    }

}