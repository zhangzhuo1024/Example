package com.example.zz.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.zz.konlin.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var str:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener{

        }
    }

    inner class Adapter(){

    }
}
