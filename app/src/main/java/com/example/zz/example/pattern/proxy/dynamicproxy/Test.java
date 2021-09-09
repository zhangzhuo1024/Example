package com.example.zz.example.pattern.proxy.dynamicproxy;

import java.lang.reflect.Proxy;

/**
 * 测试类  参考：https://www.jianshu.com/p/9bcac608c714
 */
public class Test {
    public static void main(String[] args) {
        Hello hello = new Hello();
        ProxyHandler proxyHandler = new ProxyHandler(hello);
        HelloInterface proxyInstance = (HelloInterface) Proxy.newProxyInstance(hello.getClass().getClassLoader(), hello.getClass().getInterfaces(), proxyHandler);
        proxyInstance.sayHello();

        HelloInterface hello1 = new Hello();
        hello1.sayHello();

        Bye bye = new Bye();
        ProxyHandler proxyHandler1 = new ProxyHandler(bye);
        ByeInterface o = (ByeInterface)Proxy.newProxyInstance(bye.getClass().getClassLoader(), bye.getClass().getInterfaces(), proxyHandler1);
        o.sayBye();

    }
}
