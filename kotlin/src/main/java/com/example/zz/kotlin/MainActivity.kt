package com.example.zz.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.zz.konlin.R

class MainActivity : AppCompatActivity() {
    lateinit var str:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    inner class Adapter(){

    }
}
