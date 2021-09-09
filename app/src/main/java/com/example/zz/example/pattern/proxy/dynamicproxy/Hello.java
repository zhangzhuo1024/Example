package com.example.zz.example.pattern.proxy.dynamicproxy;


/**
 * 被代理类
 */
class Hello implements HelloInterface {
    @Override
    public void sayHello() {
        System.out.println("Hello dynamic proxy");
    }
}
