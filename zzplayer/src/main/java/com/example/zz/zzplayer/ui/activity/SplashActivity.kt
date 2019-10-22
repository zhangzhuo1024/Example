package com.example.zz.zzplayer.ui.activity

import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.view.View
import com.example.zz.zzplayer.R
import com.example.zz.zzplayer.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity

class SplashActivity: BaseActivity(), ViewPropertyAnimatorListener {
    override fun onAnimationEnd(view: View?) {
        startActivityAndFinish<MainActivity>()
    }

    override fun onAnimationCancel(view: View?) {
    }

    override fun onAnimationStart(view: View?) {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
//        val animate = ViewCompat.animate(imageView)
//        animate.scaleX(1.0f)
//                .scaleY(1.0f)
//                .setDuration(2000)
//                .setListener(this)
        ViewCompat.animate(imageView).scaleX(1.0f).scaleY(1.0f).setDuration(2000).setListener(this)
    }
}