package com.javarush.task.task39.task3910;

/* 
isPowerOfThree
*/
public class Solution {
    public static void main(String[] args) {
        Solution.isPowerOfThree(100);
        Solution.isPowerOfThree(3);
        Solution.isPowerOfThree(4);
        Solution.isPowerOfThree(9);

    }

    public static boolean isPowerOfThree(int n) {
        double d = Math.log(n)/Math.log(3);
        double v = d % 1;
        if (Double.compare(v, 0.0) == 0) {
            return true;
        }
        return false;
    }
}
