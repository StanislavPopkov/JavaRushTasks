package com.javarush.task.task39.task3908;

import java.util.*;

/*
Возможен ли палиндром?
*/
public class Solution {
    public static void main(String[] args) {
        String tenet = "tenet";
        String abcaacb = "abcaacb";
        String abcaacbabcaacb = "abcaacbabcaacb";
        String aaavvv = "aaavvv";
        String saippuakauppias = "saippuakauppias";
        String PiWpWoi = "PiWpWoi";
        System.out.println(isPalindromePermutation(PiWpWoi));
    }

    public static boolean isPalindromePermutation(String s) {
        if (s == null || s.isEmpty()) return false;
        String s1 = s.toLowerCase();
        char[] chars = s1.toCharArray();
        List<Character> characterList = new ArrayList<>();
        Set<Character> s3 = new HashSet<>();
        for (char aChar : chars) {
            characterList.add(aChar);
            s3.add(aChar);
        }
        int odd = 0;
        for (Character character : s3) {
            long count = characterList.stream().filter(character1 -> character == character1).count();
            if (count%2 != 0) {
                odd++;
            }
        }
        if (odd <= 1) return true;
        else return false;
    }
}
