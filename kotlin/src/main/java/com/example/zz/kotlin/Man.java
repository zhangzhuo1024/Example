package com.example.zz.kotlin;

public class Man extends Human {
    public String string1 = "public 变2";

    public Man(String name) {
        super(name);
        System.out.println("Man");
    }

    private void getLock(){

    }

    public static void main(String args[]) {
        Man 儿子 = new Man("儿子");
        儿子.getLock();
    }
}
