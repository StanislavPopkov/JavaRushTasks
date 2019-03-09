package com.javarush.task.task32.task3210;

import java.io.IOException;
import java.io.RandomAccessFile;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) throws IOException {
        String fileName = args[0];
        long number = Long.parseLong(args[1]);
        String str = args[2];

        try (RandomAccessFile file = new RandomAccessFile(fileName, "rw")) {
            byte[] mass = new byte[str.length()];
            file.seek(number);
            file.read(mass, 0, mass.length);
            String textFile = new String(mass);
            String toFile = textFile.equals(str) ? "true" : "false";
            file.seek(file.length());
            file.write(toFile.getBytes());
        }
    }

}
