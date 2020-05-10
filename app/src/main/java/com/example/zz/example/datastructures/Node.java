package com.example.zz.example.datastructures;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/5/5
 */
public class Node<E> {
    E item;
    Node<E> next;

    public Node(E e) {
        item = e;
        next = null;
    }
}
