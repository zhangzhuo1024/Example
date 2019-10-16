fun main(args: Array<String>) {

    var addResult = addCalculator(2, 3)
    var sub = sub(3, 2)
    var boolean = checkAge(18)
    var string = getMyString("99")
    println(string)

    StringCompare()

    NullCheck()

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