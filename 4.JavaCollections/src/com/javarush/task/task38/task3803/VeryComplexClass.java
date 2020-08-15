package com.javarush.task.task38.task3803;

/* 
Runtime исключения (unchecked exception)
*/

public class VeryComplexClass {
    public void methodThrowsClassCastException() {
        Object object = "";
        Integer integer = (Integer) object ;
    }

    public void methodThrowsNullPointerException() {
        String nullString = null;
        nullString.charAt(1);
    }

    public static void main(String[] args) {

    }
}
