import java.util.*
import java.io.Serializable

private val i3 = 3

fun main(args: Array<String>) {

    //主函数  java的主函数要放在单独的类，如果在类中，就放在类里面，kotlin的主函数要放在类外面
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
    接口和抽象类()
    委托和代理()
    单例()
    枚举和印章()

    面向对象编程和函数式编程()
    高阶函数()
    领域特定语言DSL()

    定义常量和成员变量()
    内部类()

    for循环点击事件()
    数据类和序列化()

    var addResult = addCalculator(2, 3)
    var sub = sub(3, 2)
    var boolean = checkAge(18)
    var string = getMyString("99")
    println(string)

    StringCompare()

    NullCheck()

}

fun 数据类和序列化() {
    //数据类 DATACLASS： 在Kotlin中，不用自己手动写javabean类，可以直接使用data calss实现，并且data class自动生成了很多函数
    //使用方法就是在最前面加data 如Student类
    Student("达达", 18, 188)
    Student("文文", 4, 13)
    val student = Student("西西", 34, 176)
    println(student) // Student(name="西西", age=34, height=176) 自动有toString功能

    //序列化 Serializable  https://baijiahao.baidu.com/s?id=1633305649182361563&wfr=spider&for=pc
    /**
     *      如何实现序列化 ，实现Serializable接口就行了 如Student类
     *     把对象转换为字节序列的过程叫做序列化
     *     把字节序列转换为对象的过程叫做反序列化
     *
     *     开发过程中，实体并没有实现序列化，但我同样可以将数据保存到mysql、Oracle数据库中，为什么非要序列化才能存储呢？
     *     在Java中的这个Serializable接口其实是给jvm看的，通知jvm，我不对这个类做序列化了，你(jvm)帮我序列化就好了。就是用来标记通知一下，提高效率的
     *      Serializable接口就是Java提供用来进行高效率的异地共享实例对象的机制，实现这个接口即可。
     *
     */

}

data class Student(val name: String, var age: Int, var height: Int):Serializable

fun for循环点击事件() {

    // for循环写法

    var a = Girl("达达", 18, 173)
    var b = Girl("文文", 19, 173)
    var c = Girl("西西", 22, 171)
    var items = listOf<Girl>(a, b, c)

    //循环一
    for (item in items) {
        if (item.age > 20) {
            println(item)
        }
    }

    //循环二  点击事件一，点击事件后面加匿名内部类，用object修饰，后面是小括号里面在监听大括号
//    for (i in 0 until main_bottom_bar.childCount) {
//        main_bottom_bar.getChildAt(i).setOnClickListener(object : View.OnClickListener {
//            override fun onClick(view: View?) {
//
//            }
//
//        })
//    }

    //点击事件二，后面不加匿名内部类，加lambda表达式 lambda表达式后面是直接加大括号, 如果只用角标，v->  符号也可以省略
//    main_bottom_bar.getChildAt(i).setOnClickListener { v ->
//        changeIndex(i)
//    }

}

fun 内部类() {

    //内部类定义的时候需要在前面加 inner

    class Adapter() {
        inner class Date() {

        }
    }
}

fun 定义常量和成员变量() {

    //成员变量如果定义的时候不初始化需要在前面加上lateinit, 如
    lateinit var str: String

//    companion object {
//        val TYPE_TITLE = 0
//        val TYPE_SELLER = 1
//    }
}

fun 领域特定语言DSL() {
    /**
     * 扩展函数  扩展函数可以给现有的各种类添加各种新的方法
     * 中缀表达式  方法前添加infix之后，对象.方法  调用可以把点替换成空格
     *
     * 综合上面两点就可以自由度很高的添加自己的框架，添加了infix关键字，
     * 可以不用点就可以自然调用，更符合人类语言，是第五代语言。定义了自己的框架后
     * 按照框架，电脑小白也可以实现编程，如下面的例子 items getGirlAgeAbove 18 就非常符合人类语言
     */

    var a = Girl("达达", 18, 173)
    var b = Girl("文文", 19, 173)
    var c = Girl("西西", 22, 171)
    var items = listOf<Girl>(a, b, c)

    //给List类新增（扩展）了一个方法，getAge18Girl
    items.getGirlAgeAbove(18)

    //添加中缀关键字infix之后，可以不用点来调用
    items.getGirlAgeAbove2(19)
    items getGirlAgeAbove2 18
}

