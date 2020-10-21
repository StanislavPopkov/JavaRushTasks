package com.javarush.task.task39.task3904;


/* 
Лестница
*/
public class Solution {
    private static int n = 2;

    public static void main(String[] args) {
        System.out.println("The number of possible ascents for " + n + " steps is: " + numberOfPossibleAscents(n));
    }

    public static long numberOfPossibleAscents(int n) {
        long one = 1;
        long two = 2;
        long three = 4;
        long result = 1;
        if (n < 0) return 0;
        else if (n == 2) return two;
        else if (n == 3) return three;
        for (int i = 4; i <= n ; i++) {
            result = one + two + three;
            one = two;
            two = three;
            three = result;
        }{

        }
        return result;
    }

    private static long number(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 0) {
            return 1;
        }
        long summ = n;
        long summ3 = n / 3;
        long i3 = n % 3;
        if (i3 == 0) {
            summ += summ3;
        }
        long summ2 = n / 2;
        long i2 = n % 2;
        if (i2 == 0) {
            summ += summ2;
        }
        if (n > 3) {
            summ += (n / 3) + (n % 3);
        }
        if (n > 2) {
            summ += (n / 2) + (n % 2);
        }
        if (n > 3) {
            summ += possible3plus2(n);
        }
        //summ += optimumPossible(n);
        return summ;
    }

    private static long possible3plus2(int n) {
        long summ = n / 3;
        int i3 = n % 3;
        if (i3 != 0) {
            summ += i3 / 2;
            int i2 = i3 % 2;
            if (i2 == 0) {
                return summ;
            }
        }
        return 0;
    }

    private static long optimumPossible(int n) {
        long summ = n / 3;
        int i3 = n % 3;
        if (i3 == 0) {
            return summ;
        } else {
            summ += i3 / 2;
            int i2 = i3 % 2;
            if (i2 == 0) {
                return summ;
            } else {
                return summ + 1;
            }
        }
    }
}

