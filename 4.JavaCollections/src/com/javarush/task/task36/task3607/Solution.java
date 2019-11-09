package com.javarush.task.task36.task3607;


/* 
Найти класс по описанию
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        Class cls = null;
        try {
            cls = Class.forName("java.util.concurrent.DelayQueue");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cls;
    }
}
