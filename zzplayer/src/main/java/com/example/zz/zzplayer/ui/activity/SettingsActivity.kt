package com.example.zz.zzplayer.ui.activity

import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.support.v7.widget.Toolbar
import com.example.zz.zzplayer.R
import com.example.zz.zzplayer.base.BaseActivity
import com.example.zz.zzplayer.util.ToolBarManager
import org.jetbrains.anko.find

class SettingsActivity : BaseActivity(),ToolBarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toobar) }

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initData() {

        initSettingsToolBar()
        val defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val boolean = defaultSharedPreferences.getBoolean("push", false)
    }

}