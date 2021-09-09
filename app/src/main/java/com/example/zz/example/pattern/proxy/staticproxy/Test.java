package com.example.zz.example.pattern.proxy.staticproxy;

/**
 * 参考：https://www.jianshu.com/p/9bcac608c714
 *
 * 静态代理测试类 ：
 *
 * 使用静态代理很容易就完成了对一个类的代理操作。但是静态代理的缺点也暴露了出来：由于代理只能为一个类服务，如果需要代理的类很多，那么就需要编写大量的代理类，比较繁琐。
 */
public class Test {
    public static void main(String[] args) {
        HelloProxy helloProxy = new HelloProxy();
        helloProxy.sayHello();

        HelloInterface hello = new Hello();
        hello.sayHello();
    }
}
