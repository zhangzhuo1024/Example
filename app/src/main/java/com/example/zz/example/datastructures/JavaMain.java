package com.example.zz.example.datastructures;

import java.util.Arrays;

/**
  Created by zhangzhuo.
  Blog: https://blog.csdn.net/zhangzhuo1024
  Date: 2020/5/3
 */
public class JavaMain {

    public static void main(String[] args) {
        int pre[] = {1,2,4,7,3,5,6,8};
        int in[] = {4,7,2,1,5,3,8,6};

//        TreeNodeDemo.TreeNode treeNode = TreeNodeDemo.reConstructBinaryTree(pre, in);
//
//        StackDemo stackDemo = new StackDemo();
//        stackDemo.push(1);
//        stackDemo.push(2);
//        stackDemo.push(3);
//        int pop = stackDemo.pop();
//        int pop1 = stackDemo.pop();
//        stackDemo.push(4);
//        int pop2 = stackDemo.pop();
//        stackDemo.push(5);
//        int pop3 = stackDemo.pop();
//        int pop4 = stackDemo.pop();
//        System.out.println(" 1 = " + pop + "  2 = " + pop1 +"  3 = " + pop2 +"  4 = " + pop3 +"  5 = " + pop4);
//

        Node<String> head = new Node<>("node 1");
        Node<String> node2 = new Node<>("node 2");
        Node<String> node3 = new Node<>("node 3");
        head.next = node2;
        node2.next = node3;

        //顺序遍历
//        Node<String> current = head;
//        while (current != null) {
//            System.out.println(current.item);
//            current = current.next;
//        }

        //倒序遍历
        printListRev(head);

    }

    static void printListRev(Node<String> head) {
        //倒序遍历链表主要用了递归的思想
        if (head != null) {
            printListRev(head.next);
//            if (head.next != null){
//                if (head.next.next != null){
//                    if (head.next.next.next != null){
//                        //...
//                        System.out.println(head.next.item);
//                    }
//                    System.out.println(head.next.next.item);
//                }
//                System.out.println(head.next.item);
//            }
            System.out.println(head.item);
        }
    }
}




