package com.example.zz.kotlin;

public class Human {
    public String string1 = "public 变量";
    private String string2 = "private 变量";
    static String string3 = "static 变量";
    public Human(String name, String age){

    }

    public Human(String name){
        System.out.println("Human");
    }

    public String getPublicString() {
        return "public String";
    }

    private String getPrivateString() {
        return "private String";
    }

    static String getstaticString() {
        return "static String";
    }

}
