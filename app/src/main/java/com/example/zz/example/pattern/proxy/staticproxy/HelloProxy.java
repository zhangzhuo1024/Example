package com.example.zz.example.pattern.proxy.staticproxy;

/**
 * 代理类
 */
public class HelloProxy implements HelloInterface {

    private Hello hello = new Hello();

    @Override
    public void sayHello() {
        hello.sayHello();
    }
}
