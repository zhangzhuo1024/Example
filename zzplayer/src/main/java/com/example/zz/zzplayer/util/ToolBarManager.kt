package com.example.zz.zzplayer.util

import android.content.Intent
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import com.example.zz.zzplayer.R
import com.example.zz.zzplayer.ui.activity.SettingsActivity

interface ToolBarManager {
    val toolbar:Toolbar

    fun initMainToolBar(){
        toolbar.setTitle("ZZplayer")
        toolbar.inflateMenu(R.menu.main)
        toolbar.setOnMenuItemClickListener{item->
        when(item?.itemId){
                    R.id.setting-> {
                    toolbar.context.startActivity(Intent(toolbar.context, SettingsActivity::class.java))
                    }
                }
         true
        }
    }

    fun initSettingsToolBar(){
        toolbar.setTitle("设置")
    }
}