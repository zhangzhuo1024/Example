package com.example.zz.example.pattern.proxy.staticproxy;

/**
 * 被代理类
 */
public class Hello implements HelloInterface {
    @Override
    public void sayHello() {
        System.out.println("Hello static proxy");
    }
}
