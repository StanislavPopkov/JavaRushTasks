package com.javarush.task.task09.task0929;

import java.io.*;

/* 
Обогатим код функциональностью!
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        String str1 = "I love Java";

        String str2 = str1;//обе переменные-ссылки указывают на одну строку.
        System.out.println(str2);

        str1 = "I love Python";//но поведение str1 никак не влияет на str2
        System.out.println(str2);//str2 продолжает указывать на строку "I love Java", хотя str1 уже указывает на другой объект
    }
}

