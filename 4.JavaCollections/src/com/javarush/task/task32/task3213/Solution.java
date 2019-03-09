package com.javarush.task.task32.task3213;

import java.io.IOException;
import java.io.StringReader;

/* 
Шифр Цезаря
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        StringReader reader = new StringReader("Khoor#Dpljr#&C,₷B'3");
        System.out.println(decode(reader, -3));  //Hello Amigo #@)₴?$0
    }

    public static String decode(StringReader reader, int key) throws IOException {
        if(reader == null) return new String();
        StringBuilder buld = new StringBuilder();
        System.out.println(reader.ready());
        int r;
        while((r = reader.read()) != -1){
            buld.append((char)(r +key));
        }
        return buld.toString();

    }
}
