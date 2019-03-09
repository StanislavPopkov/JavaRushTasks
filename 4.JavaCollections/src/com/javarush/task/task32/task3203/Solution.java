package com.javarush.task.task32.task3203;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/*
Пишем стек-трейс
*/
public class Solution {
    public static void main(String[] args) {
        String text = getStackTrace(new IndexOutOfBoundsException("fff"));
        System.out.println(text);
    }

    public static String getStackTrace(Throwable throwable) {
        String stack = null;
        try (Writer writer = new StringWriter()) {
            throwable.printStackTrace(new PrintWriter(writer));
            stack = writer.toString();
        } catch (IOException e){
            e.getMessage();
        }
        return stack;



    }
}