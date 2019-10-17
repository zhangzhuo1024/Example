import java.util.*

fun main(args: Array<String>) {

    var addResult = addCalculator(2, 3)
    var sub = sub(3, 2)
    var boolean = checkAge(18)
    var string = getMyString("99")
    println(string)

    StringCompare()

    NullCheck()

    diaryGenerator("颐和园");

    calculatorGaoSi()

    listAndMap()

    函数和函数表达式()

    tailrecTest()
}

fun tailrecTest() {

    var result = 0
    println(olladd(100, result))
}

fun olladd(i: Int, result: Int): Int {

    println("进行第${i}次尾递归优化计算，累加结果是 ${result}")
    if (i == 0) {
        return i
    } else {
        return olladd(i - 1, result + i)
    }
}

fun 函数和函数表达式() {

    var a = calculatorNum(1, 2)

    var calculatorNum2 = {x:Int,y:Int -> x+y}
    val result = calculatorNum2(1, 2)

    var calculatorNum3:(Int, Int)->Int = {x,y -> x+y}
    println(calculatorNum3(2, 4))
}

fun calculatorNum(i: Int, i1: Int): Int {
    return i + i1
}

fun listAndMap() {
    var lists = listOf("卖鸡蛋","卖鸡蛋","煎鸡蛋")
    for (list in lists) {

    }
    for ((i, e) in lists.withIndex()) {
        var result = "${i}和${e}"
        println(result)
    }

    var treemaps = TreeMap<String, String>()
    treemaps["大哥"] = "大哥大"
    treemaps["er哥"] = "er哥er"

    for (treemap in treemaps) {

    }
    println(treemaps["大哥"])

}

fun calculatorGaoSi() {
    var nums = 1..100
    var nums2 = 1 until 100 //前闭后开
    var result = 0
    for (num in nums) {
        result += num
    }
    for (num in nums step 2){  //1 3 5  7   步长为2
//        println()
    }
    for (num in nums.reversed())  // 循环

    println(result)
}

fun diaryGenerator(string: String) {
    var diary = "今天天气晴朗，我们去${string}游玩，门口写着${string}${numToString(string.length)}个大字"
    println(diary)
}

fun numToString(length: Int): String {
    var string = when (length) {
        1 -> "一"
        2 -> "二"
        3 -> "三"
        4 -> "四"
        else -> "我也不知道几"
    }
    return string
}

fun NullCheck() {
    var result = heat("水")
//    var result2 = heat(null)   报错，默认无法传入空
    var result3 = heatNull(null)
}

fun heatNull(string: String?): String? {     //第一个问号表示允许传入为空，第二个问号表示可以允许传出为空
    return null
}

fun heat(s: String): String {
    return "热${s}"
}

private fun StringCompare() {
    //字符串比较，与java不同，直接使用== 进行比较
    var a = "字符串"
    var b = "字符串"
    println(a == b)

    //字符串不比较使用equal也行，后面第二个参数是忽略大小写
    var c = "Andy"
    var d = "andy"
    println(a.equals(b, true))
}

fun getMyString(string: String): String {
    var yuju = "他今年${string}岁"
    return yuju
}

fun checkAge(i: Int): Boolean {
    if (i>18)return true else return false
}

fun addCalculator(i: Int, i1: Int): Int {
    val i2 = i + i1
    return i2
}

fun sub(i: Int, b: Long): Long{
    return i - b
}

private fun printStar() {
    println("  a  ")
    println(" aaa ")
    println("aaaaa")
    println(" aaa ")
}