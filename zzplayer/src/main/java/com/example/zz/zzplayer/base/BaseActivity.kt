package com.example.zz.zzplayer.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

abstract class BaseActivity: AppCompatActivity(),AnkoLogger{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initListener()
        initData()
    }

    inline fun <reified T:BaseActivity>startActivityAndFinish (){
        startActivity<T>();
        finish()
    }

    open protected fun initData() {

    }

    open protected fun initListener() {

    }

    abstract fun getLayoutId(): Int

    open protected fun myToast(mString:String){
        runOnUiThread { toast(mString) }
    }
}