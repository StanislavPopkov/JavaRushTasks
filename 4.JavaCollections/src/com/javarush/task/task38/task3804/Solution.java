package com.javarush.task.task38.task3804;

/* 
Фабрика исключений
*/

public class Solution {
    public static Class getFactoryClass() {
        return FactoryExeption.class;
    }

    public static void main(String[] args) {
        try {
            Throwable exception = FactoryExeption.getException(ApplicationExceptionMessage.SOCKET_IS_CLOSED);
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }
}