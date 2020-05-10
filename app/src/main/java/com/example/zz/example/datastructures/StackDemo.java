package com.example.zz.example.datastructures;

import java.util.Stack;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/5/3
 */
public class StackDemo {

    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.add(node);
        System.out.println(node);
    }

    public int pop() {
        if (stack2.size() <= 0) {

            int size = stack1.size();
            System.out.println("stack1.size() " + size);
            for (int i = 0; i < size; i++) {
                System.out.println("i  " + i);
                Integer pop = stack1.pop();
                System.out.println("push  " + pop);
                stack2.push(pop);
                String aa =  Integer.toBinaryString(2);
                String[] split = aa.split("");
            }
        }

        return stack2.pop();
    }

}
