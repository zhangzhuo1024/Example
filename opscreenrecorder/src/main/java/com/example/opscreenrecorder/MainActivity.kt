package com.example.opscreenrecorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        initIntercept();
    }

    private fun initIntercept() {
        val dialogIntercept = DialogIntercept(null)
    }

}
