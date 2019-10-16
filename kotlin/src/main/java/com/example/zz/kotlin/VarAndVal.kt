//变量的生命和使用

//fun main(args: Array<String>) {
//    var name = "张山";
//    println(name);
//}

fun main(args: Array<String>) {
    var name = "zhangsan"
    name = "lisi"
    println(name)
    var i:Int = 9
//    i = 9999999999      此处不能设置为长整型
    var j:Long = 9999999999

//    var a         这样写是错误的，var无法给a判断类型
    var a:Int      //这样写是可以的


    val num : Int = 99    //val是只读的，是不可修改的
}
