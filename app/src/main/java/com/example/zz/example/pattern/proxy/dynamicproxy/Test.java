package com.example.zz.example.pattern.proxy.dynamicproxy;

import java.lang.reflect.Proxy;

/**
 * 测试类  参考：https://www.jianshu.com/p/9bcac608c714
 * 动态代理在框架中的使用：retrofit
 * 0、retrofit请求接口类，对方法注解、参数注解进行解析获取到请求参数；注解+反射+动态代理实现 create 后在retrofit类中loadservicemethod获取方法，实现动态代；在调用getPersonInfo时，会ServiceMethod中获取注解，解析注解参数，会调用匿名动态代理的invoke方法
 * 1、建造者模式，构建builder，client等
 * 2、retrofit返回的数据是在主线程中，在enqueue的实现类ExecutorCallAdapterFactory中的enqueue中的ExecutorCallbackCall.this.callbackExecutor.execute中切换的主线程
 * 3、retrofit同一时间最大请求数64，单个域名同一时间最大请求数5，在Dispatcher中
 * 4、职责链模式，interceptors，在RealCall类中实现，自定义拦截器、重试重定向拦截器、桥接拦截器、缓存拦截器（有缓存直接返回）、连接拦截器、网络拦截器
 *
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