//此处给List添加（扩展）一个新的函数
fun List<Girl>.getGirlAgeAbove(age: Int) {
    filter {
        it.age > 18
    }.forEach(::println)
}

infix fun List<Girl>.getGirlAgeAbove2(age: Int) {
    filter {
        it.age > 18
    }.forEach(::println)
}

fun 高阶函数() {
    //将函数作为参数，或者返回一个函数，称为高阶函数
    // 这样的函数有maxBy

    var a = Girl("达达", 18, 173)
    var b = Girl("文文", 19, 173)
    var c = Girl("西西", 22, 171)
    var items = listOf<Girl>(a, b, c)
    for (item in items) {
        if (item.age > 20) {
            println(item)
        }
    }
    //找最大的高阶函数,返回age最大的对象集合
    val maxBy = items.maxBy { it.age }
    println(maxBy?.name + "maxBy")
    //筛选高阶函数，返回满足条件的对象集合
    val filter = items.filter { (it.age > 18) and (it.height > 170) }
    println(filter)
    //抽取高阶函数，返回只有mane 和height两个属性的集合
    val map = items.map { it.name; it.height }
    //判断高阶函数，返回值是Boolean, 是否满足条件
    val any = items.any() { it.age > 18 }
    //统计高阶函数，返回值是符合条件的有多少个
    val count = items.count() { it.age > 18 }
    //查询高阶函数，返回值是满足条件的集合
    val find = items.find { it.age > 18 }
    //分组高阶函数，返回值是按条件分组后的map集合key是分组标准，value是该标准下的对象集合
    val groupBy = items.groupBy { it.height }
    val get = groupBy.get(173)

    /**
     *     使用高阶函数(higher-order functions)会导致一些性能的损耗:
    每个函数都是对象,且会捕获闭包closure(即变量会在函数体内被访问),
    函数对象/类会增加内存分配,而且虚拟调用栈也会增加额外内存开销！

    可用内联函数(inline function)消除这些额外内存开销,
    说白了就是在调用处插入函数体代码,以此减少新建函数栈和对象的内存开销!
    被inline修饰的函数或lambda表达式,在调用时都会被内联(在调用处插入函数体代码)
     */

}

class Girl(var name: String, var age: Int, var height: Int) {

}

public inline fun <Girl, Int : Comparable<Int>> Iterable<Girl>.maxBy(selector: (Girl) -> Int): Girl? {
    val iterator = iterator()
    if (!iterator.hasNext()) return null
    var maxElem = iterator.next()
    var maxValue = selector(maxElem)
    while (iterator.hasNext()) {
        val e = iterator.next()
        val v = selector(e)
        if (maxValue < v) {
            maxElem = e
            maxValue = v
        }
    }
    return maxElem
}

fun 面向对象编程和函数式编程() {

    var items = listOf<String>("a", "b", "c")

    //函数式编程1
    items.forEach() { a -> printItem(a) }
    //函数式编程2
    items.forEach() { printItem(it) }
    //函数式编程3
    items.forEach(printItem3)
    //函数式编程4
    var printItem4 = { s: String -> println(s) }
    items.forEach(printItem4)
    //函数式编程5
    items.forEach({ s: String -> println(s) })

}

var printItem3 = fun(s: String): Unit { println(s) }

fun printItem(s: String) {
    println(s + "111")
}

fun 枚举和印章() {
    //枚举可以限定取值的范围，所有的内容只能从指定范围中取得.
    Week.Mon

    //Sealed calss 印章类，子类类型有限的class，就是限定子类的取值范围，所有的类只能从指定的类中选取
    var minCat = Animal.Cat()         //Animal 是不可用的，后面必须带
    var minDog = Animal.Dog()
}

enum class Week {
    Mon, Tues, Wed, Thur, Fri, Sat, Sun
}

sealed class Animal {
    class Cat {
        fun eat() {}
    }

    class Dog {
        fun play() {}
    }
}

