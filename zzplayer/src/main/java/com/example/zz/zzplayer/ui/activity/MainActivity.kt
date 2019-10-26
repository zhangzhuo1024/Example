package com.example.zz.zzplayer.ui.activity

import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.Choreographer
import com.example.zz.zzplayer.R
import com.example.zz.zzplayer.base.BaseActivity
import com.example.zz.zzplayer.ui.fragment.FragmentUtil
import com.example.zz.zzplayer.ui.fragment.TestFragment
import com.example.zz.zzplayer.util.ToolBarManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find

class MainActivity : BaseActivity(), ToolBarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toobar) }

    //    override val toolbar: Toolbar = findViewById(R.id.toobar) 错误
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        initMainToolBar()

    }

    override fun initListener() {
        bottomBar.setOnTabSelectListener {
            val beginTransaction = supportFragmentManager.beginTransaction()
//            val testFragment = TestFragment()
//            val fragment = Fragment()
//            beginTransaction.hide(testFragment!!)
            val fragment = FragmentUtil.fragmentUtil.getFragment(it)!!
            beginTransaction.replace(R.id.container, fragment, it.toString())
            beginTransaction.commit()
        }
    }
}
