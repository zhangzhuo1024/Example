package com.example.zz.example.reflection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.zz.example.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionActivity extends AppCompatActivity {
    private static final String TAG = "ReflectionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection);
        /**
         * 关于java.lang.Class类的理解
         *     1.类的加载过程：
         *     程序经过javac.exe命令以后，会生成一个或多个字节码文件(.class结尾)。
         *     接着我们使用java.exe命令对某个字节码文件进行解释运行。相当于将某个字节码文件
         *     加载到内存中。此过程就称为类的加载。加载到内存中的类，我们就称为运行时类，此
         *     运行时类，就作为Class的一个实例。
         *
         *     2.换句话说，Class的实例就对应着一个运行时类。
         *
         *     3.加载到内存中的运行时类，会缓存一定的时间。在此时间之内，我们可以通过不同的方式
         *     来获取此运行时类。
         */

        /**
         * getFields()                                      获得某个类的所有的公共（public）的字段，包括父类中的字段。
         * getDeclaredFields()                              获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
         * getDeclaredFiled()                               获得某个类本身的属性成员（包括私有、共有、保护）
         */

        /**
         * getMethods()                                     获得该类所有公有的方法(包括那些由该类或接口声明的以及从超类和超接口继承的那些的类或接口)
         * getDeclaredMethods()                             获得该类所有方法(包括公共、保护、默认（包）访问和私有方法，但不包括继承的方法)
         * getDeclaredMethod(methodName,parameterTypes)     获得该类某个方法
         */
    }

    //反射获取类方法一：如果可以获取到类的对象时，使用此方法获取反射类，然后通过反射获取到类的私有变量、私有方法，如：Book类，可以获取到对象
    public void methodOne(View view) {
        Book book = new Book();
        Class bookClass = book.getClass();
//        Class<? extends Book> bookClass = book.getClass(); 自动提示生成，效果同上，不推荐使用，保持三种反射类都是Class class的较好
//        Toast.makeText(this, "aClass = " + bookClass + "\n" + "aClass.getName() = " + bookClass.getName(), Toast.LENGTH_SHORT).show();
        try {
            //1、获取全部变量(私有、共有、保护)
            Field[] fields = bookClass.getDeclaredFields();
//            Class clazz = book.getClass();
//            Field[] fields = clazz.getDeclaredFields();    //效果同上
            for (Field field : fields) {
                field.setAccessible(true);
                Log.e(TAG, "book 获取全部变量 = " + field);
            }

            //2、获取指定变量、修改指定变量(私有、共有、保护)
            Field name = bookClass.getDeclaredField("name");
            name.setAccessible(true);
            String nameStr = (String) name.get(book);
            Log.e(TAG, "book获取指定私有变量值 = " + nameStr);
            name.set(book, "hahaha");
            String name2 = (String) name.get(book);
            Log.e(TAG, "book修改指定私有变量值，修改后 = " + name2);

            //3、获取全部方法（私有、共有、保护）
            Method[] declaredMethods = bookClass.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                declaredMethod.setAccessible(true);
                Log.e(TAG, "book获取全部方法 = " + declaredMethod);
            }

            //4、获取指定方法（私有、共有、保护）
            Method privateMethod = bookClass.getDeclaredMethod("privateMethod", int.class);
            privateMethod.setAccessible(true);
            Log.e(TAG, "book获取指定private私有方法 = " + privateMethod);

            //5、调用指定方法，有参数有返回值
            String str = (String) privateMethod.invoke(book, 1);
            Object bookInstance = bookClass.newInstance();
            String str2 = (String) privateMethod.invoke(bookInstance, 1);
            Log.e(TAG, "book调用指定有参有返回值私有方法结果 = " + str);
            Log.e(TAG, "book调用指定有参有返回值私有方法结果 = " + str2);
            //方法调用时，privateMethod.invoke的参数object可以使用book也可以newInstance一个，既然book已经是有的，就不推荐newInstance

            //6、调用指定方法，无参数有返回值
            Method privateMethod1 = bookClass.getDeclaredMethod("privateMethod1");
            privateMethod1.setAccessible(true);
            String str1 = (String) privateMethod1.invoke(book);
            Log.e(TAG, "book调用指定无参有返回值私有方法结果 = " + str1);

            //7、调用指定方法，无参数无返回值
            Method privateMethod2 = bookClass.getDeclaredMethod("privateMethod2");
            privateMethod2.setAccessible(true);
            privateMethod2.invoke(book);
            //检查Book类是否有privateMethod3中的打印
        } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    //反射获取类方法二：需要导入此类，然后通过反射获取到类的私有变量、私有方法，如：Book类，可以获取到对象
    public void methodTwo(View view) {
        try {
//        Class<Book> bookClass = Book.class;    //不要使用提示自动生成的泛型，访问不到反射方法！！！
            Class privateBookClass = Book.class;
            Object privateBookInstance = privateBookClass.newInstance();  //对于有空构造函数的类 可以直接用字节码文件获取实例,会调用空参构造器 如果没有则会报错
            Toast.makeText(this, "privateBookClass = " + privateBookClass, Toast.LENGTH_SHORT).show();

            //1、获取全部变量(私有、共有、保护)
            Field[] fields = privateBookClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Log.e(TAG, "book 获取全部变量 = " + field);
            }

            //2、获取指定变量、修改指定变量(私有、共有、保护)
            Field name = privateBookClass.getDeclaredField("name");
            name.setAccessible(true);
            String nameStr = (String) name.get(privateBookInstance);
            Log.e(TAG, "book获取指定私有变量值 = " + nameStr);
            name.set(privateBookInstance, "hahaha");
            String name2 = (String) name.get(privateBookInstance);
            Log.e(TAG, "book修改指定私有变量值，修改后 = " + name2);

            //3、获取全部方法（私有、共有、保护）
            Method[] declaredMethods = privateBookClass.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                declaredMethod.setAccessible(true);
                Log.e(TAG, "book获取全部方法 = " + declaredMethod);
            }

            //4、获取指定方法（私有、共有、保护）
            Method privateMethod = privateBookClass.getDeclaredMethod("privateMethod", int.class);
            privateMethod.setAccessible(true);
            Log.e(TAG, "book获取指定私有方法 = " + privateMethod);

            //5、调用指定方法，有参数有返回值
            String str = (String) privateMethod.invoke(privateBookInstance, 1);
            Object bookInstance = privateBookClass.newInstance();
            String str2 = (String) privateMethod.invoke(bookInstance, 1);
            Log.e(TAG, "book调用指定有参有返回值私有方法结果 = " + str);
            Log.e(TAG, "book调用指定有参有返回值私有方法结果 = " + str2);

            //6、调用指定方法，无参数有返回值
            Method privateMethod1 = privateBookClass.getDeclaredMethod("privateMethod1");
            privateMethod1.setAccessible(true);
            String str1 = (String) privateMethod1.invoke(privateBookInstance);
            Log.e(TAG, "book调用指定无参有返回值私有方法结果 = " + str1);

            //7、调用指定方法，无参数无返回值
            Method privateMethod2 = privateBookClass.getDeclaredMethod("privateMethod2");
            privateMethod2.setAccessible(true);
            privateMethod2.invoke(privateBookInstance);
            //检查Book类是否有privateMethod3中的打印
        } catch (IllegalAccessException | NoSuchFieldException
                | NoSuchMethodException | InvocationTargetException
                | InstantiationException e) {
            e.printStackTrace();
        }
    }

    //反射获取类方法三：不需要导入此类，只需要知道路径就行，使用静态方法Class.forName(类的全路径)获取，
    // 但是这个类如果不存在时，可能抛出ClassNotFountException；
    public void methodThree(View view) {
        try {
            Class<?> aClass = Class.forName("com.example.zz.example.reflection.Book");
            Toast.makeText(this, "aClass = " + aClass, Toast.LENGTH_SHORT).show();

            Object clazzInstance = aClass.newInstance();
            //对于有空构造函数的类 可以直接用字节码文件获取实例,会调用空参构造器 如果没有则会报错
            //Class.newInstance() 只能够调用无参的构造函数，即默认的构造函数；要求被调用的构造函数是可见的，也即必须是public类型的;
            //Constructor.newInstance() 可以根据传入的参数，调用任意构造构造函数。在特定的情况下，可以调用私有的构造函数。

            Toast.makeText(this, "privateBookClass = " + aClass, Toast.LENGTH_SHORT).show();

            //1、获取全部变量(私有、共有、保护)
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Log.e(TAG, "book 获取全部变量 = " + field);
            }

            //2、获取指定变量、修改指定变量(私有、共有、保护)
            Field name = aClass.getDeclaredField("name");
            name.setAccessible(true);
            String nameStr = (String) name.get(clazzInstance);
            Log.e(TAG, "book获取指定私有变量值 = " + nameStr);
            name.set(clazzInstance, "hahaha");
            String name2 = (String) name.get(clazzInstance);
            Log.e(TAG, "book修改指定私有变量值，修改后 = " + name2);

            //3、获取全部方法（私有、共有、保护）
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                declaredMethod.setAccessible(true);
                Log.e(TAG, "book获取全部方法 = " + declaredMethod);
            }

            //4、获取指定方法（私有、共有、保护）
            Method privateMethod = aClass.getDeclaredMethod("privateMethod", int.class);
            privateMethod.setAccessible(true);
            Log.e(TAG, "book获取指定私有方法 = " + privateMethod);

            //5、调用指定方法，有参数有返回值
            String str = (String) privateMethod.invoke(clazzInstance, 1);
            Object bookInstance = aClass.newInstance();
            String str2 = (String) privateMethod.invoke(bookInstance, 1);
            Log.e(TAG, "book调用指定有参有返回值私有方法结果 = " + str);
            Log.e(TAG, "book调用指定有参有返回值私有方法结果 = " + str2);

            //6、调用指定方法，无参数有返回值
            Method privateMethod1 = aClass.getDeclaredMethod("privateMethod1");
            privateMethod1.setAccessible(true);
            String str1 = (String) privateMethod1.invoke(clazzInstance);
            Log.e(TAG, "book调用指定无参有返回值私有方法结果 = " + str1);

            //7、调用指定方法，无参数无返回值
            Method privateMethod2 = aClass.getDeclaredMethod("privateMethod2");
            privateMethod2.setAccessible(true);
            privateMethod2.invoke(clazzInstance);
            //检查Book类是否有privateMethod3中的打印
        } catch (NoSuchMethodException | ClassNotFoundException
                | IllegalAccessException | InvocationTargetException
                | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}