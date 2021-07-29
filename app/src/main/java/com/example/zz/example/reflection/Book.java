package com.example.zz.example.reflection;

import android.util.Log;
/**
 * 对于顶级类(外部类)来说，只有两种修饰符：public和默认(default)。
 * 因为外部类的上一单元是包，所以外部类只有两个作用域：同包，任何位置。
 * 因此，只需要两种控制权限：包控制权限和公开访问权限，也就对应两种控制修饰符：public和默认(default)。
 * 如果一个java文件的类被private修饰，那么他的程序或是类是无法使用它的，那么他作为一个单独的文件就没啥用了
 *
 * public：可以被所有其他类所访问
 * protected：自身、子类及同一个包中类均可以访问
 * default：同一包中的类可以访问，声明时没有加修饰符，认为是friendly
 * private：只能被自己访问和修改
 *
 * 本类DefaultBook没有设置，所以默认为default，同一包中的类可以访问
 *
 * 注意：java的访问控制是停留在编译层的，也就是它不会在.class文件中留下任何的痕迹，只在编译的时候进行访问控制的检查。其实，通过反射的手段，是可以访问任何包下任何类中的成员，例如，访问类的私有成员也是可能的。
 */
public class Book {

    private static final String TAG = "Book_Tag";

    private String name = "zhangsan";

    private int age;

    public Book() {

    }

    private Book(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private String privateMethod(int index) {
        String string = null;
        switch (index) {
            case 0:
                string = "I am privateMethod 0 !";
                break;
            case 1:
                string = "I am privateMethod 1 !";
                break;
            default:
                string = "I am privateMethod -1 !";
        }

        return string;
    }

    private String privateMethod1() {
        String string = "privateMethod1 result";
        return string;
    }

    private void privateMethod2() {
        int num = 0;
        for (int i = 0; i < 10; i++) {
            num++;
        }
        Log.e("eeeeee", "book调用指定无参无返回值私有方法结果 num = " + num);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
