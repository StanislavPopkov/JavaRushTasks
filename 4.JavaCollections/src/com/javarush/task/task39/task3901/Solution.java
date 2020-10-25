package com.javarush.task.task39.task3901;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/* 
Уникальные подстроки
*/
public class Solution {
    public static void main(String[] args) throws IOException {
/*        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter your string: ");
        String s = bufferedReader.readLine();*/
        String a123bcbcqwe = "a123bcbcqwe";
        String ttttwt = "ttttwt";
        String abc = "abc";
        String a123bcbcqwelmnopq = "a123bcbcqwelmnopq";
        String abcdeaouiz = "abcdeaouiz";
        System.out.println("The longest unique substring length is: \n" + lengthOfLongestUniqueSubstring(a123bcbcqwelmnopq));
    }

    public static int lengthOfLongestUniqueSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        Set<Character> hashSet = new HashSet<>();
        char[] chars = s.toCharArray();
        int max = 0;
        for (int i = 0; i < chars.length; i++) {
            if (hashSet.size() > max) {
                max = hashSet.size();
            }
            hashSet.clear();
            for (int j = i; j < chars.length; j++) {
                char aChar = chars[j];
                if (!hashSet.contains(aChar)) {
                    hashSet.add(aChar);
                } else {
                    break;
                }
            }
        }
        return hashSet.size() > max ? hashSet.size() : max;
    }


}
