package com.javarush.task.task32.task3201;

import java.io.IOException;
import java.io.RandomAccessFile;


/*
Запись в существующий файл
*/
public class Solution {
    public static void main(String... args) throws IOException {
        String[] mass = args;
        RandomAccessFile random = new RandomAccessFile(args[0], "rw");
        if(Long.parseLong(args[1]) < random.length()) random.seek(Long.parseLong(args[1]));
        else random.seek(random.length());

        random.write(args[2].getBytes());
        random.close();
    }
}
