package com.example.zz.kotlin

class Son(val nameSon: String) : Father("father const") {
    override var fatherOpenStr = "sonOverrideStr"
    val sonNormalStr = "sonNormalStr "

    override fun eat() {
        println("Son eat")
    }

    private fun jump2() {
        println("father private fun jump")
    }

    fun play() {
        println("Son paly")
    }

}

fun main(args: Array<String>) {
    var s = Son("son")
    s.nameSon
    s.name
    s.fatherOpenStr
    s.fatherNormalStr
    s.sonNormalStr
    s.eat()
    s.drink()
    s.play()
    println("s.nameSon = " + s.nameSon + "    s.name = " + s.name + "   s.fatherOpenStr = " + s.fatherOpenStr +
            "s.fatherNormalStr = " + s.fatherNormalStr + "    s.sonNormalStr = " + s.sonNormalStr)


    var s2: Father = Son("son")
//     s2.nameSon   和java一样，无法调用
    s2.name
    s2.fatherOpenStr
    s2.fatherNormalStr
//     s2.sonNormalStr   和java一样，无法调用

    s2.eat()
    s2.drink()
//  s2.play()   和java一样，无法调用
    println("    s2.name = " + s2.name + "   s2.fatherOpenStr = " + s2.fatherOpenStr +
            "s2.fatherNormalStr = " + s2.fatherNormalStr )


    var s3: Son = Son("son")
    s3.nameSon
    s3.name
    s3.fatherOpenStr
    s3.fatherNormalStr
    s3.sonNormalStr
    s3.eat()
    s3.drink()
    s3.play()


    println("s3.nameSon = " + s3.nameSon + "    s3.name = " + s3.name + "   s3.fatherOpenStr = " + s3.fatherOpenStr +
            "s3.fatherNormalStr = " + s3.fatherNormalStr + "    s3.sonNormalStr = " + s3.sonNormalStr)
}