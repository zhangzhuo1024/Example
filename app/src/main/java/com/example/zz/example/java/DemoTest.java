package com.example.zz.example.java;


import com.example.zz.example.datastructures.TreeNodeDemo;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/5/12
 */
public class DemoTest {
    public static void main(String[] args) {




        int[] ints = {1, -2, 3, 10, -4, 7, 2, -5};
        FindGreatestSumOfSubArray(ints);

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
