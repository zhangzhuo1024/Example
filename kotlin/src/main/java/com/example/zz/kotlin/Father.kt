package com.example.zz.kotlin

open class Father(var name: String) {

    open val fatherOpenStr = "fatherOpenStr "
    val fatherNormalStr = "fatherNormalStr "

    open fun eat() {
        println("father open fun eat")
    }

    private fun jump() {
        println("father private fun jump")
    }

    fun drink() {
        println("father normal drink")
    }

}