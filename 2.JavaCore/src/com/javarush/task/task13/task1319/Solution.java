package com.javarush.task.task13.task1319;

import java.io.*;

/* 
Писатель в файл с консоли
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        // напишите тут ваш код
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        String path = buff.readLine();
        FileOutputStream to = new FileOutputStream(path);
        BufferedWriter bw  = new BufferedWriter(new OutputStreamWriter(to));
        String str;
        do{
            str = buff.readLine();
            bw.newLine();
            bw.write(str);
        }while(!str.equals("exit"));
        buff.close();
        bw.close();
    }
}
