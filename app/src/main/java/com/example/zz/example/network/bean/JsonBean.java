package com.example.zz.example.network.bean;

import java.util.HashMap;
import java.util.List;

public class JsonBean {
    public JsonBean() {
    }

    public JsonBean(String name, int age) {
        this.name = name;
        this.age = age;
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

    private String name;
    private int age;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    private String num;

    public HashMap<String, String> getResult() {
        return result;
    }

    public void setResult(HashMap<String, String> result) {
        this.result = result;
    }

    private HashMap<String, String> result;
}
