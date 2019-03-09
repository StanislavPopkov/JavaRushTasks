package com.javarush.task.task25.task2512;

import java.util.ArrayList;
import java.util.List;

/*
Живем своим умом
*/
public class Solution implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        t.interrupt();
        List<Throwable> list = new ArrayList<>();
        Throwable y = e;
        while( y != null){
            list.add(y);
            y = y.getCause();
        }
        for(int i = list.size()-1; i >= 0; i--){
            System.out.println(list.get(i));
        }
        /*
        try{
            uncaughtException(t, e.getCause());
            System.out.println(e);
        }catch (NullPointerException i){
            t.interrupt();
        }*/



    }

    public static void main(String[] args) {
        new Solution().uncaughtException(Thread.currentThread(), new Exception("ABC", new RuntimeException("DEF", new IllegalAccessException("GHI"))));
    }
}

//System.out.println(e.getCause().getCause());
//System.out.println(e.getCause());