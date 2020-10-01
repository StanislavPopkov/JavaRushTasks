package com.javarush.task.task39.task3909;

import java.util.*;

/* 
Одно изменение
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(isOneEditAway("", "")); // true
        System.out.println(isOneEditAway("", "m")); //true
        System.out.println(isOneEditAway("m", "")); //true

        System.out.println("------");
        System.out.println(isOneEditAway("mama", "ramas")); //false
        System.out.println(isOneEditAway("mamas", "rama")); //false
        System.out.println(isOneEditAway("rama", "mama")); //true
        System.out.println(isOneEditAway("mama", "dama")); //true
        System.out.println(isOneEditAway("ama", "mama"));  //true
        System.out.println(isOneEditAway("mama", "ama"));  //true
        System.out.println(isOneEditAway("01", "12")); //false
        System.out.println(isOneEditAway("01", "102")); //false
        System.out.println(isOneEditAway("mam", "mama")); //false
    }

    public static boolean isOneEditAway(String first, String second) {
        if (first.equals(second)) {
            return true;
        }
        if (first.isEmpty() || second.isEmpty()) {
            return true;
        }
        if (Math.abs(first.length() - second.length()) > 1) {
            return false;
        }
        char[] charsFirst = first.toCharArray();
        char[] charsSecond = second.toCharArray();
        int count = 0;
        if (charsFirst.length > charsSecond.length) {
            count = getCount(charsFirst, charsSecond);
        } else  if (charsFirst.length < charsSecond.length) {
            count = getCount(charsSecond, charsFirst);
        } else {
            count = getCount2(charsFirst, charsSecond);
        }
        if (count <= 1) {
            return true;
        } else {
            return false;
        }
    }

    private static int getCount2(char[] charsFirst, char[] charsSecond) {
        int count = 0;
        for (int i = 0, j = 0;  i < charsFirst.length && j < charsSecond.length; i++, j++) {
            if (charsFirst[i] != charsSecond[j]) {
                count++;
            }
        }
        return count;
    }

    private static int getCount(char[] charsFirst, char[] charsSecond) {
        int count = 0;
        for (int i = 0, j = 0;  i < charsFirst.length && j < charsSecond.length; i++, j++) {
            if (charsFirst[i] != charsSecond[j]) {
                count++;
                i++;
                if (i >= charsFirst.length ) {
                    return count;
                }
                if (charsFirst[i] != charsSecond[j]) {
                    count++;
                    return count;
                } else {
                    i--;
                    j--;
                }
            }
        }
        return count;
    }
}