fun 单例() {
    //一个类加了object关键字就是单例类，可以用类名直接调用，使用的是饿汉式单例
    // Tools->Kotlin-> Show Kotlin ByteCode -> Decompile 可以反编译为java文件查看时饿汉式
    BigHeadSon.eat()
}

object BigHeadSon {
    fun eat() {

    }
}

fun 委托和代理() {
    //Kotlin 官方示例代码，还是一脸懵逼
//    interface	Base	{
//        fun	print()
//    }
//
//    class	BaseImpl(val	x:	Int)	:	Base	{
//        override	fun	print()	{
//            print(x)
//        }
//    }
//
//    class	Derived(b:	Base)	:	Base	by	b
//
//    fun	main(args:	Array<String>)	{
//        val	b	=	BaseImpl(10)
//        Derived(b).print()	//	输出	10 }
//    }

}

fun 接口和抽象类() {
    /**
     * 接口 体现的是事物的能力
     * 抽象类 体现的是事物的本质
     *
     * 简单讲就是，所有对象都必须有的，就抽成抽象类；比如人吃饭时必须的，抽象类Human，eat方法
     * 部分对象有，部分对象又没有的，就抽成接口；比如开车是种能力，不一定所有对象都会，抽成接口，ISkill，drive方法
     *
     *
     */


}

fun 封装继承和多态() {
    //kotlin构造函数
    /**
     * class Son(nameSon: String) : Father(nameSon)
     * class Son(val nameSon: String) : Father(nameSon)
     * 1.什么都没有,在该类中使不能使用的, 这个参数的作用就是,传递给父类的构造方法
     * 2.使用var 可以在类中使用,相当于 我们声明了一个该类中定义了一个private 的成员变量
     * 3.val表示不让修改该参数 加上了final 修饰符
     */

    //封装 private关键字，加了以后之内在本类中直接调用，与java一致，
    // 无法在其他类中通过“对象.方法（）”的形式调用！！！
    getMoney()

    /**
     * java继承多态
     * 继承：单继承，多实现；子类继承父类的非私有变量和方法，在子类中可以使用
     * 多态：三要素，1、有继承关系，2、子类重写父类成员方法，3、父类数据类型引用至子类，
     * 重写：不能重写成员变量及static修饰的成员变量或方法，只能重写方法。（多态是运行时动态绑定的，
     * static是在编译时随类一起被静态绑定的）。所以“成员变量、静态方法看左边；非静态方法，
     * 编译看左，运行看右”
     * 案例：
     * Son extends Father
     * Son son = new Son（）；
     * Father father = new Son（）；
     * son可以使用 自己的所有方法变量 + 父类的非私有方法变量
     * father可以使用 自己的所有方法变量，但是如果自己的方法被子类重写，
     * 运行的时候就时运行子类重写的此方法。要想父类也运行，需要子类调用supper
     * 重写只能重写方法，不能重写变量，子类父类成员变量可以有相同的变量名，通过son和father可以分别调用到！！！
     *
     * 子类继承父类，运行顺序为:
     * 父类静态代码块>子类静态代码块>父类普通代码块>父类构造函数>子类普通代码块>
     * 子类构造函数
     * 子类构造函数第一行一定会调用super来初始化父类构造函数，构造函数没参数时，子类
     * 构造函数第一行默认会加super，手动加上也可以；如果构造函数有参数时，子类必须在构造函数
     * 第一行加上super调用，并把参数传入。原因很好理解，继承了父类肯定时想使用父类的变量方法
     * 要使用父类特性，肯定要先将父类初始化 参见Human 和 Man
     *
     *
     * Kotlin继承多态
     * java的继承多态中，son不能调用父类的私有变量和方法，而father不能调用Son中自己没有的方法，
     * Kotlin中呢，有自动推断功能，它使用的是什么来接收呢？
     * 1、类和方法默认都是final的，父类只有加了open关键字打开了，才能被人继承，方法加了open才能被重写，
     * 与java不同，变量加open也可以被重写！！！所以子类父类不能有相同变量名，相同变量名要重写
     * 2、重写方法前面需要添加override
     * 3、var s = Son("son")的写法相当于 Son s = new Son("son")的同时，s可以当作Father，见Son类的调用
     */


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