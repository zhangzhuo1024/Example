package com.example.zz.example.java;



import android.util.Log;

import com.example.zz.example.datastructures.TreeNodeDemo;

import java.util.Scanner;
import java.util.TreeSet;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/5/12
 */
public class DemoTest {
    public static void main(String[] args) {

        testIntMove(2);

        明明随机数();
        String s = "";
        s.substring(1);
        int[] ints = {1, -2, 3, 10, -4, 7, 2, -5};
        FindGreatestSumOfSubArray(ints);

    }

    private static void testIntMove(int seq) {
        byte[] data2 = new byte[2];
        data2[0] = 1;
        data2[1] = 1;
        byte[] data3 = new byte[2];
        data3[0] = 1;
        data3[1] = 1;

        System.out.println(data2 == data3);
        System.out.println(data2.equals(data3));


        byte[] data = new byte[6];
        int offset = 0;
        data[offset++] = '$';
        data[offset++] = 0x11;
        data[offset++] = (byte) seq;
        data[offset++] = (byte) (seq >> 8);
        data[offset++] = (byte) (seq >> 16);
        data[offset++] = (byte) (seq >> 24);

        for (byte datum : data) {
            System.out.println(datum);
        }
    }


    private static void 明明随机数() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            TreeSet<Integer> out = new TreeSet<>();
            Integer size = Integer.valueOf(scanner.next());
            for (int i = 0; i < size; i++) {
                out.add(Integer.valueOf(scanner.next()));
            }
            out.forEach( x-> System.out.println(x));
        }
    }

    public static int TreeDepth(TreeNode root) {
        if(root==null){
            return 0;
        } 
        int left=TreeDepth(root.left);
        int right=TreeDepth(root.right);
        return Math.max(left,right)+1;
    }

    public static int FindGreatestSumOfSubArray(int[] array) {

        int result = 0;
        int length = array.length;
        for(int i = 0; i< length; i++){
            for(int j = i+1; j < length; j++){
                int temp = 0;
                for(int k = i; k < j; k++){
                    temp += array[k];
                }
                if(result<temp){
                    result = temp;
                }
            }
        }
        return result;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
}
