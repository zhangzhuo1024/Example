import java.util.*

private val i3 = 3

/**
 * java继承多态
 * 继承：单继承，多实现；子类继承父类的非私有变量和方法，在子类中可以使用
 * 多态：三要素，1、有继承关系，2、子类重写父类成员方法，3、父类数据类型引用至子类，
 * 重写不能重写成员变量及static修饰的成员变量或方法，只能重写方法。（多态是运行时动态绑定的，
 * static是在编译时随类一起被静态绑定的）。所以“成员变量、静态方法看左边；非静态方法，
 * 编译看左，运行看右”
 *
 */


fun main(args: Array<String>) {

    二进制()
    类型声明与推断()
    函数编写规则()
    字符串模板()
    条件控制()
    字符串比较()
    null的处理()
    when表达式()
    roop和range()
    listAndMap()

    函数和函数表达式()
    默认参数和具名参数()
    字符串数字转化()
    人机交互和问号感叹号()
    递归()
    封装继承和多态()

    var addResult = addCalculator(2, 3)
    var sub = sub(3, 2)
    var boolean = checkAge(18)
    var string = getMyString("99")
    println(string)

    StringCompare()

    NullCheck()

}

fun 封装继承和多态() {

    //封装 private关键字
    getMoney()



}

private fun getMoney() {}


fun 人机交互和问号感叹号() {
    //键盘录入
    println("请输入第一个数字")
    var number1 = readLine()
    println("请输入第二个数字")
    var number2 = readLine()

    var toInt = number1!!.toInt()
    var toInt2 = number1?.toInt()

    //用户可能输入为空，所以number1可能为空
    // 出现可能为空的情况就会报错，除非加标记
    //  ？    表示认可当前对象可以为空
    //  ！！  表示只有当前对象不为空时才执行

    //如果上面的两个感叹号改为问号，下面的加法就会出错，不能两个空相加
    println("${toInt}+${toInt2}=${toInt + toInt2}")
}

fun 字符串数字转化() {

    var i = 2;
    var j = "3"
    //数字转字符串
    val toString = i.toString()
    //字符串转数字
    val toInt = j.toInt()
}

fun 默认参数和具名参数() {

    getArea(height = 2);
    //这个函数第一个参数有默认参数，调用时不给覆盖默认参数的话，可以只给另外的具体的参数赋值
}

fun getArea(width: Int = 4, height: Int): Int {  //4 是第一个参数的默认参数
    return width * height;
}

fun roop和range() {

    calculatorGaoSi()
}

fun when表达式() {
    // when 相当于java的switch
    numToString(5);
}

fun null的处理() {

    var getStr = getStringNull("天王盖地虎")
//    var getStr2 = getStringNull(null)
}

fun getStringNull(s: String): Any {

//    return null   强制不能返回空

    return s
}

fun 字符串比较() {

    /**
     * 与java不同，Kotlin中“==”和 “.equals”完全等价，都可以用，java中前者是比较内存地址，
     * 后者是比较字符串内容
     */
    var str1 = "zhangsan"
    var str2 = "Zhangsan"

    println(str1 == str2)
    println(str1.equals(str2))

    // .equals 第二个参数是忽略大小写比较
    println(str1.equals(str2, true))
}

fun 条件控制() {
    /**
     * 条件控制使用if else 和java一样
     */
    olladd(1, 2)
    /**
     * 如果if else中只有一行，推荐写成一行
     */
    checkAge(1)

}

fun 字符串模板() {
    diaryGenerator("颐和园");
    diaryGenerator("故宫")
}

fun 函数编写规则() {
    //    函数就是方法
//    fun 函数名(参数名:参数类型):返回值类型{
//        函数体
//    }
    fun isNotNull(a: Int, b: String): Boolean {
        var c = a.toString() + b
        return c.equals(null)
    }
}

fun 类型声明与推断() {
    var a: Int = 1          //显式定义整型
    var b: String = "s"     //显式定义字符串型
    val c: Boolean = true    //显式定义为boolean型
    // var 定义的是可变变量  val是不可变变量，相当于final字段修饰，一旦定义就无法再赋值

    var i = 1     //自动推断i为整型
    var j = "s"   //自动推断j为字符串型
    val k = true  //自动推断k为boolean型
}

fun 二进制() {
    /**
     *  整数容器
     *  bit     比特                                      取值 0 1
     *  Byte    字节      1 Byte = 8 bit                  取值 -128 ~ 127
     *  Short   短整型    1 Short = 2 Byte = 16 bit       取值 -32768 ~ 32767
     *  Int     整型      1 Int = 4 Byte = 32 bit         取值 -2147483648 ~ 2147483647[
     *  Long    长整型    1 Long = 8 Byte = 64 bit        取值 -9223372036854775808 ~ 9223372036854775807
     *
     */
}

fun 递归() {

    var result = 0
    println(olladd(10, result))
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

    //普通函数表达方式
    var a = calculatorNum(1, 2)

    //函数表达式，左边的式函数名，右边的是入参类型、运算逻辑和返回值
    var calculatorNum2 = { x: Int, y: Int -> x + y }
    val result = calculatorNum2(1, 2)

    //另一种函数表达式写法，左边是函数名、入参类型、返回值类型，右边是运算逻辑
    var calculatorNum3: (Int, Int) -> Int = { x, y -> x + y }
    println(calculatorNum3(2, 4))

}

fun calculatorNum(i: Int, i1: Int): Int {
    return i + i1
}

fun listAndMap() {
    // list 是存储单列数据的集合，存储的数据是有序并且可以重复的
    // map 是存储双列数据的集合，通过键值对存储数据，是无序的，key是不能重复的，vlue是可以重复的
    // list和数组是通过有序角标做索引查询，map是通过存储的key对象进行索引，
    // hashmap和treemap都是线程不安全的，hashmap是基于hashcode的，treemap是基于红黑树

    var lists = listOf("卖鸡蛋", "卖鸡蛋", "煎鸡蛋")
    for (list in lists) {

    }

    // i 是角标，e是元素
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
    var nums = 1..10  //声明数组 闭区间 包含1 到 10
    var nums2 = 1 until 10 //前闭后开
    var result = 0
    for (num in nums) {
        result += num
    }
    for (num in nums step 2) {  //1 3 5  7   步长为2
//        println()
    }
    nums.count()                        //数组长度
    for (num in nums.reversed())  // reversed 反转 10  9  8  ...

        println(result)
}

fun diaryGenerator(string: String) {
    var diary = "今天天气晴朗，我们去${string}游玩，门口写着${string}${numToString(string.length)}个大字"
    var strSingle = "字符串"
    var all = strSingle + "拼接"
    println(all)
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
    if (i > 18) return true else return false
}

fun addCalculator(i: Int, i1: Int): Int {
    val i2 = i + i1
    return i2
}

fun sub(i: Int, b: Long): Long {
    return i - b
}

private fun printStar() {
    println("  a  ")
    println(" aaa ")
    println("aaaaa")
    println(" aaa ")
}