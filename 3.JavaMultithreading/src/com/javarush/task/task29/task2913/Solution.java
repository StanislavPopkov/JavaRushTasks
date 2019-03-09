package com.javarush.task.task29.task2913;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/* 
Замена рекурсии
*/

public class Solution {
    private static int numberA;
    private static int numberB;

    public static String getAllNumbersBetween(int a, int b) {
        StringBuilder strBuld = new StringBuilder();
        if (a > b) {
            List<String> list = new ArrayList<>();
            for(int i = a; i >=b; i-- ){
                list.add(String.valueOf(i));
            }

            for(String str : list){
                strBuld.append(str).append(" ");
            }
            String listString = String.join(" ", list);
            //myList.toString().replaceAll("\\[|\\]", "").replaceAll(", ","\t")
            System.out.println(listString);

        } else {
            List<String> list = new ArrayList<>();
            for(int i = a; i <=b; i++ ){
                list.add(String.valueOf(i));
            }

            for(String str : list){
                strBuld.append(str).append(" ");
            }

        }
        return strBuld.toString().trim();
    }

    public static void main(String[] args) {
        Random random = new Random();
        numberA = random.nextInt(100);
        numberB = random.nextInt(1000);
        System.out.println(getAllNumbersBetween(numberA, numberB));
        System.out.println(getAllNumbersBetween(numberB, numberA));
    }
}