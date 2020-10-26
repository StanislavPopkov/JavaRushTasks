package com.javarush.task.task39.task3902;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
Биты были биты
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        long l = 00101010;
        String result = isWeightEven(l) ? "even" : "odd";
        System.out.println("The entered number has " + result + "ones");

    }

    public static boolean isWeightEven(long number) {
/*        if(number==0) return false;
        int count = 0;
        for (int i = 0; i <64 ; i++) {
            number =number>>1;
            if((number&1)==1) {
                count++;
            }
        }*/
        int count = Long.bitCount(number);
        return (count&1)==0;
    }
}
